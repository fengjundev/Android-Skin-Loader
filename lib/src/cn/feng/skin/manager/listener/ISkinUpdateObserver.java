package cn.feng.skin.manager.listener;

import android.content.res.Resources;

/**
 * Call back when theme has changed </br>
 * Normally implements by activity of fragment
 * 
 * @author fengjun
 */
public interface ISkinUpdateObserver {
	void onThemeUpdate(String skinPackageName, Resources skinResources);	
}
