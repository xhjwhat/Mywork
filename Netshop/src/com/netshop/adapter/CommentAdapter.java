package com.netshop.adapter;

import java.util.List;

import com.netshop.app.R;
import com.netshop.entity.Comment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CommentAdapter extends BaseAdapter {
	public List<Comment> datas;
	public Context context;
	public CommentAdapter (Context context,List<Comment> list){
		this.context = context;
		datas = list;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
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
			convertView = LayoutInflater.from(context).inflate(R.layout.shop_comment_item, null);
			holder = new HolderView();
			holder.name = (TextView)convertView.findViewById(R.id.shopcom_name);
			holder.time = (TextView)convertView.findViewById(R.id.shopcom_time);
			holder.comment = (TextView)convertView.findViewById(R.id.shopcom_comment);
			convertView.setTag(holder);
		}else{
			holder = (HolderView) convertView.getTag();
		}
		holder.name.setText(datas.get(position).getNickname());
		holder.time.setText(datas.get(position).getTime());
		holder.comment.setText(datas.get(position).getContent());
		return convertView;
	}
	class HolderView {
		TextView name,time,comment;
	}
}
