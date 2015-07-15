package cn.feng.skin.manager.base;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import cn.feng.skin.manager.listener.ISkinUpdate;
import cn.feng.skin.manager.loader.SkinInflaterFactory;
import cn.feng.skin.manager.util.L;

/**
 * Base Activity for development
 * 
 * <p>NOTICE:<br> 
 * You should extends from this if you what to do skin change
 * 
 * @author fengjun
 */
public class SkinPluginActivity extends Activity implements ISkinUpdate{
	
	/**
	 * Whether response to skin changing after create
	 */
	private boolean isResponseOnSkinChanging			= true;
	
	private SkinInflaterFactory mSkinInflaterFactory;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mSkinInflaterFactory = new SkinInflaterFactory();
		getLayoutInflater().setFactory(mSkinInflaterFactory);
	}
	
	protected void addNewSkinWidget(View view, String attrName, int attrValueResId){	
	}
	
	final protected void enableResponseOnSkinChanging(boolean enable){
		isResponseOnSkinChanging = enable;
	}

	@Override
	public void onThemeUpdate() {
		if(!isResponseOnSkinChanging) return;
		
		L.e("call onThemeUpdate()"); 
		
		mSkinInflaterFactory.applySkin();
	}
}
