package com.gaobo.e5community.database;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.gaobo.e5community.model.Shopping;
import com.gaobo.e5community.util.AppContants;

public class DataBaseDao {

	private static final String NATIVE_GOODS_TABLE = "shopping";
	public static final String ID = "_id";
	public static final String GOODS_ID = "goods_id";
	public static final String COMMUNITY_ID = "community_id";
	public static final String CATEGORY_ID = "category_id";
	public static final String ALIAS = "alias";
	public static final String NAME = "name";
	public static final String PATH = "path";
	public static final String PRICE = "price";
	public static final String CONTENT = "content";
	public static final String COUNT = "count";
	public static final String ISPAY = "isPay";
	public static final String TEL = "tel";

	private static Context context;

	public DataBaseDao(Context context) {
		this.context = context;
	}

	/**
	 * 获取数据库操作类的对象
	 * 
	 * @param context
	 * @return
	 */
	private static DataBaseHelper getBaseHelper() {
		return DataBaseHelper.getInstance(context);
	}

	/**
	 * 插入商品到购物车
	 * 
	 * @param context
	 * @param id
	 *            商品ID
	 * @param name
	 *            名字
	 * @param coverpath
	 *            图片URL
	 * @param price
	 *            价格
	 * @param num
	 *            数量
	 */
	public void addGoods(Shopping goods) {
		if (isExistNativeGoods(goods)) {
			updateGoodsAll(goods);
			return;
		}
		SQLiteDatabase db = getBaseHelper().getWritableDatabase();
		ContentValues cv = new ContentValues();
		cv.put(GOODS_ID, goods.getGoods_id());
		cv.put(COMMUNITY_ID, goods.getCommunity_id());
		cv.put(CATEGORY_ID, goods.getCategory_id());
		cv.put(ALIAS, goods.getAlias());
		cv.put(NAME, goods.getName());
		cv.put(PATH, goods.getPath());
		cv.put(PRICE, goods.getPrice());
		cv.put(CONTENT, goods.getContent());
		cv.put(COUNT, goods.getCount());
		cv.put(ISPAY, goods.getIsPay());
		cv.put(TEL, goods.getTel());
		long row = db.insert(NATIVE_GOODS_TABLE, null, cv);
		System.out.println("加入了一条数据"+goods.getName());
		db.close();
	}

	/**
	 * 得到购物车此ID的商品的个数
	 * 
	 * @param context
	 * @return id
	 */
	public int getExistGoods(int id) {
		int num = 0;
		SQLiteDatabase db = getBaseHelper().getWritableDatabase();
		Cursor c = db.query(NATIVE_GOODS_TABLE,
				new String[] { AppContants.KEY.GOODS_NUM }, GOODS_ID + "=?",
				new String[] { String.valueOf(id) }, null, null, null, null);
		if (c.moveToNext()) {
			num = c.getInt(0);
		}
		return num;
	}

	/**
	 * 查看购物车此ID的商品是否存在
	 * 
	 * @param context
	 * @return id
	 */
	public boolean isExistNativeGoods(Shopping s) {
		SQLiteDatabase db = getBaseHelper().getWritableDatabase();
		String where = GOODS_ID + " =?";
		String[] whereValue = { s.getGoods_id() + "" };
		Cursor cursor = db.query(NATIVE_GOODS_TABLE, null, where, whereValue,
				null, null, "_id desc");
		if (cursor != null && cursor.getCount() > 0) {
			return true;
		}
		return false;
	}

	/**
	 * 删除购物车商品对应ID的商品
	 * 
	 * @param context
	 * @param id
	 */
	public void deleteGoods(Context context, int id) {
		String sql = "DELETE FROM " + NATIVE_GOODS_TABLE + " WHERE " + GOODS_ID
				+ "=" + id + ";";
		SQLiteDatabase db = getBaseHelper().getWritableDatabase();
		db.execSQL(sql);
	}

	/**
	 * 更新购物车商品数量
	 * 
	 * @param context
	 * @param id
	 * @param num
	 * @return
	 */
	public int updateGoodsNum(Context context, int id, int num) {
		ContentValues cv = new ContentValues();
		SQLiteDatabase db = getBaseHelper().getWritableDatabase();
		cv.put(AppContants.KEY.GOODS_NUM, num);
		String[] args = { String.valueOf(id) };
		return db.update(NATIVE_GOODS_TABLE, cv, GOODS_ID + "=?", args);
	}

