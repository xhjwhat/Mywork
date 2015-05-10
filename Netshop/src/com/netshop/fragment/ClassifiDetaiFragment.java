package com.netshop.fragment;

import java.util.List;

import com.google.l99gson.Gson;
import com.netshop.app.R;
import com.netshop.entity.Product;
import com.netshop.entity.ProductEntity;
import com.netshop.net.HttpRequest;
import com.netshop.net.HttpRequest.HttpCallBack;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

public class ClassifiDetaiFragment extends Fragment {
	private ImageView backImg;
	private ListView list;
	private ImageView searImg;
	private EditText editText;
	private List<Product> datas;
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		HttpRequest request = new HttpRequest("3", "0002");
		request.request(HttpRequest.REQUEST_GET, callBack);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.class2, null);
		backImg = (ImageView)view.findViewById(R.id.main_img_classification);
		backImg.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				getFragmentManager().popBackStack();
			}
		});
		editText = (EditText)view.findViewById(R.id.main_search_edit);
		searImg = (ImageView)view.findViewById(R.id.main_seach_img);
		searImg.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
			}
		});
		list = (ListView)view.findViewById(R.id.class2_list);
		return view;
	}
	
	HttpCallBack callBack = new HttpCallBack() {
		@Override
		public void success(String json) {
			Gson gson = new Gson();
			ProductEntity entity = gson.fromJson(json, ProductEntity.class);
			datas = (List<Product>) entity.getList().getProduct();
		}
		
		@Override
		public void fail(String failReason) {
			
		}
	};
	@Override
	public void onDestroy() {
		super.onDestroy();
	}
	
}
