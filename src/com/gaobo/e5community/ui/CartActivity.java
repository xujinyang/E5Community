package com.gaobo.e5community.ui;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.widget.ListView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.gaobo.e5community.R;
import com.gaobo.e5community.adapter.CartAdapter;
import com.gaobo.e5community.database.DataBaseDao;
import com.gaobo.e5community.model.Shopping;

public class CartActivity extends SherlockActivity {
	private ListView mListView;
	private CartAdapter mCartAdapter;
	private DataBaseDao myDb;
	private List<Shopping> mShopArrayList;
	private ActionBar mActionBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_carts);
		setActionbar();
		init();
		setListener();
	}

	private void setActionbar() {
		mActionBar = getSupportActionBar();
		mActionBar.setDisplayHomeAsUpEnabled(true);
		mActionBar.setDisplayShowTitleEnabled(true);
		mActionBar.setDisplayShowHomeEnabled(true);
		mActionBar.setTitle("购物车");
	}

	private void init() {
		myDb = new DataBaseDao(getApplicationContext());
		mListView = (ListView) findViewById(R.id.lvCommon);
		mShopArrayList = new ArrayList<Shopping>();
		getData();
		mCartAdapter = new CartAdapter(getApplicationContext(), mShopArrayList);
		mListView.setAdapter(mCartAdapter);
	}

	private void getData() {
		myDb.selectAllShoppings(mShopArrayList);
	}
	private void setListener() {

	}
}
