package com.gaobo.e5community.util;

import com.loopj.android.http.RequestParams;

import android.content.Context;
import android.telephony.TelephonyManager;

/**
 * 读取Sim卡信息
 * @author TK
 *
 * 2013-5-22
 */
public class SIMCardInfo {
	/**
	 *TelephonyManager提供设备上获取通讯服务信息的入口。 
	 *		应用程序可以使用这个类方法确定的电信服务商和国家 以及某些类型的用户访问信息。 
	 * 应用程序也可以注册一个监听器到电话收状态的变化。不需要直接实例化这个类 
	 * 使用Context.getSystemService(Context.TELEPHONY_SERVICE)来获取这个类的实例。
	 */
	private TelephonyManager mTelephonyManager;
	//  国际移动用户识别码
	private String mIMSI;
	private Context mContext;
	
	public SIMCardInfo(Context context) {
		mContext = context;
		mTelephonyManager = (TelephonyManager) mContext
				.getSystemService(Context.TELEPHONY_SERVICE);
		// 唯一的用户ID,卡编号
		mIMSI = mTelephonyManager.getSubscriberId();
	}
	
	/**
	 * 判断Sim卡插入情况
	 * @return flase=>Sim卡没有插入, true=>Sim卡已插入
	 */
	public boolean isExistSim() {
		boolean result = true;
		if(mIMSI == null) {
			result = false;
		}
		return result;
	}
	
	/**
	 * 获取当前设置的电话号码 
	 * @return String
	 */
	public String getNetivePhoneNumber() {
		String nativePhoneNumber = null;
		nativePhoneNumber = mTelephonyManager.getLine1Number();
System.out.println("nativePhoneNumber-->"+mTelephonyManager.getLine1Number());
		return nativePhoneNumber;
	}
	
	/**
	 * 获取手机服务商信息
	 * 需要加入权限<uses-permission android:name="android.permission.READ_PHONE_STATE"/>
	 * @return String
	 */
	public String getProviderName() {
		String providerName = null;
System.out.println("mIMSI-->"+mIMSI);
		// mIMSI前三位是国家,紧接着后面2位  00、02=>中国移动 01=>中国联通  03=>中国电信
		if(mIMSI.startsWith("46000") || mIMSI.startsWith("46002")) {
			providerName = "中国移动";  
        } else if (mIMSI.startsWith("46001")) {  
        	providerName = "中国联通";
        } else if (mIMSI.startsWith("46003")) {  
        	providerName = "中国电信";
        } 
		
		return providerName;
	}
	
	/**
	 * 设置请求参数
	 * return RequestParams
	 */
	public RequestParams getSimRequestParams() {
		SIMCardInfo sCardInfo = new SIMCardInfo(mContext);
		RequestParams params = new RequestParams();
		
		params.put("phone", sCardInfo.getNetivePhoneNumber());
		params.put("provide", sCardInfo.getProviderName());
		
		return params;
	}
}
