package com.gaobo.e5community.util;

import java.text.DecimalFormat;
import java.util.List;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.telephony.TelephonyManager;
import android.view.WindowManager;
import android.widget.Toast;

public class CommonMethod {
	/**
	 * 显示Toast
	 * 
	 * @param context
	 * @param text
	 */
	public static void showToast(Context context, String text) {
		Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
	}

	/**
	 * 开始加载
	 * 
	 * @param context
	 * @param progressDialog
	 */
	public static ProgressDialog showProcess(Context context, String title) {
		ProgressDialog progressDialog = ProgressDialog.show(context, "", title,
				true, false);
		progressDialog.setCancelable(true);
		progressDialog.setCanceledOnTouchOutside(false);
		return progressDialog;
	}

	/**
	 * 加载结束
	 * 
	 * @param progressDialog
	 */
	public static void dismissProcess(ProgressDialog progressDialog) {
		if (progressDialog != null) {
			progressDialog.dismiss();
		}
	}

	/**
	 * 将Unix时间转换成标准时间
	 * 
	 * @param timestampString
	 * @return
	 */
	public static String convertTime2Date(String timestampString) {
		Long timestamp = Long.parseLong(timestampString) * 1000;
		String date = new java.text.SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
				.format(new java.util.Date(timestamp));
		return date;
	}

	/**
	 * 将Unix时间转换成订单格式时间
	 * 
	 * @param timestampString
	 * @return
	 */
	public static String convertTime2OrderDate(String timestampString) {
		Long timestamp = Long.parseLong(timestampString) * 1000;
		String date = new java.text.SimpleDateFormat("MM月dd日  HH:mm")
				.format(new java.util.Date(timestamp));
		return date;
	}

	/**
	 * 获取版本号
	 * 
	 * @return 当前应用的版本号
	 */
	public static String getVersion(Context context) {
		try {
			PackageManager manager = context.getPackageManager();
			PackageInfo info = manager.getPackageInfo(context.getPackageName(),
					0);
			String version = info.versionName;

			return version;
		} catch (Exception e) {
			e.printStackTrace();
			return "0";
		}
	}

	/**
	 * 获取手机序列号
	 * 
	 * @param context
	 * @return
	 */
	public static String getDevid(Context context) {
		TelephonyManager telephonemanage = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		try {
			return telephonemanage.getDeviceId();
		} catch (Exception e) {
			return "0";
		}
	}

	/**
	 * 格式化Double
	 * 
	 * @param d
	 * @return
	 */
	public static String formatDouble(double d) {
		return new DecimalFormat("#0.00").format(d);
	}

	/**
	 * 获得屏幕宽度
	 * 
	 * @param Context
	 * @return int
	 */
	public static int getScreenWidth(Context context) {
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		int width = wm.getDefaultDisplay().getWidth();
		// int height = wm.getDefaultDisplay().getHeight();
		return width;

	}
}
