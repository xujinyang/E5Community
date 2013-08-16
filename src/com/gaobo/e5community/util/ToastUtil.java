package com.gaobo.e5community.util;
/**
 * 
 */
import android.content.Context;
import android.widget.Toast;

public class ToastUtil {

	public static void show(Context context, String info) {
		Toast.makeText(context, info, Toast.LENGTH_LONG).show();
	}
}
