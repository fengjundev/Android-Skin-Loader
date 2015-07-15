package cn.feng.skin.manager.entity;

import java.util.ArrayList;
import java.util.List;

import cn.feng.skin.manager.util.ListUtils;
import android.content.res.Resources;
import android.view.View;

public class SkinItem {
	
	public View view;
	
	public List<SkinAttr> attrs;
	
	public SkinItem(){
		attrs = new ArrayList<SkinAttr>();
	}
	
	public void apply(){
		if(ListUtils.isEmpty(attrs)) return;
		for(SkinAttr at : attrs){
			at.apply(view);
		}
	}

	@Override
	public String toString() {
		return "SkinItem [view=" + view.getClass().getSimpleName() + ", attrs=" + attrs + "]";
	}
}
