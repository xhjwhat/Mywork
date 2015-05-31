package com.netshop.fragment;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import com.google.l99gson.Gson;
import com.netshop.activity.SearchActivity;
import com.netshop.adapter.ProductGridAdapter;
import com.netshop.adapter.ProductTypeAdapter;
import com.netshop.app.R;
import com.netshop.entity.ProductTypes;
import com.netshop.entity.ProductTypes.CType;
import com.netshop.entity.ProductTypes.ProductType;
import com.netshop.net.HttpRequest;
import com.netshop.net.HttpRequest.HttpCallBack;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

public class ClassificationFragment extends Fragment {
	public ImageView searchImg,seriesImg;
	public EditText searchEdit;
	public ListView seriesList;
	public GridView seriesGrid;
	public List<ProductType> datas;
	public List<CType> ctypeList;
	public ProductTypeAdapter adapter;
	public ProductGridAdapter gridAdapter;
	public ClassifiDetaiFragment fragment;
	public FragmentTransaction transaction;
	public FragmentManager manager;
	public ClassFragment parentFragment;
	public Context context;
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		
		
		super.onActivityCreated(savedInstanceState);
		
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	public void setParentFragment(ClassFragment parentFragment){
		this.parentFragment = parentFragment;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		context = getActivity();
		HttpRequest request = new HttpRequest("3", "0001");
		request.request(HttpRequest.REQUEST_GET, callback);
		View view = inflater.inflate(R.layout.classification, null);
		searchEdit = (EditText)view.findViewById(R.id.main_search_edit);
		searchImg = (ImageView)view.findViewById(R.id.main_seach_img);
		searchImg.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context,SearchActivity.class);
				intent.putExtra("key", searchEdit.getText().toString());
				startActivity(intent);
				
			}
		});
		seriesImg = (ImageView)view.findViewById(R.id.class_img);
		seriesList = (ListView)view.findViewById(R.id.class_list);
		seriesList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if(adapter!=null){
					adapter.setSelectItem(position);
					adapter.notifyDataSetChanged();
					Object tempObject = datas.get(position).getCtype();
					ctypeList = new ArrayList<ProductTypes.CType>();
					if(tempObject instanceof LinkedHashMap<?, ?>){
						LinkedHashMap<String, Object> tempHashMap = (LinkedHashMap<String, Object>)tempObject;
						LinkedHashMap<String, Object> temp = (LinkedHashMap<String, Object>) tempHashMap.get("ctype");
						CType cType = new CType();
						cType.setId(String.valueOf(temp.get("id")));
						cType.setName(String.valueOf(temp.get("name")));
						ctypeList.add(cType);
					}else if(tempObject instanceof ArrayList<?>){
						ArrayList<LinkedHashMap<String, CType>> tempList = (ArrayList<LinkedHashMap<String, CType>>)tempObject;
						for(LinkedHashMap<String, CType> map:tempList){
							CType ctype=new CType();
							ctype.setId(String.valueOf(map.get("id")));
							ctype.setName(String.valueOf(map.get("name")));
							ctypeList.add(ctype);
						}
					}
					gridAdapter.setData(ctypeList);
					gridAdapter.notifyDataSetChanged();
				}
			}
		});
		seriesGrid = (GridView)view.findViewById(R.id.class_grid);
		seriesGrid.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Bundle bundle = new Bundle();
				bundle.putString("type_id", ctypeList.get(position).getId());
				parentFragment.changetoDetialFragment(bundle);
			}
		});
		return view;
	}
	HttpCallBack callback = new HttpCallBack() {
		@Override
		public void success(String json) {
			Gson gson = new Gson();
			ProductTypes entity = gson.fromJson(json, ProductTypes.class);
			datas = (List<ProductType>) entity.getPtype();
			adapter = new ProductTypeAdapter(context, datas);
			seriesList.setAdapter(adapter);
			Object tempObject = datas.get(0).getCtype();
			ctypeList = new ArrayList<ProductTypes.CType>();
			if(tempObject instanceof LinkedHashMap<?, ?>){
				LinkedHashMap<String, Object> tempHashMap = (LinkedHashMap<String, Object>)tempObject;
				LinkedHashMap<String, Object> temp = (LinkedHashMap<String, Object>) tempHashMap.get("ctype");
				CType cType = new CType();
				cType.setId(String.valueOf(temp.get("id")));
				cType.setName(String.valueOf(temp.get("name")));
				ctypeList.add(cType);
			}else if(tempObject instanceof ArrayList<?>){
				ArrayList<LinkedHashMap<String, CType>> tempList = (ArrayList<LinkedHashMap<String, CType>>)tempObject;
				for(LinkedHashMap<String, CType> map:tempList){
					CType ctype=new CType();
					ctype.setId(String.valueOf(map.get("id")));
					ctype.setName(String.valueOf(map.get("name")));
					ctypeList.add(ctype);
				}
			}
			gridAdapter = new ProductGridAdapter(context,ctypeList );
			seriesGrid.setAdapter(gridAdapter);
		}
		
		@Override
		public void fail(String failReason) {
			Toast.makeText(context, failReason, Toast.LENGTH_SHORT).show();
		}
	};
	
	@Override
	public void onDestroy() {
		super.onDestroy();
	}
	
}
