package com.netshop.adapter;

import java.util.List;

import com.netshop.app.NetShopApp;
import com.netshop.entity.MyListItem;
import com.netshop.util.NetShopUtil;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MyAdapter extends BaseAdapter {
	
	private Context context;
	private List<MyListItem> myList;
	
	public MyAdapter(Context context, List<MyListItem> myList) { 
		this.context = context; 
		this.myList = myList;
	}
	
	public int getCount() {
		return myList.size(); 
	} 
	public Object getItem(int position) {
		return myList.get(position);
	} 
	public long getItemId(int position) {
		return position;
	} 
	
	public View getView(int position, View convertView, ViewGroup parent)
	{ 
		MyListItem myListItem = myList.get(position); 
		return new MyAdapterView(this.context, myListItem ); 
	}

	class MyAdapterView extends LinearLayout { 
		public static final String LOG_TAG = "MyAdapterView";
		
		public MyAdapterView(Context context, MyListItem myListItem ) { 
		super(context);
		this.setOrientation(HORIZONTAL); 
		
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(500, NetShopUtil.dip2px(context, 30)); 
		params.setMargins(1, 1, 1, 1); 
		//LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(200, LayoutParams.WRAP_CONTENT); 
		TextView name = new TextView( context ); 
		//name.setLayoutParams(param);
		name.setTextSize(18);
		name.setTextColor(0xff494949);
		name.setText( myListItem.getName() ); 
		addView( name, params); 
		
//		LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(200, NetShopUtil.dip2px(context, 30)); 
//		params2.setMargins(1, 1, 1, 1); 
//		
//		TextView pcode = new TextView(context); 
//		pcode.setText(myListItem.getPcode()); 
//		addView( pcode, params2); 
//		pcode.setVisibility(GONE);

		}		 

		}

}