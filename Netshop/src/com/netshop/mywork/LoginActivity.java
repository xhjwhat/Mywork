package com.netshop.mywork;



import net.sf.json.xml.XMLSerializer;

import com.netshop.entity.Products;
import com.netshop.net.HttpRequest;
import com.netshop.net.ProductRequest;
import com.netshop.util.DESCrypto;
import com.netshop.util.StringUtil;
import com.netshop.util.ToastUtil;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
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
        String str = "si=3&cd=0001&ap=18565699305,ae79b33b63ba28a0443b62d1ec1032ff";
        String tempstr = "<response description=\"获取成功\" error=\"0\"><list currentpage=\"1\" totalpage=\"1\" totalnum=\"4\"> <product pid=\"7\" pname=\"化肥7\" price=\"456\" pimg=\"http://localhost:8080/wxnhProject/upload/proImg/1430549033557.jpg\" weight=\"70\"/></list></response>";
        DESCrypto des;
		try {
			des = new DESCrypto();
			Log.e("What",des.encrypt(str));
			Log.e("What2",des.decrypt(des.encrypt(str)));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        ProductRequest request = new ProductRequest();
        request.request(HttpRequest.REQUEST_GET, new HttpRequest.HttpCallBack() {

			@Override
			public void success(Object obj) {
				Products product = (Products) obj;
				if(product!=null)
				Log.e("What", product.getList().product.get(0).pname);
			}

			@Override
			public void fail(String failReason) {
				Log.e("What", failReason);
				
			}
		});
		
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
