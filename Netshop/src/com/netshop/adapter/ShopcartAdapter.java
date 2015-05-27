package com.netshop.adapter;

import java.util.List;

import android.content.Context;
import android.support.mdroid.cache.DefaultLoader;
import android.support.mdroid.cache.ImageWorker;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.netshop.app.R;
import com.netshop.entity.ShopcartItem;

public class ShopcartAdapter extends BaseAdapter {
	public List<ShopcartItem> datas;
	public Context context;
	public ImageWorker worker;
	public ShopcartAdapter(Context context,List<ShopcartItem> list){
		this.context = context;
		datas = list;
		worker = new DefaultLoader(context);
	}
	@Override
	public int getCount() {
		return datas.size();
	}

	@Override
	public Object getItem(int position) {
		return datas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		HolderView holder = null;
		if(convertView == null){
			convertView = LayoutInflater.from(context).inflate(R.layout.shop_car_item, null);
			holder = new HolderView();
			holder.shopName = (TextView) convertView.findViewById(R.id.shop_car_store_name);
			holder.productName = (TextView)convertView.findViewById(R.id.shop_car_product_name);
			holder.productWeight = (TextView)convertView.findViewById(R.id.shop_car_product_name);
			holder.productPrice = (TextView)convertView.findViewById(R.id.shop_car_price_text);
			holder.productNum = (TextView)convertView.findViewById(R.id.shop_car_num);
			holder.edit = (TextView)convertView.findViewById(R.id.shop_car_store_edit);
			//holder.selectedImg = (ImageView)convertView.findViewById(R.id.shop_car_select_img);
			holder.productImg = (ImageView)convertView.findViewById(R.id.shop_car_product_img);
			convertView.setTag(holder);
		}else{
			holder = (HolderView) convertView.getTag();
		}
		holder.shopName.setText(datas.get(position).getShop().getName());
		holder.productName.setText(datas.get(position).getProduct().getPname());
		holder.productWeight.setText(datas.get(position).getProduct().getWeight());
		holder.productPrice.setText(datas.get(position).getProduct().getPrice());
		holder.productNum.setText(datas.get(position).getNum());
		worker.loadImage(datas.get(position).getProduct().getImg(), holder.productImg);
//		convertView.setOnClickListener(new OnClickListener() {
//			public void onClick(View v) {
//				
//			}
//		});
		return convertView;
	}
	
	class HolderView{
		TextView shopName,productName,productWeight,productPrice,productNum;
		TextView edit;
		ImageView selectedImg,productImg;
	}

}
