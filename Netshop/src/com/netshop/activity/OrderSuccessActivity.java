package com.netshop.activity;

import com.netshop.app.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class OrderSuccessActivity extends Activity {
	public TextView orderNum,nextText;
	
	private TextView title;
	private ImageView backImg;
	public void onCreate(Bundle bundle){
		super.onCreate(bundle);
		setContentView(R.layout.order_ok);
		backImg = (ImageView) findViewById(R.id.title_back_img);
		backImg.setVisibility(View.VISIBLE);
		backImg.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		title = (TextView)findViewById(R.id.title_text);
		title.setText("购买成功");
		String num= getIntent().getStringExtra("num");
		orderNum = (TextView)findViewById(R.id.order_num_text);
		if(num!=null){
			orderNum.setText(num);
		}
		nextText = (TextView)findViewById(R.id.order_go_shop_text);
		nextText.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(OrderSuccessActivity.this,MainActivity.class);
				intent.putExtra("to", 1);
				startActivity(intent);
				finish();
			}
		});
	}
}
