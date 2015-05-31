package com.netshop.adapter;

import java.util.List;

import com.netshop.activity.GoodsDetilsActivity;
import com.netshop.entity.ProductTypes.CType;
import com.netshop.util.NetShopUtil;

import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ProductGridAdapter extends BaseAdapter {
	private List<CType> datas;
	private Context context;
	
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
	
	
	public void setData(List<CType> datas){
		this.datas = datas;
	}
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		if(datas.get(position)==null) {
			return new TextView(context);
		}
		TextView text = null;
		if(convertView == null){
			text = new TextView(context);
		}else{
			text = (TextView) convertView;
		}
		text.setTextColor(0xff494949);
		text.setTextSize(18.0f);
		text.setGravity(Gravity.CENTER);
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,NetShopUtil.dip2px(context, 60));
		text.setLayoutParams(params);
		text.setText(datas.get(position).getName());
		text.setBackgroundColor(0xfff8f8f8);
//		text.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				Intent intent = new Intent(context,GoodsDetilsActivity.class);
//				intent.putExtra("id", datas.get(position).getId());
//				context.startActivity(intent);
//			}
//		});
		return text;
	}

}
