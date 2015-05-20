package com.netshop.fragment;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.mdroid.cache.DefaultLoader;
import android.support.mdroid.cache.ImageCache;
import android.support.mdroid.cache.ImageWorker;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.l99gson.Gson;
import com.netshop.activity.GoodsDetilsActivity;
import com.netshop.activity.MainActivity;
import com.netshop.activity.SearchActivity;
import com.netshop.adapter.BannerAdapter;
import com.netshop.app.R;
import com.netshop.entity.BannerEntity;
import com.netshop.entity.Product;
import com.netshop.entity.ProductEntity;
import com.netshop.net.HttpRequest;
import com.netshop.net.HttpRequest.HttpCallBack;
import com.netshop.view.CirclePageIndicator;

public class HomePageFragment extends Fragment implements OnClickListener{
	private ImageView classificationImg;
	private ImageView searchImg;
	private EditText searchEdit;
	private ViewPager viewPager;
	private CirclePageIndicator indicator;
	private LinearLayout bookLayout,feedbackLayout,adviceLayout,informLayout;
	private TextView moreText;
	private ImageView productImg0,productImg1,productImg2,productImg3;
	private List<Product> productList;
	private ImageWorker imageWorker;
	private BannerAdapter adapter;
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		
		HttpRequest request = new HttpRequest("3", "0010");//获取首页四个推荐
		request.request(HttpRequest.REQUEST_GET, new HttpCallBack() {
			@Override
			public void success(String json) {
				Gson gson = new Gson();
				ProductEntity entity = gson.fromJson(json, ProductEntity.class);
				productList = entity.getList().getProduct();
				if(productList!=null && productList.size()==4){
					imageWorker.loadImage(productList.get(0).getPimg(), productImg0);
					imageWorker.loadImage(productList.get(1).getPimg(), productImg1);
					imageWorker.loadImage(productList.get(2).getPimg(), productImg2);
					imageWorker.loadImage(productList.get(3).getPimg(), productImg3);
				}
			}
			
			@Override
			public void fail(String failReason) {
				Toast.makeText(getActivity(), failReason, Toast.LENGTH_SHORT).show();
				
			}
		});
		HttpRequest bannerRequest = new HttpRequest("8", "0003");//首页banner
		bannerRequest.request(HttpRequest.REQUEST_GET, new HttpCallBack() {
			@Override
			public void success(String json) {
				Gson gson = new Gson();
				BannerEntity entity = gson.fromJson(json, BannerEntity.class);
				adapter = new BannerAdapter(getActivity(), entity.getLists());
				viewPager.setAdapter(adapter);
				indicator.setViewPager(viewPager);
			}
			
			@Override
			public void fail(String failReason) {
				Toast.makeText(getActivity(), failReason, Toast.LENGTH_SHORT).show();
				
			}
		});
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		imageWorker = new DefaultLoader(getActivity());
		View view =inflater.inflate(R.layout.homepager, null);
		classificationImg = (ImageView)view.findViewById(R.id.main_img_classification);
		classificationImg.setOnClickListener(this);
		searchImg = (ImageView)view.findViewById(R.id.main_seach_img);
		searchImg.setOnClickListener(this);
		searchEdit = (EditText)view.findViewById(R.id.main_search_edit);
		viewPager = (ViewPager)view.findViewById(R.id.main_viewpager);
		indicator = (CirclePageIndicator)view.findViewById(R.id.indicator);
		bookLayout = (LinearLayout)view.findViewById(R.id.main_book_layout);
		bookLayout.setOnClickListener(this);
		feedbackLayout = (LinearLayout)view.findViewById(R.id.main_feedback_layout);
		feedbackLayout.setOnClickListener(this);
		adviceLayout = (LinearLayout)view.findViewById(R.id.main_advice_layout);
		adviceLayout.setOnClickListener(this);
		informLayout = (LinearLayout)view.findViewById(R.id.main_inform_layout);
		informLayout.setOnClickListener(this);
		moreText = (TextView)view.findViewById(R.id.main_more_text);
		productImg0 = (ImageView)view.findViewById(R.id.main_img_bot1); 
		productImg0.setOnClickListener(this);
		productImg1 = (ImageView)view.findViewById(R.id.main_img_bot2); 
		productImg1.setOnClickListener(this);
		productImg2 = (ImageView)view.findViewById(R.id.main_img_bot3);
		productImg2.setOnClickListener(this);
		productImg3 = (ImageView)view.findViewById(R.id.main_img_bot4); 
		productImg3.setOnClickListener(this);
		return view;
	}

	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.main_img_classification:
			((MainActivity)getActivity()).viewpager.setCurrentItem(MainActivity.CLASSIFICATION);
			break;
		case R.id.main_seach_img:
			Intent intent = new Intent(getActivity(),SearchActivity.class);
			intent.putExtra("key", searchEdit.getText().toString());
			startActivity(intent);
			break;
		case R.id.main_book_layout:
			break;
		case R.id.main_advice_layout:
			break;
		case R.id.main_feedback_layout:
			break;
		case R.id.main_inform_layout:
			break;
		case R.id.main_more_text:
			break;
		case R.id.main_img_bot1:
			Intent intent1 = new Intent(getActivity(),GoodsDetilsActivity.class);
			intent1.putExtra("pid", productList.get(0).getPid());
			startActivity(intent1);
			break;
		case R.id.main_img_bot2:
			Intent intent2 = new Intent(getActivity(),GoodsDetilsActivity.class);
			intent2.putExtra("pid", productList.get(1).getPid());
			startActivity(intent2);
			break;
		case R.id.main_img_bot3:
			Intent intent3 = new Intent(getActivity(),GoodsDetilsActivity.class);
			intent3.putExtra("pid", productList.get(2).getPid());
			startActivity(intent3);
			break;
		case R.id.main_img_bot4:
			Intent intent4 = new Intent(getActivity(),GoodsDetilsActivity.class);
			intent4.putExtra("pid", productList.get(3).getPid());
			startActivity(intent4);
			break;
			default:
				break;
		}
		
	}
	
}
