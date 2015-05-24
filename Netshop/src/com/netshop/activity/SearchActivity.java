package com.netshop.activity;

import java.util.List;

import com.google.l99gson.Gson;
import com.netshop.adapter.ProductAdapter;
import com.netshop.app.R;
import com.netshop.entity.Product;
import com.netshop.entity.ProductEntity;
import com.netshop.net.HttpRequest;
import com.netshop.net.HttpRequest.HttpCallBack;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class SearchActivity extends Activity {
	private ImageView backImg;
	private ImageView searchImg;
	private EditText searchEdit;
	private ListView listView;
	private String key;
	private List<Product> datas;
	public ProductAdapter adapter;
	public void onCreate(Bundle bundle){
		super.onCreate(bundle);
		setContentView(R.layout.search);
		Intent intent = getIntent();
		key = intent.getStringExtra("key");
		backImg = (ImageView)findViewById(R.id.title_back);
		backImg.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				finish();
			}
		});
		searchEdit = (EditText)findViewById(R.id.search_edit);
		searchImg = (ImageView)findViewById(R.id.seach_img);
		searchImg.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
			}
		});
		listView = (ListView)findViewById(R.id.search_list);
		initData(key);
	}
	public void initData(String key){
		HttpRequest request = new HttpRequest("3", "0003");
		request.setPc(key);
		request.setPs("10");
		request.setPg("1");
		request.request(callBack);
		
	}
	HttpCallBack callBack = new HttpCallBack() {
		@Override
		public void success(String json) {
			Gson gson = new Gson();
			ProductEntity entity = gson.fromJson(json, ProductEntity.class);
			datas = (List<Product>) entity.getList().getProduct();
			if(datas != null){
				adapter = new ProductAdapter(SearchActivity.this, datas);
				listView.setAdapter(adapter);
				listView.setOnItemClickListener(new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						Intent intent = new Intent(SearchActivity.this,GoodsDetilsActivity.class);
						intent.putExtra("id", datas.get(position).getPid());
						startActivity(intent);
					}
				});
			}
		}
		
		@Override
		public void fail(String failReason) {
			if(failReason!=null)
			Toast.makeText(SearchActivity.this, failReason, Toast.LENGTH_SHORT).show();
		}
	};
}
