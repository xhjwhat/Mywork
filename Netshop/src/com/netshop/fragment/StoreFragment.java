package com.netshop.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.google.l99gson.Gson;
import com.netshop.activity.ShopDetialsActivity;
import com.netshop.adapter.StoreAdapter;
import com.netshop.app.R;
import com.netshop.entity.Shop;
import com.netshop.entity.ShopsEntity;
import com.netshop.net.HttpRequest;
import com.netshop.net.HttpRequest.HttpCallBack;

public class StoreFragment extends Fragment {
	private TextView titleText;
	private View areaLayout,orderLayout;
	private ListView listview;
	private StoreAdapter adapter;
	private List<Shop> shopList = new ArrayList<Shop>();
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.stores_detail, null);
		titleText = (TextView) view.findViewById(R.id.title_text);
		titleText.setText("门店");
		areaLayout=view.findViewById(R.id.store_area_layout);
		orderLayout=view.findViewById(R.id.store_order_layout);
		listview = (ListView)view.findViewById(R.id.store_list);
		
		return view;
	}


	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		HttpRequest request = new HttpRequest("4", "0001");
		request.request(HttpRequest.REQUEST_GET, new HttpCallBack() {
			
			@Override
			public void success(String json) {
				Gson gson = new Gson();
				ShopsEntity entity = gson.fromJson(json, ShopsEntity.class);
				shopList = entity.getShop();
				adapter = new StoreAdapter(getActivity(), shopList);
				listview.setAdapter(adapter);
				listview.setOnItemClickListener(new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						Intent intent = new Intent(getActivity(),ShopDetialsActivity.class);
						intent.putExtra("id", shopList.get(position).getId());
						startActivity(intent);
						
					}
				});
			}
			
			@Override
			public void fail(String failReason) {
				// TODO Auto-generated method stub
				
			}
		});
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	
}
