package com.netshop.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.mdroid.cache.CachedList;
import android.support.mdroid.cache.CachedModel;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.l99gson.Gson;
import com.netshop.app.NetShopApp;
import com.netshop.app.R;
import com.netshop.entity.BaseEntity;
import com.netshop.entity.Product;
import com.netshop.entity.ProductEntity;
import com.netshop.entity.ProductInfo;
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
	
	private TextView title;
	private ListView listView;
	private ImageView backImg;
	private Product product;
	public void onCreate(Bundle bundle){
		super.onCreate(bundle);
		setContentView(R.layout.goods_detail);
		initView();
		backImg = (ImageView) findViewById(R.id.title_back_img);
		backImg.setVisibility(View.VISIBLE);
		backImg.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		title = (TextView)findViewById(R.id.title_text);
		title.setText("商品详情");
		listView = (ListView)findViewById(R.id.list);
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
					ProductInfo productInfo = gson.fromJson(json, ProductInfo.class);
					if(productInfo!=null){
						product = productInfo.getProduct();
						nameText.setText(product.getPname());
						priceText.setText("¥"+product.getPrice());
						
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
//		numText = (TextView)findViewById(R.id.selected_text_num);
//		weightText = (TextView)findViewById(R.id.selected_price);
		storeAddrText = (TextView)findViewById(R.id.store_addr);
//		hasText = (TextView)findViewById(R.id.store_has_product);
//		phoneText = (TextView)findViewById(R.id.store_phone);
//		numLayout = findViewById(R.id.num_layout);
		//numLayout.setOnClickListener(this);
		informLayout = findViewById(R.id.inform_layout);
		informLayout.setOnClickListener(this);
//		addrLayout = findViewById(R.id.store_addr_layout);
		//addrLayout.setOnClickListener(this);
		commentLayout = findViewById(R.id.product_comment_layout);
		commentLayout.setOnClickListener(this);
//		cityLayout = findViewById(R.id.city_layout);
//		buyLayout = findViewById(R.id.buy_layout);
		pager = (ViewPager)findViewById(R.id.goods_viewpager);
		indicator = (CirclePageIndicator)findViewById(R.id.goods_indicator);
		myShopcarBtn = (Button)findViewById(R.id.goods_btn_my_car);
		myShopcarBtn.setOnClickListener(this);
		addToShopcarBtn = (Button)findViewById(R.id.goods_btn_add_car);
		addToShopcarBtn.setOnClickListener(this);
//		buyNowBtn = (Button)findViewById(R.id.goods_buy_btn_now);
//		buyNowBtn.setOnClickListener(this);
//		buyShopcarBtn = (Button)findViewById(R.id.goods_buy_btn_car);
//		buyShopcarBtn.setOnClickListener(this);
//		reduceImg = (ImageView)findViewById(R.id.jian_img);
//		reduceImg.setOnClickListener(this);
//		addImg = (ImageView)findViewById(R.id.jia_img);
//		addImg.setOnClickListener(this);
//		numEdit = (EditText)findViewById(R.id.num_edit);
	}
	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.goods_collect_img:
			HttpRequest collectRequest = new HttpRequest("5", "0002");
			collectRequest.setPc(product.getPid());
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
//		case R.id.num_layout:
//			buyLayout.setVisibility(View.VISIBLE);
//			break;
//		case R.id.store_addr_layout:
//			break;
		case R.id.inform_layout:
			Intent intent = new Intent(this,GoodDetailActivity.class);
			intent.putExtra("product", product);
			startActivity(intent);
			
			break;
		case R.id.product_comment_layout:
			break;
		case R.id.goods_btn_my_car:
			Intent intent2 = new Intent(this,MainActivity.class);
			startActivity(intent2);
			break;
		case R.id.goods_btn_add_car:
			CachedList<Product> cachedList =(CachedList<Product>) CachedModel.find(NetShopApp.getInstance().modelCache, "shopcart01", CachedList.class);
			if(cachedList == null){
				cachedList = new CachedList<Product>("shopcart01");
			}
			cachedList.add(product);
			if(cachedList.save(NetShopApp.getInstance().modelCache)){
				Toast.makeText(this, "添加成功", Toast.LENGTH_SHORT).show();
			}
			break;
//		case R.id.goods_buy_btn_car:
//			break;
//		case R.id.goods_buy_btn_now:
//			break;
//		case R.id.jia_img:
//			int addNum =Integer.valueOf(numEdit.getText().toString())+1;
//			numEdit.setText(addNum+"");
//			break;
//		case R.id.jian_img:
//			int reduceNum =Integer.valueOf(numEdit.getText().toString());
//			if(reduceNum > 0){
//				reduceNum--;
//				numEdit.setText(reduceNum+"");
//			}
//			break;
		}
		
	}
}
