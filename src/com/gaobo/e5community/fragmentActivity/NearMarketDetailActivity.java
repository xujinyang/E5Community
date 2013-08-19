package com.gaobo.e5community.fragmentActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.gaobo.e5community.R;
import com.gaobo.e5community.model.NearMarket;
import com.gaobo.e5community.model.Vagetable;
import com.loopj.android.image.SmartImage;
import com.loopj.android.image.SmartImageView;

/**
 * 菜市场详情
 * 
 * @author moblieSXu
 * 
 */
public class NearMarketDetailActivity extends SherlockActivity {
	/*
	 * 全局控件
	 */
	private Button mBtn_callPhone;
	private SmartImageView mIv_image;
	private TextView mTv_name;
	private TextView mTv_price;
	private TextView mTv_describe;
	private TextView mTv_market;
	private TextView mTv_address;
	/*
	 * 全局变量
	 */

	private NearMarket mnearMarket;
	private ActionBar mActionBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_vagetablemarket_detail);
		// 获取传递来的数据
		Intent intent = getIntent();
		mnearMarket = (NearMarket) intent.getSerializableExtra("nearMarket");
		setActionBar();
		init();
		setData();
		setListener();
	}

	/**
	 * 设置ActionBar
	 */
	private void setActionBar() {
		mActionBar = getSupportActionBar();
		mActionBar.setTitle("菜市场详情");
		mActionBar.setDisplayHomeAsUpEnabled(true);// 可以点击左上角
		mActionBar.setDisplayShowTitleEnabled(true);// 可以显示标题
		mActionBar.setDisplayShowHomeEnabled(true);// 不显示图片
	}

	/**
	 * 填充数据
	 */
	private void setData() {
		mTv_name.setText(mnearMarket.getName());
		mTv_price.setText(mnearMarket.getPrice() + "元/坨");
		mIv_image.setImageUrl(mnearMarket.getPath());
	}

	/**
	 * 初始化控件
	 */
	private void init() {
		mBtn_callPhone = (Button) findViewById(R.id.btn_vg_callphone);
		mIv_image = (SmartImageView) findViewById(R.id.iv_vd_image);
		mTv_address = (TextView) findViewById(R.id.tv_vg_address);
		mTv_describe = (TextView) findViewById(R.id.tv_vg_describe);
		mTv_market = (TextView) findViewById(R.id.tv_vg_market);
		mTv_name = (TextView) findViewById(R.id.tv_vd_name);
		mTv_price = (TextView) findViewById(R.id.tv_vg_price);
	}

	/**
	 * 设置监听
	 */
	private void setListener() {
		mBtn_callPhone.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setAction(Intent.ACTION_CALL);
				intent.setData(Uri.parse("tel:"
						+ mBtn_callPhone.getText().toString()));
				startActivity(intent);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.other, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
}
