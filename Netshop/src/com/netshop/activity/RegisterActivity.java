package com.netshop.activity;

import javax.crypto.spec.PSource;

import com.netshop.app.NetShopApp;
import com.netshop.app.R;
import com.netshop.net.HttpRequest;
import com.netshop.net.HttpRequest.HttpCallBack;
import com.netshop.util.DESCrypto;
import com.netshop.util.NetShopUtil;
import com.netshop.util.StringUtil;
import com.netshop.util.ToastUtil;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends Activity implements OnClickListener {
	private Button verificationBtn, registBtn;
	private EditText phoneEdit, pwdEdit, verificationEdit;
	ProgressDialog dialog;
	String phone;
	String password;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
		initView();
	}

	public void initView() {
//		verificationBtn = (Button) findViewById(R.id.regist_verification_btn);
//		verificationBtn.setOnClickListener(this);
		registBtn = (Button) findViewById(R.id.login_register);
		registBtn.setOnClickListener(this);
		phoneEdit = (EditText) findViewById(R.id.login_edit_phone);
		pwdEdit = (EditText) findViewById(R.id.login_edit_pwd);
		//verificationEdit = (EditText) findViewById(R.id.login_edit_verification);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
//		case R.id.regist_verification_btn:
//			// 获取验证码
//			HttpRequest veriRequest = new HttpRequest("1", "0003");
//			veriRequest.request(HttpRequest.REQUEST_GET, veriCcallback);
//			break;
		case R.id.login_register:
			// 注册
			phone = phoneEdit.getText().toString();
			password = pwdEdit.getText().toString();
			register(phone, password);
			dialog = new ProgressDialog(this);
			dialog.setMessage("正在请求数据，请稍后...");
			dialog.show();
			break;
		/*case R.id.login_text_forgetpwd:
			// 忘记密码
			break;*/
		default:
			break;
		}

	}

	private void register(String account, String passWord) {
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
		HttpRequest registRequest = new HttpRequest("1", "0001");
		registRequest.setUserId(account);
		String pwd = DESCrypto.GetMD5Code(passWord);
		Log.e("register", account + "," + pwd);
		registRequest.setPassword(pwd);
		registRequest.request(HttpRequest.REQUEST_GET, registCcallback);
	}

	HttpCallBack veriCcallback = new HttpCallBack() {

		@Override
		public void success(String obj) {
			
			Toast.makeText(RegisterActivity.this, "获取成功，请稍后",
					Toast.LENGTH_SHORT).show();
		}

		@Override
		public void fail(String failReason) {
			if(dialog!=null && dialog.isShowing()){
				dialog.dismiss();
			}
			Toast.makeText(RegisterActivity.this, failReason,
					Toast.LENGTH_SHORT).show();
		}

	};
	HttpCallBack registCcallback = new HttpCallBack() {

		@Override
		public void success(String obj) {
			if(dialog!=null && dialog.isShowing()){
				dialog.dismiss();
			}
			SharedPreferences share = NetShopApp.getInstance().share;
			Editor editor = share.edit();
			editor.putString("user_id", phone);
			editor.putString("password", DESCrypto.GetMD5Code(password));
			editor.commit();
			Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT)
			.show();
			Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);
			finish();
		}

		@Override
		public void fail(String failReason) {
			if(dialog!=null && dialog.isShowing()){
				dialog.dismiss();
			}
			Log.e("register_callback", failReason);
			Toast.makeText(RegisterActivity.this, failReason,
					Toast.LENGTH_SHORT).show();
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
