package cn.feng.skin.demo.activity;

import java.io.File;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import cn.feng.skin.demo.R;
import cn.feng.skin.manager.base.BaseActivity;
import cn.feng.skin.manager.listener.ILoaderListener;
import cn.feng.skin.manager.loader.SkinManager;
import cn.feng.skin.manager.util.L;

public class SettingActivity extends BaseActivity {

	/**
	 * Put this skin file on the root of sdcard 
	 * /mnt/sdcard/BlackFantacy.skin
	 */
	private static final String SKIN_NAME = "BlackFantacy.skin";
	private static final String SKIN_DIR = Environment
			.getExternalStorageDirectory() + File.separator + SKIN_NAME;
	
	
	private TextView titleText;
	private Button setOfficalSkinBtn;
	private Button setNightSkinBtn;
	
	private boolean isOfficalSelected = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);

		initView();
	}

	private void initView() {
		titleText = (TextView) findViewById(R.id.title_text);
		titleText.setText("设置皮肤");
		setOfficalSkinBtn = (Button) findViewById(R.id.set_default_skin);
		setNightSkinBtn = (Button) findViewById(R.id.set_night_skin);
		
		
		isOfficalSelected = !SkinManager.getInstance().isExternalSkin();
		
		if(isOfficalSelected){
			setOfficalSkinBtn.setText("官方默认(当前)");
			setNightSkinBtn.setText("黑色幻想");
		}else{
			setNightSkinBtn.setText("黑色幻想(当前)");
			setOfficalSkinBtn.setText("官方默认");			
		}
		
		setNightSkinBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				onSkinSetClick();
			}
		});
		
		setOfficalSkinBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				onSkinResetClick();
			}
		});
	}

	protected void onSkinResetClick() {
		if(!isOfficalSelected){
			SkinManager.getInstance().restoreDefaultTheme();
			Toast.makeText(getApplicationContext(), "切换成功", Toast.LENGTH_SHORT).show();			
			setOfficalSkinBtn.setText("官方默认(当前)");
			setNightSkinBtn.setText("黑色幻想");
			isOfficalSelected = true;
		}
	}

	private void onSkinSetClick() {
		if(!isOfficalSelected) return;
		
		File skin = new File(SKIN_DIR);

		SkinManager.getInstance().load(skin.getAbsolutePath(),
				new ILoaderListener() {
					@Override
					public void onStart() {
						L.e("startloadSkin");
					}

					@Override
					public void onSuccess() {
						L.e("loadSkinSuccess");
						Toast.makeText(getApplicationContext(), "切换成功", Toast.LENGTH_SHORT).show();
						setNightSkinBtn.setText("黑色幻想(当前)");
						setOfficalSkinBtn.setText("官方默认");		
						isOfficalSelected = false;
					}

					@Override
					public void onFailed() {
						L.e("loadSkinFail");
						Toast.makeText(getApplicationContext(), "切换失败", Toast.LENGTH_SHORT).show();
					}
				});
	}
}
