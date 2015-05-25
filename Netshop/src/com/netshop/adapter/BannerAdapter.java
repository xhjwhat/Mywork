package com.netshop.adapter;

import java.util.List;

import com.netshop.activity.BannerDetailActivity;
import com.netshop.entity.BannerEntity.Banner;
import com.netshop.util.NetShopUtil;

import android.content.Context;
import android.content.Intent;
import android.support.mdroid.cache.DefaultLoader;
import android.support.mdroid.cache.ImageWorker;
import android.support.v4.view.PagerAdapter;
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
	public BannerAdapter(Context context,List<Banner> banners){
		this.context = context;
		this.banners = banners;
		worker = new DefaultLoader(context);
		int width = NetShopUtil.getScreenHeight(context);
		worker.setRequestWidthAndHeight(width, 300);
	}
	@Override
	public int getCount() {
		return banners.size();
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Object instantiateItem(ViewGroup container,final int position) {
		ImageView image = new ImageView(context);
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		image.setLayoutParams(params);
		worker.loadImage(banners.get(position).getImg(), image);
		image.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(context,BannerDetailActivity.class);
				intent.putExtra("url", banners.get(position).getUrl());
				context.startActivity(intent);
			}
		});
		container.addView(image);
		return image;
	}
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		
	}
	

}
