package com.netshop.activity;

import com.netshop.app.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class AccountActivity extends Activity {
	private TextView title;
	private ImageView backImg;
	private View phone;
	private View password;
	public void onCreate(Bundle bundle){
		super.onCreate(bundle);
		setContentView(R.layout.account_safe);
		backImg = (ImageView) findViewById(R.id.title_back_img);
		backImg.setVisibility(View.VISIBLE);
		backImg.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		title = (TextView)findViewById(R.id.title_text);
		title.setText("账户安全");
		phone = findViewById(R.id.phone_layout);
		phone.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				//Intent intent = new Intent(AccountActivity.this,)
			}
		});
	}
}
