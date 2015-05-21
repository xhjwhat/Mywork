package com.netshop.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.l99gson.Gson;
import com.netshop.app.R;
import com.netshop.entity.BaseEntity;
import com.netshop.entity.Product;
import com.netshop.net.HttpRequest;
import com.netshop.net.HttpRequest.HttpCallBack;
import com.netshop.view.CirclePageIndicator;

public class GoodsDetilsActivity extends Activity implements OnClickListener{
	private ImageView collectImg,reduceImg,addImg;
	private TextView nameText,hasText,phoneText;
	private TextView seriesText,priceText,numText,weightText,storeAddrText;
	private View numLayout,informLayout,addrLayout,commentLayout,cityLayout,buyLayout;
	private ViewPager pager;
	private CirclePageIndicator indicator;
	private Button myShopcarBtn,addToShopcarBtn,buyShopcarBtn,buyNowBtn;
	private EditText numEdit;
	public void onCreate(Bundle bundle){
		super.onCreate(bundle);
		setContentView(R.layout.goods_detail);
		initView();
		
		HttpRequest  request = new HttpRequest("3", "0004");
		Intent intent = getIntent();
		String id=intent.getStringExtra("id");
		if(id!=null){
			request.setPc(id);
		}
		
		request.request(new HttpCallBack() {
			@Override
			public void success(String json) {
				try{
					Gson gson = new Gson();
					Product product = gson.fromJson(json, Product.class);
					if(product!=null){
						nameText.setText(product.getPname());
						priceText.setText(product.getPrice());
						
					}
				}catch(Exception e){
					Toast.makeText(GoodsDetilsActivity.this, "网络数据有误", Toast.LENGTH_SHORT).show();
					e.printStackTrace();
				}
				
			}
			
			@Override
			public void fail(String failReason) {
				Toast.makeText(GoodsDetilsActivity.this, failReason, Toast.LENGTH_SHORT).show();
			}
		});
	}
	public void initView(){
		collectImg = (ImageView)findViewById(R.id.goods_collect_img);
		collectImg.setOnClickListener(this);
		nameText = (TextView)findViewById(R.id.goods_collect_name_text);
		seriesText = (TextView)findViewById(R.id.goods_collect_series_text);
		priceText = (TextView)findViewById(R.id.goods_price_text);
		numText = (TextView)findViewById(R.id.selected_text_num);
		weightText = (TextView)findViewById(R.id.selected_price);
		storeAddrText = (TextView)findViewById(R.id.store_addr);
		hasText = (TextView)findViewById(R.id.store_has_product);
		phoneText = (TextView)findViewById(R.id.store_phone);
		numLayout = findViewById(R.id.num_layout);
		numLayout.setOnClickListener(this);
		informLayout = findViewById(R.id.inform_layout);
		informLayout.setOnClickListener(this);
		addrLayout = findViewById(R.id.store_addr_layout);
		addrLayout.setOnClickListener(this);
		commentLayout = findViewById(R.id.product_comment_layout);
		commentLayout.setOnClickListener(this);
		cityLayout = findViewById(R.id.city_layout);
		buyLayout = findViewById(R.id.buy_layout);
		pager = (ViewPager)findViewById(R.id.goods_viewpager);
		indicator = (CirclePageIndicator)findViewById(R.id.goods_indicator);
		myShopcarBtn = (Button)findViewById(R.id.goods_btn_my_car);
		myShopcarBtn.setOnClickListener(this);
		addToShopcarBtn = (Button)findViewById(R.id.goods_btn_add_car);
		addToShopcarBtn.setOnClickListener(this);
		buyNowBtn = (Button)findViewById(R.id.goods_buy_btn_now);
		buyNowBtn.setOnClickListener(this);
		buyShopcarBtn = (Button)findViewById(R.id.goods_buy_btn_car);
		buyShopcarBtn.setOnClickListener(this);
		reduceImg = (ImageView)findViewById(R.id.jian_img);
		reduceImg.setOnClickListener(this);
		addImg = (ImageView)findViewById(R.id.jia_img);
		addImg.setOnClickListener(this);
		numEdit = (EditText)findViewById(R.id.num_edit);
	}
	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.goods_collect_img:
			HttpRequest collectRequest = new HttpRequest("3", "0007");
			collectRequest.request(new HttpCallBack() {
				public void success(String json) {
					Gson gson = new Gson();
					BaseEntity entity = gson.fromJson(json, BaseEntity.class);
					if(entity!=null && entity.getError().equals("0")){
						Toast.makeText(GoodsDetilsActivity.this, "收藏成功", Toast.LENGTH_SHORT).show();
					}
				}
				public void fail(String failReason) {
					Toast.makeText(GoodsDetilsActivity.this, failReason, Toast.LENGTH_SHORT).show();
				}
			});
			break;
		case R.id.num_layout:
			buyLayout.setVisibility(View.VISIBLE);
			break;
		case R.id.inform_layout:
			break;
		case R.id.store_addr_layout:
			break;
		case R.id.product_comment_layout:
			break;
		case R.id.goods_btn_my_car:
		case R.id.goods_buy_btn_car:
			break;
		case R.id.goods_buy_btn_now:
			break;
		case R.id.goods_btn_add_car:
			break;
		case R.id.jia_img:
			int addNum =Integer.valueOf(numEdit.getText().toString())+1;
			numEdit.setText(addNum+"");
			break;
		case R.id.jian_img:
			int reduceNum =Integer.valueOf(numEdit.getText().toString());
			if(reduceNum > 0){
				reduceNum--;
				numEdit.setText(reduceNum+"");
			}
			break;
		}
		
	}
}
