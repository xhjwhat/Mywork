package com.netshop.adapter;

import java.util.List;

import android.content.Context;
import android.support.mdroid.cache.DefaultLoader;
import android.support.mdroid.cache.ImageWorker;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.netshop.app.R;
import com.netshop.entity.ShopEntity;

public class StoreAdapter extends BaseAdapter {
	public Context context;
	public List<ShopEntity.Shop> shopList;
	public ImageWorker imageWork;
	public StoreAdapter(Context context,List<ShopEntity.Shop> shops){
		this.context = context;
		shopList = shops;
		imageWork = new DefaultLoader(context);
	}
	@Override
	public int getCount() {
		return shopList.size();
	}

	@Override
	public Object getItem(int position) {
		return shopList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		HolderView holder;
		if(convertView == null){
			holder = new HolderView();
			convertView = LayoutInflater.from(context).inflate(R.layout.store_item, null);
			holder.storeImg = (ImageView) convertView.findViewById(R.id.store_item_img);
			holder.storeNameText = (TextView)convertView.findViewById(R.id.store_item_name);
			holder.addrText = (TextView)convertView.findViewById(R.id.store_item_addr);
			holder.contactText = (TextView)convertView.findViewById(R.id.store_item_conn_people);
			holder.phoneText = (TextView)convertView.findViewById(R.id.store_item_conn_phone);
			holder.newImg = (ImageView)convertView.findViewById(R.id.store_item_new_img);
			convertView.setTag(holder);
		}else{
			holder = (HolderView) convertView.getTag();
		}
		imageWork.loadImage(shopList.get(position).getImg(), holder.storeImg);
		holder.storeNameText.setText(shopList.get(position).getName());
		holder.addrText.setText(shopList.get(position).getAddress());
		holder.contactText.setText(shopList.get(position).getLinkname());
		holder.phoneText.setText(shopList.get(position).getPhone());
		if(shopList.get(position).getIsnew().equals("0")){
			holder.newImg.setVisibility(View.GONE);
		}else{
			holder.newImg.setVisibility(View.VISIBLE);
		}
		return convertView;
	}
	public class HolderView{
		ImageView storeImg;
		TextView storeNameText;
		TextView addrText;
		TextView contactText;
		TextView phoneText;
		ImageView newImg;
	}

}
