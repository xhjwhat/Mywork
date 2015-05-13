package com.netshop.activity;

import com.netshop.app.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class ShopDetialsActivity extends Activity {
	
	public void onCreate(Bundle bundle){
		super.onCreate(bundle);
		setContentView(R.layout.store_details);
		initView();
	}
	public void initView(){
		findViewById(R.id.title_back_img).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}
}
