package cn.feng.skin.manager.loader;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import android.content.Context;
import android.content.res.Resources.NotFoundException;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.LayoutInflater.Factory;
import android.view.View;
import cn.feng.skin.manager.entity.SkinAttr;
import cn.feng.skin.manager.entity.SkinItem;

public class SkinInflaterFactory implements Factory {
	private static final boolean DEBUG 					= true;
	private static final String  ANDROID_VIEW_PREFIX 	= "android.view.";
	private static final String  ANDROID_WIDGET_PREFIX 	= "android.widget.";
	private static final String  ANDROID_WEBKIT_PREFIX 	= "android.webkit.";
	
	private List<SkinItem> mSkinItems = new ArrayList<SkinItem>();
	
	@Override
	public View onCreateView(String name, Context context, AttributeSet attrs) {
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
		if (view == null) {
			return null;
		}
		
		boolean hasSkinTag = false;
		List<SkinAttr> viewAttrs = new ArrayList<SkinAttr>();
		for (int i = 0; i < attrs.getAttributeCount(); i++){
			String attrName = attrs.getAttributeName(i);
			String attrValue = attrs.getAttributeValue(i);
			
		    if(attrValue.startsWith("?")){
		    	SkinAttr mSkinAttr;
		    	try{
		    		int id = Integer.parseInt(attrValue.substring(1));

		    		mSkinAttr = new SkinAttr();
			    	mSkinAttr.attrName = attrName;
			    	mSkinAttr.attrValueRefId = id;
			    	mSkinAttr.attrValueRefName = context.getResources().getResourceEntryName(id);
			    	viewAttrs.add(mSkinAttr);
			    	hasSkinTag = true;
		    	}catch(NumberFormatException e){
		    		mSkinAttr = null;
		    	}catch(NotFoundException e){
		    		mSkinAttr = null;
		    	}
		    	if(DEBUG){
		    		Log.i("attr", "[attrName] : " + attrName + " [attrValue] : " + attrValue); 
			    	int id = Integer.parseInt(attrValue.substring(1));
			    	Log.i("attr", "[resId] : 0x" + Integer.toHexString(id));
			    	String name1 = context.getResources().getResourceEntryName(id);
			    	Log.i("attr", "[typedName] : " + name1);
		    	}
		    }
		}
		if(hasSkinTag && !isListEmpty(viewAttrs)){
			SkinItem skinItem = new SkinItem();
			skinItem.view = view;
			skinItem.attrs = viewAttrs;
		}
		
		return view;
	}
	
	private static View createView(Context context, String name, String prefix, AttributeSet attrs) {
		View view = null;
		try {
			view = LayoutInflater.from(context).createView(name, prefix, attrs);
		} catch (Exception e) { 
			view = null;
		}
		return view;
	}
	
	private boolean isListEmpty(Collection<?> collection){
		return collection == null || collection.size() < 1;
	}
}
