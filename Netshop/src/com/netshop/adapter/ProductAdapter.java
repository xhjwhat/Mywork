package com.netshop.adapter;

import java.util.List;

import com.netshop.app.R;
import com.netshop.entity.Product;

import android.content.Context;
import android.support.mdroid.cache.DefaultLoader;
import android.support.mdroid.cache.ImageWorker;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ProductAdapter extends BaseAdapter {
	private Context context;
	private List<Product> datas;
	private ImageWorker worker;
	
	public ProductAdapter(Context context,List<Product> dataList){
		this.context = context;
		datas =dataList;
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
		HolderView holder;
		if(convertView == null){
			holder = new HolderView();
			convertView = LayoutInflater.from(context).inflate(R.layout.products_item, null);
			holder.productImg = (ImageView) convertView.findViewById(R.id.shop_car_product_img);
			holder.productName = (TextView)convertView.findViewById(R.id.confirm_product_name);
			holder.productPrice = (TextView)convertView.findViewById(R.id.confirm_money_text);
			holder.productWeight = (TextView)convertView.findViewById(R.id.confirm_weight_text);
			convertView.setTag(holder);
		}else{
			holder = (HolderView) convertView.getTag();
		}
		if(datas.get(position).getPimg() != null){
			worker.loadImage(datas.get(position).getPimg(), holder.productImg);
		}else{
			holder.productImg.setImageResource(R.drawable.ic_launcher);
		}
		holder.productName.setText(datas.get(position).getPname());
		holder.productPrice.setText(datas.get(position).getPrice());
		holder.productWeight.setText(datas.get(position).getWeight());
		return convertView;
	}
	public class HolderView{
		public ImageView productImg;
		public TextView productName;
		public TextView productPrice;
		public TextView productWeight;
	}
}
