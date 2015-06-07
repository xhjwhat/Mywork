package com.netshop.activity;

import com.google.l99gson.Gson;
import com.netshop.entity.Account;
import com.netshop.entity.AccountEntity;
import com.netshop.entity.AccountInfo;
import com.netshop.entity.ProductEntity;
import com.netshop.app.NetShopApp;
import com.netshop.app.R;
import com.netshop.net.HttpRequest;
import com.netshop.net.HttpRequest.HttpCallBack;
import com.netshop.util.DESCrypto;
import com.netshop.util.NetShopUtil;
import com.netshop.util.StringUtil;
import com.netshop.util.ToastUtil;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.mdroid.cache.CachedModel;
import android.support.mdroid.cache.ModelCache;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity implements OnClickListener {
	private Button loginBtn, registBtn;
	private EditText phoneEdit, pwdEdit;
	private TextView forgetText;
	private String phone;
	private String password;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(!NetShopApp.getInstance().getUserId().equals("") && 
				!NetShopApp.getInstance().getPassword().equals("")){
			Intent intent = new Intent(LoginActivity.this,MainActivity.class);
			startActivity(intent);
			finish();
		}
			
		setContentView(R.layout.login);
		initView();
	}

	public void initView() {
		loginBtn = (Button) findViewById(R.id.login_btn);
		loginBtn.setOnClickListener(this);
		registBtn = (Button) findViewById(R.id.login_register);
		registBtn.setOnClickListener(this);
		phoneEdit = (EditText) findViewById(R.id.login_edit_phone);
		pwdEdit = (EditText) findViewById(R.id.login_edit_pwd);
		/*forgetText = (TextView) findViewById(R.id.login_text_forgetpwd);
		forgetText.setOnClickListener(this);*/
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.login_btn:
			phone = phoneEdit.getText().toString();
			password = pwdEdit.getText().toString();
			login(phone, password);
			break;
		case R.id.login_register:
			// 注册
			Intent intent = new Intent(this,RegisterActivity.class);
			startActivity(intent);
			break;
		default:
			break;
		}

	}

	private void login(String account, String passWord) {

		// 验证用户名必须是手机号
		if (StringUtil.isMobileNum(account)) {
			// 需要验证
			if (account.length() == 0 || passWord.length() == 0) {
				ToastUtil.toastOnUiThread(this,
						R.string.account_password_not_null);
				return;
			}
		} else {
			ToastUtil.toastOnUiThread(this, R.string.illegal_mobile_num);
			return;
		}
		if (!StringUtil.isStandar(passWord)) {
			ToastUtil.toastOnUiThread(this, "密码请输入字母或数字");
			return;
		}
		HttpRequest loginRequest = new HttpRequest("1", "0002");
		loginRequest.setUserId(account);
		String pwd= DESCrypto.GetMD5Code(passWord);
		loginRequest.setPassword(pwd);
		loginRequest.request(HttpRequest.REQUEST_GET, loginCallback);
	}
	HttpCallBack loginCallback = new HttpCallBack() {
		@Override
		public void success(String json) {
			Gson gson = new Gson();
			SharedPreferences share = NetShopApp.getInstance().share;
			Editor editor = share.edit();
			editor.putString("user_id", phone);
			editor.putString("password", DESCrypto.GetMD5Code(password));
			editor.commit();
			AccountInfo info = gson.fromJson(json, AccountInfo.class);
			Account account = info.getAccount();
			ModelCache modelCache = new ModelCache(LoginActivity.this, "Netshop");
			account.setSerializableId("account01");
			account.save(modelCache);
			
			Intent intent = new Intent(LoginActivity.this,MainActivity.class);
			startActivity(intent);
			finish();
		}
		@Override
		public void fail(String failReason) {
			Toast.makeText(LoginActivity.this, failReason, Toast.LENGTH_SHORT).show();
		}
	};

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		boolean result = super.dispatchTouchEvent(ev);
		if (ev.getAction() == MotionEvent.ACTION_DOWN) {
			View v = getCurrentFocus();

			v.clearFocus();
			NetShopUtil.hideSoftKeyboard(this, v);
		}
		return result;
	}
	
}
