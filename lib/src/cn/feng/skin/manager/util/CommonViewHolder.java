package cn.feng.skin.manager.util;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

/**
 * Common View Holder to establish a absview
 * 
 * @author fengjun
 */
public class CommonViewHolder {
	
	private SparseArray<View> mViews;
	private int mPosition;
	private View mConvertView;
	private Context context;
	
	public CommonViewHolder(Context context, ViewGroup parent, int itemLayoutId, int position) {
		this.context = context;
		mPosition = position;
		mViews = new SparseArray<View>();
		mConvertView = LayoutInflater.from(context).inflate(itemLayoutId, parent, false);
		mConvertView.setTag(this);
	}
	
	public static CommonViewHolder get(Context context, View convertView, ViewGroup parent, int layoutId, int position){
		if(convertView == null){
			return new CommonViewHolder(context, parent, layoutId, position);
		}else{
			CommonViewHolder holder = (CommonViewHolder)convertView.getTag();
			// 每次都对position进行更新
			holder.mPosition = position;
			return holder;
		}
	}
	
	public <T extends View> T getView(int viewId){
		View view = mViews.get(viewId);
		if(view == null){
			view = mConvertView.findViewById(viewId);
			
			if(view == null){
				String resName = context.getResources().getResourceEntryName(viewId);
				throw new RuntimeException("Can not find "+ resName + " in parent view !");
			}
			
			mViews.put(viewId, view);
		}
		return (T)view;
	}

	public <T extends View> T getView(int viewId, OnClickListener listener){
		View view = mViews.get(viewId);
		if(view == null){
			view = mConvertView.findViewById(viewId);
			
			if(view == null){
				String resName = context.getResources().getResourceEntryName(viewId);
				throw new RuntimeException("Can not find "+ resName + " in parent view !");
			}
			
			view.setOnClickListener(listener);
			mViews.put(viewId, view);
		}
		return (T)view;
	}
	
	public View getConvertView(){
		return mConvertView;
	}
	
	public int getPosititon(){
		return mPosition;
	}
	
	public CommonViewHolder setText(int viewId, String text){
		TextView textview = getView(viewId);
		textview.setText(text);
		return this;
	}
	
	public CommonViewHolder setRating(int viewId, float rate){
		RatingBar ratingBar = getView(viewId);
		ratingBar.setRating(rate);
		return this;
	}
	
	public CommonViewHolder setImageResource(int viewId, int resId){
		ImageView imageview = getView(viewId);
		imageview.setImageResource(resId);
		return this;
	}
	
	public CommonViewHolder setVisibility(int viewId, int visibility){
		View view = getView(viewId);
		
		if(view == null){
			return this;
		}
		
		view.setVisibility(visibility);
		
		return this;
	}
	
	public CommonViewHolder setOnClickListenr(int viewId, OnClickListener listener){
		View view = mViews.get(viewId);
		
		if(view == null){
			view = mConvertView.findViewById(viewId);
			view.setOnClickListener(listener);
			mViews.put(viewId, view);
		}else if(!view.hasOnClickListeners()){
			view.setOnClickListener(listener);
			mViews.put(viewId, view);
		}
		
		return this;
	}
}
