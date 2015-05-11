package com.netshop.activity;

import com.netshop.app.R;
import com.netshop.net.HttpRequest;
import com.netshop.net.HttpRequest.HttpCallBack;
import com.netshop.util.StringUtil;
import com.netshop.util.ToastUtil;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends Activity implements OnClickListener {
	private Button verificationBtn, registBtn;
	private EditText phoneEdit, pwdEdit, verificationEdit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
		initView();
	}

	public void initView() {
		verificationBtn = (Button) findViewById(R.id.regist_verification_btn);
		verificationBtn.setOnClickListener(this);
		registBtn = (Button) findViewById(R.id.login_register);
		registBtn.setOnClickListener(this);
		phoneEdit = (EditText) findViewById(R.id.login_edit_phone);
		verificationEdit = (EditText) findViewById(R.id.login_edit_verification);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.regist_verification_btn:
			// 获取验证码
			HttpRequest veriRequest = new HttpRequest("1", "0003");
			veriRequest.request(HttpRequest.REQUEST_GET, veriCcallback);
			break;
		case R.id.login_register:
			// 注册
			register(phoneEdit.getText().toString(), pwdEdit.getText()
					.toString(), verificationEdit.getText().toString());
			break;
		case R.id.login_text_forgetpwd:
			// 忘记密码
			break;
		default:
			break;
		}

	}

	private void register(String account, String passWord, String verification) {
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
		if(!StringUtil.isStandar(passWord)){
			ToastUtil.toastOnUiThread(this,"密码请输入字母或数字");
			return;
		}
		HttpRequest registRequest = new HttpRequest("1", "0001");
		registRequest.request(HttpRequest.REQUEST_GET, registCcallback);
	}
	HttpCallBack veriCcallback = new HttpCallBack(){

		@Override
		public void success(String obj) {
			Toast.makeText(RegisterActivity.this, "获取成功，请稍后", Toast.LENGTH_SHORT).show();
		}
		@Override
		public void fail(String failReason) {
			Toast.makeText(RegisterActivity.this, "获取失败，请稍后再试", Toast.LENGTH_SHORT).show();
		}
		
	};
	HttpCallBack registCcallback = new HttpCallBack(){

		@Override
		public void success(String obj) {
			Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
		}
		@Override
		public void fail(String failReason) {
			Toast.makeText(RegisterActivity.this, failReason, Toast.LENGTH_SHORT).show();
		}
		
	};
}
