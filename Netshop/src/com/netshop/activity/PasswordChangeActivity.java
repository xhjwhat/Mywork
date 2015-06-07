package com.netshop.activity;

import com.netshop.app.R;
import com.netshop.fragment.Password1Fragment;
import com.netshop.fragment.Password2Fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class PasswordChangeActivity extends FragmentActivity {
	ImageView backImg;
	TextView title;
	FrameLayout frameLayout;
	FragmentManager manager;
	public void onCreate(Bundle bundle){
		super.onCreate(bundle);
		setContentView(R.layout.password_change);
		backImg = (ImageView) findViewById(R.id.title_back_img);
		backImg.setVisibility(View.VISIBLE);
		backImg.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		title = (TextView)findViewById(R.id.title_text);
		title.setText("修改密码");
		manager = getSupportFragmentManager();
		FragmentTransaction transaction =manager.beginTransaction();
		Password2Fragment fragment = new Password2Fragment();
		Bundle bundle2 = new Bundle();
		bundle2.putString("type", "password");
		fragment.setArguments(bundle2);
		transaction.add(R.id.password_frame, fragment);
		transaction.commit();
	}
}
