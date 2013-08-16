package com.gaobo.e5community.util;

import android.content.Context;
import android.os.Vibrator;
import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.BDNotifyListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

public class LocationUtil {
	private LocationClient mLocClient;
	public MyLocationListenner myListener = new MyLocationListenner();
	public NotifyLister mNotifyer = null;
	public Vibrator mVibrator01;
	private String mData;
	private onGetLocationListener mLocationListener;

	public LocationUtil(Context context) {

		mLocClient = new LocationClient(context);
		mLocClient.registerLocationListener(myListener);
		setLocationOption();
		mLocClient.start();
	}

	public void setGetLocationListener(onGetLocationListener l) {
		mLocationListener = l;
	}

	public interface onGetLocationListener {
		public void getAddress(String string);

		public void getLocation(String sb);

	}

	// 设置相关参数
	private void setLocationOption() {
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(false); // 打开gps
		option.setCoorType("bd09ll"); // 设置坐标类型
		option.setServiceName("com.baidu.location.service_v2.9");
		option.setPoiExtraInfo(true);
		option.setAddrType("all");
		option.setScanSpan(3000);
		option.setPriority(LocationClientOption.NetWorkFirst); // 设置网络优先
		option.setPoiNumber(10);
		option.disableCache(true);
		mLocClient.setLocOption(option);
	}

	/**
	 * 监听函数，又新位置的时候，格式化成字符串，输出到屏幕中
	 */
	public class MyLocationListenner implements BDLocationListener {
		@Override
		public void onReceiveLocation(BDLocation location) {
			if (location == null)
				return;
			StringBuffer sb = new StringBuffer(256);
			// sb.append("time : ");
			// sb.append(location.getTime());
			// sb.append("\nerror code : ");
			// sb.append(location.getLocType());
			// sb.append("\nlatitude : ");
			// sb.append(location.getLatitude());
			// sb.append("\nlontitude : ");
			// sb.append(location.getLongitude());
			// sb.append("\nradius : ");
			// sb.append(location.getRadius());
			// if (location.getLocType() == BDLocation.TypeGpsLocation){
			// sb.append("\nspeed : ");
			// sb.append(location.getSpeed());
			// sb.append("\nsatellite : ");
			// sb.append(location.getSatelliteNumber());
			// } else
			if (location.getLocType() == BDLocation.TypeNetWorkLocation) {
				// sb.append("\n省：");
				// sb.append(location.getProvince());
				// sb.append("\n市：");
				// sb.append(location.getCity());
				// sb.append("\n区/县：");
				// sb.append(location.getDistrict());
				// sb.append("\naddr : ");
				sb.append(location.getAddrStr());
				mLocationListener.getAddress(location.getAddrStr());
			}
			mLocationListener.getLocation("经度" + location.getLongitude() + "纬度"
					+ location.getLatitude());
			Log.i("Tag", sb.toString());
		}

		public void onReceivePoi(BDLocation poiLocation) {
			if (poiLocation == null) {
				return;
			}
			StringBuffer sb = new StringBuffer(256);
			sb.append("Poi time : ");
			sb.append(poiLocation.getTime());
			sb.append("\nerror code : ");
			sb.append(poiLocation.getLocType());
			sb.append("\nlatitude : ");
			sb.append(poiLocation.getLatitude());
			sb.append("\nlontitude : ");
			sb.append(poiLocation.getLongitude());
			sb.append("\nradius : ");
			sb.append(poiLocation.getRadius());
			if (poiLocation.getLocType() == BDLocation.TypeNetWorkLocation) {
				sb.append("\naddr : ");
				sb.append(poiLocation.getAddrStr());
			}
			if (poiLocation.hasPoi()) {
				sb.append("\nPoi:");
				sb.append(poiLocation.getPoi());
			} else {
				sb.append("noPoi information");
			}
		}
	}

	public class NotifyLister extends BDNotifyListener {
		public void onNotify(BDLocation mlocation, float distance) {
			mVibrator01.vibrate(1000);
		}
	}
}
