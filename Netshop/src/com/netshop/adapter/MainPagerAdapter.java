package com.netshop.adapter;

import com.netshop.activity.MainActivity;
import com.netshop.app.R;
import com.netshop.fragment.ClassFragment;
import com.netshop.fragment.ClassificationFragment;
import com.netshop.fragment.HomePageFragment;
import com.netshop.fragment.PersonFragment;
import com.netshop.fragment.ShopcarFragment;
import com.netshop.fragment.StoreFragment;
import com.netshop.view.IconPagerAdapter;

import android.R.integer;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class MainPagerAdapter extends FragmentPagerAdapter implements IconPagerAdapter{
	
	public int[] icons=  new int[]{R.drawable.icon_home_selector,R.drawable.icon_class_selector,R.drawable.icon_store_selector,
	                             R.drawable.icon_shopcar_selector,R.drawable.icon_person_selector};
	
	public String[] titles = new String[]{"首页","分类","门店","购物车","我的"};
	public MainPagerAdapter(FragmentManager fm) {
		super(fm);
		
	}

	@Override
	public Fragment getItem(int position) {
		switch(position){
		case MainActivity.HOME_PAGER:
			return new HomePageFragment();
		case MainActivity.CLASSIFICATION:
			return new ClassFragment();
		case MainActivity.STORE:
			return new StoreFragment();
		case MainActivity.SHOPCAR:
			return new ShopcarFragment();
		case MainActivity.PERSONAL:
			return new PersonFragment();
		}
		return null;
	}

	@Override
	public int getCount() {
		return 5;
	}

	@Override
	public int getIconResId(int index) {
		return icons[index];
	}

	@Override
	public CharSequence getPageTitle(int position) {
		
		return titles[position];
	}
	
}
