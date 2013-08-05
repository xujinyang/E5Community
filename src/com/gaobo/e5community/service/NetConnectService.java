package com.gaobo.e5community.service;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;

import com.gaobo.e5community.R;

/**
 * 网络连接服务类
 * 
 */
public class NetConnectService {
	private Context mContext;
	
	public NetConnectService(Context context) {
		mContext = context;
	}
	
	/**
	 * 网络是否已经连接
	 * 
	 * @return 若已连接 返回true, 否则返回 false
	 */
	public boolean isAvailable() {
		boolean flag = false;
		ConnectivityManager manager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
		
		if (manager.getActiveNetworkInfo() != null){
			flag = manager.getActiveNetworkInfo().isAvailable();
		}
		return flag;
	}
	
	/**
	 * 显示对话框
	 * <p> 在 Activity 的 onStop()方法中要将该 对话框 dismiss 掉</p>
	 * 
	 * @return 一个对话框实例
	 */
	public AlertDialog showDialog(){
		AlertDialog dialog = new AlertDialog.Builder(mContext)
			.setTitle(mContext.getString(R.string.text_dialog_netconn_title))
			.setMessage(mContext.getString(R.string.text_dialog_netconn_msg))
			.setIcon(mContext.getResources().getDrawable(R.drawable.ic_launcher))
			.setPositiveButton(mContext.getString(R.string.text_dialog_netconn_btn_open_wireless), 
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							turnSettingActivity();
						}
					}
			).setNegativeButton(mContext.getString(R.string.text_dialog_know), null)
			.create();
		
		dialog.show();
		return dialog;
	}
	/**
	 * 跳转到设置网络的界面
	 */
	private void turnSettingActivity(){
		Intent mIntent = new Intent("/");
		ComponentName comp = new ComponentName(
				"com.android.settings",
				"com.android.settings.WirelessSettings");
		mIntent.setComponent(comp);
		mIntent.setAction("android.intent.action.VIEW");
		mContext.startActivity(mIntent);
	}
}
