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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class MyOrderListActivity extends FragmentActivity {
	private static final String[] TITLE = new String[] { "待付款", "待收货", "待评论","全部"};
	private ImageView backImg;
	private TextView title;
	private Product product;
	private ListView listview;
	private List<Order> orderList;
	private OrderItemAdapter adapter;
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
		listview = (ListView)findViewById(R.id.myorder_listview);
		//getdata();
		HttpRequest request = new HttpRequest("2", "0002");
		request.setPs("10");
		request.setPg("1");
		request.request(new HttpCallBack() {
			@Override
			public void success(String json) {
				Gson gson = new Gson();
				OrderEntity entity = gson.fromJson(json, OrderEntity.class);
				orderList = new ArrayList<Order>();
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
							LinkedHashMap<String, Object> tempProduct = (LinkedHashMap<String, Object>) object;
							Product product = new Product();
							product.setPid(String.valueOf(tempProduct.get("pid")));
							product.setPname(String.valueOf(tempProduct.get("pname")));
							product.setPimg(String.valueOf(tempProduct.get("pimg")));
							product.setPrice(String.valueOf(tempProduct.get("price")));
							product.setWeight(String.valueOf(tempProduct.get("weight")));
							order.products.add(product);
						}else{
							ArrayList<LinkedHashMap<String, Object>> productlist = (ArrayList<LinkedHashMap<String, Object>>) tempObject;
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
				adapter = new OrderItemAdapter(MyOrderListActivity.this, orderList);
				listview.setAdapter(adapter);
				//initFragment();
			}
			
			@Override
			public void fail(String failReason) {
				
			}
		});
		
        
	}
