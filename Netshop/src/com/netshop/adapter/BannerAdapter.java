package com.netshop.adapter;

import java.util.ArrayList;
import java.util.List;

import com.netshop.activity.BannerDetailActivity;
import com.netshop.app.R;
import com.netshop.entity.BannerEntity.Banner;
import com.netshop.util.NetShopUtil;

import android.content.Context;
import android.content.Intent;
import android.support.mdroid.cache.DefaultLoader;
import android.support.mdroid.cache.ImageWorker;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

public class BannerAdapter extends PagerAdapter {
	public Context context;
	public List<Banner> banners;
	public ImageWorker worker;
	public ArrayList<View> views;
	public BannerAdapter(Context context,List<Banner> banners){
		this.context = context;
		this.banners = banners;
		worker = new DefaultLoader(context);
		worker.setRequestWidthAndHeight(NetShopUtil.getScreenWidth(context), 400);
	}
	@Override
	public int getCount() {
		return banners.size();
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		
		return view == object;
	}

	@Override
	public Object instantiateItem(ViewGroup container,final int position) {
		View view = LayoutInflater.from(context).inflate(R.layout.banner, null);
		ImageView image = (ImageView) view.findViewById(R.id.img);
		//LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		//image.setLayoutParams(params);
		worker.loadImage(banners.get(position).getImg(), image);
		image.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(context,BannerDetailActivity.class);
				intent.putExtra("url", banners.get(position).getUrl());
				context.startActivity(intent);
			}
		});
		container.addView(view);
		return view;
	}
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		//container.removeView((View)object);
	}
	

}
