package com.netshop.fragment;

import com.netshop.activity.AddrManagerActivity;
import com.netshop.activity.CollectActivity;
import com.netshop.app.R;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class PersonFragment extends Fragment implements OnClickListener{
	private TextView nameText;
	private TextView pointText;
	private RelativeLayout orderLayout,collectLayout,addrLayout,accountLayout,moreLayout;
	private ImageView editImg;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.my_inform, null);
		nameText = (TextView)view.findViewById(R.id.myinform_name);
		pointText = (TextView)view.findViewById(R.id.myinform_points);
		editImg = (ImageView)view.findViewById(R.id.myinform_edit_img);
		orderLayout = (RelativeLayout)view.findViewById(R.id.order_layout);
		orderLayout.setOnClickListener(this);
		collectLayout = (RelativeLayout)view.findViewById(R.id.my_collect);
		collectLayout.setOnClickListener(this);
		addrLayout = (RelativeLayout)view.findViewById(R.id.my_addr);
		addrLayout.setOnClickListener(this);
		accountLayout = (RelativeLayout)view.findViewById(R.id.my_account);
		accountLayout.setOnClickListener(this);
		moreLayout = (RelativeLayout)view.findViewById(R.id.my_more);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.order_layout:
			break;
		case R.id.my_collect:
			Intent collectIntent = new Intent(getActivity(),CollectActivity.class);
			startActivity(collectIntent);
			break;
		case R.id.my_addr:
			Intent intent = new Intent(getActivity(),AddrManagerActivity.class);
			getActivity().startActivity(intent);
			break;
		case R.id.my_account:
			break;
		}
		
	}

}
