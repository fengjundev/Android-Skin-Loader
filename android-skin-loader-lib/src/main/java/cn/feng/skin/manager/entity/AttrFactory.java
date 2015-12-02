package cn.feng.skin.manager.entity;


public class AttrFactory {
	
	public static final String BACKGROUND = "background";
	public static final String TEXT_COLOR = "textColor";
	public static final String LIST_SELECTOR = "listSelector";
	public static final String DIVIDER = "divider";
	
	public static SkinAttr get(String attrName, int attrValueRefId, String attrValueRefName, String typeName){
		
		SkinAttr mSkinAttr = null;
		
		if(BACKGROUND.equals(attrName)){ 
			mSkinAttr = new BackgroundAttr();
		}else if(TEXT_COLOR.equals(attrName)){ 
			mSkinAttr = new TextColorAttr();
		}else if(LIST_SELECTOR.equals(attrName)){ 
			mSkinAttr = new ListSelectorAttr();
		}else if(DIVIDER.equals(attrName)){ 
			mSkinAttr = new DividerAttr();
		}else{
			return null;
		}
		
		mSkinAttr.attrName = attrName;
		mSkinAttr.attrValueRefId = attrValueRefId;
		mSkinAttr.attrValueRefName = attrValueRefName;
		mSkinAttr.attrValueTypeName = typeName;
		return mSkinAttr;
	}
	
	/**
	 * Check whether the attribute is supported
	 * @param attrName
	 * @return true : supported <br>
	 * 		   false: not supported
	 */
	public static boolean isSupportedAttr(String attrName){
		if(BACKGROUND.equals(attrName)){ 
			return true;
		}
		if(TEXT_COLOR.equals(attrName)){ 
			return true;
		}
		if(LIST_SELECTOR.equals(attrName)){ 
			return true;
		}
		if(DIVIDER.equals(attrName)){ 
			return true;
		}
		return false;
	}
}
