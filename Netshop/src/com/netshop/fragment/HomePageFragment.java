package com.netshop.fragment;

import android.os.Bundle;
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

import com.netshop.app.R;

public class HomePageFragment extends Fragment implements OnClickListener{
	private ImageView classificationImg;
	private ImageView searchImg;
	private EditText searchEdit;
	private ViewPager viewPager;
	private LinearLayout bookLayout,feedbackLayout,adviceLayout,informLayout;
	private TextView moreText;
	private ImageView productImg1,productImg2,productImg3,productImg4;
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view =inflater.inflate(R.layout.homepager, null);
		classificationImg = (ImageView)view.findViewById(R.id.main_img_classification);
		classificationImg.setOnClickListener(this);
		searchImg = (ImageView)view.findViewById(R.id.main_seach_img);
		searchImg.setOnClickListener(this);
		searchEdit = (EditText)view.findViewById(R.id.main_search_edit);
		viewPager = (ViewPager)view.findViewById(R.id.main_viewpager);
		bookLayout = (LinearLayout)view.findViewById(R.id.main_book_layout);
		bookLayout.setOnClickListener(this);
		feedbackLayout = (LinearLayout)view.findViewById(R.id.main_feedback_layout);
		feedbackLayout.setOnClickListener(this);
		adviceLayout = (LinearLayout)view.findViewById(R.id.main_advice_layout);
		adviceLayout.setOnClickListener(this);
		informLayout = (LinearLayout)view.findViewById(R.id.main_inform_layout);
		informLayout.setOnClickListener(this);
		moreText = (TextView)view.findViewById(R.id.main_more_text);
		productImg1 = (ImageView)view.findViewById(R.id.main_img_bot1); 
		productImg1.setOnClickListener(this);
		productImg2 = (ImageView)view.findViewById(R.id.main_img_bot2); 
		productImg2.setOnClickListener(this);
		productImg3 = (ImageView)view.findViewById(R.id.main_img_bot3);
		productImg3.setOnClickListener(this);
		productImg4 = (ImageView)view.findViewById(R.id.main_img_bot4); 
		productImg4.setOnClickListener(this);
		return view;
	}

	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.main_img_classification:
			break;
		case R.id.main_seach_img:
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
			break;
		case R.id.main_img_bot2:
			break;
		case R.id.main_img_bot3:
			break;
		case R.id.main_img_bot4:
			break;
			default:
				break;
		}
		
	}
	
}
