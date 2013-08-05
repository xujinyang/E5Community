package com.gaobo.e5community.ui;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockPreferenceActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.gaobo.e5community.R;
import com.gaobo.e5community.util.CommonMethod;
import com.gaobo.e5community.util.photo.FileCache;

public class SettingsActivity extends SherlockPreferenceActivity {
	private ActionBar mActionBar;
	private Preference pref_clear_cache;
	private FileCache fileCache;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.settings);
		setActionBar();
		findPreferenceByKey();
		setListener();
	}

	private void findPreferenceByKey() {
		fileCache = new FileCache(getApplicationContext());
		pref_clear_cache = (Preference) findPreference("pref_clear_cache");
		pref_clear_cache.setSummary(fileCache.getCacheSize() + "");
	}

	/**
	 * 设置ActionBar
	 */
	private void setActionBar() {
		mActionBar = getSupportActionBar();
		mActionBar.setLogo(R.drawable.ic_launcher);
		mActionBar.setDisplayHomeAsUpEnabled(true);
		mActionBar.setDisplayShowTitleEnabled(true);
		mActionBar.setDisplayShowHomeEnabled(false);
	}

	/**
	 * 设置监听
	 */
	private void setListener() {
		pref_clear_cache
				.setOnPreferenceClickListener(new OnPreferenceClickListener() {
					@Override
					public boolean onPreferenceClick(Preference preference) {
						fileCache.clear();
						CommonMethod.showToast(getApplicationContext(),
								"清除缓存成功");
						pref_clear_cache.setSummary("");
						return true;
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
