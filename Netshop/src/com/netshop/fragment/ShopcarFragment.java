package com.netshop.fragment;

import com.netshop.app.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class ShopcarFragment extends Fragment {
	private TextView titleText;
	private TextView moneyText;
	private ListView listView;
	private ImageView selectAllImg;
	private Button settleBtn;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.shopcar, null);
		titleText = (TextView) view.findViewById(R.id.title_text);
		titleText.setText("购物车");
		listView = (ListView) view.findViewById(R.id.shopcar_list);
		selectAllImg = (ImageView)view.findViewById(R.id.shopcar_select_img);
		moneyText = (TextView)view.findViewById(R.id.shopcar_combine_money);
		settleBtn = (Button)view.findViewById(R.id.shopcar_settlement_btn);
		settleBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
			}
		});
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	
}
