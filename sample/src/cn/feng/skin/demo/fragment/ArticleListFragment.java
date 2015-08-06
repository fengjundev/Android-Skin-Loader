package cn.feng.skin.demo.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import cn.feng.skin.demo.R;
import cn.feng.skin.demo.activity.DetailActivity;
import cn.feng.skin.demo.activity.MainActivity;
import cn.feng.skin.demo.activity.SettingActivity;
import cn.feng.skin.demo.entity.News;
import cn.feng.skin.manager.util.CommonBaseAdapter;
import cn.feng.skin.manager.util.CommonViewHolder;

public class ArticleListFragment extends Fragment{

	private TextView titleText;
	private Button settingBtn;
	private ListView newsList;
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
