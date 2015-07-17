# Android-Skin-Loader

> Contact me : fengjun.dev@gmail.com
>
> A skin manager load skin from local file dynamically.  
> Now support background , text color , list divider and list selector


## Usage

1. Init skin loader in `Application` 

    ```java
public class SkinApplication extends Application {
	    public void onCreate() {
	            super.onCreate();
		    // Must call init first 
		    SkinManager.getInstance().init(this);
		    SkinManager.getInstance().load();
 	    }
}
    ```

2. Tag the view that need to change skin in layout xml files
    ```xml
    ...
    xmlns:skin="http://schemas.android.com/android/skin"
    ...
    
    <TextView
        ...
        skin:enable="true" 
        ... />
    ```

3. Extend Acticity from `SkinPluginActivity`

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
