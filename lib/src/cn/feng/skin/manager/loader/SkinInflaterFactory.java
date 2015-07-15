package cn.feng.skin.manager.loader;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.res.Resources.NotFoundException;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.LayoutInflater.Factory;
import android.view.View;
import cn.feng.skin.manager.config.SkinConfig;
import cn.feng.skin.manager.entity.AttrFactory;
import cn.feng.skin.manager.entity.SkinAttr;
import cn.feng.skin.manager.entity.SkinItem;
import cn.feng.skin.manager.util.L;
import cn.feng.skin.manager.util.ListUtils;

/**
 * Supply {@link SkinInflaterFactory} to be called when inflating from a LayoutInflater.
 * 
 * <p>Use this to collect the {skin:enable="true|false"} views availabled in our XML layout files.
 * 
 * @author fengjun
 */
public class SkinInflaterFactory implements Factory {
	private static final boolean  DEBUG 					= true;
	private static final String   ANDROID_VIEW_PREFIX 	= "android.view.";
	private static final String   ANDROID_WIDGET_PREFIX 	= "android.widget.";
	private static final String   ANDROID_WEBKIT_PREFIX 	= "android.webkit.";
	
	/**
	 * Store the view item that need skin changing in the activity
	 */
	private List<SkinItem> mSkinItems = new ArrayList<SkinItem>();
	
	@Override
	public View onCreateView(String name, Context context, AttributeSet attrs) {
		
		long start;
        if (DEBUG) {
        	start = System.currentTimeMillis();
        }
		
		View view = null;
		if (name.lastIndexOf('.') == -1) {
			if ("View".equals(name)) {
				view = createView(context, name, ANDROID_VIEW_PREFIX, attrs);
			}
			if (view == null) {
				view = createView(context, name, ANDROID_WIDGET_PREFIX, attrs);
			}
			if (view == null) {
				view = createView(context, name, ANDROID_WEBKIT_PREFIX, attrs);
			}
		} else {
			view = createView(context, name, null, attrs);
		}
		
		if (view == null){
			return null;
		}
		
		// if this is NOT enable to be skined , simplly skip it 
		boolean isSkinEnable = attrs.getAttributeBooleanValue(SkinConfig.NAMESPACE, SkinConfig.ATTR_SKIN_ENABLE, false);
        if (!isSkinEnable){
        	return view;
        }
		
		parseSkinAttr(context, attrs, view);
		
		if (DEBUG) {
			L.e("_________________________________________________________");
    		L.e("onCreateView cost " + (System.currentTimeMillis() - start) + " ms"); 
        }
		
		return view;
	}

	/**
	 * Collect skin able tag such as background , textColor and so on
	 * 
	 * @param context
	 * @param attrs
	 * @param view
	 */
	private void parseSkinAttr(Context context, AttributeSet attrs, View view) {
		List<SkinAttr> viewAttrs = new ArrayList<SkinAttr>();
		
		for (int i = 0; i < attrs.getAttributeCount(); i++){
			String attrName = attrs.getAttributeName(i);
			String attrValue = attrs.getAttributeValue(i);
			//Log.w("attr", "[attrName] = " + attrName + " [attrValue] = " + attrValue);
			
			if(!"background".equals(attrName)) continue;
			
		    if(attrValue.startsWith("@")){
		    	try{ 
		    		int id = Integer.parseInt(attrValue.substring(1));
		    		String entryName = context.getResources().getResourceEntryName(id);
		    		String typeName = context.getResources().getResourceTypeName(id);
		    		context.getResources().getResourceTypeName(id);
		    		SkinAttr mSkinAttr = AttrFactory.get(attrName, id, entryName, typeName);
		    		if(mSkinAttr != null){
		    			viewAttrs.add(mSkinAttr);
		    			
		    			if(DEBUG){ 
				    		L.i("_________________________________________________________");
				    		L.w(mSkinAttr.toString()); 
				    	}
		    		}
		    	}catch(NumberFormatException e){
		    		e.printStackTrace();
		    	}catch(NotFoundException e){
		    		e.printStackTrace();
		    	}
		    }
		}
		
		if(!ListUtils.isEmpty(viewAttrs)){
			SkinItem skinItem = new SkinItem();
			skinItem.view = view;
			skinItem.attrs = viewAttrs;
			mSkinItems.add(skinItem);
			
			if(SkinManager.getInstance().isExternalSkin()){
				long start;
				if(DEBUG){ 
					start = System.currentTimeMillis();
				}
				
				skinItem.apply();
				
				if (DEBUG) {
					L.e("_________________________________________________________");
		    		L.e("apply skin cost " + (System.currentTimeMillis() - start) + " ms"); 
		        }
			}
		}
	}
	
	/**
     * Invoke low-level function for instantiating a view by name. This attempts to
     * instantiate a view class of the given <var>name</var> found in this
     * LayoutInflater's ClassLoader.
     * 
     * @param context 
     * @param name The full name of the class to be instantiated.
     * @param attrs The XML attributes supplied for this instance.
     * 
     * @return View The newly instantiated view, or null.
     */
	private static View createView(Context context, String name, String prefix, AttributeSet attrs) {
		View view = null;
		try {
			view = LayoutInflater.from(context).createView(name, prefix, attrs);
		} catch (Exception e) { }
		return view;
	}
	
	public void applySkin(){
		if(ListUtils.isEmpty(mSkinItems)) return;
		
		for(SkinItem si : mSkinItems){
			si.apply();
		}
	}
}
