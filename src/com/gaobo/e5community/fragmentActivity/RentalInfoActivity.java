package com.gaobo.e5community.fragmentActivity;

import android.annotation.SuppressLint;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.gaobo.e5community.R;

@SuppressLint("NewApi")
public class RentalInfoActivity extends SherlockFragmentActivity {
	private static final String TABINDEX = "tab_index";
	private static ActionBar mActionBar;
	private Bundle bundle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setActionBar();
	}
	private void setActionBar() {
		mActionBar = getSupportActionBar();
		mActionBar.setTitle(getResources().getString(R.string.retalInfo));
		mActionBar.setDisplayHomeAsUpEnabled(true);
		mActionBar.setDisplayShowHomeEnabled(true);
		mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		mActionBar.addTab(mActionBar
				.newTab()
				.setText(R.string.rent_out)
				.setTabListener(
						new MyTabListener<RentalInfoFragment>("rent_out",
								RentalInfoFragment.class)));

		mActionBar.addTab(mActionBar
				.newTab()
				.setText(R.string.rent_in)
				.setTabListener(
						new MyTabListener<RentalInfoFragment>("rent_in",
								RentalInfoFragment.class)));
		// 实例化bundle
		bundle = new Bundle();
	}

	// 旋屏时保存TabIndex
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		bundle = outState;
		bundle.putInt(TABINDEX, getSupportActionBar().getSelectedNavigationIndex());
	}

	// 恢复TabIndex
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		bundle = savedInstanceState;
		if (bundle.containsKey(TABINDEX))
			 getSupportActionBar().setSelectedNavigationItem(bundle.getInt(TABINDEX));
	}

	@Override
	protected void onPause() {
		super.onPause();
		bundle.putInt(TABINDEX, getSupportActionBar().getSelectedNavigationIndex());
	}

	public class MyTabListener<T extends Fragment> implements
			com.actionbarsherlock.app.ActionBar.TabListener {

		private final String mTag;
		private final Class<T> mClass;
		private final Bundle mArgs;
		private Fragment mFragment;

		public MyTabListener(String tag, Class<T> clz) {
			this(getSupportFragmentManager(), tag, clz, null);
		}

		public MyTabListener(FragmentManager fm, String tag, Class<T> clz,
				Bundle args) {
			mTag = tag;
			mClass = clz;
			mArgs = args;
			mFragment = fm.findFragmentByTag(mTag);
			if (mFragment != null && !mFragment.isDetached()) {
				FragmentTransaction ft = fm.beginTransaction();
				ft.detach(mFragment);
				ft.commit();
			}
		}

		public void onTabSelected(com.actionbarsherlock.app.ActionBar.Tab tab,
				FragmentTransaction ft) {
			if (mFragment == null) {
				mFragment = Fragment.instantiate(RentalInfoActivity.this,
						mClass.getName(), mArgs);
				ft.add(android.R.id.content, mFragment, mTag);
			} else {
				ft.attach(mFragment);
			}

		}

		public void onTabUnselected(
				com.actionbarsherlock.app.ActionBar.Tab tab,
				FragmentTransaction ft) {
			if (mFragment != null) {
				ft.detach(mFragment);
			}
		}

		public void onTabReselected(
				com.actionbarsherlock.app.ActionBar.Tab tab,
				FragmentTransaction ft) {
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.other, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			break;

		default:
			break;
		}
		return super.onMenuItemSelected(featureId, item);
	}
}
