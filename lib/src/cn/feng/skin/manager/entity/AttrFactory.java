package cn.feng.skin.manager.entity;

public class AttrFactory {
	
	private static final String BACKGROUND = "background";
	
	public static SkinAttr get(String attrName, int attrValueRefId, String attrValueRefName, String typeName){
		SkinAttr mSkinAttr = null;
		if(BACKGROUND.equals(attrName)){ 
			mSkinAttr = new BackgroundAttr();
			mSkinAttr.attrName = attrName;
			mSkinAttr.attrValueRefId = attrValueRefId;
			mSkinAttr.attrValueRefName = attrValueRefName;
			mSkinAttr.attrValueTypeName = typeName;
		}
		return mSkinAttr;
	}
}
