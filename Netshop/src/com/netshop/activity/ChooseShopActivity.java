package com.netshop.activity;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import com.google.l99gson.Gson;
import com.netshop.adapter.CityAdapter;
import com.netshop.adapter.StoreAdapter;
import com.netshop.app.NetShopApp;
import com.netshop.app.R;
import com.netshop.entity.ShopEntity;
import com.netshop.entity.ShopsEntity;
import com.netshop.entity.ShopEntity.Shop;
import com.netshop.net.HttpRequest;
import com.netshop.net.HttpRequest.HttpCallBack;
import com.netshop.util.NetShopUtil;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class ChooseShopActivity extends Activity {
	private TextView titleText;
	private View areaLayout, orderLayout;
	private ListView listview;
	private StoreAdapter adapter;
	private ImageView cityBack;
	private List<ShopEntity.Shop> shopList;
	private ListView areaList;
	private CityAdapter cityAdapter;
	private PopupWindow window;
	private Context context;
	private FragmentManager manager;
	private ImageView backImg;
	double[] location;
	public void onCreate(Bundle bundle){
		super.onCreate(bundle);
		setContentView(R.layout.stores_detail);
		double[] location = NetShopApp.getInstance().getLocation();
		String pc = "lan:" + location[0] + ";lat:" + location[1];
		initData(pc);
		titleText = (TextView)findViewById(R.id.title_text);
		titleText.setText("附近门店");
		backImg = (ImageView) findViewById(R.id.title_back_img);
		backImg.setVisibility(View.VISIBLE);
		backImg.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		areaList = new ListView(this);
		cityAdapter = new CityAdapter(this, handler);
		areaList.setAdapter(cityAdapter);
		cityBack = new ImageView(this);
		areaLayout = findViewById(R.id.store_area_layout);
		areaLayout.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (window == null) {
					window = new PopupWindow(areaList, NetShopUtil
							.getScreenWidth(ChooseShopActivity.this) / 2, NetShopUtil
							.dip2px(ChooseShopActivity.this, 300));
					window.setFocusable(true);
					window.setOutsideTouchable(true);
					window.setBackgroundDrawable(new ColorDrawable(0x00000000));
				}
				cityAdapter.reset();
				window.showAsDropDown(areaLayout);
			}
		});
		orderLayout = findViewById(R.id.store_order_layout);
		orderLayout.setVisibility(View.GONE);
		listview = (ListView) findViewById(R.id.store_list);
	}
	
	public void initData(String pc) {
		HttpRequest request = new HttpRequest("4", "0001");
		request.setPg("1");
		request.setPs("8");
		request.setPc(pc);
		request.request(HttpRequest.REQUEST_GET, new HttpCallBack() {
			@Override
			public void success(String json) {
				Gson gson = new Gson();
				ShopsEntity entity = gson.fromJson(json, ShopsEntity.class);
				Object tempObject = entity.getList().getShop();
				if(tempObject ==null){
					Toast.makeText(ChooseShopActivity.this, "没有数据", Toast.LENGTH_SHORT).show();
					return;
				}
				shopList = new ArrayList<ShopEntity.Shop>();
				if(tempObject instanceof LinkedHashMap<?, ?>){
					LinkedHashMap<String, Object> tempHashMap =(LinkedHashMap<String, Object>)tempObject;
					//LinkedHashMap<String, Object> temp = (LinkedHashMap<String, Object>)tempHashMap.get("product");
					Shop shop = new Shop();
					shop.setId(String.valueOf(tempHashMap.get("id")));
					shop.setImg(String.valueOf(tempHashMap.get("img")));
					shop.setAddress(String.valueOf(tempHashMap.get("address")));
					shop.setIsnew(String.valueOf(tempHashMap.get("isnew")));
					shop.setLinkname(String.valueOf(tempHashMap.get("linkname")));
					shop.setName(String.valueOf(tempHashMap.get("name")));
					shop.setPhone(String.valueOf(tempHashMap.get("phone")));
					shopList.add(shop);
				}else {
					ArrayList<LinkedHashMap<String, Object>> list = (ArrayList<LinkedHashMap<String, Object>>) tempObject;
					for(LinkedHashMap<String, Object> temp:list){
						Shop shop = new Shop();
						shop.setId(String.valueOf(temp.get("id")));
						shop.setImg(String.valueOf(temp.get("img")));
						shop.setAddress(String.valueOf(temp.get("address")));
						shop.setIsnew(String.valueOf(temp.get("isnew")));
						shop.setLinkname(String.valueOf(temp.get("linkname")));
						shop.setName(String.valueOf(temp.get("name")));
						shop.setPhone(String.valueOf(temp.get("phone")));
						shopList.add(shop);
					}
				}
				adapter = new StoreAdapter(ChooseShopActivity.this, shopList);
				listview.setAdapter(adapter);
				listview.setOnItemClickListener(new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						Intent intent = new Intent();
						intent.putExtra("shop", shopList.get(position));
						setResult(2, intent);
						finish();
					}
				});
			}

			@Override
			public void fail(String failReason) {
				// TODO Auto-generated method stub

			}
		});
	}
	
	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			if (window.isShowing()) {
				window.dismiss();
			}
			String pc = (String) msg.obj;
			initData(pc);
		}
	};
}
