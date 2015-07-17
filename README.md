# Android-Skin-Loader

> Contact me : fengjun.dev@gmail.com

---

> A skin manager load skin from local file dynamically.  
> Now support background , text color , list divider and list selector

---

## Usage

1. init skin loader in `Application`, must call `SkinManager.getInstance().init(this);` first 

    ```java
    public class SkinApplication extends Application {
	
	public void onCreate() {
		super.onCreate();
		
		initSkinLoader();
	}

	/**
	 * Must call init first
	 */
	private void initSkinLoader() {
		SkinManager.getInstance().init(this);
		SkinManager.getInstance().load();
	}
}
    ```

2. tag the view that need to change skin in layout xml files, with `skin:enable="true"` , remember the namespace `xmlns:skin="http://schemas.android.com/android/skin"`

3. Extend all your Acticity from `SkinPluginActivity`


4. Set a custom skin from `.skin` file
    ```java
String SKIN_NAME = "BlackFantacy.skin";
String SKIN_DIR = Environment.getExternalStorageDirectory() + File.separator + SKIN_NAME;
File skin = new File(SKIN_DIR);
SkinManager.getInstance().load(skin.getAbsolutePath(),
				new ILoaderListener() {
					@Override
					public void onStart() {
					}

					@Override
					public void onSuccess() {
					}

					@Override
					public void onFailed() {
					}
				});
    ```

5. Restore to default skin

    ```java
    SkinManager.getInstance().restoreDefaultTheme();
    ```

---

## Sample

![sample-1](https://raw.githubusercontent.com/fengjundev/Android-Skin-Loader/master/sample/image/1.png)
  
  
![sample-2](https://raw.githubusercontent.com/fengjundev/Android-Skin-Loader/master/sample/image/2.png)



![sample-3](https://raw.githubusercontent.com/fengjundev/Android-Skin-Loader/master/sample/image/3.png)



![sample-4](https://raw.githubusercontent.com/fengjundev/Android-Skin-Loader/master/sample/image/4.png)



![sample-5](https://raw.githubusercontent.com/fengjundev/Android-Skin-Loader/master/sample/image/5.png)



![sample-5](https://raw.githubusercontent.com/fengjundev/Android-Skin-Loader/master/sample/image/6.png)
