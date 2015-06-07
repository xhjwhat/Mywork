package com.netshop.activity;

import java.util.ArrayList;
import java.util.List;

import com.google.l99gson.Gson;
import com.netshop.adapter.AddrAdapter;
import com.netshop.app.R;
import com.netshop.entity.Addr;
import com.netshop.entity.AddrEntity;
import com.netshop.entity.AddrsEntity;
import com.netshop.net.HttpRequest;
import com.netshop.net.HttpRequest.HttpCallBack;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class AddrManagerActivity extends Activity {
	public static final int DELETE = 0;
	public static final int EDIT = 1;
	public static final int CHECK_DEFAULT =2;
	public static final int ADD_NEW = 3;
	private ImageView backImg;
	private TextView title;
	private TextView newText;
	private ListView list;
	private AddrAdapter adapter;
	private List<Addr> datas;
	String from;
	public void onCreate(Bundle bundle){
		super.onCreate(bundle);
		setContentView(R.layout.addr_manage);
		backImg = (ImageView) findViewById(R.id.title_back_img);
		backImg.setVisibility(View.VISIBLE);
		backImg.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				finish();
			}
		});
		title = (TextView)findViewById(R.id.title_text);
		title.setText("地址管理");
		
		from = getIntent().getStringExtra("from");
		newText = (TextView)findViewById(R.id.add_new);
		newText.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent= new Intent(AddrManagerActivity.this,AddrEditActivity.class);
				startActivityForResult(intent, ADD_NEW);
			}
		});
		list = (ListView) findViewById(R.id.addr_list);
		list.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if(from!=null && from.equals("order")){
					Intent intent = new Intent();
					intent.putExtra("addr", datas.get(position));
					setResult(1, intent);
					finish();
				}
			}
		});
		datas = new ArrayList<Addr>();
		adapter = new AddrAdapter(this, datas, handler);
		list.setAdapter(adapter);
		initData();
	}
	
	public void initData(){
		datas.clear();
//		HttpRequest defaultRequest = new HttpRequest("6", "0001");
//		defaultRequest.request(new HttpCallBack() {
//			public void success(String json) {
//				Gson gson = new Gson();
//				Addr entity = gson.fromJson(json, Addr.class);
//				if(entity!=null){
//					datas.add(0, entity);
//					adapter.setList(datas);
//					adapter.notifyDataSetChanged();
//				}
//			}
//			public void fail(String failReason) {
//				
//			}
//		});
		HttpRequest nomalRequest = new HttpRequest("6", "0002");
		nomalRequest.setPs("20");
		nomalRequest.setPg("1");
		nomalRequest.request(new HttpCallBack() {
			public void success(String json) {
				Gson gson = new Gson();
				AddrsEntity entitys = null;
				try{
					entitys = gson.fromJson(json, AddrsEntity.class);
				}catch(Exception e){
					e.printStackTrace();
				}
				
				if(entitys!=null){
					datas.addAll(entitys.getList().getDelivery());
					adapter.setList(datas);
					adapter.notifyDataSetChanged();
				}else{
					AddrEntity entity = gson.fromJson(json, AddrEntity.class);
					if(entity!=null){
						datas.add(entity.getList().getDelivery());
						adapter.setList(datas);
						adapter.notifyDataSetChanged();
					}
				}
			}
			public void fail(String failReason) {
				
			}
		});
	}
	Handler handler = new Handler(){
		public void handleMessage(Message msg){
			switch(msg.what){
			case DELETE:
				HttpRequest delRequest = new HttpRequest("6", "0004");
				delRequest.setPc(msg.obj.toString());
				delRequest.request(new HttpCallBack() {
					public void success(String json) {
						initData();
					}
					public void fail(String failReason) {
						Toast.makeText(AddrManagerActivity.this, failReason, Toast.LENGTH_SHORT).show();
					}
				});
				break;
			case EDIT:
				Intent intent = new Intent(AddrManagerActivity.this,AddrEditActivity.class);
				startActivity(intent);
				break;
			case CHECK_DEFAULT:
				HttpRequest defaultRequest = new HttpRequest("6", "0005");
				defaultRequest.setPc(msg.obj.toString());
				defaultRequest.request(new HttpCallBack() {
					public void success(String json) {
						initData();
					}
					public void fail(String failReason) {
						Toast.makeText(AddrManagerActivity.this, failReason, Toast.LENGTH_SHORT).show();
					}
				});
				break;	
			}
		}
	};
	

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode == ADD_NEW){
			initData();
		}
	}
}
