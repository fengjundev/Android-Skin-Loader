# Android-Skin-Loader 

> 联系我 : fengjun.dev@gmail.com
>
> 一个通过动态加载本地皮肤包进行换肤的皮肤框架



## 演示
#### 1. 下载[demo](https://github.com/fengjundev/Android-Skin-Loader/tree/master/apk),将`BlackFantacy.skin`放在SD卡根目录
#### 2. 效果图
![sample](https://raw.githubusercontent.com/fengjundev/Android-Skin-Loader/master/sample/image/screenshot.png)


## 用法

#### 1. 在`Application`中进行初始化
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

#### 2. 在布局文件中标识需要换肤的View

```xml
...
xmlns:skin="http://schemas.android.com/android/skin"
...
  <TextView
     ...
     skin:enable="true" 
     ... />
```

#### 3. 继承`SkinPluginActivity`作为BaseActivity进行开发
  
  
#### 4. 从`.skin`文件中设置皮肤
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

#### 5. 重设默认皮肤
```java
SkinManager.getInstance().restoreDefaultTheme();
```

