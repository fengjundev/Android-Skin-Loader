package cn.feng.skin.manager.loader;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import android.annotation.TargetApi;
import android.content.res.AssetManager;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Drawable.ConstantState;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.LongSparseArray;
import android.util.TypedValue;

public class SkinResources extends Resources {

	private final LongSparseArray<WeakReference<Drawable.ConstantState>> mDrawableCache = new LongSparseArray<WeakReference<Drawable.ConstantState>>(0);
	private final LongSparseArray<WeakReference<ColorStateList>> mColorStateListCache = new LongSparseArray<WeakReference<ColorStateList>>(0);
	private final LongSparseArray<WeakReference<Drawable.ConstantState>> mColorDrawableCache = new LongSparseArray<WeakReference<Drawable.ConstantState>>(0);

	private final Object synchronizedLock = new Object();
	private TypedValue mTmpValue = new TypedValue();

	public SkinResources(AssetManager assets, DisplayMetrics metrics, Configuration config) {
		super(assets, metrics, config);
	}

	@Override
	public int getColor(int id) throws NotFoundException {
		TypedValue value;
		synchronized (synchronizedLock) {
			value = mTmpValue;
			if (value == null) {
				value = new TypedValue();
			}
			getValue(id, value, true);
			if (value.type >= TypedValue.TYPE_FIRST_INT
					&& value.type <= TypedValue.TYPE_LAST_INT) {
				mTmpValue = value;
				return value.data;
			} else if (value.type != TypedValue.TYPE_STRING) {
				throw new NotFoundException("Resource ID #0x"
						+ Integer.toHexString(id) + " type #0x"
						+ Integer.toHexString(value.type) + " is not valid");
			}
			mTmpValue = null;
		}
		ColorStateList csl = loadColorStateList(value, id);
		synchronized (synchronizedLock) {
			if (mTmpValue == null) {
				mTmpValue = value;
			}
		}
		return csl.getDefaultColor();
	}

	@Override
	public ColorStateList getColorStateList(int id) throws NotFoundException {
		TypedValue value;
		synchronized (synchronizedLock) {
			value = mTmpValue;
			if (value == null) {
				value = new TypedValue();
			} else {
				mTmpValue = null;
			}
			getValue(id, value, true);
		}
		ColorStateList res = loadColorStateList(value, id);
		synchronized (synchronizedLock) {
			if (mTmpValue == null) {
				mTmpValue = value;
			}
		}
		return res;
	}

	private ColorStateList loadColorStateList(TypedValue value, int id) throws NotFoundException {
		final long key = (((long) value.assetCookie) << 32) | value.data;
		ColorStateList csl;
		if (value.type >= TypedValue.TYPE_FIRST_COLOR_INT && value.type <= TypedValue.TYPE_LAST_COLOR_INT) {
			csl = ColorStateList.valueOf(value.data);
			return csl;
		}
		csl = getCachedColorStateList(key);
		if (csl != null) {
			return csl;
		}
		if (value.string == null) {
			throw new NotFoundException("Resource is not a ColorStateList (color or path): " + value);
		}
		String file = value.string.toString();
		if (file.endsWith(".xml")) {
			try {
				XmlResourceParser rp = loadXmlResourceParserReflect(file, id, value.assetCookie, "colorstatelist");
				if (rp == null) {
					rp = loadXmlResourceParserEqual(file, id, value.assetCookie, "colorstatelist");
				}
				csl = ColorStateList.createFromXml(this, rp);
				rp.close();
			} catch (Exception e) {
				NotFoundException rnf = new NotFoundException("File " + file + " from color state list resource ID #0x" + Integer.toHexString(id));
				rnf.initCause(e);
				throw rnf;
			}
		} else {
			throw new NotFoundException("File " + file + " from drawable resource ID #0x" + Integer.toHexString(id) + ": .xml extension required");
		}
		if (csl != null) {
			synchronized (synchronizedLock) {
				mColorStateListCache.put(key, new WeakReference<ColorStateList>(csl));
			}
		}
		return csl;
	}

	private ColorStateList getCachedColorStateList(long key) {
		synchronized (synchronizedLock) {
			WeakReference<ColorStateList> wr = mColorStateListCache.get(key);
			if (wr != null) {
				ColorStateList entry = wr.get();
				if (entry != null) {
					return entry;
				} else {
					mColorStateListCache.delete(key);
				}
			}
		}
		return null;
	}

	@Override
	public Drawable getDrawable(int id) throws NotFoundException {
		TypedValue value;
		synchronized (synchronizedLock) {
			value = mTmpValue;
			if (value == null) {
				value = new TypedValue();
			} else {
				mTmpValue = null;
			}
			getValue(id, value, true);
		}
		Drawable res = loadDrawable(value, id);
		synchronized (synchronizedLock) {
			if (mTmpValue == null) {
				mTmpValue = value;
			}
		}
		return res;
		// return super.getDrawable(id);
	}

