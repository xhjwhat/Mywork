package com.netshop.adapter;

import java.util.List;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.mdroid.cache.DefaultLoader;
import android.support.mdroid.cache.ImageWorker;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.netshop.app.R;
import com.netshop.entity.Product;
import com.netshop.entity.ShopcartItem;

public class ShopcartAdapter extends BaseAdapter {
	public List<Product> datas;
	public Context context;
	public ImageWorker worker;
	public Handler handler;
	public boolean allSelected = false;
	public ShopcartAdapter(Context context,List<Product> list,Handler handler){
		this.context = context;
		datas = list;
		this.handler = handler;
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
	public void setData(List<Product> list){
		datas = list;
	}
	public void setAllSelected(boolean selected){
		allSelected = selected;
	}
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final HolderView holder;
		if(convertView == null){
			convertView = LayoutInflater.from(context).inflate(R.layout.shop_car_item, null);
			holder = new HolderView();
			holder.productName = (TextView)convertView.findViewById(R.id.shop_car_product_name);
			holder.productWeight = (TextView)convertView.findViewById(R.id.shop_car_weight_text);
			holder.productPrice = (TextView)convertView.findViewById(R.id.shop_car_price_text);
			holder.productNum = (TextView)convertView.findViewById(R.id.shop_car_num);
			holder.edit = (TextView)convertView.findViewById(R.id.shop_car_store_edit);
			holder.checkBox = (CheckBox)convertView.findViewById(R.id.shop_car_check);
			holder.productImg = (ImageView)convertView.findViewById(R.id.shop_car_product_img);
			holder.priceLayout = convertView.findViewById(R.id.price_layout);
			holder.numLayout = convertView.findViewById(R.id.num_layout);
			holder.addImg = (ImageView)convertView.findViewById(R.id.add_img);
			holder.reduceImg = (ImageView)convertView.findViewById(R.id.reduce_img);
			holder.numEdit = (EditText)convertView.findViewById(R.id.num_edit);
			holder.delectBtn = (Button)convertView.findViewById(R.id.delete_btn);
			
			convertView.setTag(holder);
		}else{
			holder = (HolderView) convertView.getTag();
		}
		holder.checkBox.setClickable(false);
		if(allSelected){
			holder.checkBox.setChecked(true);
		}else{
			holder.checkBox.setChecked(false);
		}
		holder.edit.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if(holder.priceLayout.getVisibility() == View.VISIBLE){
					holder.priceLayout.setVisibility(View.GONE);
					holder.numLayout.setVisibility(View.VISIBLE);
					holder.edit.setText("完成");
				}else{
					holder.priceLayout.setVisibility(View.VISIBLE);
					holder.numLayout.setVisibility(View.GONE);
					datas.get(position).setNum(holder.numEdit.getText().toString());
					holder.productNum.setText(holder.numEdit.getText().toString());
					holder.edit.setText("编辑");
				}
			}
		});
		holder.delectBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Message msg = handler.obtainMessage();
				msg.what = -1;
				msg.arg1 = position;
				handler.sendMessage(msg);
			}
		});
		holder.addImg.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String numString = holder.numEdit.getText().toString();
				if(numString.equals("")){
					numString ="1";
				}
				int temp = Integer.valueOf(numString)+1;
				holder.numEdit.setText(temp+"");
			}
		});
		holder.reduceImg.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String numString = holder.numEdit.getText().toString();
				if(numString.equals("")){
					numString ="1";
				}
				int temp = Integer.valueOf(holder.numEdit.getText().toString());
				if(temp > 1){
					temp--;
					holder.numEdit.setText(temp+"");
				}
				
			}
		});
		holder.productName.setText(datas.get(position).getPname());
		holder.productWeight.setText("/袋/"+datas.get(position).getWeight()+"kg");
		holder.productPrice.setText("¥"+datas.get(position).getPrice());
		holder.productNum.setText(datas.get(position).getNum());
		worker.loadImage(datas.get(position).getImg(), holder.productImg);
		convertView.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if(holder.edit.getText().toString().equals("完成")){
					return;
				}
				Message msg = handler.obtainMessage();
				if(holder.checkBox.isChecked()){
					holder.checkBox.setChecked(false);
					msg.what = position;
					msg.arg1 = 0;
					handler.sendMessage(msg);
				}else{
					holder.checkBox.setChecked(true);
					msg.what = position;
					msg.arg1 = 1;
					handler.sendMessage(msg);
				}
			}
		});
		return convertView;
	}
	
	class HolderView{
		TextView shopName,productName,productWeight,productPrice,productNum;
		TextView edit;
		ImageView selectedImg,productImg,addImg,reduceImg;
		CheckBox checkBox;
		Button delectBtn;
		EditText numEdit;
		View priceLayout,numLayout;
	}

}
