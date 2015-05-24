package com.netshop.fragment;

import com.netshop.app.NetShopApp;
import com.netshop.app.R;
import com.netshop.net.HttpRequest;
import com.netshop.net.HttpRequest.HttpCallBack;
import com.netshop.util.DESCrypto;
import com.netshop.util.StringUtil;
import com.netshop.util.ToastUtil;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
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
import android.widget.Toast;

public class Phone2Fragment extends Fragment {
	private EditText phoneEditText,verifiEditText;
	private Button nextBtn,verifiBtn;
	private Context context;
	private FragmentManager manager;
	private String newPhone;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		context = getActivity();
		manager = getFragmentManager();
		View view = inflater.inflate(R.layout.update_phone_phone, null);
		phoneEditText = (EditText)view.findViewById(R.id.input_original_edit);
		verifiEditText = (EditText)view.findViewById(R.id.update_pwd_edit);
		nextBtn = (Button)view.findViewById(R.id.update_phone_next_btn);
		nextBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				newPhone = phoneEditText.getText().toString();
				// 验证用户名必须是手机号
				if (StringUtil.isMobileNum(newPhone)) {
					// 需要验证
					if (newPhone.length() == 0 ) {
						Toast.makeText(context,
								R.string.account_password_not_null,Toast.LENGTH_SHORT).show();
						return;
					}
				} else {
					Toast.makeText(context, R.string.illegal_mobile_num,Toast.LENGTH_SHORT).show();
					return;
				}
				HttpRequest request = new HttpRequest("1", "0007");
				request.setCode(verifiEditText.getText().toString());
				request.setPc("phone:"+NetShopApp.getInstance().getUserId());
				request.request(new HttpCallBack() {
					public void success(String json) {
						HttpRequest request = new HttpRequest("1", "0005");
						request.setPc("oldphone:"+NetShopApp.getInstance().getUserId()+";phone:"+phoneEditText.getText().toString());
						request.request(new HttpCallBack() {
							public void success(String json) {
								SharedPreferences share = NetShopApp.getInstance().share;
								Editor editor = share.edit();
								editor.putString("user_id", newPhone);
								editor.commit();
								FragmentTransaction transaction = manager.beginTransaction();
								transaction.replace(R.id.password_frame, new Phone3Fragment());
								transaction.commit();
							}
							
							@Override
							public void fail(String failReason) {
								
							}
						});
					}
					
					@Override
					public void fail(String failReason) {
						
					}
				});
			}
		});
		verifiBtn = (Button)view.findViewById(R.id.update_pwd_verifi_btn);
		verifiBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				HttpRequest request = new HttpRequest("1", "0003");
				request.request(new HttpCallBack() {
					public void success(String json) {
						Toast.makeText(context, "发送成功", Toast.LENGTH_SHORT).show();
					}
					
					@Override
					public void fail(String failReason) {
						Toast.makeText(context, failReason, Toast.LENGTH_SHORT).show();
					}
				});
			}
		});
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
	}

}
