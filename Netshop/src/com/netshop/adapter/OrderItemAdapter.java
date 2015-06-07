package com.netshop.adapter;

import java.util.List;

import com.netshop.app.R;
import com.netshop.entity.Order;
import com.netshop.entity.Product;

import android.content.Context;
import android.support.mdroid.cache.DefaultLoader;
import android.support.mdroid.cache.ImageWorker;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class OrderItemAdapter extends BaseAdapter {
	public List<Order> datas;
	public Context context;
	public ImageWorker worker;
	public OrderItemAdapter(Context context,List<Order> list){
		datas = list;
		this.context = context;
		worker = new DefaultLoader(context);
		worker.setRequestWidthAndHeight(200, 200);
	}
	public void setData(List<Order> list){
		datas = list;
	}
	@Override
	public int getCount() {
		
		return datas.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return datas.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		HolderView holder = null;
		if(convertView == null){
			convertView = LayoutInflater.from(context).inflate(R.layout.myorder_item, null);
			holder = new HolderView();
			holder.shopname = (TextView)convertView.findViewById(R.id.myorder_shopname);
			holder.total = (TextView)convertView.findViewById(R.id.myorder_total);
			//holder.btn = (Button)convertView.findViewById(R.id.myorder_btn);
			holder.layout = (LinearLayout)convertView.findViewById(R.id.myorder_list);
			convertView.setTag(holder);
		}else{
			holder = (HolderView) convertView.getTag();
		}
		holder.shopname.setText(datas.get(position).getShopname());
		holder.total.setText(datas.get(position).getTotal());
//		holder.btn.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				
//			}
//		});
		for(Product product:datas.get(position).getProducts()){
			holder.layout.removeAllViews();
			holder.layout.addView(getView(product, datas.get(position).getTime()));
			View view = new View(context);
			view.setBackgroundColor(0xff888888);
			holder.layout.addView(view,LinearLayout.LayoutParams.MATCH_PARENT,1);
		}
		
		return convertView;
	}
	class HolderView{
		TextView shopname;
		TextView total;
		//Button btn;
		LinearLayout layout;
	}
	public View getView(Product product,String date){
		View view = LayoutInflater.from(context).inflate(R.layout.myorder_item_detail, null);
		ImageView img=(ImageView) view.findViewById(R.id.shop_car_product_img);
		TextView name=(TextView)view.findViewById(R.id.confirm_product_name);
		TextView weight=(TextView)view.findViewById(R.id.confirm_product_weight);
		TextView price=(TextView)view.findViewById(R.id.confirm_price_text);
		TextView num=(TextView)view.findViewById(R.id.confirm_num_text);
		TextView time=(TextView)view.findViewById(R.id.confirm_time_text);
		worker.loadImage(product.getPimg(), img);
		name.setText(product.getPname());
		weight.setText(product.getWeight());
		price.setText(product.getPrice());
		num.setText("x"+product.getNum());
		time.setText(date.substring(0, 11));
		return view;
	}
}
