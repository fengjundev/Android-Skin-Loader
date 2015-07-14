package cn.feng.skin.demo.activity;

import java.io.File;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import cn.feng.skin.demo.R;
import cn.feng.skin.manager.listener.ILoaderListener;
import cn.feng.skin.manager.listener.ISkinUpdate;
import cn.feng.skin.manager.loader.SkinInflaterFactory;
import cn.feng.skin.manager.loader.SkinManager;

public class MainActivity extends Activity implements ISkinUpdate{
	
	private static final String 	SKIN_NAME = "SkinPackage.skin";
	private static final String 	SKIN_DIR  = Environment.getExternalStorageDirectory() + File.separator + SKIN_NAME;
	
	private RelativeLayout 			rootLayout;
	private RelativeLayout 			titleBar;
	private Button 					editSkinBtn;
	private Button 					defaultSkinBtn;
	
	
	SkinInflaterFactory mSkinInflaterFactory;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		mSkinInflaterFactory = new SkinInflaterFactory();
		getLayoutInflater().setFactory(mSkinInflaterFactory);
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		initView();
		initTheme();
	}

	private void initTheme() {
		if(SkinManager.getInstance(this).getResources() != null){
			onThemeUpdate();
		}else{
			Log.d("yzy", "no resource");
		}
	}

	private void initView() {
		rootLayout = (RelativeLayout) findViewById(R.id.root_layout);
		titleBar = (RelativeLayout) findViewById(R.id.title_bar_layout);
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
		SkinManager.getInstance(this).restoreDefaultTheme();
	}

	@Override
	protected void onResume() {
		super.onResume();
		
		SkinManager.getInstance(this).attach(this);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		
		SkinManager.getInstance(this).detach(this);
	}

	private void onSkinSetClick() {
		File skin = new File(SKIN_DIR);

		SkinManager.getInstance(MainActivity.this).load(
				skin.getAbsolutePath(), new ILoaderListener() {
					@Override
					public void onStart() {
						Log.d("yzy", "startloadSkin");
					}

					@Override
					public void onSuccess() {
						Log.d("yzy", "loadSkinSuccess");
					}

					@Override
					public void onFailed() {
						Log.d("yzy", "loadSkinFail");
					}
				});
	}

	@Override
	public void onThemeUpdate() {
		if(rootLayout == null) return;
		SkinManager mSkinManager = SkinManager.getInstance(this);
		rootLayout.setBackgroundDrawable(mSkinManager.getDrawable(R.drawable.app_bg_image));
		titleBar.setBackgroundColor(mSkinManager.getColor(R.color.title_bar_bg));
		editSkinBtn.setBackgroundColor(mSkinManager.getColor(R.color.app_btn_bg_color));
		defaultSkinBtn.setBackgroundColor(mSkinManager.getColor(R.color.app_btn_bg_color));
	}
}
