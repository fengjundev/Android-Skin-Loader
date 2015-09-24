package cn.feng.skin.manager.base;

import java.util.List;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.view.View;
import cn.feng.skin.manager.entity.DynamicAttr;
import cn.feng.skin.manager.listener.IDynamicNewView;

public class BaseFragment extends Fragment implements IDynamicNewView{
	
	private IDynamicNewView mIDynamicNewView;
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try{
			mIDynamicNewView = (IDynamicNewView)activity;
		}catch(ClassCastException e){
			mIDynamicNewView = null;
		}
	}

	@Override
	public void dynamicAddView(View view, List<DynamicAttr> pDAttrs) {
		if(mIDynamicNewView == null){
			throw new RuntimeException("IDynamicNewView should be implements !");
		}else{
			mIDynamicNewView.dynamicAddView(view, pDAttrs);
		}
	}
	
}
