package com.netshop.fragment;

import java.util.List;

import com.google.l99gson.Gson;
import com.netshop.activity.GoodsDetilsActivity;
import com.netshop.adapter.ProductAdapter;
import com.netshop.app.R;
import com.netshop.entity.Product;
import com.netshop.entity.ProductEntity;
import com.netshop.net.HttpRequest;
import com.netshop.net.HttpRequest.HttpCallBack;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

public class ClassifiDetaiFragment extends Fragment {
	private ImageView backImg;
	private ListView list;
	private ImageView searImg;
	private EditText editText;
	private List<Product> datas;
	public ClassFragment parentFragment;
	public ProductAdapter adapter;
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		HttpRequest request = new HttpRequest("3", "0002");
		if(getArguments()!=null){
			String id = getArguments().getString("type_id");
			request.setPc(id);
			request.setPs("8");
			request.setPg("1");
		}
		request.request(HttpRequest.REQUEST_GET, callBack);
		View view = inflater.inflate(R.layout.classdetial, null);
		backImg = (ImageView)view.findViewById(R.id.main_img_back);
		backImg.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				getFragmentManager().popBackStack();
				//parentFragment.changetoClassiFragment();
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
	
	public void setParentFragment(ClassFragment fragment){
		parentFragment = fragment;
	}
	HttpCallBack callBack = new HttpCallBack() {
		@Override
		public void success(String json) {
			Gson gson = new Gson();
			ProductEntity entity = gson.fromJson(json, ProductEntity.class);
			datas = (List<Product>) entity.getList().getProduct();
			if(datas != null){
				adapter = new ProductAdapter(getActivity(), datas);
				list.setAdapter(adapter);
				list.setOnItemClickListener(new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						Intent intent = new Intent(getActivity(),GoodsDetilsActivity.class);
						intent.putExtra("id", datas.get(position).getPid());
						getActivity().startActivity(intent);
					}
				});
			}
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
