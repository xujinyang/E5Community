package com.gaobo.e5community.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.gaobo.e5community.R;
/**
 * 注册页面
 * @author 
 *
 */
public class RegisterActivity extends SherlockActivity {
	private EditText mEd_register_name;
	private EditText mEd_register_pwd;
	private EditText mEd_register_pwd_confirm;
	private Button mBt_register_ok;

	private ActionBar mActionBar;
	private String mUserName;
	private String mUserPwd;
	private String mConfirmPwd;
	private ProgressDialog mProgressDialog = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		setActionBar();
		init();
	}

	/**
	 * 控件初始化
	 */
	private void init() {
		mEd_register_name = (EditText) findViewById(R.id.Et_register_username);
		mEd_register_pwd = (EditText) findViewById(R.id.Et_register_password);
		mEd_register_pwd_confirm = (EditText) findViewById(R.id.Et_register_password_confirm);
		mBt_register_ok = (Button) findViewById(R.id.Bt_register_ok);
	}

	/**
	 * 设置ActionBar
	 */
	private void setActionBar() {
		mActionBar = getSupportActionBar();
		mActionBar.setTitle(getResources().getString(R.string.register));
		mActionBar.setDisplayHomeAsUpEnabled(true);
		mActionBar.setDisplayShowHomeEnabled(true);
	}

	/**
	 * 注册按钮的点击事件
	 * 
	 * @param view
	 */
	public void onRegister(View view) {
		mUserName = mEd_register_name.getText().toString().trim();
		mUserPwd = mEd_register_pwd.getText().toString().trim();
		mConfirmPwd = mEd_register_pwd_confirm.getText().toString().trim();
		if (mUserName.length() == 0) {
			mEd_register_name.setError("用户名不能为空");
			return;
		}
		if (mUserPwd.length() == 0) {
			mEd_register_pwd.setError("密码不能为空");
			return;
		}
		if (mConfirmPwd.length() == 0) {
			mEd_register_pwd_confirm.setError("确认密码不能为空");
			return;
		}
		if (!mUserPwd.equals(mConfirmPwd)) {
			mEd_register_pwd_confirm.setError("俩次密码输入不一样");
			mEd_register_pwd_confirm.setText("");
			return;
		}
		if (mUserPwd.length() < 3 || mUserPwd.length() > 10) {
			mEd_register_pwd.setError("密码长度不能小于3位，大于10位");
			return;
		}
		registerSuccess();
	}

	/**
	 * 注册
	 */
	private void registerSuccess() {
		Intent intent = new Intent();
		intent.setClass(RegisterActivity.this, LoginActivity.class);
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
