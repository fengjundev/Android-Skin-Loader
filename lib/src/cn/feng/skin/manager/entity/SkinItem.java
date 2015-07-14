package cn.feng.skin.manager.entity;

import java.util.ArrayList;
import java.util.List;

import android.view.View;

public class SkinItem {
	
	public View view;
	
	public List<SkinAttr> attrs;
	
	public SkinItem(){
		attrs = new ArrayList<SkinAttr>();
	}
}
