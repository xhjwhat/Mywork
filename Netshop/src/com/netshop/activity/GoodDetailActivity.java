package com.netshop.activity;

import com.netshop.app.R;
import com.netshop.entity.Product;
import com.netshop.fragment.ItemFragment;
import com.netshop.view.IconPagerAdapter;
import com.netshop.view.TabPageIndicator;
import com.netshop.view.TitlePageIndicator;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class GoodDetailActivity extends FragmentActivity {
	public static final int INTRO = 0;
	public static final int DESC = 1;
	public static final int DEMO = 2;
	private static final String[] TITLE = new String[] { "产品特点", "使用说明", "效果验证"};
	private ImageView backImg;
	private TextView title;
	private Product product;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.goods_detai);
		product = (Product) getIntent().getSerializableExtra("product");
		backImg = (ImageView) findViewById(R.id.title_back_img);
		backImg.setVisibility(View.VISIBLE);
		backImg.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		title = (TextView)findViewById(R.id.title_text);
		title.setText("商品信息");
		//ViewPager的adapter
		FragmentPagerAdapter adapter = new TabPageIndicatorAdapter(getSupportFragmentManager());
        ViewPager pager = (ViewPager)findViewById(R.id.pager);
        pager.setAdapter(adapter);

        TitlePageIndicator indicator = (TitlePageIndicator)findViewById(R.id.indicator);
        indicator.setViewPager(pager);
        indicator.setOnPageChangeListener(new OnPageChangeListener() {
			public void onPageSelected(int arg0) {
				//Toast.makeText(getApplicationContext(), TITLE[arg0], Toast.LENGTH_SHORT).show();
			}
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				
			}
			public void onPageScrollStateChanged(int arg0) {
				
			}
		});
        
	}

	
	/**
	 * ViewPager适配器
	 * @author len
	 *
	 */
    class TabPageIndicatorAdapter extends FragmentPagerAdapter{
        public TabPageIndicatorAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
        	Fragment fragment = new ItemFragment();  
            Bundle args = new Bundle();  
            switch (position) {
			case INTRO:
				args.putString("arg", product.getIntro());
				break;
			case DESC:
				args.putString("arg", product.getDesc());
				break;
			case DEMO:
				args.putString("arg", product.getDemo());
				break;
			default:
				break;
			}
            fragment.setArguments(args);  
        	
            return fragment;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return TITLE[position % TITLE.length];
        }

        @Override
        public int getCount() {
            return TITLE.length;
        }
    }

}
