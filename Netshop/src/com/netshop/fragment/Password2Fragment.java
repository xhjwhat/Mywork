package com.netshop.fragment;

import com.netshop.app.NetShopApp;
import com.netshop.app.R;
import com.netshop.net.HttpRequest;
import com.netshop.net.HttpRequest.HttpCallBack;
import com.netshop.util.DESCrypto;

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

public class Password2Fragment extends Fragment {
	private EditText reEdit,newEdit,comfirmEdit;
	private Button nextBtn;
	private Context context;
	private FragmentManager manager;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		context = getActivity();
		manager = getFragmentManager();
		View view = inflater.inflate(R.layout.update_pwd_pwd, null);
		reEdit = (EditText) view.findViewById(R.id.input_original_edit);
		newEdit = (EditText) view.findViewById(R.id.input_new_edit);
		comfirmEdit = (EditText) view.findViewById(R.id.input_comfirm_edit);
		nextBtn = (Button) view.findViewById(R.id.update_pwd_next_btn);
		nextBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if(!newEdit.getText().toString().equals(comfirmEdit.getText().toString())){
					Toast.makeText(context, "新密码与确认密码不一致！", Toast.LENGTH_SHORT).show();
					return;
				}else if(newEdit.getText().toString().trim().equals("")){
					Toast.makeText(context, "密码不能为空！", Toast.LENGTH_SHORT).show();
					return;
				}
				HttpRequest request = new HttpRequest("1", "0004");
				String oldpwd= NetShopApp.getInstance().getPassword();
				final String newPwd= DESCrypto.GetMD5Code(newEdit.getText().toString());
				request.setPc("oldpwd:"+oldpwd+";pwd:"+newPwd);
				request.request(new HttpCallBack() {
					public void success(String json) {
						SharedPreferences share = NetShopApp.getInstance().share;
						Editor editor = share.edit();
						editor.putString("password", DESCrypto.GetMD5Code(newEdit.getText().toString()));
						editor.commit();
						FragmentTransaction transaction = manager.beginTransaction();
						transaction.replace(R.id.password_frame, new Password3Fragment());
						transaction.commit();
					}
					
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
