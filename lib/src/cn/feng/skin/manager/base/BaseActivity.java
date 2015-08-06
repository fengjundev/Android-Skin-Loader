package cn.feng.skin.manager.base;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import cn.feng.skin.manager.listener.ISkinUpdate;
import cn.feng.skin.manager.loader.SkinInflaterFactory;
import cn.feng.skin.manager.loader.SkinManager;

/**
 * Base Activity for development
 * 
 * <p>NOTICE:<br> 
 * You should extends from this if you what to do skin change
 * 
 * @author fengjun
 */
public class BaseActivity extends Activity implements ISkinUpdate{
	
	/**
	 * Whether response to skin changing after create
	 */
	private boolean isResponseOnSkinChanging = true;
	
	private SkinInflaterFactory mSkinInflaterFactory;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mSkinInflaterFactory = new SkinInflaterFactory();
		getLayoutInflater().setFactory(mSkinInflaterFactory);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		SkinManager.getInstance().attach(this);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		SkinManager.getInstance().detach(this);
	}
	
	/**
	 * dynamic add a skin view 
	 * 
	 * @param view
	 * @param attrName
	 * @param attrValueResId
	 */
	protected void dynamicAddNewSkinWidget(View view, String attrName, int attrValueResId){	
		// developing ... 
	}
	
	final protected void enableResponseOnSkinChanging(boolean enable){
		isResponseOnSkinChanging = enable;
	}

	@Override
	public void onThemeUpdate() {
		if(!isResponseOnSkinChanging) return;
		mSkinInflaterFactory.applySkin();
	}
}