	@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1)
	@Override
	public Drawable getDrawableForDensity(int id, int density) throws NotFoundException {
		TypedValue value;
		synchronized (synchronizedLock) {
			value = mTmpValue;
			if (value == null) {
				value = new TypedValue();
			} else {
				mTmpValue = null;
			}
			getValueForDensity(id, density, value, true);
			DisplayMetrics metrics = getDisplayMetrics();
			if (value.density > 0 && value.density != TypedValue.DENSITY_NONE) {
				if (value.density == density) {
					value.density = metrics.densityDpi;
				} else {
					value.density = (value.density * metrics.densityDpi) / density;
				}
			}
		}
		Drawable res = loadDrawable(value, id);
		synchronized (synchronizedLock) {
			if (mTmpValue == null) {
				mTmpValue = value;
			}
		}
		return res;
		// return super.getDrawableForDensity(id, density);
	}

	private Drawable loadDrawable(TypedValue value, int id) throws NotFoundException {
		boolean isColorDrawable = false;
		if (value.type >= TypedValue.TYPE_FIRST_COLOR_INT && value.type <= TypedValue.TYPE_LAST_COLOR_INT) {
			isColorDrawable = true;
		}
		final long key = isColorDrawable ? value.data : (((long) value.assetCookie) << 32) | value.data;
		Drawable dr = getCachedDrawable(isColorDrawable ? mColorDrawableCache : mDrawableCache, key);
		if (dr != null) {
			return dr;
		}
		if (isColorDrawable) {
			dr = new ColorDrawable(value.data);
		}
		if (dr == null) {
			if (value.string == null) {
				throw new NotFoundException("Resource is not a Drawable (color or path): " + value);
			}
			String file = value.string.toString();
			if (file.endsWith(".xml")) {
				try {
					XmlResourceParser rp = loadXmlResourceParserReflect(file, id, value.assetCookie, "drawable");
					if (rp == null) {
						rp = loadXmlResourceParserEqual(file, id, value.assetCookie, "drawable");
					}
					dr = Drawable.createFromXml(this, rp);
					rp.close();
				} catch (Exception e) {
					NotFoundException rnf = new NotFoundException("File " + file + " from drawable resource ID #0x" + Integer.toHexString(id));
					rnf.initCause(e);
					throw rnf;
				}
			} else {
				try {
					InputStream is = openNonAssetReflect(value.assetCookie, file, AssetManager.ACCESS_STREAMING);
					if (is == null) {
						is = openNonAssetEqual(id);
					}
					dr = Drawable.createFromResourceStream(this, value, is, file, null);
					is.close();
				} catch (Exception e) {
					NotFoundException rnf = new NotFoundException("File " + file + " from drawable resource ID #0x" + Integer.toHexString(id));
					rnf.initCause(e);
					throw rnf;
				}
			}
		}
		if (dr != null) {
			dr.setChangingConfigurations(value.changingConfigurations);
			Drawable.ConstantState cs = dr.getConstantState();
			if (cs != null) {
				synchronized (synchronizedLock) {
					if (isColorDrawable) {
						mColorDrawableCache.put(key, new WeakReference<Drawable.ConstantState>(cs));
					} else {
						mDrawableCache.put(key, new WeakReference<Drawable.ConstantState>(cs));
					}
				}
			}
		}
		return dr;
	}

	private Drawable getCachedDrawable(LongSparseArray<WeakReference<ConstantState>> drawableCache, long key) {
		synchronized (synchronizedLock) {
			WeakReference<Drawable.ConstantState> wr = drawableCache.get(key);
			if (wr != null) {
				Drawable.ConstantState entry = wr.get();
				if (entry != null) {
					return entry.newDrawable(this);
				} else {
					drawableCache.delete(key);
				}
			}
		}
		return null;
	}

	/**
	 * 反射方法
	 */
	private XmlResourceParser loadXmlResourceParserReflect(String file, int id, int assetCookie, String type) {
		try {
			Method method = Resources.class.getMethod("loadXmlResourceParser", new Class[] { String.class, int.class, int.class, String.class });
			method.setAccessible(true);
			return (XmlResourceParser) method.invoke(this, file, id, assetCookie, type);
		} catch (NoSuchMethodException e) {
			// ignore
		} catch (IllegalAccessException e) {
			// ignore
		} catch (IllegalArgumentException e) {
			// ignore
		} catch (InvocationTargetException e) {
			// ignore
		}
		return null;
	}

	/**
	 * 等效方法
	 */
	private XmlResourceParser loadXmlResourceParserEqual(String file, int id, int assetCookie, String type) {
		try {
			XmlResourceParser parser = getAssets().openXmlResourceParser(assetCookie, file);
			return parser;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 反射方法
	 */
	private InputStream openNonAssetReflect(int cookie, String fileName, int accessMode) throws IOException {
		try {
			Method method = AssetManager.class.getMethod("openNonAsset", new Class[] { int.class, String.class, int.class });
			method.setAccessible(true);
			return (InputStream) method.invoke(getAssets(), cookie, fileName, accessMode);
		} catch (NoSuchMethodException e) {
			// ignore
		} catch (IllegalAccessException e) {
			// ignore
		} catch (IllegalArgumentException e) {
			// ignore
		} catch (InvocationTargetException e) {
			// ignore
		}
		return null;
	}

	/**
	 * 等效方法
	 */
	private InputStream openNonAssetEqual(int id) {
		return openRawResource(id);
	}
}
