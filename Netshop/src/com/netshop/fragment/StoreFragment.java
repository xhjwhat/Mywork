package com.netshop.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.l99gson.Gson;
import com.netshop.activity.ShopDetialsActivity;
import com.netshop.adapter.CityAdapter;
import com.netshop.adapter.StoreAdapter;
import com.netshop.app.NetShopApp;
import com.netshop.app.R;
import com.netshop.entity.ShopEntity;
import com.netshop.entity.ShopsEntity;
import com.netshop.net.HttpRequest;
import com.netshop.net.HttpRequest.HttpCallBack;
import com.netshop.util.NetShopUtil;

public class StoreFragment extends Fragment {
	private TextView titleText;
	private View areaLayout,orderLayout;
	private ListView listview;
	private StoreAdapter adapter;
	private ImageView cityBack;
	private List<ShopEntity.Shop> shopList = new ArrayList<ShopEntity.Shop>();
	private ListView areaList;
	private CityAdapter cityAdapter;
	private PopupWindow window;
	private Context context;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		double[] location = NetShopApp.getInstance().getLocation();
		String pc="lan:"+location[0]+";lat:"+location[1];
		initData(pc);
		View view = inflater.inflate(R.layout.stores_detail, null);
		titleText = (TextView) view.findViewById(R.id.title_text);
		titleText.setText("门店");
		areaList = new ListView(getActivity());
		cityAdapter = new CityAdapter(getActivity(), handler);
		areaList.setAdapter(cityAdapter);
		cityBack = new ImageView(getActivity());
		areaLayout=view.findViewById(R.id.store_area_layout);
		areaLayout.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if(window == null){
					window = new PopupWindow(areaList, 
							NetShopUtil.getScreenWidth(getActivity())/2, NetShopUtil.dip2px(getActivity(), 300));
					window.setFocusable(true);
					window.setOutsideTouchable(true);
					window.setBackgroundDrawable(new ColorDrawable(0x00000000));
				}
				cityAdapter.reset();
				window.showAsDropDown(areaLayout);
			}
		});
		orderLayout=view.findViewById(R.id.store_order_layout);
		listview = (ListView)view.findViewById(R.id.store_list);
		
		return view;
	}


	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		context = getActivity();
	}
	public void initData(String pc){
		HttpRequest request = new HttpRequest("4", "0001");
		request.setPg("1");
		request.setPs("8");
		request.setPc(pc);
		request.request(HttpRequest.REQUEST_GET, new HttpCallBack() {
			
			@Override
			public void success(String json) {
				Gson gson = new Gson();
				ShopsEntity entity = gson.fromJson(json, ShopsEntity.class);
				shopList = entity.getList().getShop();
				adapter = new StoreAdapter(context, shopList);
				listview.setAdapter(adapter);
				listview.setOnItemClickListener(new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						Intent intent = new Intent(context,ShopDetialsActivity.class);
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
	Handler handler = new Handler(){
		public void handleMessage(Message msg){
			if(window.isShowing()){
				window.dismiss();
			}
			String pc = (String) msg.obj;
			initData(pc);
		}
	};
	
}
