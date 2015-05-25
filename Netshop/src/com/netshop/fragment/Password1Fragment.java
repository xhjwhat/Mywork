package com.netshop.fragment;

import com.netshop.app.NetShopApp;
import com.netshop.app.R;
import com.netshop.net.HttpRequest;
import com.netshop.net.HttpRequest.HttpCallBack;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Password1Fragment extends Fragment {
	TextView phone;
	Button nextBtn;
	Button verfiBtn;
	EditText verfiEdit;
	Context context;
	FragmentManager manager;
	Bundle bundle;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		context = getActivity();
		manager = getFragmentManager();
		bundle = getArguments();
		View view;
		if(bundle.getString("type").equals("phone")){
			view = inflater.inflate(R.layout.update_phone_verifi, null);
		}else{
			view = inflater.inflate(R.layout.update_pwd_phone, null);
		}
		phone = (TextView) view.findViewById(R.id.update_pwd_phone);
		String temp= NetShopApp.getInstance().getUserId();
		String phoneString = temp.substring(0, 3)+"****"+temp.substring(7, 11);
		phone.setText(phoneString);
		nextBtn = (Button) view.findViewById(R.id.update_pwd_next_btn);
		nextBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
//				FragmentTransaction transaction = manager.beginTransaction();
//				transaction.replace(R.id.password_frame, new Password2Fragment());
//				transaction.commit();
				HttpRequest request = new HttpRequest("1", "0007");
				request.setCode(verfiEdit.getText().toString());
				request.setPc("phone:"+NetShopApp.getInstance().getUserId());
				request.request(new HttpCallBack() {
					public void success(String json) {
						FragmentTransaction transaction = manager.beginTransaction();
						transaction.replace(R.id.password_frame, new Password2Fragment());
						transaction.commit();
					}
					
					public void fail(String failReason) {
						Toast.makeText(context, failReason, Toast.LENGTH_SHORT).show();
					}
				});
			}
		});
		verfiBtn = (Button) view.findViewById(R.id.update_pwd_verifi_btn);
		verfiBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				HttpRequest request = new HttpRequest("1", "0003");
				request.request(new HttpCallBack() {
					public void success(String json) {
						Toast.makeText(context, "请求成功", Toast.LENGTH_SHORT).show();
					}
					public void fail(String failReason) {
						Toast.makeText(context, failReason, Toast.LENGTH_SHORT).show();
					}
				});
			}
		});
		
		verfiEdit = (EditText) view.findViewById(R.id.update_pwd_edit);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
	}
	
}
