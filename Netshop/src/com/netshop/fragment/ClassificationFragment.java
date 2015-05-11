package com.netshop.fragment;

import java.util.List;

import com.google.l99gson.Gson;
import com.netshop.adapter.ProductTypeAdapter;
import com.netshop.app.R;
import com.netshop.entity.ProductTypes;
import com.netshop.entity.ProductTypes.ProductType;
import com.netshop.net.HttpRequest;
import com.netshop.net.HttpRequest.HttpCallBack;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
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
	public ProductTypeAdapter adapter;
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.classification, null);
		searchImg = (ImageView)view.findViewById(R.id.main_seach_img);
		seriesImg = (ImageView)view.findViewById(R.id.class_img);
		seriesList = (ListView)view.findViewById(R.id.class_list);
		seriesList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if(adapter!=null){
					adapter.setSelectItem(position);
					adapter.notifyDataSetChanged();
				}
			}
		});
		seriesGrid = (GridView)view.findViewById(R.id.class_grid);
		HttpRequest request = new HttpRequest("3", "0001");
		request.request(HttpRequest.REQUEST_GET, callback);
		return view;
	}
	HttpCallBack callback = new HttpCallBack() {
		@Override
		public void success(String json) {
			Gson gson = new Gson();
			ProductTypes entity = gson.fromJson(json, ProductTypes.class);
			datas = (List<ProductType>) entity.getPtype();
			adapter = new ProductTypeAdapter(getActivity(), datas);
			seriesList.setAdapter(adapter);
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
