package cn.feng.skin.manager.listener;

import java.util.List;

import android.view.View;
import cn.feng.skin.manager.entity.DynamicAttr;

public interface IDynamicNewView {
	void dynamicAddView(View view, List<DynamicAttr> pDAttrs);
}
