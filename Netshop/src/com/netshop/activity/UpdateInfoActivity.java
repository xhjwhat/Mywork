package com.netshop.activity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.google.l99gson.Gson;
import com.netshop.app.R;
import com.netshop.entity.PlantAreaEntity;
import com.netshop.entity.PlantAreaEntity.PlantArea;
import com.netshop.net.HttpRequest;
import com.netshop.net.HttpRequest.HttpCallBack;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.DatePicker;

public class UpdateInfoActivity extends Activity implements OnClickListener {
	public Button commitBtn;
	public EditText editText;
	private TextView title, nick;
	private String type;
	private String value;
	private String nickStr;
	private int intType;
	private String name;
	private Spinner spinner;
	private List<PlantArea> datas;
	private List<String> areaList;
	private ArrayAdapter<String> adapter;
	private String areaId = "";
	private String showName;
	private RadioGroup radio;
	private int sexId = 0;

	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.personal);
		Intent intent = getIntent();
		intType = intent.getIntExtra("int", 0);
		type = intent.getStringExtra("type");
		nickStr = intent.getStringExtra("nick");
		value = intent.getStringExtra("value");
		title = (TextView) findViewById(R.id.title_text);
		title.setText("修改信息");
		findViewById(R.id.title_back_img).setOnClickListener(this);
		editText = (EditText) findViewById(R.id.person_edit);
		spinner = (Spinner) findViewById(R.id.Spinner);
		radio = (RadioGroup) findViewById(R.id.sex);
		if (intType == PersonInfoActivity.FIXPHONE) {
			editText.setInputType(InputType.TYPE_CLASS_PHONE);
			editText.setFilters(new InputFilter[] { new InputFilter.LengthFilter(
					13) });
		} else if (intType == PersonInfoActivity.BIRTH) {
			editText.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					AlertDialog.Builder builder = new AlertDialog.Builder(
							UpdateInfoActivity.this);
					View view = View.inflate(UpdateInfoActivity.this,
							R.layout.choose_date, null);
					final DatePicker datePicker = (DatePicker) view
							.findViewById(R.id.date_picker);
					builder.setView(view);

					Calendar cal = Calendar.getInstance();
					cal.setTimeInMillis(System.currentTimeMillis());
					datePicker.init(cal.get(Calendar.YEAR),
							cal.get(Calendar.MONTH),
							cal.get(Calendar.DAY_OF_MONTH), null);
					builder.setTitle("选时间");
					builder.setPositiveButton("确  定",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									StringBuffer sb = new StringBuffer();
									sb.append(String.format("%d-%02d-%02d",
											datePicker.getYear(),
											datePicker.getMonth() + 1,
											datePicker.getDayOfMonth()));
									sb.append("  ");

									editText.setText(sb);
									dialog.cancel();
								}
							});
					Dialog dialog = builder.create();
					dialog.show();
				}
			});
		} else if (intType == PersonInfoActivity.SEX) {
			editText.setVisibility(View.GONE);
			radio.setVisibility(View.VISIBLE);
			if (value.equals("男")) {
				RadioButton button = (RadioButton) findViewById(R.id.male);
				button.setChecked(true);
			} else {
				RadioButton button = (RadioButton) findViewById(R.id.female);
				button.setChecked(true);
			}
			radio.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(RadioGroup group, int checkedId) {
					if (checkedId == R.id.male) {
						sexId = 0;
					} else {
						sexId = 1;
					}
				}
			});
		} else if (intType == PersonInfoActivity.PLANTAREA) {
			editText.setVisibility(View.GONE);
			spinner.setVisibility(View.VISIBLE);
			HttpRequest request = new HttpRequest("1", "0008");
			request.request(new HttpCallBack() {
				@Override
				public void success(String json) {
					Gson gson = new Gson();
					PlantAreaEntity entity = gson.fromJson(json,
							PlantAreaEntity.class);
					datas = entity.getBean();
					areaList = new ArrayList<String>();
					for (PlantArea temp : datas) {
						areaList.add(temp.getName());
					}
					adapter = new ArrayAdapter<String>(UpdateInfoActivity.this,
							android.R.layout.simple_spinner_item, areaList);
					adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
					spinner.setAdapter(adapter);

					// 添加事件Spinner事件监听
					spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
						@Override
						public void onItemSelected(AdapterView<?> parent,
								View view, int position, long id) {
							areaId = datas.get(position).getId();
							showName = datas.get(position).getName();
						}

						public void onNothingSelected(AdapterView<?> parent) {
							areaId = "";
							showName = "";
						}
					});
				}

				public void fail(String failReason) {

				}
			});
		} else {
			editText.setHint(value);
		}

		nick = (TextView) findViewById(R.id.nick);
		nick.setText(nickStr);
		commitBtn = (Button) findViewById(R.id.person_commit);
		commitBtn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.title_back_img:
			finish();
			break;
		case R.id.person_commit:
			HttpRequest request = new HttpRequest("1", "0006");
			if (intType == PersonInfoActivity.PLANTAREA) {
				name = areaId;
			} else if (intType == PersonInfoActivity.SEX) {
				name = sexId + "";
			} else {
				name = editText.getText().toString();
			}
			request.setPc(type + ":" + name);
			request.request(HttpRequest.REQUEST_GET, new HttpCallBack() {
				@Override
				public void success(String json) {
					Toast.makeText(UpdateInfoActivity.this, "修改成功",
							Toast.LENGTH_SHORT).show();
					Intent intent = new Intent();
					if (intType == PersonInfoActivity.PLANTAREA) {
						intent.putExtra("name", showName);
					} else {
						intent.putExtra("name", name);
					}
					setResult(intType, intent);
					finish();
				}

				@Override
				public void fail(String failReason) {
					Toast.makeText(UpdateInfoActivity.this, failReason,
							Toast.LENGTH_SHORT).show();
				}
			});
		}

	}
}
