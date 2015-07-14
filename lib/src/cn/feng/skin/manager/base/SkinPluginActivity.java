package cn.feng.skin.manager.base;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import cn.feng.skin.manager.listener.ISkinUpdate;

/**
 * Base Activity for development
 * 
 * <p>You should extends from this if you what to do skin changging
 * 
 * @author fengjun
 */
public class SkinPluginActivity extends Activity implements ISkinUpdate{
	
	/**
	 * Whether response to skin changing after create
	 */
	private boolean isResponseOnSkinChanging			= true;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	}
	
	
	protected void addNewSkinWidget(View view, String attrName, int attrValueResId){
		
	}
	
	final protected void enableResponseOnSkinChanging(boolean enable){
		isResponseOnSkinChanging = enable;
	}

	@Override
	public void onThemeUpdate() {
		
	}
}
