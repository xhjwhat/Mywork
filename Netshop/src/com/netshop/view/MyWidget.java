package com.netshop.view;


import com.netshop.app.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MyWidget extends LinearLayout {
	public Context context;
	public TextView firstText;
	public TextView secondText;
	public ImageView firstImage;
	public ImageView secondImage;
	public RelativeLayout firstLayout,secondLayout;
	public View firstView,secondView;
	public FrameLayout contentView;
	public String firstTitle,secondTitle;
	//public LinearLayout linear;
	public MyWidget(Context  context){
		super(context);
		this.context=context;
		init();
	}
	public MyWidget(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context=context;
		init();
	}
	public void init(){
		LayoutInflater flater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view=flater.inflate(R.layout.tab_host, this);
		firstText=(TextView) view.findViewById(R.id.first_text);
		secondText=(TextView) view.findViewById(R.id.second_text);
		firstImage=(ImageView) view.findViewById(R.id.first_img);
		secondImage=(ImageView) view.findViewById(R.id.sencond_img);
		//linear=(LinearLayout) view.findViewById(R.id.widget_banner);
		firstLayout=(RelativeLayout) view.findViewById(R.id.first_layout);
		firstLayout.setOnClickListener(listener);
		secondLayout=(RelativeLayout) view.findViewById(R.id.second_layout);
		secondLayout.setOnClickListener(listener);
		contentView=(FrameLayout) view.findViewById(R.id.content_view);
		firstText.setTextColor(0xffff0000);
		firstImage.setVisibility(View.VISIBLE);
	}
	public void clear(){
		firstText.setTextColor(0xff606060);
		secondText.setTextColor(0xff606060);
		firstImage.setVisibility(View.INVISIBLE);
		secondImage.setVisibility(View.INVISIBLE);
	}
	OnClickListener listener=new OnClickListener() {
		public void onClick(View v) {
			switch(v.getId()){
			case R.id.first_layout:
				clear();
				firstText.setTextColor(0xffff0000);
				firstImage.setVisibility(View.VISIBLE);
				setContentView(firstView);
				break;
			case R.id.second_layout:
				clear();
				secondText.setTextColor(0xffff0000);
				secondImage.setVisibility(View.VISIBLE);
				setContentView(secondView);
				break;
			}
			
		}
	};
	public void setContentView(View view){
		contentView.removeAllViews();
		contentView.addView(view);
	}
	public void setFirstView(View view){
		firstView=view;
	}
	public void setSecondView(View view){
		secondView=view;
	}
	public  void  setTitle(String first,String second){
		firstTitle=first;
		secondTitle=second;
		firstText.setText(firstTitle);
		secondText.setText(secondTitle);
	}
	/*public void setBannerVisible(boolean flag){
		if(flag){
			linear.setVisibility(View.VISIBLE);
		}else{
			linear.setVisibility(View.GONE);
		}
	}
	public LinearLayout getLinearBanner(){
		return  linear;
	}*/
}
