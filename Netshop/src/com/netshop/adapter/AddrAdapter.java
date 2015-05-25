package com.netshop.adapter;

import java.util.List;

import com.netshop.activity.AddrManagerActivity;
import com.netshop.app.R;
import com.netshop.entity.Addr;
import com.netshop.net.HttpRequest;
import com.netshop.net.HttpRequest.HttpCallBack;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AddrAdapter extends BaseAdapter {
	public List<Addr> addrs;
	public Context context;
	public Handler handler;
	public AddrAdapter(Context context,List<Addr> datas,Handler handler){
		this.context = context;
		addrs = datas;
		this.handler = handler;
	}
	public void setList(List<Addr> datas){
		addrs = datas;
	}
	@Override
	public int getCount() {
		return addrs.size();
	}

	@Override
	public Object getItem(int position) {
		return addrs.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		HoldView holder;
		if(convertView == null){
			holder = new HoldView();
			convertView = LayoutInflater.from(context).inflate(R.layout.addr_manage_item, null);
			holder.addr = (TextView)convertView.findViewById(R.id.addmanage_addr);
			holder.name = (TextView)convertView.findViewById(R.id.addmanage_name);
			holder.phone = (TextView)convertView.findViewById(R.id.addmanage_phone);
			holder.del = (TextView)convertView.findViewById(R.id.addrmanage_delete_text);
			//holder.edit = (TextView)convertView.findViewById(R.id.addrmanage_eidt_text);
			holder.check = (ImageView)convertView.findViewById(R.id.addrmanage_default_img);
			holder.layout = convertView.findViewById(R.id.default_layout);
			convertView.setTag(holder);
		}
		else{
			holder = (HoldView) convertView.getTag();
		}
		if(addrs.get(position).getIsde().equals("1")){
			holder.layout.setVisibility(View.GONE);
		}else{
			holder.layout.setVisibility(View.VISIBLE);
			holder.check.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					Message msg = handler.obtainMessage();
					msg.obj = addrs.get(position).getId();
					msg.what = AddrManagerActivity.CHECK_DEFAULT;
					handler.sendMessage(msg);
				}
			});
//			holder.edit.setOnClickListener(new OnClickListener() {
//				public void onClick(View v) {
//					handler.sendEmptyMessage(AddrManagerActivity.EDIT);
//				}
//			});
			holder.del.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					Message msg = handler.obtainMessage();
					msg.obj = addrs.get(position).getId();
					msg.what = AddrManagerActivity.DELETE;
					handler.sendMessage(msg);
				}
			});
		}
		holder.addr.setText(addrs.get(position).getAddress());
		holder.name.setText(addrs.get(position).getName());
		holder.phone.setText(addrs.get(position).getPhone());
		return convertView;
	}
	class HoldView {
		TextView name,phone,addr,del,edit;
		ImageView check;
		View layout;
	}
}
