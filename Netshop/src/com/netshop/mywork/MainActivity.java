package com.netshop.mywork;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {
	private Button loginBtn,registBtn;
	private EditText phoneEdit,pwdEdit;
	private TextView forgetText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        
    }
    public void initView(){
    	
    }
}
