package com.netshop.fragment;

import java.util.ArrayList;
import java.util.List;

import com.netshop.activity.ConfirmOrderActivity;
import com.netshop.adapter.ShopcartAdapter;
import com.netshop.app.NetShopApp;
import com.netshop.app.R;
import com.netshop.entity.Product;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.mdroid.cache.CachedList;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class ShopcarFragment extends Fragment {
	private TextView titleText;
	private TextView moneyText;
	private ListView listView;
	private CheckBox checkBox;
	private Button settleBtn;
	private FragmentManager manager;
	private List<Product> dataList;
	private ArrayList<Product> selectedList;
	private ShopcartAdapter adapter;
	private int totalMoney = 0;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		manager = getFragmentManager();
		View view = inflater.inflate(R.layout.shopcar, null);
		titleText = (TextView) view.findViewById(R.id.title_text);
		titleText.setText("购物车");
		listView = (ListView) view.findViewById(R.id.shopcar_list);
		checkBox = (CheckBox) view.findViewById(R.id.shopcar_check);

		checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked) {
					selectedList.clear();
					totalMoney = 0;
					for (Product temp : dataList) {
						totalMoney += Integer.valueOf(temp.getPrice())
								* Integer.valueOf(temp.getNum());
						selectedList.add(temp);
					}
					moneyText.setText("" + totalMoney);
					if (adapter != null) {
						adapter.setAllSelected(true);
						adapter.notifyDataSetChanged();
					}
				} else {
					selectedList.clear();
					totalMoney = 0;
					moneyText.setText("" + totalMoney);
					if (adapter != null) {
						adapter.setAllSelected(false);
						adapter.notifyDataSetChanged();
					}
				}

			}
		});
		moneyText = (TextView) view.findViewById(R.id.shopcar_combine_money);
		moneyText.setText("" + totalMoney);
		selectedList = new ArrayList<Product>();
		settleBtn = (Button) view.findViewById(R.id.shopcar_settlement_btn);
		// 结算
		settleBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(selectedList!=null && selectedList.size()>0){
					
					Intent intent = new Intent(getActivity(),
							ConfirmOrderActivity.class);
					intent.putExtra("selected", selectedList);
					getActivity().startActivity(intent);
				}else{
					Toast.makeText(getActivity(), "还没选择商品", Toast.LENGTH_SHORT).show();
				}
			}
		});

		CachedList<Product> cachedList = (CachedList<Product>) CachedList.find(
				NetShopApp.getInstance().modelCache, "shopcart01",
				CachedList.class);
		if (cachedList != null) {
			dataList = cachedList.getList();
			if (dataList != null && dataList.size() > 0) {
				adapter = new ShopcartAdapter(getActivity(), dataList, handler);
				listView.setAdapter(adapter);
			}

		}
		return view;
	}

	public Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			if (msg.what == -1) {
				Product product = dataList.remove(msg.arg1);
				adapter.notifyDataSetChanged();
				selectedList.remove(product);
				totalMoney = 0;
				if (selectedList.size() > 0) {
					for (Product temp : selectedList) {
						totalMoney += Integer.valueOf(temp.getPrice())
								* Integer.valueOf(temp.getNum());
					}
				} else if (selectedList.size() == 0) {
					if (checkBox.isChecked()) {
						checkBox.setChecked(false);
					}
				}
				moneyText.setText("" + totalMoney);
			}

			if (msg.arg1 == 1) {
				selectedList.add(dataList.get(msg.what));
			} else {
				selectedList.remove(dataList.get(msg.what));
			}
			totalMoney = 0;
			if (selectedList.size() > 0) {
				for (Product temp : selectedList) {
					totalMoney += Integer.valueOf(temp.getPrice())
							* Integer.valueOf(temp.getNum());
				}
			} else if (selectedList.size() == 0) {
				if (checkBox.isChecked()) {
					checkBox.setChecked(false);
				}
			}
			moneyText.setText("" + totalMoney);
		}

	};

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

}
