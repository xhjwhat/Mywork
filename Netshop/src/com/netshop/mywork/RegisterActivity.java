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

public class RegisterActivity extends Activity implements OnClickListener{
	private Button verificationBtn,registBtn;
	private EditText phoneEdit,pwdEdit,verificationEdit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        
        
    }
    public void initView(){
    	verificationBtn = (Button)findViewById(R.id.regist_verification_btn);
    	verificationBtn.setOnClickListener(this);
    	registBtn = (Button)findViewById(R.id.login_register);
    	registBtn.setOnClickListener(this);
    	phoneEdit = (EditText)findViewById(R.id.login_edit_phone);
    	verificationEdit = (EditText)findViewById(R.id.login_edit_verification);
    }
	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.regist_verification_btn:
			//获取验证码
			
			break;
		case R.id.login_register:
			//注册
			register(phoneEdit.getText().toString(),pwdEdit.getText().toString(),verificationEdit.getText().toString());
			break;
		case R.id.login_text_forgetpwd:
			//忘记密码
			break;
		default:
			break;
		}
		
	}
	private void register(String account, String passWord,String verification) {
		
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
