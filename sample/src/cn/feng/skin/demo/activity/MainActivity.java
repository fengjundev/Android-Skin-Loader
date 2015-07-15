package cn.feng.skin.demo.activity;

import java.io.File;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import cn.feng.skin.demo.R;
import cn.feng.skin.manager.base.SkinPluginActivity;
import cn.feng.skin.manager.listener.ILoaderListener;
import cn.feng.skin.manager.loader.SkinManager;
import cn.feng.skin.manager.util.L;

public class MainActivity extends SkinPluginActivity{
	
	private static final String 	SKIN_NAME = "red.skin";
	private static final String 	SKIN_DIR  = Environment.getExternalStorageDirectory() + File.separator + SKIN_NAME;
	
	private Button 					editSkinBtn;
	private Button 					defaultSkinBtn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		initView();
		
		L.e("DONE WITH ON CREATE");
	}

	private void initView() {
		editSkinBtn = (Button) findViewById(R.id.btn);
		defaultSkinBtn = (Button) findViewById(R.id.btn2);
		
		editSkinBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				onSkinSetClick();
			}
		});
		defaultSkinBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				onSkinResetClick();
			}
		});
	}
	
	protected void onSkinResetClick() {
		SkinManager.getInstance().restoreDefaultTheme();
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

	private void onSkinSetClick() {
		File skin = new File(SKIN_DIR);

		SkinManager.getInstance().load(
				skin.getAbsolutePath(), new ILoaderListener() {
					@Override
					public void onStart() {
						Log.d("attr", "startloadSkin");
					}

					@Override
					public void onSuccess() {
						Log.d("attr", "loadSkinSuccess");
					}

					@Override
					public void onFailed() {
						Log.d("attr", "loadSkinFail");
					}
				});
	}
}
