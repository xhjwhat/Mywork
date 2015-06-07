package com.netshop.activity;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import net.sf.json.xml.XMLSerializer;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.l99gson.Gson;
import com.netshop.adapter.OrderItemAdapter;
import com.netshop.app.R;
import com.netshop.entity.Order;
import com.netshop.entity.OrderEntity;
import com.netshop.entity.Product;
import com.netshop.fragment.ItemFragment;
import com.netshop.net.HttpRequest;
import com.netshop.net.HttpRequest.HttpCallBack;
import com.netshop.view.TitlePageIndicator;
import com.netshop.view.XListView;
import com.netshop.view.XListView.IXListViewListener;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class MyOrderListActivity extends FragmentActivity {
	private static final String[] TITLE = new String[] { "待付款", "待收货", "待评论","全部"};
	private ImageView backImg;
	private TextView title;
	private Product product;
	private XListView listview;
	private List<Order> orderList;
	private OrderItemAdapter adapter;
	private int totalPg = 0;
	private int currentPg =1;
	private int total = 0;
	private boolean hasMore = true;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.myorder);
		
		backImg = (ImageView) findViewById(R.id.title_back_img);
		backImg.setVisibility(View.VISIBLE);
		backImg.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		title = (TextView)findViewById(R.id.title_text);
		title.setText("我的订单");
		listview = (XListView)findViewById(R.id.myorder_listview);
		listview.removeHeadView();
		listview.setPullLoadEnable(true);
		listview.setAutoLoadMoreEnable(true);
		orderList = new ArrayList<Order>();
		adapter = new OrderItemAdapter(MyOrderListActivity.this, orderList);
		listview.setAdapter(adapter);
		
		listview.setXListViewListener(new IXListViewListener() {
			@Override
			public void onRefresh() {
				
			}
			public void onLoadMore() {	
				if(totalPg > currentPg){
					initData(currentPg+1);
				}else{
					listview.stopLoadMore();
					if(hasMore){
						hasMore = false;
						Toast.makeText(MyOrderListActivity.this, "没有更多数据了", Toast.LENGTH_SHORT).show();
					}
					
				}
			}
		});
		initData(currentPg);
		
		//getdata();
		
		
        
	}
	public void initData(int currentpg){
		HttpRequest request = new HttpRequest("2", "0002");
		request.setPs("10");
		request.setPg(currentpg+"");
		request.request(new HttpCallBack() {
			@Override
			public void success(String json) {
				Gson gson = new Gson();
				OrderEntity entity = gson.fromJson(json, OrderEntity.class);
				currentPg = Integer.valueOf(entity.getList().getCurrentpage());
				totalPg = Integer.valueOf(entity.getList().getTotalpage());
				total = Integer.valueOf(entity.getList().getTotalnum());
				Object tempObject = entity.getList().getOrder();
				if(tempObject instanceof LinkedHashMap<?, ?>){
					LinkedHashMap<String, Object> tempHashMap =(LinkedHashMap<String, Object>)tempObject;
					//LinkedHashMap<String, Object> temp = (LinkedHashMap<String, Object>)tempHashMap.get("product");
					Order order = new Order();
					order.setOid(String.valueOf(tempHashMap.get("oid")));
					order.setTime(String.valueOf(tempHashMap.get("time")));
					order.setStatus(String.valueOf(tempHashMap.get("status")));
					order.setStatusdes(String.valueOf(tempHashMap.get("statusdes")));
					order.setShopid(String.valueOf(tempHashMap.get("shopid")));
					order.setShopname(String.valueOf(tempHashMap.get("shopname")));
					order.setTotal(String.valueOf(tempHashMap.get("total")));
					order.setDetaillist(tempHashMap.get("detaillist"));
					Object object = tempHashMap.get("detaillist");
					if(order.getProducts() ==null){
						order.products = new ArrayList<Product>();
					}
					if(object instanceof LinkedHashMap<?, ?>){
						LinkedHashMap<String, Object> tempProduct = (LinkedHashMap<String, Object>) object;
						Product product = new Product();
						product.setPid(String.valueOf(tempProduct.get("pid")));
						product.setPname(String.valueOf(tempProduct.get("pname")));
						product.setPimg(String.valueOf(tempProduct.get("pimg")));
						product.setPrice(String.valueOf(tempProduct.get("price")));
						product.setWeight(String.valueOf(tempProduct.get("weight")));
						order.products.add(product);
					}else{
						ArrayList<LinkedHashMap<String, Object>> productlist = (ArrayList<LinkedHashMap<String, Object>>) object;
						for(LinkedHashMap<String, Object> tempProduct:productlist){
							Product product = new Product();
							product.setPid(String.valueOf(tempProduct.get("pid")));
							product.setPname(String.valueOf(tempProduct.get("pname")));
							product.setPimg(String.valueOf(tempProduct.get("pimg")));
							product.setPrice(String.valueOf(tempProduct.get("price")));
							product.setWeight(String.valueOf(tempProduct.get("weight")));
							order.products.add(product);
						}
					}
					orderList.add(order);
				}else if(tempObject instanceof ArrayList<?>){
					ArrayList<LinkedHashMap<String, Object>> list = (ArrayList<LinkedHashMap<String, Object>>) tempObject;
					for(LinkedHashMap<String, Object> temp:list){
						Order order = new Order();
						order.setOid(String.valueOf(temp.get("oid")));
						order.setTime(String.valueOf(temp.get("time")));
						order.setStatus(String.valueOf(temp.get("status")));
						order.setStatusdes(String.valueOf(temp.get("statusdes")));
						order.setShopid(String.valueOf(temp.get("shopid")));
						order.setShopname(String.valueOf(temp.get("shopname")));
						order.setTotal(String.valueOf(temp.get("total")));
						order.setDetaillist(temp.get("detaillist"));
						Object object = temp.get("detaillist");
						if(order.getProducts() ==null){
							order.products = new ArrayList<Product>();
						}
						if(object instanceof LinkedHashMap<?, ?>){
							LinkedHashMap<String, Object> tempProduct = (LinkedHashMap<String, Object>)((LinkedHashMap<String, Object>) object).get("detail");
							Product product = new Product();
							product.setPid(String.valueOf(tempProduct.get("pid")));
							product.setPname(String.valueOf(tempProduct.get("pname")));
							product.setPimg(String.valueOf(tempProduct.get("pimg")));
							product.setPrice(String.valueOf(tempProduct.get("price")));
							product.setWeight(String.valueOf(tempProduct.get("weight")));
							order.products.add(product);
						}else{
							ArrayList<LinkedHashMap<String, Object>> productlist = (ArrayList<LinkedHashMap<String, Object>>) object;
							for(LinkedHashMap<String, Object> tempProduct:productlist){
								Product product = new Product();
								product.setPid(String.valueOf(tempProduct.get("pid")));
								product.setPname(String.valueOf(tempProduct.get("pname")));
								product.setPimg(String.valueOf(tempProduct.get("pimg")));
								product.setPrice(String.valueOf(tempProduct.get("price")));
								product.setWeight(String.valueOf(tempProduct.get("weight")));
								order.products.add(product);
							}
						}
						orderList.add(order);
					}
				}
				adapter.setData(orderList);
				adapter.notifyDataSetChanged();
				listview.stopLoadMore();
				if(total<=10){
					listview.removeFootView();
				}
				if(orderList.size()== total){
					listview.setAutoLoadMoreEnable(false);
				}
			}
			
			@Override
			public void fail(String failReason) {
				
			}
		});
	}
}
