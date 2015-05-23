package com.netshop.adapter;

import java.util.ArrayList;
import java.util.List;







import com.netshop.entity.MyListItem;
import com.netshop.util.DBManager;
import com.netshop.util.NetShopUtil;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CityAdapter extends BaseAdapter {
	public Context context;
	public List<MyListItem> proviceList;
	public List<MyListItem> cityList;
	public List<MyListItem> countyList;
	public List<MyListItem> currentList;
	public static final int PROVICE = 0;
	public static final int CITY = 1;;
	public static final int COUNTY = 2;
	public int type = PROVICE;
	public DBManager dbm;
	public SQLiteDatabase db;
	public Handler handler;
	public String provice;
	public String city;
	public String county;
	public  void setCurrentList(List<MyListItem> list){
		currentList = list;
	}
	public CityAdapter(Context context,Handler handler){
		this.context = context;
		this.handler = handler;
		currentList = queryProList();
	}
	public void reset(){
		type = PROVICE;
		provice = "";
		city = "";
		county = "";
		currentList = queryProList();
		notifyDataSetChanged();
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return currentList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return currentList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		TextView text;
		if(convertView == null){
			text = new TextView(context);
			LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, 
					NetShopUtil.dip2px(context, 50));
			text.setLayoutParams(params);
			text.setGravity(Gravity.CENTER);
			text.setTextColor(0xff494949);
			text.setTextSize(18);
			text.setBackgroundColor(0xffbbbbbb);
			convertView = text;
			convertView.setTag(text);
		}else{
			text = (TextView) convertView.getTag();
		}
		text.setText(currentList.get(position).getName());
		text.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				switch(type){
				case PROVICE:
					provice = currentList.get(position).getName();
					currentList = queryCityList(currentList.get(position).getPcode());
					type = CITY;
					notifyDataSetChanged();
					break;
				case CITY:
					city = currentList.get(position).getName();
					String temp = currentList.get(position).getPcode();
					currentList = queryCountyList(temp);
					type = COUNTY;
					notifyDataSetChanged();
					break;
				case COUNTY:
					county = currentList.get(position).getName();
					Message msg=handler.obtainMessage();
					String pc = "province:"+provice.trim()+";city:"+city.trim()+";country:"+county.trim();
					msg.obj = pc;
					handler.sendMessage(msg);
					break;
				}
			}
		});
		return convertView;
	}
	public List<MyListItem> queryProList(){
		List<MyListItem> datasList = new ArrayList<MyListItem>();
		dbm = new DBManager(context);
	 	dbm.openDatabase();
	 	db = dbm.getDatabase();
	 	try {    
	        String sql = "select * from province";  
	        Cursor cursor = db.rawQuery(sql,null);  
	        cursor.moveToFirst();
	        while (!cursor.isLast()){ 
		        String code=cursor.getString(cursor.getColumnIndex("code")); 
		        byte bytes[]=cursor.getBlob(2); 
		        String name=new String(bytes,"gbk");
		        MyListItem myListItem=new MyListItem();
		        myListItem.setName(name);
		        myListItem.setPcode(code);
		        datasList.add(myListItem);
		        cursor.moveToNext();
	        }
	        String code=cursor.getString(cursor.getColumnIndex("code")); 
	        byte bytes[]=cursor.getBlob(2); 
	        String name=new String(bytes,"gbk");
	        MyListItem myListItem=new MyListItem();
	        myListItem.setName(name);
	        myListItem.setPcode(code);
	        datasList.add(myListItem);
	        
	    } catch (Exception e) {  
	    } 
	 	dbm.closeDatabase();
	 	db.close();	
		return datasList;
	}
	
	public List<MyListItem> queryCityList(String pcode){
		List<MyListItem> datasList = new ArrayList<MyListItem>();
		dbm = new DBManager(context);
	 	dbm.openDatabase();
	 	db = dbm.getDatabase();
		
	 	try {    
	        String sql = "select * from city where pcode='"+pcode+"'";  
	        Cursor cursor = db.rawQuery(sql,null);  
	        cursor.moveToFirst();
	        while (!cursor.isLast()){ 
		        String code=cursor.getString(cursor.getColumnIndex("code")); 
		        byte bytes[]=cursor.getBlob(2); 
		        String name=new String(bytes,"gbk");
		        MyListItem myListItem=new MyListItem();
		        myListItem.setName(name);
		        myListItem.setPcode(code);
		        datasList.add(myListItem);
		        cursor.moveToNext();
	        }
	        String code=cursor.getString(cursor.getColumnIndex("code")); 
	        byte bytes[]=cursor.getBlob(2); 
	        String name=new String(bytes,"gbk");
	        MyListItem myListItem=new MyListItem();
	        myListItem.setName(name);
	        myListItem.setPcode(code);
	        datasList.add(myListItem);
	        
	    } catch (Exception e) {  
	    } 
	 	dbm.closeDatabase();
	 	db.close();	
		return datasList;
	}
	public List<MyListItem> queryCountyList(String pcode){
		List<MyListItem> datasList = new ArrayList<MyListItem>();
		dbm = new DBManager(context);
	 	dbm.openDatabase();
	 	db = dbm.getDatabase();
		
	 	try {    
	 		String sql = "select * from district where pcode='"+pcode+"'";    
	        Cursor cursor = db.rawQuery(sql,null);  
	        cursor.moveToFirst();
	        while (!cursor.isLast()){ 
		        String code=cursor.getString(cursor.getColumnIndex("code")); 
		        byte bytes[]=cursor.getBlob(2); 
		        String name=new String(bytes,"gbk");
		        MyListItem myListItem=new MyListItem();
		        myListItem.setName(name);
		        myListItem.setPcode(code);
		        datasList.add(myListItem);
		        cursor.moveToNext();
	        }
	        String code=cursor.getString(cursor.getColumnIndex("code")); 
	        byte bytes[]=cursor.getBlob(2); 
	        String name=new String(bytes,"gbk");
	        MyListItem myListItem=new MyListItem();
	        myListItem.setName(name);
	        myListItem.setPcode(code);
	        datasList.add(myListItem);
	        
	    } catch (Exception e) {  
	    } 
	 	dbm.closeDatabase();
	 	db.close();	
		return datasList;
	}
}
