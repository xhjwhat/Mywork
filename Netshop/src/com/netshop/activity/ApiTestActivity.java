package com.netshop.activity;

import com.netshop.app.R;
import com.netshop.net.HttpRequest;
import com.netshop.net.HttpRequest.HttpCallBack;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class ApiTestActivity extends Activity implements OnClickListener{
	private HttpRequest request;
	public void onCreate(Bundle bundle){
		super.onCreate(bundle);
		setContentView(R.layout.api_test);
	}
	HttpCallBack callback = new HttpCallBack() {
		@Override
		public void success(String json) {
			
		}
		
		@Override
		public void fail(String failReason) {
			
		}
	};
	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.btn_registe:
			request = new HttpRequest("1", "0001");
			request.request(HttpRequest.REQUEST_GET, callback);
			break;
		
		}
		
	}
}
