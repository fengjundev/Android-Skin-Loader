package cn.feng.skin.manager.entity;

import cn.feng.skin.manager.loader.SkinManager;
import android.util.Log;
import android.view.View;

public class BackgroundAttr extends SkinAttr {

	@Override
	public void apply(View view) {
		
		if(RES_TYPE_NAME_COLOR.equals(attrValueTypeName)){
			view.setBackgroundColor(SkinManager.getInstance().getColor(attrValueRefId));
			Log.i("attr", "_________________________________________________________");
			Log.i("attr", "apply as color");
		}else if(RES_TYPE_NAME_DRAWABLE.equals(attrValueTypeName)){
			view.setBackground(SkinManager.getInstance().getDrawable(attrValueRefId));
			Log.i("attr", "_________________________________________________________");
			Log.i("attr", "apply as drawable");
		}
	}
}
