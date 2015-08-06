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
	
	private static final boolean DEBUG = true;
	
	/**
	 * Store the view item that need skin changing in the activity
	 */
	private List<SkinItem> mSkinItems = new ArrayList<SkinItem>();
	
	@Override
	public View onCreateView(String name, Context context, AttributeSet attrs) {
	
		L.e("tgtg",name); 
		
		// if this is NOT enable to be skined , simplly skip it 
		boolean isSkinEnable = attrs.getAttributeBooleanValue(SkinConfig.NAMESPACE, SkinConfig.ATTR_SKIN_ENABLE, false);
        if (!isSkinEnable){
        	L.e(name + " is Not skin able , skin it !"); 
        	return null;
        }
		
		View view = createView(context, name, attrs);
		
		if (view == null){
			L.e(name + " is NULL !"); 
			return null;
		}
		
		parseSkinAttr(context, attrs, view);
		
		return view;
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
	private View createView(Context context, String name, AttributeSet attrs) {
		long start;
		if(DEBUG){ 
			start = System.currentTimeMillis();
		}
		View view = null;
		try {
			if (-1 == name.indexOf('.')){
				if ("View".equals(name)) {
					view = LayoutInflater.from(context).createView(name, "android.view.", attrs);
				} 
				if (view == null) {
					view = LayoutInflater.from(context).createView(name, "android.widget.", attrs);
				} 
				if (view == null) {
					view = LayoutInflater.from(context).createView(name, "android.webkit.", attrs);
				} 
			}else {
	            view = LayoutInflater.from(context).createView(name, null, attrs);
	        }
		} catch (Exception e) { 
			L.e("error while create " + name);
			L.e(e.getMessage());
		}
		if (DEBUG) {
    		L.e("createView " + name + " cost [" + (System.currentTimeMillis() - start) + "] ms"); 
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
		long start;
		if(DEBUG){ 
			start = System.currentTimeMillis();
		}
		
		List<SkinAttr> viewAttrs = new ArrayList<SkinAttr>();
		
		for (int i = 0; i < attrs.getAttributeCount(); i++){
			String attrName = attrs.getAttributeName(i);
			String attrValue = attrs.getAttributeValue(i);
			
			if(!AttrFactory.isSupportedAttr(attrName)){
				continue;
			}
			
		    if(attrValue.startsWith("@")){
		    	try{ 
		    		int id = Integer.parseInt(attrValue.substring(1));
		    		String entryName = context.getResources().getResourceEntryName(id);
		    		String typeName = context.getResources().getResourceTypeName(id);
		    		context.getResources().getResourceTypeName(id);
		    		SkinAttr mSkinAttr = AttrFactory.get(attrName, id, entryName, typeName);
		    		if(mSkinAttr != null){
		    			viewAttrs.add(mSkinAttr);
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
			if(DEBUG){ 
	    		L.w("successful add a item \n" + viewAttrs.toString()); 
	    	} 
			mSkinItems.add(skinItem);
			
			if(SkinManager.getInstance().isExternalSkin()){
				skinItem.apply();
			}
		}else{
			L.e("attr is empty " + view.getClass().getSimpleName());
		}
		
		if (DEBUG) {
    		L.e("parseSkinAttr " + view.getClass().getSimpleName() + " cost [" + (System.currentTimeMillis() - start) + "] ms"); 
        }
	}
	
	public void applySkin(){
		if(ListUtils.isEmpty(mSkinItems)) return;
		
		for(SkinItem si : mSkinItems){
			if(si.view == null) continue;
			si.apply();
		}
	}
}
