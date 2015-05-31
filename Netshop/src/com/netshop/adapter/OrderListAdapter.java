package com.netshop.adapter;

import java.util.List;

import com.netshop.adapter.ProductAdapter.HolderView;
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
import android.widget.ListView;
import android.widget.TextView;

public class OrderListAdapter extends BaseAdapter {
	public List<Product> dataList;
	public Context context;
	public ImageWorker worker;
	public OrderListAdapter(Context context,List<Product> list){
		this.context = context;
		dataList = list;
		worker = new DefaultLoader(context);
	}
	@Override
	public int getCount() {
		
		return dataList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return dataList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		HolderView holder;
		if(convertView == null){
			holder = new HolderView();
			convertView = LayoutInflater.from(context).inflate(R.layout.order_item, null);
			holder.productImg = (ImageView) convertView.findViewById(R.id.shop_car_product_img);
			holder.productName = (TextView)convertView.findViewById(R.id.confirm_product_name);
			holder.productPrice = (TextView)convertView.findViewById(R.id.confirm_price_text);
			holder.productWeight = (TextView)convertView.findViewById(R.id.confirm_weight_text);
			holder.numText = (TextView)convertView.findViewById(R.id.confirm_num_text);
			convertView.setTag(holder);
		}else{
			holder = (HolderView) convertView.getTag();
		}
		if(dataList.get(position).getPimg() != null){
			worker.loadImage(dataList.get(position).getPimg(), holder.productImg);
		}else{
			holder.productImg.setImageResource(R.drawable.ic_launcher);
		}
		holder.productName.setText(dataList.get(position).getPname());
		holder.productPrice.setText(dataList.get(position).getPrice());
		holder.productWeight.setText("袋/"+dataList.get(position).getWeight()+"kg");
		holder.numText.setText(dataList.get(position).getNum());
		return convertView;
	}
	public class HolderView{
		public ImageView productImg;
		public TextView productName;
		public TextView productPrice;
		public TextView productWeight,numText;
	}

}