	/**
	 * 更新购物车商品信息
	 * 
	 * @param context
	 * @param id
	 * @param num
	 * @return
	 */
	public int updateGoodsAll(Shopping goods) {
		SQLiteDatabase db = getBaseHelper().getWritableDatabase();
		ContentValues cv = new ContentValues();
		cv.put(GOODS_ID, goods.getGoods_id());
		cv.put(COMMUNITY_ID, goods.getCommunity_id());
		cv.put(CATEGORY_ID, goods.getCategory_id());
		cv.put(ALIAS, goods.getAlias());
		cv.put(NAME, goods.getName());
		cv.put(PATH, goods.getPath());
		cv.put(PRICE, goods.getPrice());
		cv.put(CONTENT, goods.getContent());
		cv.put(COUNT, goods.getCount());
		cv.put(ISPAY, goods.getIsPay());
		cv.put(TEL, goods.getTel());
		long row = db.insert(NATIVE_GOODS_TABLE, null, cv);
		String[] args = { String.valueOf(goods.getGoods_id()) };
		return db.update(NATIVE_GOODS_TABLE, cv, GOODS_ID + "=?", args);
	}

	/**
	 * 更新商品是否被选择
	 * 
	 * @param context
	 * @param id
	 * @param isSelected
	 * @return
	 */
	public int updateGoodsSelected(Context context, int id, boolean isSelected) {
		ContentValues cv = new ContentValues();
		SQLiteDatabase db = getBaseHelper().getWritableDatabase();
		cv.put(AppContants.KEY.GOODS_SELECTED, isSelected ? 1 : 0);
		String[] args = { String.valueOf(id) };
		return db.update(NATIVE_GOODS_TABLE, cv, GOODS_ID + "=?", args);
	}

	/**
	 * 获得所有的购物车数据
	 * 
	 * @param journalAL
	 * @return
	 */
	public void selectAllShoppings(List<Shopping> shoppingAL) {
		shoppingAL.clear();
		SQLiteDatabase db = getBaseHelper().getReadableDatabase();
		Cursor cursor = db.query(NATIVE_GOODS_TABLE, null, null, null, null,
				null, "_id desc");
		System.out.println("购物车里面的数据个数" + cursor.getCount());
		while (cursor.moveToNext()) {
			// 安发送了的在下面，没发送的在上面排序排序
			Shopping s = new Shopping();
			s.setAlias(cursor.getInt(cursor.getColumnIndex(ALIAS)));
			s.setCategory_id(cursor.getInt(cursor.getColumnIndex(CATEGORY_ID)));
			s.setCommunity_id(cursor.getInt(cursor.getColumnIndex(COMMUNITY_ID)));
			s.setContent(cursor.getString(cursor.getColumnIndex(CONTENT)));
			s.setCount(cursor.getInt(cursor.getColumnIndex(COUNT)));
			s.setGoods_id(cursor.getInt(cursor.getColumnIndex(GOODS_ID)));
			s.setId(cursor.getInt(cursor.getColumnIndex(ID)));
			s.setIsPay(cursor.getInt(cursor.getColumnIndex(ISPAY)));
			s.setName(cursor.getString(cursor.getColumnIndex(NAME)));
			s.setPath(cursor.getString(cursor.getColumnIndex(PATH)));
			s.setPrice(cursor.getFloat(cursor.getColumnIndex(PRICE)));
			s.setTel(cursor.getString(cursor.getColumnIndex(TEL)));
			shoppingAL.add(s);
		}
		if (!cursor.isClosed()) {
			cursor.close();
		}
		db.close();
	}

	/**
	 * 获取购物车餐品的数量
	 * 
	 * @param context
	 * @return
	 */
	public int getGoodsCount(Context context) {
		SQLiteDatabase db = getBaseHelper().getWritableDatabase();
		Cursor c = db.query(NATIVE_GOODS_TABLE, null, null, null, null, null,
				null);
		if (c == null || c.getCount() <= 0) {
			return 0;
		} else {
			return c.getCount();
		}
	}

	/**
	 * 获取购物车餐品的数量
	 * 
	 * @param context
	 * @return
	 */
	public boolean isSelectedGoods(Context context, int id) {
		SQLiteDatabase db = getBaseHelper().getWritableDatabase();
		Cursor c = db.query(NATIVE_GOODS_TABLE,
				new String[] { AppContants.KEY.GOODS_SELECTED }, GOODS_ID
						+ "=?", new String[] { String.valueOf(id) }, null,
				null, null, null);
		if (c.moveToNext()) {
			return c.getInt(0) == 1 ? true : false;
		}
		return true;
	}
}
