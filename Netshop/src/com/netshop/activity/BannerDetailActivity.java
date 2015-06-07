package com.netshop.activity;

import com.netshop.app.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class BannerDetailActivity extends Activity {
	public WebView webView;
	public String urlString;
	public ProgressDialog dialog;
	public void onCreate(Bundle bundle){
		super.onCreate(bundle);
		setContentView(R.layout.banner_detial);
		findViewById(R.id.back_img).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
//		dialog = new ProgressDialog(this);
//		dialog.setMessage("正在加载... ");
//		dialog.show();
		Intent intent = getIntent();
		urlString = intent.getStringExtra("url");
		webView = (WebView) findViewById(R.id.webview);
		WebSettings webSettings =   webView .getSettings();       
		webSettings.setUseWideViewPort(true);//设置此属性，可任意比例缩放
		webSettings.setLoadWithOverviewMode(true);
		// 设置可以支持缩放 
		webSettings.setSupportZoom(true); 
		// 设置出现缩放工具 
		webSettings.setBuiltInZoomControls(true);
		//自适应屏幕
		webSettings.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
		webSettings.setLoadWithOverviewMode(true);
//		webView.setWebViewClient(new WebViewClient(){
//			@Override
//			public boolean shouldOverrideUrlLoading(WebView view, String url) {
//				view.loadUrl(urlString);
//				return true;
//			}
//			@Override
//			public void onPageFinished(WebView view, String url) {
//				// TODO Auto-generated method stub
//				 super.onPageFinished(view, url);
//				if(dialog!=null && dialog.isShowing()){
//					dialog.dismiss();
//				}
//			}
//
//			@Override
//			public void onReceivedError(WebView view, int errorCode,
//					String description, String failingUrl) {
//				// TODO Auto-generated method stub
//				super.onReceivedError(view, errorCode, description, failingUrl);
//				Toast.makeText(BannerDetailActivity.this, "加载失败，请稍候再试", Toast.LENGTH_SHORT)
//						.show();
//			}
//		});
		if(urlString!=null){
			webView.loadUrl(urlString);
		}
	}
}
