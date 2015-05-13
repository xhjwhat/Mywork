package com.netshop.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;

import com.netshop.adapter.MainPagerAdapter;
import com.netshop.app.R;
import com.netshop.view.TabPageIndicator;

public class MainActivity extends FragmentActivity {
	public ViewPager viewpager;
	public TabPageIndicator indicator;
	public MainPagerAdapter adapter;
	public void onCreate(Bundle bundle){
		super.onCreate(bundle);
		setContentView(R.layout.main);
		viewpager = (ViewPager) findViewById(R.id.main_pager);
		indicator = (TabPageIndicator)findViewById(R.id.main_indicator);
		adapter = new MainPagerAdapter(getSupportFragmentManager());
		viewpager.setAdapter(adapter);
		indicator.setViewPager(viewpager, 0);
		indicator.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				
			}
			@Override
			public void onPageScrolled(int position, float positionOffset,
					int positionOffsetPixels) {
				
			}
			@Override
			public void onPageScrollStateChanged(int state) {
				
			}
		});
	}
}
