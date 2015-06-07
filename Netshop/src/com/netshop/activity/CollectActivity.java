package com.netshop.activity;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import com.google.l99gson.Gson;
import com.netshop.adapter.ProductAdapter;
import com.netshop.adapter.StoreAdapter;
import com.netshop.app.R;
import com.netshop.entity.Product;
import com.netshop.entity.ProductEntity;
import com.netshop.entity.ShopEntity;
import com.netshop.net.HttpRequest;
import com.netshop.net.HttpRequest.HttpCallBack;
import com.netshop.view.XListView;
import com.netshop.view.XListView.IXListViewListener;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class CollectActivity extends Activity {
	private TextView title;
	private XListView listview;
	private ImageView backImg;
	private List<Product> datas;
	public ProductAdapter adapter;
	private int totalPg = 0;
	private int currentPg =1;
	private int total = 0;
	private boolean hasMore = true;
	public void onCreate(Bundle bundle){
		super.onCreate(bundle);
		setContentView(R.layout.collect);
		backImg = (ImageView) findViewById(R.id.title_back_img);
		backImg.setVisibility(View.VISIBLE);
		backImg.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		title = (TextView)findViewById(R.id.title_text);
		title.setText("收藏");
		listview = (XListView)findViewById(R.id.list);
		listview.removeHeadView();
		listview.setPullLoadEnable(true);
		listview.setAutoLoadMoreEnable(true);
		
		datas = new ArrayList<Product>();
		adapter = new ProductAdapter(CollectActivity.this, datas);
		listview.setAdapter(adapter);
		listview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(CollectActivity.this,GoodsDetilsActivity.class);
				intent.putExtra("id", datas.get(position).getPid());
				CollectActivity.this.startActivity(intent);
			}
		});
		listview.setOnItemLongClickListener(new OnItemLongClickListener() {
			public boolean onItemLongClick(AdapterView<?> parent,
					View view, final int position, long id) {
				AlertDialog dialog = new AlertDialog.Builder(CollectActivity.this).create();
				dialog.setTitle("提示");
				dialog.setMessage("确认要删除当前收藏吗？");
				dialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						String id = datas.get(position).getPid();
						HttpRequest request = new HttpRequest("5", "0003");
						request.setPc(id);
						request.request(new HttpCallBack(){
							@Override
							public void success(String json) {
								datas.remove(position);
								adapter.setList(datas);
								adapter.notifyDataSetChanged();
								Toast.makeText(CollectActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
							}
							@Override
							public void fail(String failReason) {
								Toast.makeText(CollectActivity.this, failReason, Toast.LENGTH_SHORT).show();
							}
							
						});
					}
				});
				dialog.show();
				return true;
			}
		});
		listview.setXListViewListener(new IXListViewListener() {
			@Override
			public void onRefresh() {
				
			}
			public void onLoadMore() {	
				if(totalPg > currentPg){
					initData(currentPg+1);
				}else{
					listview.stopLoadMore();
					if(hasMore){
						hasMore = false;
						Toast.makeText(CollectActivity.this, "没有更多数据了", Toast.LENGTH_SHORT).show();
					}
				}
			}
		});
		initData(currentPg);
	}
	public void initData(int currentPg){
		HttpRequest request = new HttpRequest("5", "0001");
		request.setPg(currentPg + "");
		request.setPs("20");
		request.request(callBack);
	}
	HttpCallBack callBack = new HttpCallBack() {
		@Override
		public void success(String json) {
			Gson gson = new Gson();
			ProductEntity entity = gson.fromJson(json, ProductEntity.class);
			Object tempObject = entity.getList().getProduct();
			
			if(tempObject instanceof LinkedHashMap<?, ?>){
				LinkedHashMap<String, Object> tempHashMap = (LinkedHashMap<String, Object>) tempObject;
				Product product = new Product();
				product.setPid(String.valueOf(tempHashMap.get("pid")));
				product.setPname(String.valueOf(tempHashMap.get("pname")));
				product.setPimg(String.valueOf(tempHashMap.get("pimg")));
				product.setPrice(String.valueOf(tempHashMap.get("price")));
				product.setWeight(String.valueOf(tempHashMap.get("weight")));
				datas.add(product);
			}else{
				ArrayList<LinkedHashMap<String, Object>> list = (ArrayList<LinkedHashMap<String, Object>>) tempObject;
				for(LinkedHashMap<String, Object> temp:list){
					Product product = new Product();
					product.setPid(String.valueOf(temp.get("pid")));
					product.setPname(String.valueOf(temp.get("pname")));
					product.setPimg(String.valueOf(temp.get("pimg")));
					product.setPrice(String.valueOf(temp.get("price")));
					product.setWeight(String.valueOf(temp.get("weight")));
					datas.add(product);
				}
			}
			adapter.setList(datas);;
			adapter.notifyDataSetChanged();
			listview.stopLoadMore();
			if(total<=20){
				listview.removeFootView();
			}
			if(datas.size()== total){
				listview.setAutoLoadMoreEnable(false);
				listview.removeFootView();
			}
		}
		
		@Override
		public void fail(String failReason) {
			
		}
	};
	
}
