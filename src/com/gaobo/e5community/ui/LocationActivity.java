package com.gaobo.e5community.ui;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.gaobo.e5community.R;
import com.gaobo.e5community.adapter.LocationAdapter;
import com.gaobo.e5community.model.Community;

/**
 * 社区定位界面
 * 
 * @author mobileXu
 * 
 */
public class LocationActivity extends SherlockActivity {
	/*
	 * 控件
	 */
	private Button mBtn_gotoCommunity;
	private EditText mEd_search;
	private ListView mLv_hotCommunity;
	/*
	 * 变量
	 */
	private LocationAdapter mAp_hotLocation;
	private ArrayList<Community> mAl_community;
	private ActionBar mActionBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_location);
		setActionBar();
		init();
		SetListener();
	}

	/**
	 * 设置ActionBar
	 */
	private void setActionBar() {
		mActionBar = getSupportActionBar();
		mActionBar.setTitle("社区定位");
		mActionBar.setDisplayHomeAsUpEnabled(false);// 可以点击左上角
		mActionBar.setDisplayShowTitleEnabled(true);// 可以显示标题
		mActionBar.setDisplayShowHomeEnabled(false);// 不显示图片
	}

	/**
	 * 控件和全局变量初始化
	 */
	private void init() {
		// 控件
		mEd_search = (EditText) findViewById(R.id.ed_search);
		mBtn_gotoCommunity = (Button) findViewById(R.id.btn_goto_community);
		mLv_hotCommunity = (ListView) findViewById(R.id.lv_hot_community);
		// 全局变量
		mAl_community = new ArrayList<Community>();
		test();
		mAp_hotLocation = new LocationAdapter(getApplicationContext(),
				mAl_community);
		mLv_hotCommunity.setAdapter(mAp_hotLocation);
	}

	/**
	 * 设置监听
	 */
	private void SetListener() {
		mBtn_gotoCommunity.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(LocationActivity.this,
						MainActivity.class);
				startActivity(intent);
			}
		});
	}

	private void test() {
		for (int i = 0; i < 5; i++) {
			Community c = new Community();
			c.setId(i);
			c.setName("小区" + i);
			mAl_community.add(c);
		}
	}
}
