package com.netshop.activity;

import com.netshop.app.R;
import com.netshop.view.MyWidget;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class GoodsInfoActivity extends Activity {
	private MyWidget widget;
	private ImageView backImg;
	private TextView title;
	public void onCreate(Bundle bundle){
		super.onCreate(bundle);
		setContentView(R.layout.goods_info);
		widget = (MyWidget)findViewById(R.id.widget);
		backImg = (ImageView) findViewById(R.id.title_back_img);
		backImg.setVisibility(View.VISIBLE);
		backImg.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		title = (TextView)findViewById(R.id.title_text);
		title.setText("商品详情");
	}
}
