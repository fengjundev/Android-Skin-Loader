package cn.feng.skin.manager.entity;

import cn.feng.skin.manager.loader.SkinManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class TextColorAttr extends SkinAttr {

	@Override
	public void apply(View view) {
		if(view instanceof TextView){
			TextView tv = (TextView)view;
			if(RES_TYPE_NAME_COLOR.equals(attrValueTypeName)){
				tv.setTextColor(SkinManager.getInstance().getColor(attrValueRefId));
			}
			// developing : need to add drawable supportment
		}
	}
}
