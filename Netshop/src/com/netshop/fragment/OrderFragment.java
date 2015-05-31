package com.netshop.fragment;

import java.util.ArrayList;
import java.util.List;

import com.netshop.app.R;
import com.netshop.entity.Order;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class OrderFragment extends Fragment {
	private ListView listView;
	private ArrayList<Order> orders;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View contextView = inflater.inflate(R.layout.order_fragment, container, false);
		listView = (ListView)contextView.findViewById(R.id.order_list);
		
		Bundle mBundle = getArguments();
		orders = (ArrayList<Order>) mBundle.getSerializable("orders");
		
		return contextView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}
}
