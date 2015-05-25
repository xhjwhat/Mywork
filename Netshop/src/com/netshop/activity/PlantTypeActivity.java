package com.netshop.activity;

import java.util.ArrayList;
import java.util.List;

import com.google.l99gson.Gson;
import com.netshop.app.R;
import com.netshop.entity.PlantAreaEntity;
import com.netshop.entity.PlantAreaEntity.PlantArea;
import com.netshop.net.HttpRequest;
import com.netshop.net.HttpRequest.HttpCallBack;
import com.netshop.util.NetShopUtil;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class PlantTypeActivity extends Activity {
	private TextView title;
	private ImageView backImg;
	private Button btn;
	private GridView gridView;
	private List<PlantArea> datas;
	private List<String> areaList;
	private GridAdapter adapter;
	private String name ="";
	
	private List<PlantArea> selectList;
	public void onCreate(Bundle bundle){
		super.onCreate(bundle);
		setContentView(R.layout.plant_type);
		backImg = (ImageView) findViewById(R.id.title_back_img);
		backImg.setVisibility(View.VISIBLE);
		backImg.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		title = (TextView)findViewById(R.id.title_text);
		title.setText("修改信息");
		gridView = (GridView)findViewById(R.id.planttype_grid);
		btn=(Button)findViewById(R.id.planttype_btn);
		btn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if(selectList!=null && selectList.size()>0){
					HttpRequest request =new HttpRequest("1", "0006");
					StringBuffer buffer = new StringBuffer();
					for(int i=0;i<selectList.size();i++){
						if(i==selectList.size()-1){
							buffer.append(selectList.get(i).getId());
							name = name+selectList.get(i).getName();
						}else{
							buffer.append(selectList.get(i).getId()+",");
							name = name+selectList.get(i).getName()+";";
						}
						
					}
					request.setPc("crops:"+buffer.toString());
					request.request(new HttpCallBack() {
						public void success(String json) {
							Toast.makeText(PlantTypeActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
							Intent intent = new Intent();
							intent.putExtra("name", name);
							setResult(PersonInfoActivity.PLANTTYPE,intent );
							finish();
						}
						public void fail(String failReason) {
							
						}
					});
				}
				
			}
		});
		HttpRequest request = new HttpRequest("1", "0009");
		request.request(new HttpCallBack() {
			@Override
			public void success(String json) {
				Gson gson = new Gson();
				PlantAreaEntity entity = gson.fromJson(json, PlantAreaEntity.class);
				datas = entity.getBean();
				areaList = new ArrayList<String>();
				selectList = new ArrayList<PlantAreaEntity.PlantArea>();
				for(PlantArea temp: datas){
					areaList.add(temp.getName());
				}
				adapter = new GridAdapter();
				gridView.setAdapter(adapter);
			}
			@Override
			public void fail(String failReason) {
				
			}
		});
	}
	class GridAdapter extends BaseAdapter{
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

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			TextView textView = new TextView(PlantTypeActivity.this);
			LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
					NetShopUtil.dip2px(PlantTypeActivity.this, 40));
			textView.setLayoutParams(params);
			textView.setTextSize(18);
			textView.setGravity(Gravity.CENTER);
			textView.setTextColor(0xff494949);
			textView.setBackgroundColor(0xffffffff);
			textView.setText(datas.get(position).getName());
			textView.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					if(selectList.contains(datas.get(position))){
						v.setBackgroundColor(0xffffffff);
						selectList.remove(datas.get(position));
					}else{
						v.setBackgroundColor(0xff888888);
						selectList.add(datas.get(position));
					}
				}
			});
			return textView;
		}
		
	}
}
