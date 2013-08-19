package com.gaobo.e5community.ui;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
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
	private TextView mTv_cart_count;
	private TextView mTv_cart_price;

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
		mTv_cart_count = (TextView) findViewById(R.id.tv_cart_totalCount);
		mTv_cart_price = (TextView) findViewById(R.id.tv_cart_totalPrice);
		mShopArrayList = new ArrayList<Shopping>();
		getData();
		mCartAdapter = new CartAdapter(getApplicationContext(), mShopArrayList);
		mListView.setAdapter(mCartAdapter);
	}

	private void getData() {
		myDb.selectAllShoppings(mShopArrayList);
		mTv_cart_count.setText(myDb.getCartTotalCount() + "");
		mTv_cart_price.setText(myDb.getCartTotalPrice() + "");
	}

	private void setListener() {
		mCartAdapter
				.setCountChangeListener(new CartAdapter.onCountChangeListener() {
					@Override
					public void onCartCountChange() {
						mTv_cart_count.setText(myDb.getCartTotalCount() + "");
						mTv_cart_price.setText(myDb.getCartTotalPrice() + "");
					}
				});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.other, menu);
		return super.onCreateOptionsMenu(menu);
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
