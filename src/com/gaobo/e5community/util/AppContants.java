package com.gaobo.e5community.util;

public class AppContants {
	
	public static class PHONE {
		// 软件版本
		public static String VERSION  = "";
		// 手机序列号
		public static String DEVID    = "";
	}
	
	/**
	 *  API地址
	 * @author TK
	 */								//http://42.121.118.13:90/
	public static class API_URL {	//http://10.208.1.253:81/api/receive/index?access_token=klulaote525lkkudbmfq6aqbe4
		// IP 
		public static final String HTTP_IP		  = "http://42.121.118.13:90/";
		public static final String SHOPS     	  = HTTP_IP + "api/shop";
		public static final String SHOP_GOODS  	  = HTTP_IP + "api/product";
		public static final String SHOP_CATEGORY  = HTTP_IP + "api/product/category";
		public static final String ORDER	      = HTTP_IP + "api/order/index";
		public static final String ORDER_DETAIL   = HTTP_IP + "api/order/detail";
		public static final String LOGIN		  = HTTP_IP + "api/account/login";
		public static final String REGISTER       = HTTP_IP + "api/account/register";
		public static final String ORDER_CREATE   = HTTP_IP + "api/order/create";
		public static final String OPTEN_ADDRESS  = HTTP_IP + "api/receive/index";
		public static final String LOGOUT 		  = HTTP_IP + "api/account/logout";
		public static final String SHACK          = HTTP_IP + "api/product/random";
		// 检测更新
		public static final String CHECK_NOW      = HTTP_IP + "api/version/check";
		// 获取学校信息
		public static final String SCHOOLS		  = HTTP_IP + "api/order/school";	
		// 图片前缀
		public static final String PIC_PRE 		  = HTTP_IP + "upload/images/thumb/";
		public static final String PIC_400X400	  = HTTP_IP + "upload/images/400x400/";
		public static final String PIC_480X800	  = HTTP_IP + "upload/images/480x800/";
	}
	
	/**
	 * 缓存的key
	 * @author TK
	 */
	public static class CACHE_KEY {
		// Home界面店家缓存
		public static final String SHOPS_JSON_ARRAY = "shops";
	}
	
	/**
	 * 接口中的key
	 * @author TK
	 *
	 */
	public static class KEY {
		// 服务器中的key
		public static final String ID 				 = "id";
//		public static final String CID     			 = "cid";
		public static final String PHONE        	 = "phone";
		public static final String SCHOOL            = "school";
		public static final String SHOP_ID           = "shop_id";
		public static final String CLOSEING          = "closing";
		public static final String CLOSE_MSG         = "close_msg";
		public static final String STATUS            = "status";
		public static final String NAME              = "name";
		public static final String SHOP_NAME         = "shop_name";
		public static final String PRICE             = "price";
		public static final String RECEIVER          = "receiver";
		public static final String COVERPATH		 = "coverpath";
		public static final String TITLE 			 = "title";
		public static final String BUSINESS_HOURS    = "businesshours";
		public static final String ADDRESS           = "address";
		public static final String EMAIL             = "email";
		public static final String USERNAME          = "username";
		public static final String PASSWORD          = "password";
		public static final String CREATELINE        = "createline";
		public static final String BUY_COUNTS        = "buy_counts";
		public static final String MIN_PRICE         = "min_price";
		// 本地数据库中的key
//		public static final String GOODS_ID          = "_id";
		public static final String GOODS_NAME        = "goods_name";
		public static final String GOODS_SHOP_NAME   = "shop_name";
		public static final String GOODS_PRICE       = "goods_price";
		public static final String GOODS_NUM         = "goods_num";
		public static final String GOODS_IMAGE_URL   = "goods_image_url";
		public static final String GOODS_SELECTED    = "isSelected";
		public static final String GOODS_IMAGE_RES   = "goods_image_res";
		public static final String GOODS_FIRST       = "isFirst";
		
		public static final String ORDER_ID = "order_id";
		public static final String ORDER_PRICE = "order_price";
		public static final String ORDER_TIME = "order_time";
		
		public static final String ACCESS_TOKEN	     = "access_token";
		
		public static final String TOTAL_NUM        = "total_num";
		public static final String TOTAL_PRICE     = "total_price";
		
		public static final String SET_COOKIE = "Set-Cookie";
	}
	
	public static final String PREF_NAME = "suibian_pref";
	
	public static boolean isChecked = false;
	
	// 登录状态
//	public static boolean login_status = false;
	public static String cookie_value = "";
	// access_token记录
//	public static String auto = "";
//	public static String username_value = "";
	
}
