package com.gaobo.e5community.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.gaobo.e5community.R;

public class LoginActivity extends SherlockActivity {
	private EditText mEd_login_name;
	private EditText mEd_login_pwd;
	private TextView mBtn_toRegister;

	private ActionBar mActionBar;
	private String mUserName;
	private String mUserPwd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		setActionBar();
		init();
		setOnListener();
	}

	/**
	 * 设置监听
	 */
	private void setOnListener() {
		mBtn_toRegister.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent toRegisterIntent = new Intent(LoginActivity.this,
						RegisterActivity.class);
				startActivity(toRegisterIntent);
			}
		});
	}

	/**
	 * 控件初始化
	 */
	private void init() {
		mEd_login_name = (EditText) findViewById(R.id.Ed_login_username);
		mEd_login_pwd = (EditText) findViewById(R.id.Ed_login_password);
		mBtn_toRegister = (TextView) findViewById(R.id.toRegister);
	}

	/**
	 * 设置ActionBar
	 */
	private void setActionBar() {
		mActionBar = getSupportActionBar();
		mActionBar.setTitle(getResources().getString(R.string.login));
		mActionBar.setDisplayHomeAsUpEnabled(true);
		mActionBar.setDisplayShowHomeEnabled(true);
	}

	/**
	 * 登陆按钮点击事件
	 * 
	 * @param view
	 */
	public void onLogin(View view) {
		mUserName = mEd_login_name.getText().toString().trim();
		mUserPwd = mEd_login_pwd.getText().toString().trim();
		if (mUserName.length() == 0) {
			mEd_login_name.setError("用户名不能为空啊亲");
			return;
		}
		if (mUserPwd.length() == 0) {
			mEd_login_pwd.setError("密码不能为空啊亲");
			return;
		}
		loginSuccess();
	}

	private void loginSuccess() {
		Intent intent = new Intent();
		intent.setClass(LoginActivity.this, MainActivity.class);
		startActivity(intent);
		finish();
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
