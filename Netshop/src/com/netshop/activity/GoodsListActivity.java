package com.netshop.activity;

import java.util.ArrayList;
import java.util.LinkedHashMap;
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

public class GoodsListActivity extends Activity {
	private ImageView backImg;
	private ListView listView;
	private String key;
	private List<Product> datas;
	public ProductAdapter adapter;
	private TextView title;
	public void onCreate(Bundle bundle){
		super.onCreate(bundle);
		setContentView(R.layout.goods_list);
		Intent intent = getIntent();
		key = intent.getStringExtra("key");
		
		backImg = (ImageView) findViewById(R.id.title_back_img);
		backImg.setVisibility(View.VISIBLE);
		backImg.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		title = (TextView)findViewById(R.id.title_text);
		title.setText("主营产品");
		listView = (ListView)findViewById(R.id.search_list);
		initData(key);
	}
	public void initData(String key){
		HttpRequest request = new HttpRequest("3", "0009");
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
			datas = new ArrayList<Product>();
			Object tempObject = entity.getList().getProduct();
			if(tempObject == null){
				Toast.makeText(GoodsListActivity.this, "没有数据", Toast.LENGTH_SHORT).show();
				return;
			}
			if(tempObject instanceof LinkedHashMap<?, ?>){
				LinkedHashMap<String, Object> tempHashMap = (LinkedHashMap<String, Object>) tempObject;
				Product product = new Product();
				product.setPid(String.valueOf(tempHashMap.get("pid")));
				product.setPname(String.valueOf(tempHashMap.get("pname")));
				product.setPimg(String.valueOf(tempHashMap.get("pimg")));
				product.setPrice(String.valueOf(tempHashMap.get("price")));
				product.setWeight(String.valueOf(tempHashMap.get("weight")));
				datas.add(product);
			}else{
				ArrayList<LinkedHashMap<String, Object>> list = (ArrayList<LinkedHashMap<String, Object>>) tempObject;
				for(LinkedHashMap<String, Object> temp:list){
					Product product = new Product();
					product.setPid(String.valueOf(temp.get("pid")));
					product.setPname(String.valueOf(temp.get("pname")));
					product.setPimg(String.valueOf(temp.get("pimg")));
					product.setPrice(String.valueOf(temp.get("price")));
					product.setWeight(String.valueOf(temp.get("weight")));
					datas.add(product);
				}
			}
			if(datas != null){
				adapter = new ProductAdapter(GoodsListActivity.this, datas);
				listView.setAdapter(adapter);
				listView.setOnItemClickListener(new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						Intent intent = new Intent(GoodsListActivity.this,GoodsDetilsActivity.class);
						intent.putExtra("id", datas.get(position).getPid());
						startActivity(intent);
					}
				});
			}
		}
		
		@Override
		public void fail(String failReason) {
			if(failReason!=null)
			Toast.makeText(GoodsListActivity.this, failReason, Toast.LENGTH_SHORT).show();
		}
	};
}
