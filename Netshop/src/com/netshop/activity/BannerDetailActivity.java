package com.netshop.activity;

import com.netshop.app.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;

public class BannerDetailActivity extends Activity {
	public WebView webView;
	public String url;
	public void onCreate(Bundle bundle){
		super.onCreate(bundle);
		setContentView(R.layout.banner_detial);
		findViewById(R.id.back_img).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		Intent intent = getIntent();
		url = intent.getStringExtra("url");
		webView = (WebView) findViewById(R.id.webview);
		if(url!=null){
			webView.loadUrl(url);
		}
	}
}
