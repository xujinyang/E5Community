package com.gaobo.e5community.fragmentActivity;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import com.gaobo.e5community.R;

@SuppressLint("NewApi")
public class RentalInfoActivity extends FragmentActivity {
	private static final String TABINDEX = "tab_index";
	private static ActionBar mActionBar;
	private Bundle bundle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setActionBar();
	}

	private void setActionBar() {
		mActionBar = getActionBar();
		mActionBar.setTitle(getResources().getString(R.string.retalInfo));
		mActionBar.setDisplayHomeAsUpEnabled(true);
		mActionBar.setDisplayShowHomeEnabled(true);
		mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		mActionBar.addTab(mActionBar
				.newTab()
				.setText(R.string.rent_out)
				.setTabListener(
						new MyTabListener<RentalInfoFragment>(this, "rent_out",
								RentalInfoFragment.class)));

		mActionBar.addTab(mActionBar
				.newTab()
				.setText(R.string.rent_in)
				.setTabListener(
						new MyTabListener<RentalInfoFragment>(this, "rent_in",
								RentalInfoFragment.class)));
		// 实例化bundle
		bundle = new Bundle();
	}

	// 旋屏时保存TabIndex
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		bundle = outState;
		bundle.putInt(TABINDEX, getActionBar().getSelectedNavigationIndex());
	}

	// 恢复TabIndex
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		bundle = savedInstanceState;
		if (bundle.containsKey(TABINDEX))
			getActionBar().setSelectedNavigationItem(bundle.getInt(TABINDEX));
	}

	@Override
	protected void onPause() {
		super.onPause();
		bundle.putInt(TABINDEX, getActionBar().getSelectedNavigationIndex());
	}

	@SuppressLint("NewApi")
	public static class MyTabListener<T extends Fragment> implements
			ActionBar.TabListener {

		private final Activity mActivity;
		private final String mTag;
		private final Class<T> mClass;
		private final Bundle mArgs;
		private Fragment mFragment;

		public MyTabListener(Activity activity, String tag, Class<T> clz) {
			this(activity, tag, clz, null);
		}

		public MyTabListener(Activity activity, String tag, Class<T> clz,
				Bundle args) {
			mActivity = activity;
			mTag = tag;
			mClass = clz;
			mArgs = args;
			mFragment = mActivity.getFragmentManager().findFragmentByTag(mTag);
			if (mFragment != null && !mFragment.isDetached()) {
				FragmentTransaction ft = mActivity.getFragmentManager()
						.beginTransaction();
				ft.detach(mFragment);
				ft.commit();
			}
		}

		public void onTabSelected(Tab tab, FragmentTransaction ft) {
			if (mFragment == null) {
				mFragment = Fragment.instantiate(mActivity, mClass.getName(),
						mArgs);
				ft.add(android.R.id.content, mFragment, mTag);
			} else {
				ft.attach(mFragment);
			}

		}

		public void onTabUnselected(Tab tab, FragmentTransaction ft) {
			if (mFragment != null) {
				ft.detach(mFragment);
			}
		}

		public void onTabReselected(Tab tab, FragmentTransaction ft) {
		}
	}

	@Override
	public boolean onCreateOptionsMenu(android.view.Menu menu) {
		getMenuInflater().inflate(R.menu.other, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onMenuItemSelected(int featureId, android.view.MenuItem item) {
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
