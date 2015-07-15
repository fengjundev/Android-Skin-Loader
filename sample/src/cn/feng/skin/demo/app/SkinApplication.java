package cn.feng.skin.demo.app;

import android.app.Application;
import cn.feng.skin.manager.loader.SkinManager;

public class SkinApplication extends Application {
	
	public void onCreate() {
		super.onCreate();
		
		SkinManager.getInstance().init(this);
		SkinManager.getInstance().load();
	}
}