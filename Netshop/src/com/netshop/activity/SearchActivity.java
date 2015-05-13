package com.netshop.activity;

import com.netshop.app.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class SearchActivity extends Activity {
	private ImageView backImg;
	private ImageView searchImg;
	private EditText searchEdit;
	private ListView listView;
	public void onCreate(Bundle bundle){
		super.onCreate(bundle);
		setContentView(R.layout.search);
		backImg = (ImageView)findViewById(R.id.title_back);
		backImg.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				finish();
			}
		});
		searchEdit = (EditText)findViewById(R.id.search_edit);
		searchImg = (ImageView)findViewById(R.id.seach_img);
		searchImg.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
			}
		});
	}
}
