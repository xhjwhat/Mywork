package com.netshop.fragment;

import com.netshop.app.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ClassFragment extends Fragment {
	public FragmentManager manager;
	public FragmentTransaction transaction;
	public ClassifiDetaiFragment detailFragment;
	public ClassificationFragment fragment;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.classfragment, null);
		manager = getFragmentManager();
		transaction = manager.beginTransaction();
		fragment = (ClassificationFragment) manager.findFragmentByTag("classification");
		detailFragment = (ClassifiDetaiFragment) manager.findFragmentByTag("details");
		if(detailFragment == null){
			detailFragment = new ClassifiDetaiFragment();
			detailFragment.setParentFragment(this);
			transaction.add(detailFragment, "details");
		}
		if(fragment == null){
			fragment = new ClassificationFragment();
			fragment.setParentFragment(this);
			transaction.add(fragment, "classification");
		}
		transaction.replace(R.id.frame_layout, fragment);
		transaction.commit();
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}
	
	public void changetoDetialFragment(Bundle bundle){
		
		if(detailFragment == null){
			detailFragment = new ClassifiDetaiFragment();
			detailFragment.setParentFragment(this);
			transaction.add(detailFragment, "details");
		}
		detailFragment.setArguments(bundle);
		transaction.replace(R.id.frame_layout, detailFragment);
		transaction.commit();
	}
	public void changetoClassiFragment(){
		
	}
}
