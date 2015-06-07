package com.netshop.activity;

import com.netshop.app.NetShopApp;
import com.netshop.app.R;
import com.netshop.entity.Account;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.mdroid.cache.CachedModel;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class PersonInfoActivity extends Activity implements OnClickListener{
	public static final int NICKNAME = 1;
	public static final int PHONE = 2;
	public static final int SEX = 3;
	public static final int BIRTH = 4;
	public static final int FIXPHONE = 5;
	public static final int REALNAME = 6;
	public static final int ADDR = 7;
	public static final int PLANTAREA = 8;
	public static final int PLANTTYPE = 9;
	private TextView title;
	private ImageView backImg;
	
	private TextView nickName,phone,sex,birth,fixPhone,addr,realName,area,plant;
	
	public void onCreate(Bundle bundle){
		super.onCreate(bundle);
		setContentView(R.layout.personal_inform);
		
		backImg = (ImageView) findViewById(R.id.title_back_img);
		backImg.setVisibility(View.VISIBLE);
		backImg.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		title = (TextView)findViewById(R.id.title_text);
		title.setText("个人信息");
		nickName = (TextView)findViewById(R.id.person_nickname_text);
		phone = (TextView)findViewById(R.id.person_phone_text);
		sex = (TextView)findViewById(R.id.person_sex_text);
		birth = (TextView)findViewById(R.id.person_birth_text);
		fixPhone = (TextView)findViewById(R.id.person_fixphone_text);
		addr = (TextView)findViewById(R.id.person_addr_text);
		realName = (TextView)findViewById(R.id.person_name_text);
		area = (TextView)findViewById(R.id.person_area_text);
		plant = (TextView)findViewById(R.id.person_plantype_text);
		findViewById(R.id.nickname_layout).setOnClickListener(this);
		//findViewById(R.id.phone_layout).setOnClickListener(this);
		findViewById(R.id.sex_layout).setOnClickListener(this);
		findViewById(R.id.birth_layout).setOnClickListener(this);
		findViewById(R.id.fixphone_layout).setOnClickListener(this);
		findViewById(R.id.realname_layout).setOnClickListener(this);
		findViewById(R.id.addr_layout).setOnClickListener(this);
		findViewById(R.id.plantarea_layout).setOnClickListener(this);
		findViewById(R.id.planttype_layout).setOnClickListener(this);
		initData();
	}
	public void initData(){
		Account account = (Account) CachedModel.find(NetShopApp.getInstance().getModelCache(), "account01", Account.class);
		if(account!=null){
			nickName.setText(account.getNickname());
			phone.setText(NetShopApp.getInstance().getUserId());
			if(account.getSex().equals("0")){
				sex.setText("男");
			}else if(account.getSex().equals("1")){
				sex.setText("女");
			}else{
				sex.setText(account.getSex());
			}
			birth.setText(account.getBirth());
			fixPhone.setText(account.getFixPhone());
			addr.setText(account.getAddress());
			realName.setText(account.getRealname());
			area.setText(account.getArea());
			plant.setText(account.getCrops());
		}else{
			phone.setText(NetShopApp.getInstance().getUserId());
			//重新登录获取数据
		}
		
	}
	@Override
	public void onClick(View v) {
		Intent intent = new Intent(this,UpdateInfoActivity.class);
		switch(v.getId()){
		case R.id.nickname_layout:
			intent.putExtra("int", NICKNAME);
			intent.putExtra("type", "nickname");
			intent.putExtra("nick", "昵称：");
			intent.putExtra("value", nickName.getText().toString());
			startActivityForResult(intent,0);
			break;
		case R.id.phone_layout:
			
			intent.putExtra("int", PHONE);
			intent.putExtra("type", "phone");
			intent.putExtra("nick", "手机：");
			intent.putExtra("value", phone.getText().toString());
			startActivityForResult(intent,0);
			break;
		case R.id.sex_layout:
			intent.putExtra("int", SEX);
			intent.putExtra("type", "sex");
			intent.putExtra("nick", "性别：");
			intent.putExtra("value", sex.getText().toString());
			startActivityForResult(intent,0);
			break;
		case R.id.birth_layout:
			intent.putExtra("int", BIRTH);
			intent.putExtra("type", "birth");
			intent.putExtra("nick", "出生年月：");
			intent.putExtra("value", birth.getText().toString());
			startActivityForResult(intent,0);
			break;
		case R.id.fixphone_layout:
			intent.putExtra("int", FIXPHONE);
			intent.putExtra("type", "telphone");
			intent.putExtra("nick", "固定电话：");
			intent.putExtra("value", fixPhone.getText().toString());
			startActivityForResult(intent,0);
			break;
		case R.id.addr_layout:
			intent = new Intent(this,CityChooseActivity.class);
			intent.putExtra("int", ADDR);
			intent.putExtra("type", "addr");
			intent.putExtra("nick", "地址：");
			intent.putExtra("value", realName.getText().toString());
			startActivityForResult(intent,0);
			break;
		case R.id.realname_layout:
			intent.putExtra("int", REALNAME);
			intent.putExtra("type", "realname");
			intent.putExtra("nick", "真实姓名：");
			intent.putExtra("value", realName.getText().toString());
			startActivityForResult(intent,0);
			break;
		case R.id.plantarea_layout:
			intent.putExtra("int", PLANTAREA);
			intent.putExtra("type", "area");
			intent.putExtra("nick", "种植面积：");
			intent.putExtra("value", realName.getText().toString());
			startActivityForResult(intent,0);
			break;
		case R.id.planttype_layout:
			intent =new Intent(this,PlantTypeActivity.class);
			intent.putExtra("int", PLANTTYPE);
			intent.putExtra("type", "crop");
			intent.putExtra("nick", "种植作物：");
			intent.putExtra("value", realName.getText().toString());
			startActivityForResult(intent,0);
			break;
			
		}
		
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(data ==null)return;
		Account account = (Account) CachedModel.find(NetShopApp.getInstance().modelCache, "account01", Account.class);
		if(account == null){
			account = new Account("account01");
			account.setTelphone(NetShopApp.getInstance().getUserId());
		}
		switch(resultCode){
		case NICKNAME:
			nickName.setText(data.getStringExtra("name"));
			account.setNickname(data.getStringExtra("name"));
			break;
		case PHONE:
			phone.setText(data.getStringExtra("name"));
			account.setTelphone(data.getStringExtra("name"));
			break;
		case SEX:
			if(data.getStringExtra("name").equals("0")){
				sex.setText("男");
				account.setSex("男");
			}else{
				sex.setText("女");
				account.setSex("女");
			}
			
			break;
		case BIRTH:
			birth.setText(data.getStringExtra("name"));
			account.setBirth(data.getStringExtra("name"));
			break;
		case FIXPHONE:
			fixPhone.setText(data.getStringExtra("name"));
			account.setFixPhone(data.getStringExtra("name"));
			break;
		case ADDR:
			addr.setText(data.getStringExtra("name"));
			account.setAddress(data.getStringExtra("name"));
			break;
		case REALNAME:
			realName.setText(data.getStringExtra("name"));
			account.setRealname(data.getStringExtra("name"));
			break;
		case PLANTAREA:
			area.setText(data.getStringExtra("name"));
			account.setArea(data.getStringExtra("name"));
			break;
		case PLANTTYPE:
			plant.setText(data.getStringExtra("name"));
			account.setCrops(data.getStringExtra("name"));
			break;
		}
		account.save(NetShopApp.getInstance().modelCache);
	}
	
}
