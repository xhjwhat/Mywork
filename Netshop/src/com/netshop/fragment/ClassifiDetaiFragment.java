package com.netshop.fragment;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import com.google.l99gson.Gson;
import com.netshop.activity.GoodsDetilsActivity;
import com.netshop.activity.LoginActivity;
import com.netshop.adapter.ProductAdapter;
import com.netshop.app.R;
import com.netshop.entity.Product;
import com.netshop.entity.ProductEntity;
import com.netshop.net.HttpRequest;
import com.netshop.net.HttpRequest.HttpCallBack;
import com.netshop.view.XListView;
import com.netshop.view.XListView.IXListViewListener;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

public class ClassifiDetaiFragment extends Fragment {
	private ImageView backImg;
	private XListView list;
	private ImageView searImg;
	private EditText editText;
	private List<Product> datas;
	public ClassFragment parentFragment;
	public ProductAdapter adapter;
	public Context context;
	int totalPg = 0;
	int currentPg = 1;
	int total = 0;
	String pc;
	private boolean hasMore = true;
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		context = getActivity();
		if(getArguments()!=null){
			pc = getArguments().getString("type_id");
		}
		
		View view = inflater.inflate(R.layout.classdetial, null);
		backImg = (ImageView)view.findViewById(R.id.main_img_back);
		backImg.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
//				FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
//				ClassificationFragment fragment = (ClassificationFragment) getChildFragmentManager().findFragmentByTag("classification");
//				if(fragment ==null){
//					fragment = new ClassificationFragment();
//				}
//				transaction.replace(R.id.frame_layout, fragment);
//				transaction.commit();
//				//getChildFragmentManager().popBackStackImmediate();
				Log.e("what","back");
				parentFragment.changetoClassiFragment();
			}
		});
//		editText = (EditText)view.findViewById(R.id.main_search_edit);
//		searImg = (ImageView)view.findViewById(R.id.main_seach_img);
//		searImg.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				
//			}
//		});
		datas = new ArrayList<Product>();
		list = (XListView)view.findViewById(R.id.class2_list);
		list.removeHeadView();
		list.setPullLoadEnable(true);
		list.setAutoLoadMoreEnable(true);
		adapter = new ProductAdapter(context, datas);
		list.setAdapter(adapter);
		list.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(context,GoodsDetilsActivity.class);
				intent.putExtra("id", datas.get(position).getPid());
				context.startActivity(intent);
			}
		});
		list.setXListViewListener(new IXListViewListener() {
			@Override
			public void onRefresh() {
				
			}
			public void onLoadMore() {	
				if(totalPg > currentPg){
					initData(currentPg+1);
				}else{
					list.stopLoadMore();
					if(hasMore){
						hasMore =false;
						Toast.makeText(getActivity(), "没有更多数据了", Toast.LENGTH_SHORT).show();
					}
				}
			}
		});
		initData(currentPg);
		return view;
	}
	public void initData(int pg){
		HttpRequest request = new HttpRequest("3", "0002");
			request.setPc(pc);
			request.setPs("20");
			request.setPg(pg +"");
		request.request(HttpRequest.REQUEST_GET, callBack);
	}
	public void setParentFragment(ClassFragment fragment){
		parentFragment = fragment;
	}
	HttpCallBack callBack = new HttpCallBack() {
		@Override
		public void success(String json) {
			Gson gson = new Gson();
			ProductEntity entity = gson.fromJson(json, ProductEntity.class);
			totalPg = Integer.valueOf(entity.getList().getTotalpage());
			currentPg = Integer.valueOf(entity.getList().getCurrentpage());
			total = Integer.valueOf(entity.getList().getTotalnum());
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
			adapter.setList(datas);
			adapter.notifyDataSetChanged();
			list.stopLoadMore();
			if(total<=20){
				list.removeFootView();
			}
			
		}
		
		@Override
		public void fail(String failReason) {
			Toast.makeText(getActivity(), failReason, Toast.LENGTH_SHORT).show();
		}
	};
	@Override
	public void onDestroy() {
		super.onDestroy();
	}
	
}
