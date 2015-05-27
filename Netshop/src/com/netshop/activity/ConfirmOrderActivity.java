package com.netshop.activity;

import com.netshop.app.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ConfirmOrderActivity extends Activity {
	private TextView title;
	private ImageView backImg;
	private TextView addrText,numText,nameText,storeName,storePhone,caculateText,productName,productPrice,productWeight,productHas,productNum;
	private Button confirmBtn;
	private ImageView productImg;
	
	public void onCreate(Bundle bundle){
		super.onCreate(bundle);
		setContentView(R.layout.comfirt_order);
		backImg = (ImageView) findViewById(R.id.title_back_img);
		backImg.setVisibility(View.VISIBLE);
		backImg.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		title = (TextView)findViewById(R.id.title_text);
		title.setText("购物车");
	}
	public void init(){
		nameText = (TextView)findViewById(R.id.comfirm_order_consignee_text);
		numText = (TextView)findViewById(R.id.comfirm_order_num_text);
		addrText = (TextView)findViewById(R.id.comfirm_order_addr);
		storeName = (TextView)findViewById(R.id.confirm_store_name_text);
		storePhone = (TextView)findViewById(R.id.confirm_store_num_text);
		caculateText = (TextView)findViewById(R.id.confirm_allmoney_text);
		productName = (TextView)findViewById(R.id.confirm_product_name);
		productPrice = (TextView)findViewById(R.id.confirm_price_text);
		productNum = (TextView)findViewById(R.id.confirm_num_text);
		productHas = (TextView)findViewById(R.id.confirm_has_text);
		confirmBtn = (Button)findViewById(R.id.confirm_btn);
	}
}
