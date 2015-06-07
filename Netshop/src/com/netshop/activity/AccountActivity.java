package com.netshop.activity;

import com.netshop.app.NetShopApp;
import com.netshop.app.R;
import com.netshop.entity.Account;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.mdroid.cache.CachedModel;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class AccountActivity extends Activity {
	private TextView title;
	private ImageView backImg;
	private TextView name;
	private TextView phoneText;
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
		name = (TextView)findViewById(R.id.account_name_text);
		Account account = (Account) CachedModel.find(NetShopApp.getInstance().modelCache, "account01", Account.class);
		if(account!=null){
			if(account.getNickname()!=null)
			name.setText(account.getNickname());
		}
		
		phoneText= (TextView)findViewById(R.id.account_update_phone_text);
		
		phone = findViewById(R.id.phone_layout);
		phone.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(AccountActivity.this,PhoneChangeActivity.class);
				startActivity(intent);
			}
		});
		password = findViewById(R.id.password_layout);
		password.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(AccountActivity.this,PasswordChangeActivity.class);
				startActivity(intent);
			}
		});
	}
	public void onResume(){
		super.onResume();
		String temp= NetShopApp.getInstance().getUserId();
		String phoneStr=temp.substring(0, 3)+"****"+temp.substring(7, 11);
		phoneText.setText(phoneStr);
	}
}
