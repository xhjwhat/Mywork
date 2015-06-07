package com.netshop.fragment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
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
import android.widget.Toast;

import com.google.l99gson.Gson;
import com.netshop.activity.CollectActivity;
import com.netshop.activity.ShopDetialsActivity;
import com.netshop.adapter.CityAdapter;
import com.netshop.adapter.StoreAdapter;
import com.netshop.app.NetShopApp;
import com.netshop.app.R;
import com.netshop.entity.Product;
import com.netshop.entity.ShopEntity;
import com.netshop.entity.ShopEntity.Shop;
import com.netshop.entity.ShopsEntity;
import com.netshop.net.HttpRequest;
import com.netshop.net.HttpRequest.HttpCallBack;
import com.netshop.util.NetShopUtil;
import com.netshop.view.XListView;
import com.netshop.view.XListView.IXListViewListener;

public class StoreFragment extends Fragment {
	private TextView titleText;
	private View areaLayout, orderLayout;
	private XListView listview;
	private StoreAdapter adapter;
	private ImageView cityBack;
	private List<ShopEntity.Shop> shopList;
	private ListView areaList;
	private CityAdapter cityAdapter;
	private PopupWindow window;
	private Context context;
	private FragmentManager manager;
	double[] location;
	private int totalPg = 0;
	private int currentPg =1;
	private int total = 0;
	private boolean hasMore = true;
	String pc;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		manager = getFragmentManager();
		context = getActivity();
		double[] location = NetShopApp.getInstance().getLocation();
		//String pc = "lan:" + location[0] + ";lat:" + location[1];
		pc = "province:"+"云南省"+";city:"+"楚雄州"+";country:"+"元谋县";
		initData(pc,currentPg);
		View view = inflater.inflate(R.layout.stores_detail, null);
		titleText = (TextView) view.findViewById(R.id.title_text);
		titleText.setText("门店");
		areaList = new ListView(getActivity());
		cityAdapter = new CityAdapter(getActivity(), handler);
		areaList.setAdapter(cityAdapter);
		cityBack = new ImageView(getActivity());
//		areaLayout = view.findViewById(R.id.store_area_layout);
//		areaLayout.setOnClickListener(new OnClickListener() {
//			public void onClick(View v) {
//				if (window == null) {
//					window = new PopupWindow(areaList, NetShopUtil
//							.getScreenWidth(getActivity()) / 2, NetShopUtil
//							.dip2px(getActivity(), 300));
//					window.setFocusable(true);
//					window.setOutsideTouchable(true);
//					window.setBackgroundDrawable(new ColorDrawable(0x00000000));
//				}
//				cityAdapter.reset();
//				window.showAsDropDown(areaLayout);
//			}
//		});
//		orderLayout = view.findViewById(R.id.store_order_layout);
		listview = (XListView) view.findViewById(R.id.store_list);
		listview.removeHeadView();
		listview.setPullLoadEnable(true);
		listview.setAutoLoadMoreEnable(true);
		
		shopList = new ArrayList<ShopEntity.Shop>();
		adapter = new StoreAdapter(context, shopList);
		listview.setAdapter(adapter);
		listview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(context,
						ShopDetialsActivity.class);
				intent.putExtra("id", shopList.get(position).getId());
				startActivity(intent);

			}
		});
		listview.setXListViewListener(new IXListViewListener() {
			@Override
			public void onRefresh() {
				
			}
			public void onLoadMore() {	
				if(totalPg > currentPg){
					initData(pc, currentPg+1);
				}else{
					listview.stopLoadMore();
					if(hasMore){
						hasMore = false;
						Toast.makeText(getActivity(), "没有更多数据了", Toast.LENGTH_SHORT).show();
					}
				}
			}
		});
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
	}

	public void initData(String pc,int pg) {
		HttpRequest request = new HttpRequest("4", "0002");
		request.setPg(pg+"");
		request.setPs("20");
		request.setPc(pc);
		request.request(HttpRequest.REQUEST_GET, new HttpCallBack() {

			@Override
			public void success(String json) {
				Gson gson = new Gson();
				ShopsEntity entity = gson.fromJson(json, ShopsEntity.class);
				currentPg = Integer.valueOf(entity.getList().getCurrentpage());
				totalPg = Integer.valueOf(entity.getList().getTotalpage());
				total = Integer.valueOf(entity.getList().getTotalnum());
				Object tempObject = entity.getList().getShop();
				if(tempObject instanceof LinkedHashMap<?, ?>){
					LinkedHashMap<String, Object> tempHashMap =(LinkedHashMap<String, Object>)tempObject;
					//LinkedHashMap<String, Object> temp = (LinkedHashMap<String, Object>)tempHashMap.get("product");
					Shop shop = new Shop();
					shop.setId(String.valueOf(tempHashMap.get("id")));
					shop.setImg(String.valueOf(tempHashMap.get("img")));
					shop.setAddress(String.valueOf(tempHashMap.get("address")));
					shop.setIsnew(String.valueOf(tempHashMap.get("isnew")));
					shop.setLinkname(String.valueOf(tempHashMap.get("linkname")));
					shop.setName(String.valueOf(tempHashMap.get("name")));
					shop.setPhone(String.valueOf(tempHashMap.get("phone")));
					shopList.add(shop);
				}else if(tempObject instanceof ArrayList<?>){
					ArrayList<LinkedHashMap<String, Object>> list = (ArrayList<LinkedHashMap<String, Object>>) tempObject;
					for(LinkedHashMap<String, Object> temp:list){
						Shop shop = new Shop();
						shop.setId(String.valueOf(temp.get("id")));
						shop.setImg(String.valueOf(temp.get("img")));
						shop.setAddress(String.valueOf(temp.get("address")));
						shop.setIsnew(String.valueOf(temp.get("isnew")));
						shop.setLinkname(String.valueOf(temp.get("linkname")));
						shop.setName(String.valueOf(temp.get("name")));
						shop.setPhone(String.valueOf(temp.get("phone")));
						shopList.add(shop);
					}
				}
				adapter.setData(shopList);
				adapter.notifyDataSetChanged();
				listview.stopLoadMore();
				if(total<=20){
					listview.removeFootView();
				}
				if(shopList.size()== total){
					listview.setAutoLoadMoreEnable(false);
					listview.removeFootView();
				}

			}

			@Override
			public void fail(String failReason) {
				Toast.makeText(getActivity(), failReason, Toast.LENGTH_SHORT).show();
				listview.stopLoadMore();

			}
		});
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			if (window.isShowing()) {
				window.dismiss();
			}
			String pc = (String) msg.obj;
			initData(pc,currentPg);
		}
	};

}
