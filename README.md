# Android-Skin-Loader

> Contact me : fengjun.dev@gmail.com
> A skin manager load skin from local file dynamically.  
> Now support background , text color , list divider and list selector


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

2. tag the view that need to change skin in layout xml files
    ```xml
    ...
    xmlns:skin="http://schemas.android.com/android/skin"
    ...
    
    <TextView
        android:id="@+id/title_text"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        skin:enable="true" 
        android:textColor="@color/color_title_bar_text"
        android:textSize="20sp" />
    ```

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

## Sample screenshot

- screenshot-1
![sample-1](https://raw.githubusercontent.com/fengjundev/Android-Skin-Loader/master/sample/image/1.png)
  
- screenshot-2
![sample-2](https://raw.githubusercontent.com/fengjundev/Android-Skin-Loader/master/sample/image/2.png)

- screenshot-3
![sample-3](https://raw.githubusercontent.com/fengjundev/Android-Skin-Loader/master/sample/image/3.png)

- screenshot-4
![sample-4](https://raw.githubusercontent.com/fengjundev/Android-Skin-Loader/master/sample/image/4.png)

- screenshot-5
![sample-5](https://raw.githubusercontent.com/fengjundev/Android-Skin-Loader/master/sample/image/5.png)

- screenshot-6
![sample-5](https://raw.githubusercontent.com/fengjundev/Android-Skin-Loader/master/sample/image/6.png)
