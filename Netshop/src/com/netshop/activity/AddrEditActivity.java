package com.netshop.activity;

import com.netshop.app.R;
import com.netshop.net.HttpRequest;
import com.netshop.net.HttpRequest.HttpCallBack;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class AddrEditActivity extends Activity {
	private EditText name, phone, addr;
	private Button confirmBtn;
	private ImageView backImg;
	private TextView title;

	public void onCreate(Bundle bundle){
		super.onCreate(bundle);
		setContentView(R.layout.new_consignee);
		backImg = (ImageView) findViewById(R.id.title_back_img);
		backImg.setVisibility(View.VISIBLE);
		backImg.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				finish();
			}
		});
		title = (TextView)findViewById(R.id.title_text);
		title.setText("新建收货人");
		name = (EditText)findViewById(R.id.newconsignee_edit_consign);
		phone = (EditText)findViewById(R.id.newconsignee_edit_conn);
		addr = (EditText)findViewById(R.id.newconsignee_edit_addr);
		confirmBtn = (Button)findViewById(R.id.newconsignee_btn_comm);
		confirmBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if(name.getText().toString().equals("")){
					Toast.makeText(AddrEditActivity.this, "名称不能为空", Toast.LENGTH_SHORT).show();
					return;
				}
				if(phone.getText().toString().trim().equals("")){
			Toast.makeText(AddrEditActivity.this, "联系方式不能为空", Toast.LENGTH_SHORT).show();
			return;
			}
				if(addr.getText().toString().trim().equals("")){
					Toast.makeText(AddrEditActivity.this, "地址不能为空", Toast.LENGTH_SHORT).show();
					return;
				}
				
				HttpRequest request = new HttpRequest("6", "0003");
				String pc = "name:"+name.getText().toString().trim()+";phone:"+phone.getText().toString().trim()
						+";address:"+addr.getText().toString().trim();
				request.setPc(pc);
				request.request(new HttpCallBack() {
					
					@Override
					public void success(String json) {
						setResult(101);
						Toast.makeText(AddrEditActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
						finish();
					}
					
					@Override
					public void fail(String failReason) {
						Toast.makeText(AddrEditActivity.this, failReason, Toast.LENGTH_SHORT).show();
						
					}
				});
			}
		});
	}
}
