package com.netshop.mywork;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class PersonalNameActivity extends Activity implements OnClickListener{
	public Button commitBtn;
	public EditText editText;
	private TextView title;
	public void onCreate(Bundle bundle){
		super.onCreate(bundle);
		setContentView(R.layout.personal);
		title = (TextView)findViewById(R.id.title_text);
		title.setText("修改昵称");
		findViewById(R.id.title_back_img).setOnClickListener(this);
		commitBtn = (Button)findViewById(R.id.person_commit);
		commitBtn.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.title_back_img:
			finish();
			break;
		case R.id.person_commit:
			String name = editText.getText().toString();
		}
		
	}
}
