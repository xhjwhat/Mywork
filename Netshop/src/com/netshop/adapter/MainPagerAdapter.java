package com.netshop.adapter;

import com.netshop.fragment.ClassificationFragment;
import com.netshop.fragment.HomePageFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class MainPagerAdapter extends FragmentPagerAdapter {
	public static final int HOME_PAGER = 0;
	public static final int CLASSIFICATION= 1;
	public static final int STORE = 2;
	public static final int SHOPCAR = 3;
	public static final int PERSONAL = 4;
	public MainPagerAdapter(FragmentManager fm) {
		super(fm);
		
	}

	@Override
	public Fragment getItem(int position) {
		switch(position){
		case HOME_PAGER:
			return new HomePageFragment();
		case CLASSIFICATION:
			return new ClassificationFragment();
		case STORE:
			break;
		case SHOPCAR:
			break;
		case PERSONAL:
			break;
		}
		return null;
	}

	@Override
	public int getCount() {
		return 4;
	}

}
