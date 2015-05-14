package com.netshop.adapter;

import java.util.List;

import com.netshop.entity.ProductEntity;
import com.netshop.entity.ProductTypes;
import com.netshop.entity.ProductTypes.CType;
import com.netshop.entity.ProductTypes.ProductType;
import com.netshop.util.NetShopUtil;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

public class ProductGridAdapter extends BaseAdapter {
	private List<CType> datas;
	private Context context;
	private int selectItem = 0;
	
	public ProductGridAdapter(Context context,List<CType> list){
		this.context = context;
		this.datas = list;
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
	
	public void setSelectItem(int selected){
		selectItem = selected;
		
	}
	
	public void setData(List<CType> datas){
		this.datas = datas;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TextView text = null;
		if(convertView == null){
			text = new TextView(context);
		}else{
			text = (TextView) convertView;
		}
		text.setTextColor(0x494949);
		text.setTextSize(18.0f);
		text.setGravity(Gravity.CENTER);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, NetShopUtil.dip2px(context, 40));
		text.setLayoutParams(params);
		text.setText(datas.get(position).getName());
		if(position == selectItem){
			text.setBackgroundColor(0xffffff);
			text.setTextColor(0xf5ac39);
		}else{
			text.setBackgroundColor(Color.TRANSPARENT);
		}
		text.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
			}
		});
		return text;
	}

}
