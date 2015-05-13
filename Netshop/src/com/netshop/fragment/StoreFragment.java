package com.netshop.fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.netshop.adapter.StoreAdapter;
import com.netshop.app.R;
import com.netshop.entity.Shop;
import com.netshop.net.HttpRequest;

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
		titleText.setText("");
		areaLayout=view.findViewById(R.id.store_area_layout);
		orderLayout=view.findViewById(R.id.store_order_layout);
		listview = (ListView)view.findViewById(R.id.store_list);
		adapter = new StoreAdapter(getActivity(), null);
		return view;
	}


	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	
}
