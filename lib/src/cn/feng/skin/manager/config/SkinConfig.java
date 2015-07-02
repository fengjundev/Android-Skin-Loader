package cn.feng.skin.manager.config;

import android.content.Context;
import cn.feng.skin.manager.util.PreferencesUtils;

public class SkinConfig {

	public 	static final String 	SKIN_SUFFIX				= 	".theme";
	public 	static final String 	SKIN_FOLER_NAME 		= 	"skin";
	public 	static final String 	PREF_CUSTOM_SKIN_PATH 	= 	"cn_feng_skin_custom_path";
	public  static final String 	DEFALT_SKIN 			= 	"cn_feng_skin_default";
	
	/**
	 * get path of last skin package path
	 * @param context
	 * @return path of skin package
	 */
	public static String getCustomSkinPath(Context context){
		return PreferencesUtils.getString(context, PREF_CUSTOM_SKIN_PATH, DEFALT_SKIN);
	}
	
	public static void saveSkinPath(Context context, String path){
		PreferencesUtils.putString(context, PREF_CUSTOM_SKIN_PATH, path);
	}
	
	public static boolean isDefaultSkin(Context context){
		return DEFALT_SKIN.equals(getCustomSkinPath(context));
	}
}
