package com.netshop.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.mdroid.cache.CachedList;
import android.support.mdroid.cache.CachedModel;
import android.support.mdroid.cache.DefaultLoader;
import android.support.mdroid.cache.ImageWorker;
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
	private Button myShopcarBtn,addToShopcarBtn,buyShopcarBtn,buyNowBtn;
	private EditText numEdit;
	
	private TextView title;
	private ListView listView;
	private ImageView backImg;
	private Product product;
	private ImageView productImg;
	private ImageWorker worker;
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
		worker = new DefaultLoader(this);
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
						worker.loadImage(product.getPimg(), productImg);
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
		storeAddrText = (TextView)findViewById(R.id.store_addr);
		informLayout = findViewById(R.id.inform_layout);
		informLayout.setOnClickListener(this);
		commentLayout = findViewById(R.id.product_comment_layout);
		commentLayout.setOnClickListener(this);
		myShopcarBtn = (Button)findViewById(R.id.goods_btn_my_car);
		myShopcarBtn.setOnClickListener(this);
		addToShopcarBtn = (Button)findViewById(R.id.goods_btn_add_car);
		addToShopcarBtn.setOnClickListener(this);
		productImg =(ImageView)findViewById(R.id.goods_details_img);
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
		case R.id.inform_layout:
			Intent intent = new Intent(this,GoodDetailActivity.class);
			intent.putExtra("product", product);
			startActivity(intent);
			
			break;
		case R.id.product_comment_layout:
			Intent commentIntent = new Intent(this,ProductCommentActivity.class);
			commentIntent.putExtra("key", product.getPid());
			startActivity(commentIntent);
			break;
		case R.id.goods_btn_my_car:
			Intent intent2 = new Intent(this,MainActivity.class);
			intent2.putExtra("to", 3);
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
		}
		
	}
}
