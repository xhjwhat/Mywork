package com.netshop.activity;

import com.google.l99gson.Gson;
import com.netshop.app.R;
import com.netshop.net.HttpRequest;
import com.netshop.net.HttpRequest.HttpCallBack;
import com.netshop.util.NetShopUtil;
import com.netshop.entity.ShopEntity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.mdroid.cache.DefaultLoader;
import android.support.mdroid.cache.ImageWorker;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ShopDetialsActivity extends Activity {
	private TextView title;
	private TextView name;
	private TextView addr,connPeople,connPhone;
	
	private ImageView backImg;
	private ImageView storeImg;
	private ImageWorker worker;
	public void onCreate(Bundle bundle){
		super.onCreate(bundle);
		setContentView(R.layout.store_details);
		initView();
		Intent intent = getIntent();
		if(intent!=null){
			initData(intent.getStringExtra("id"));
		}
	}
	public void initView(){
		worker = new DefaultLoader(this);
		worker.setRequestWidthAndHeight(NetShopUtil.getScreenWidth(this), NetShopUtil.dip2px(this, 130));
		backImg = (ImageView) findViewById(R.id.title_back_img);
		backImg.setVisibility(View.VISIBLE);
		backImg.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		title = (TextView)findViewById(R.id.title_text);
		title.setText("门店详情");
		storeImg = (ImageView)findViewById(R.id.store_img);
		name = (TextView)findViewById(R.id.store_name);
		addr = (TextView)findViewById(R.id.store_addr);
		connPeople = (TextView)findViewById(R.id.store_conn_people_text);
		connPhone = (TextView)findViewById(R.id.conn_people_phone_text);
	}
	public void initData(String pc){
		HttpRequest request = new HttpRequest("4", "0003");
		request.setPc(pc);
		request.request(new HttpCallBack() {
			public void success(String json) {
				Gson gson = new Gson();
				ShopEntity entity = gson.fromJson(json, ShopEntity.class);
				if(entity!=null){
					worker.loadImage(entity.getShop().getImg(), storeImg);
					name.setText(entity.getShop().getName());
					addr.setText(entity.getShop().getAddress());
					connPeople.setText(entity.getShop().getLinkname());
					connPhone.setText(entity.getShop().getPhone());
				}
			}
			public void fail(String failReason) {
				if(failReason!=null)
				Toast.makeText(ShopDetialsActivity.this, failReason, Toast.LENGTH_SHORT).show();
			}
		});
	}
}
