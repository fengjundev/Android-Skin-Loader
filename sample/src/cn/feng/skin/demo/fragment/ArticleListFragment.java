package cn.feng.skin.demo.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.feng.skin.demo.R;
import cn.feng.skin.demo.activity.DetailActivity;
import cn.feng.skin.demo.activity.SettingActivity;
import cn.feng.skin.demo.entity.News;
import cn.feng.skin.manager.base.BaseFragment;
import cn.feng.skin.manager.entity.AttrFactory;
import cn.feng.skin.manager.entity.DynamicAttr;
import cn.feng.skin.manager.util.CommonBaseAdapter;
import cn.feng.skin.manager.util.CommonViewHolder;

public class ArticleListFragment extends BaseFragment{

	private TextView titleText;
	private Button settingBtn;
	private ListView newsList;
	private RelativeLayout titleBarLayout;
	private NewsAdapter adapter;
	private List<News> datas;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		initData();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_article_list, container, false);
		initView(v);
		return v;
	}
	
	private void initData() {
		datas = new ArrayList<News>();
		for(int i = 0;i < 100; i++){
			News news = new News();
			news.content = "Always listen to your heart because even though it's on your left side, it's always right.";
			news.title = "Dear Diary " + i;
			datas.add(news);
		}
	}
	
	private void initView(View v) {
		titleBarLayout = (RelativeLayout) v.findViewById(R.id.title_bar_layout);
		newsList = (ListView) v.findViewById(R.id.news_list_view);
		adapter = new NewsAdapter(this.getActivity(), datas);
		
		newsList.setAdapter(adapter);
		
		newsList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				Intent intent = new Intent();
				intent.setClass(getActivity(), DetailActivity.class);
				startActivity(intent);
			}
		});
		
		titleText = (TextView) v.findViewById(R.id.title_text);
		settingBtn = (Button) v.findViewById(R.id.title_bar_setting_btn);
		
		titleText.setText("Small Article");
		
		settingBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(getActivity(), SettingActivity.class);
				startActivity(intent);
			}
		});
		
		// test for dynamicAddTitle()
		dynamicAddTitleView();
	}
	
	private void dynamicAddTitleView() {
		TextView textView = new TextView(getActivity());
		textView.setText("Small Article (动态new)");
		RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		param.addRule(RelativeLayout.CENTER_IN_PARENT);
		textView.setLayoutParams(param);
		textView.setTextColor(getActivity().getResources().getColor(R.color.color_title_bar_text));
		textView.setTextSize(20);
		titleBarLayout.addView(textView);
		
		List<DynamicAttr> mDynamicAttr = new ArrayList<DynamicAttr>();
		mDynamicAttr.add(new DynamicAttr(AttrFactory.TEXT_COLOR, R.color.color_title_bar_text));
		dynamicAddView(textView, mDynamicAttr);
	}
	
	private class NewsAdapter extends CommonBaseAdapter<News>{
 
		public NewsAdapter(Context context, List<News> mDatas) {
			super(context, mDatas, new int[]{R.layout.item_news_title});
		}

		@Override
		public void convertItemView(CommonViewHolder holder, News item, int position) {
			holder.setText(R.id.item_news_title, item.title);
			holder.setText(R.id.item_news_synopsis, item.content);
		}
	}
	
}
