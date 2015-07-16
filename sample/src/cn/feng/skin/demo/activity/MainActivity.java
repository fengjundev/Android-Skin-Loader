package cn.feng.skin.demo.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import cn.feng.skin.demo.R;
import cn.feng.skin.demo.entity.News;
import cn.feng.skin.manager.base.SkinPluginActivity;
import cn.feng.skin.manager.util.CommonBaseAdapter;
import cn.feng.skin.manager.util.CommonViewHolder;
import cn.feng.skin.manager.util.L;

public class MainActivity extends SkinPluginActivity{
	
	private TextView titleText;
	private Button settingBtn;
	private ListView newsList;
	private NewsAdapter adapter;
	private List<News> datas;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		initData();
		initView();
		
		View vv = settingBtn;
		if(vv instanceof TextView){
			L.w("sss", "ok");
		}else{
			L.w("sss", "no");
		}
	}

	private void initData() {
		datas = new ArrayList<News>();
		for(int i = 0;i < 10; i++){
			News news = new News();
			news.content = "一段依赖关系的建立,就意味着这段关系有其终点.生老病死,悲欢离合,这是每个人的天命,我们逗无法逃避";
			news.title = "每日随笔";
			datas.add(news);
		}
	}

	private void initView() {
		titleText = (TextView) findViewById(R.id.title_text);
		newsList = (ListView) findViewById(R.id.news_list_view);
		settingBtn = (Button) findViewById(R.id.title_bar_setting_btn);
		
		titleText.setText("Small Article");
		
		adapter = new NewsAdapter(this, datas);
		
		newsList.setAdapter(adapter);
		
		newsList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, DetailActivity.class);
				startActivity(intent);
			}
		});
		
		settingBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, SettingActivity.class);
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
