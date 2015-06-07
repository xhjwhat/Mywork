package com.netshop.activity;

import java.util.ArrayList;

import com.google.l99gson.Gson;
import com.netshop.adapter.OrderListAdapter;
import com.netshop.app.R;
import com.netshop.entity.Addr;
import com.netshop.entity.AddrEntity;
import com.netshop.entity.BaseEntity;
import com.netshop.entity.DeliveryEntity;
import com.netshop.entity.Product;
import com.netshop.entity.ShopEntity.Shop;
import com.netshop.net.HttpRequest;
import com.netshop.net.HttpRequest.HttpCallBack;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ConfirmOrderActivity extends Activity implements OnClickListener {
	private TextView title;
	private ImageView backImg;
	private TextView addrText, numText, nameText, storeName, storePhone,
			caculateText, productName, productPrice, productWeight, productHas,
			productNum;
	private Button confirmBtn;
	private ImageView productImg;
	private ArrayList<Product> selectedList;
	private int totalMoney;
	private View addrLayout,shopLayout;
	private OrderListAdapter adapter;
	private ListView listView;
	private Shop shop;
	private Addr addr;

	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.comfirt_order);
		selectedList = (ArrayList<Product>) getIntent().getSerializableExtra(
				"selected");
		if(selectedList == null){
			selectedList = new ArrayList<Product>();
		}
		backImg = (ImageView) findViewById(R.id.title_back_img);
		backImg.setVisibility(View.VISIBLE);
		backImg.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		title = (TextView) findViewById(R.id.title_text);
		title.setText("确认订单");
		init();
		HttpRequest defaultRequest = new HttpRequest("6", "0001");
		defaultRequest.request(new HttpCallBack() {
			public void success(String json) {
				Gson gson = new Gson();
				DeliveryEntity entity = gson.fromJson(json, DeliveryEntity.class);
				if (entity != null) {
					addr = entity.getDelivery();
					nameText.setText(addr.getName());
					addrText.setText(addr.getAddress());
					numText.setText(addr.getPhone());
				} 
			}

			public void fail(String failReason) {
//				Toast.makeText(ConfirmOrderActivity.this, "没有默认地址，先选一个地址吧",
//						Toast.LENGTH_SHORT).show();
			}
		});

	}

	public void init() {
		nameText = (TextView) findViewById(R.id.comfirm_order_consignee_text);
		numText = (TextView) findViewById(R.id.comfirm_order_num_text);
		addrText = (TextView) findViewById(R.id.comfirm_order_addr);
		storeName = (TextView) findViewById(R.id.confirm_store_name_text);
		storePhone = (TextView) findViewById(R.id.confirm_store_num_text);
		caculateText = (TextView) findViewById(R.id.confirm_allmoney_text);
		productName = (TextView) findViewById(R.id.confirm_product_name);
		productPrice = (TextView) findViewById(R.id.confirm_price_text);
		productNum = (TextView) findViewById(R.id.confirm_num_text);
		productHas = (TextView) findViewById(R.id.confirm_has_text);
		confirmBtn = (Button) findViewById(R.id.confirm_btn);
		confirmBtn.setOnClickListener(this);
		addrLayout = findViewById(R.id.addr_layout);
		addrLayout.setOnClickListener(this);
		shopLayout = findViewById(R.id.shop_layout);
		shopLayout.setOnClickListener(this);
		listView = (ListView) findViewById(R.id.order_list);
		adapter = new OrderListAdapter(this, selectedList);
		listView.setAdapter(adapter);
		totalMoney = 0;
		if (selectedList.size() > 0) {
			for (Product temp : selectedList) {
				totalMoney += Integer.valueOf(temp.getPrice())
						* Integer.valueOf(temp.getNum());
			}
		}
		caculateText.setText("" + totalMoney);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.addr_layout:
			Intent intent = new Intent(this, AddrManagerActivity.class);
			intent.putExtra("from", "order");
			startActivityForResult(intent, 1);
			break;
		case R.id.shop_layout:
			Intent intent2 = new Intent(this, ChooseShopActivity.class);
			startActivityForResult(intent2, 2);
			break;
		case R.id.confirm_btn:
			if (shop == null) {
				Toast.makeText(this, "请先选择商店", Toast.LENGTH_SHORT).show();
				return;
			} else if (addr == null) {
				Toast.makeText(this, "请先选择收货人", Toast.LENGTH_SHORT).show();
				return;
			}
			HttpRequest request = new HttpRequest("2", "0001");
			request.setShopid(shop.getId());
			request.setAddressid(addr.getId());
			StringBuffer buffer = new StringBuffer();
			for (int i = 0; i < selectedList.size(); i++) {
				if (i == selectedList.size() - 1) {
					buffer.append(selectedList.get(i).getPid() + ","
							+ selectedList.get(i).getNum());
				} else {
					buffer.append(selectedList.get(i).getPid() + ","
							+ selectedList.get(i).getNum() + ";");
				}
			}
			request.setPc(buffer.toString());
			request.request(new HttpCallBack() {
				public void success(String json) {
					BaseEntity entity = new Gson().fromJson(json,
							BaseEntity.class);
					Intent intent = new Intent(ConfirmOrderActivity.this,
							OrderSuccessActivity.class);
					intent.putExtra("num", entity.getOid());
					startActivity(intent);
					finish();
				}

				public void fail(String failReason) {
					Toast.makeText(ConfirmOrderActivity.this, failReason,
							Toast.LENGTH_SHORT).show();
				}
			});
		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 1 && data != null) {
			addr = (Addr) data.getSerializableExtra("addr");
			nameText.setText(addr.getName());
			numText.setText(addr.getPhone());
			addrText.setText(addr.getAddress());
		} else if (requestCode == 2 && data != null) {
			shop = (Shop) data.getSerializableExtra("shop");
			storeName.setText(shop.getName());
			storePhone.setText(shop.getPhone());
		}
	}

}
