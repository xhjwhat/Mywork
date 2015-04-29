package com.netshop.mywork;


import com.netshop.util.StringUtil;
import com.netshop.util.ToastUtil;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends Activity implements OnClickListener{
	private Button loginBtn,registBtn;
	private EditText phoneEdit,pwdEdit;
	private TextView forgetText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        
        
    }
    public void initView(){
    	loginBtn = (Button)findViewById(R.id.regist_verification_btn);
    	loginBtn.setOnClickListener(this);
    	registBtn = (Button)findViewById(R.id.login_register);
    	registBtn.setOnClickListener(this);
    	phoneEdit = (EditText)findViewById(R.id.login_edit_phone);
    	pwdEdit = (EditText)findViewById(R.id.login_edit_pwd);
    	forgetText = (TextView)findViewById(R.id.login_text_forgetpwd);
    	forgetText.setOnClickListener(this);
    }
	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.regist_verification_btn:
			//获取验证码
			login(phoneEdit.getText().toString(),pwdEdit.getText().toString());
			break;
		case R.id.login_register:
			//注册
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
		 			ToastUtil.toastOnUiThread(this, R.string.account_password_not_null);
		 			return;
		 		}
		     }else{
		    	 ToastUtil.toastOnUiThread(this, R.string.illegal_mobile_num);	
				return;
		     }
		}
}
