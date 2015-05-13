package com.netshop.fragment;

import com.netshop.app.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class PersonFragment extends Fragment {
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
		collectLayout = (RelativeLayout)view.findViewById(R.id.my_collect);
		addrLayout = (RelativeLayout)view.findViewById(R.id.my_addr);
		accountLayout = (RelativeLayout)view.findViewById(R.id.my_account);
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

}