//	public void getdata(){
//		try {
//			String result = "<response description=\"操作成功\" error=\"0\"><list currentpage=\"1\" totalpage=\"1\" totalnum=\"1\"><order oid=\"20150430180559748376\" time=\"2015-04-30 18:07:46\" status=\"1\" statusdes=\"未支付\" shopid=\"2\" shopname=\"张三某某化肥店2\" total=\"52000\"><detaillist><detail pid=\"3\" pname=\"化肥3\" price=\"44\" amount=\"20袋\" weight=\"70KG/袋\" /><detail pid=\"6\" pname=\"化肥6\" price=\"456\" amount=\"40袋\" weight=\"70KG/袋\" /><detail pid=\"8\" pname=\"化肥8\" price=\"548\" amount=\"60袋\" weight=\"70KG/袋\" /></detaillist></order></list></response>";
//			XMLSerializer xmls = new XMLSerializer();
//			String json = xmls.read(result).toString().replace("@", "");
//			Log.e("What", json);
//			Gson gson = new Gson();
//			OrderEntity entity = gson.fromJson(json, OrderEntity.class);
//			orderList = new ArrayList<Order>();
//			Object tempObject = entity.getList().getOrder();
//			if(tempObject instanceof LinkedHashMap<?, ?>){
//				LinkedHashMap<String, Object> tempHashMap =(LinkedHashMap<String, Object>)tempObject;
//				//LinkedHashMap<String, Object> temp = (LinkedHashMap<String, Object>)tempHashMap.get("product");
//				Order order = new Order();
//				order.setOid(String.valueOf(tempHashMap.get("oid")));
//				order.setTime(String.valueOf(tempHashMap.get("time")));
//				order.setStatus(String.valueOf(tempHashMap.get("status")));
//				order.setStatusdes(String.valueOf(tempHashMap.get("statusdes")));
//				order.setShopid(String.valueOf(tempHashMap.get("shopid")));
//				order.setShopname(String.valueOf(tempHashMap.get("shopname")));
//				order.setTotal(String.valueOf(tempHashMap.get("total")));
//				order.setDetaillist(tempHashMap.get("detaillist"));
//				Object object = tempHashMap.get("detaillist");
//				if(order.getProducts() ==null){
//					order.products = new ArrayList<Product>();
//				}
//				if(object instanceof LinkedHashMap<?, ?>){
//					LinkedHashMap<String, Object> tempProduct = (LinkedHashMap<String, Object>) object;
//					Product product = new Product();
//					product.setPid(String.valueOf(tempProduct.get("pid")));
//					product.setPname(String.valueOf(tempProduct.get("pname")));
//					product.setPimg(String.valueOf(tempProduct.get("pimg")));
//					product.setPrice(String.valueOf(tempProduct.get("price")));
//					product.setWeight(String.valueOf(tempProduct.get("weight")));
//					order.products.add(product);
//				}else{
//					ArrayList<LinkedHashMap<String, Object>> productlist = (ArrayList<LinkedHashMap<String, Object>>) tempObject;
//					for(LinkedHashMap<String, Object> tempProduct:productlist){
//						Product product = new Product();
//						product.setPid(String.valueOf(tempProduct.get("pid")));
//						product.setPname(String.valueOf(tempProduct.get("pname")));
//						product.setPimg(String.valueOf(tempProduct.get("pimg")));
//						product.setPrice(String.valueOf(tempProduct.get("price")));
//						product.setWeight(String.valueOf(tempProduct.get("weight")));
//						order.products.add(product);
//					}
//				}
//				orderList.add(order);
//			}else if(tempObject instanceof ArrayList<?>){
//				ArrayList<LinkedHashMap<String, Object>> list = (ArrayList<LinkedHashMap<String, Object>>) tempObject;
//				for(LinkedHashMap<String, Object> temp:list){
//					Order order = new Order();
//					order.setOid(String.valueOf(temp.get("oid")));
//					order.setTime(String.valueOf(temp.get("time")));
//					order.setStatus(String.valueOf(temp.get("status")));
//					order.setStatusdes(String.valueOf(temp.get("statusdes")));
//					order.setShopid(String.valueOf(temp.get("shopid")));
//					order.setShopname(String.valueOf(temp.get("shopname")));
//					order.setTotal(String.valueOf(temp.get("total")));
//					order.setDetaillist(temp.get("detaillist"));
//					Object object = temp.get("detaillist");
//					if(order.getProducts() ==null){
//						order.products = new ArrayList<Product>();
//					}
//					if(object instanceof LinkedHashMap<?, ?>){
//						LinkedHashMap<String, Object> tempProduct = (LinkedHashMap<String, Object>) object;
//						Product product = new Product();
//						product.setPid(String.valueOf(tempProduct.get("pid")));
//						product.setPname(String.valueOf(tempProduct.get("pname")));
//						product.setPimg(String.valueOf(tempProduct.get("pimg")));
//						product.setPrice(String.valueOf(tempProduct.get("price")));
//						product.setWeight(String.valueOf(tempProduct.get("weight")));
//						order.products.add(product);
//					}else{
//						ArrayList<LinkedHashMap<String, Object>> productlist = (ArrayList<LinkedHashMap<String, Object>>) tempObject;
//						for(LinkedHashMap<String, Object> tempProduct:productlist){
//							Product product = new Product();
//							product.setPid(String.valueOf(tempProduct.get("pid")));
//							product.setPname(String.valueOf(tempProduct.get("pname")));
//							product.setPimg(String.valueOf(tempProduct.get("pimg")));
//							product.setPrice(String.valueOf(tempProduct.get("price")));
//							product.setWeight(String.valueOf(tempProduct.get("weight")));
//							order.products.add(product);
//						}
//					}
//					orderList.add(order);
//				}
//			}
//			adapter = new OrderItemAdapter(MyOrderListActivity.this, orderList);
//			listview.setAdapter(adapter);
//			Log.e("What", json);
//			//initFragment();
//		
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//public void initFragment(){
//	FragmentPagerAdapter adapter = new TabPageIndicatorAdapter(getSupportFragmentManager());
//    ViewPager pager = (ViewPager)findViewById(R.id.order_pager);
//    pager.setAdapter(adapter);
//    TitlePageIndicator indicator = (TitlePageIndicator)findViewById(R.id.order_indicator);
//    indicator.setViewPager(pager);
//    indicator.setOnPageChangeListener(new OnPageChangeListener() {
//		public void onPageSelected(int arg0) {
//			//Toast.makeText(getApplicationContext(), TITLE[arg0], Toast.LENGTH_SHORT).show();
//		}
//		public void onPageScrolled(int arg0, float arg1, int arg2) {
//			
//		}
//		public void onPageScrollStateChanged(int arg0) {
//			
//		}
//	});
//}
	
//    class TabPageIndicatorAdapter extends FragmentPagerAdapter{
//        public TabPageIndicatorAdapter(FragmentManager fm) {
//            super(fm);
//        }
//
//        @Override
//        public Fragment getItem(int position) {
//        	Fragment fragment = new ItemFragment();  
//            Bundle args = new Bundle();  
//            switch (position) {
//			case 0:
//				args.putString("arg", product.getIntro());
//				break;
//			case 1:
//				args.putString("arg", product.getDesc());
//				break;
//			case 2:
//				args.putString("arg", product.getDemo());
//				break;
//			case 3:
//				args.putString("arg", product.getDemo());
//				break;
//			default:
//				break;
//			}
//            fragment.setArguments(args);  
//        	
//            return fragment;
//        }
//
//        @Override
//        public CharSequence getPageTitle(int position) {
//            return TITLE[position % TITLE.length];
//        }
//
//        @Override
//        public int getCount() {
//            return TITLE.length;
//        }
//    }
}
