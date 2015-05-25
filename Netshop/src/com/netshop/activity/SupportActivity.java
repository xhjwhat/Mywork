package com.netshop.activity;

import com.netshop.app.R;
import com.netshop.net.HttpRequest;
import com.netshop.net.HttpRequest.HttpCallBack;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class SupportActivity extends Activity {
	TextView title;
	private EditText edit;
	private Button commitBtn;
	private ImageView backImg;
	public void onCreate(Bundle bundle){
		super.onCreate(bundle);
		setContentView(R.layout.info);
		init();
		
	}
	public void init(){
		Intent intent = getIntent();
		title = (TextView) findViewById(R.id.title_text);
		if(intent.getStringExtra("title")!=null){
			title.setText(intent.getStringExtra("title"));
		}
		backImg=(ImageView) findViewById(R.id.title_back_img);
		backImg.setVisibility(View.VISIBLE);
		backImg.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				finish();
			}
		});
		edit = (EditText) findViewById(R.id.person_edit);
		commitBtn = (Button)findViewById(R.id.person_commit);
		commitBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if(edit.getText().toString().trim().equals("")){
					Toast.makeText(SupportActivity.this, "输入内容为空，请输入内容！", Toast.LENGTH_SHORT).show();
					return;
				}
				HttpRequest request = new HttpRequest("7","0001");
				request.setPc(edit.getText().toString());
				request.request(new HttpCallBack() {
					public void success(String json) {
						Toast.makeText(SupportActivity.this, "提交成功", Toast.LENGTH_SHORT).show();
						finish();
					}
					public void fail(String failReason) {
						Toast.makeText(SupportActivity.this, failReason, Toast.LENGTH_SHORT).show();
					}
				});
			}
		});
	}
}
