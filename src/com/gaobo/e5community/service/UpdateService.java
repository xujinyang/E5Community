package com.gaobo.e5community.service;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;

import com.gaobo.e5community.R;
import com.gaobo.e5community.util.AppContants;
import com.gaobo.e5community.util.UpdateUtil;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
/**
 * 更新服务
 * <p>
 * 	通过该类，可以链接服务器来判断当前是否有新的应用版本
 * </p>
 * 
 * @author TK
 */
public class UpdateService extends AsyncHttpResponseHandler implements UpdateUtil.OnUpdateListener{
	
	private final String KEY_JSON_SUCCESS = "success";
	private final String KEY_JSON_DATA = "data";
	
	private final String KEY_JSON_ANDROID = "android";
	private final String KEY_JSON_VERSION_CODE = "available";
	private final String KEY_JSON_VERSION_NAME = "newest";
	private final String KEY_JSON_UPDATE_URL = "link";

	private Context mContext;
	private String mVersionCode;
	private String mVersionName;
	private String mDesc = "有新版本啦！";
	private String mUrl;
	private Dialog mDialog;
	
	public UpdateService(Context context){
		mContext = context;
	}
	
	/**
	 * 开始更新任务
	 */
	public void startTask(){
		AsyncHttpClient client = new AsyncHttpClient();
		client.get(AppContants.API_URL.CHECK_NOW, this);
	}
	
	@Override
	public void onFailure(Throwable arg0, String arg1) {
	}

	@Override
	public void onSuccess(String data) {
		onResponseSuccess(data);
	}
	
	/**
	 * 加载成功
	 * 
	 * @param data
	 */
	public void onResponseSuccess(String data) {
		try {
			// 解析数据
			parserUpdateData(data);
			// 更新程序
			UpdateUtil util = new UpdateUtil();
			util.setOnUpdateListener(this);
			util.update(mContext, mVersionCode, mVersionName);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 解析更新数据
	 * 
	 * @param data	服务器返回的 json 字符串
	 * @throws JSONException 
	 */
	private void parserUpdateData(String data) throws JSONException {
		JSONObject responseJson = new JSONObject(data);
		if( responseJson.getInt(KEY_JSON_SUCCESS) == 1){
			JSONObject responseData = responseJson.getJSONObject(KEY_JSON_DATA);
			JSONObject adroidData = responseData.getJSONObject(KEY_JSON_ANDROID);
			mVersionCode = adroidData.getString(KEY_JSON_VERSION_CODE);
			mVersionName = adroidData.getString(KEY_JSON_VERSION_NAME);
			mUrl = adroidData.getString(KEY_JSON_UPDATE_URL);
		}else{
			onFailure(null,"更新出错");
		}
	}

	@Override
	public void onUpdate() {
		showUpdateDialog(mVersionName, mDesc, mUrl);
	}
	

	/**
	 * 显示更新对话框
	 * @param versionName 版本名
	 * @param desc 版本描述
	 * @param url 下载链接
	 */
	private void showUpdateDialog(String versionName, String desc, String url){
		mDialog = new AlertDialog.Builder(mContext)
			.setTitle(mContext.getString(R.string.text_update_title))
			.setIcon(mContext.getResources().getDrawable(R.drawable.ic_launcher))
			.setMessage(versionName + "\n" + desc)
			.setPositiveButton(mContext.getString(R.string.text_update_btn_confirm), 
					new CustomPositiveClickListener(url))
			.setNegativeButton(mContext.getString(R.string.text_update_btn_cancel), null)
			.create();
		showDialog();
	}
	
	/**
	 * 显示对话框
	 */
	private void showDialog(){
		Activity activity = (Activity)mContext;
		if( !activity.isFinishing() ) {
			mDialog.show();
		}
	}
	
	/**
	 * 卸载对话框
	 */
	public void dismissDialog(){
		if( mDialog != null && mDialog.isShowing() ){
			mDialog.dismiss();
		}
		
	}
	
	/**
	 * 自定义点击监听
	 * @author lwz
	 */
	class CustomPositiveClickListener implements DialogInterface.OnClickListener{
		private String url;
		public CustomPositiveClickListener( String url ) {
			this.url = url;
		}
		
		@Override
		public void onClick(DialogInterface arg0, int arg1) {
			// 若用户点击更新，则跳转到下载的网页去下载最新版的软件
			// 调用默认的浏览器下载
			Intent intent = new Intent();
			intent.setAction("android.intent.action.VIEW");
			Uri content_url = Uri.parse(url);   
			intent.setData(content_url);  
			mContext.startActivity(intent);
		}
	}
}
