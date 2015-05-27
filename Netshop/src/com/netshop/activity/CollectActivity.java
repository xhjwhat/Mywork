package com.netshop.activity;

import java.util.List;

import com.google.l99gson.Gson;
import com.netshop.adapter.ProductAdapter;
import com.netshop.app.R;
import com.netshop.entity.Product;
import com.netshop.entity.ProductEntity;
import com.netshop.net.HttpRequest;
import com.netshop.net.HttpRequest.HttpCallBack;

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
	private ListView listView;
	private ImageView backImg;
	private List<Product> datas;
	public ProductAdapter adapter;
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
		listView = (ListView)findViewById(R.id.list);
		HttpRequest request = new HttpRequest("5", "0001");
		request.request(callBack);
	}
	HttpCallBack callBack = new HttpCallBack() {
		@Override
		public void success(String json) {
			Gson gson = new Gson();
			ProductEntity entity = gson.fromJson(json, ProductEntity.class);
			datas = (List<Product>) entity.getList().getProduct();
			if(datas != null){
				adapter = new ProductAdapter(CollectActivity.this, datas);
				listView.setAdapter(adapter);
				listView.setOnItemClickListener(new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						Intent intent = new Intent(CollectActivity.this,GoodsDetilsActivity.class);
						intent.putExtra("id", datas.get(position).getPid());
						CollectActivity.this.startActivity(intent);
					}
				});
				listView.setOnItemLongClickListener(new OnItemLongClickListener() {
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
						return false;
					}
				});
			}
		}
		
		@Override
		public void fail(String failReason) {
			
		}
	};
}
