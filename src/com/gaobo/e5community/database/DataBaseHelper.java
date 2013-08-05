package com.gaobo.e5community.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {
	private static DataBaseHelper mInstance = null;
	//数据库名称
	public static final String DATABASE_NAME = "suibian_v3.db";
	//数据库版本号
	private static final int DATABASE_VERSION = 1;
	
	//数据库SQL语句 添加一个表---->本地购物车记录
	private static final String NATIVE_GOODS_TABLE = "CREATE TABLE goods("
			+ "_id INTEGER PRIMARY KEY,"
			+ "shop_name VARCHAR2(100) NOT NULL,"
			+ "goods_name VARCHAR2(100) NOT NULL,"
			+ "goods_price DECIMAL(12,2) NOT NULL,"
			+ "goods_image_url VARCHAR2(200) NOT NULL,"
			+ "goods_num INTEGER DEFAULT 0,"
			+ "isSelected INTEGER NUT NULL);";
	
	//数据库SQL语句 添加一个表---->摇一摇商品记录
	private static final String SHACK_GOODS_TABLE = "CREATE TABLE shack_goods("
			+ "_id INTEGER PRIMARY KEY,"
			+ "shop_name VARCHAR2(100) NOT NULL,"
			+ "goods_name VARCHAR2(100) NOT NULL,"
			+ "goods_price DECIMAL(12,2) NOT NULL,"
			+ "goods_image_url VARCHAR2(200) NOT NULL,"
			+ "goods_num INTEGER DEFAULT 0);";
	
	/**构造方法context.getCacheDir().getAbsoluteFile()**/
	
	public DataBaseHelper(Context context) {
		super(context,  context.getCacheDir().getAbsoluteFile() + "/" + DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	/**单例模式**/
	public static synchronized DataBaseHelper getInstance(Context context){
		if( mInstance == null ) {
			mInstance = new DataBaseHelper(context);
		}
		return mInstance;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		//向数据中添加表
		db.execSQL(NATIVE_GOODS_TABLE);
		db.execSQL(SHACK_GOODS_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		/**可以拿到当前数据库的版本信息 与之前数据库的版本信息 用来更新数据库**/		
	}
	
	/**
	 * 删除数据库
	 * @param context
	 * @return
	 */
	public static boolean deleteDataBase(Context context) {
		return context.deleteDatabase(DATABASE_NAME);
	}

}
