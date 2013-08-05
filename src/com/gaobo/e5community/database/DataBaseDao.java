package com.gaobo.e5community.database;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.gaobo.e5community.util.AppContants;

public class DataBaseDao {

	private static final String NATIVE_GOODS_TABLE = "goods";
	private static final String SHACK_GOODS_TABLE = "shack_goods";

	public static final String GOODS_ID = "_id";

	/**
	 * 获取数据库操作类的对象
	 * 
	 * @param context
	 * @return
	 */
	private static DataBaseHelper getBaseHelper(Context context) {
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
	public void addGoods(Context context, int id, String name, String shopName,
			Double price, String coverpath, int num) {
		if (isExistNativeGoods(context, id)) {
			updateGoodsAll(context, id, name, shopName, price, coverpath, num);
			return;
		}
		;
		SQLiteDatabase db = getBaseHelper(context).getWritableDatabase();
		String sql = "INSERT INTO " + NATIVE_GOODS_TABLE
				+ "(_id, shop_name, goods_name, goods_price, "
				+ "goods_image_url, goods_num, isSelected)" + "VALUES" + "("
				+ id + ",'" + shopName + "','" + name + "'," + price + ",'"
				+ coverpath + "'," + num + "," + 1 + ");";
		// 插入数据
		db.execSQL(sql);
	}

	/**
	 * 插入商品到摇一摇
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
	public void addShackGoods(Context context, int id, String name,
			String shopName, Double price, String coverpath, int num) {
		if (isExistShackGoods(context, id)) {
			return;
		}
		SQLiteDatabase db = getBaseHelper(context).getWritableDatabase();
		String sql = "INSERT INTO " + SHACK_GOODS_TABLE
				+ "(_id, shop_name, goods_name, goods_price, "
				+ "goods_image_url, goods_num)" + "VALUES" + "(" + id + ",'"
				+ shopName + "','" + name + "'," + price + ",'" + coverpath
				+ "'," + num + ");";
		// 插入数据
		db.execSQL(sql);
	}

	/**
	 * 得到购物车此ID的商品的个数
	 * 
	 * @param context
	 * @return id
	 */
	public int getExistGoods(Context context, int id) {
		int num = 0;
		SQLiteDatabase db = getBaseHelper(context).getWritableDatabase();
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
	public boolean isExistNativeGoods(Context context, int id) {
		boolean flag = false;
		SQLiteDatabase db = getBaseHelper(context).getWritableDatabase();
		Cursor c = db.query(NATIVE_GOODS_TABLE, new String[] { GOODS_ID },
				GOODS_ID + "=?", new String[] { String.valueOf(id) }, null,
				null, null, null);
		if (c != null && c.getCount() > 0) {
			flag = true;
		}
		return flag;
	}

	/**
	 * 查看摇一摇此ID的商品是否存在
	 * 
	 * @param context
	 * @return id
	 */
	public boolean isExistShackGoods(Context context, int id) {
		boolean flag = false;
		SQLiteDatabase db = getBaseHelper(context).getWritableDatabase();
		Cursor c = db.query(SHACK_GOODS_TABLE, new String[] { GOODS_ID },
				GOODS_ID + "=?", new String[] { String.valueOf(id) }, null,
				null, null, null);
		if (c != null && c.getCount() > 0) {
			flag = true;
		}
		return flag;
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
		SQLiteDatabase db = getBaseHelper(context).getWritableDatabase();
		db.execSQL(sql);
	}

	/**
	 * 删除摇一摇记录的所有历史商品
	 * 
	 * @param context
	 */
	public void deleteShackGoodsAll(Context context) {
		String sql = "DELETE FROM " + SHACK_GOODS_TABLE;
		SQLiteDatabase db = getBaseHelper(context).getWritableDatabase();
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
		SQLiteDatabase db = getBaseHelper(context).getWritableDatabase();
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
	public int updateGoodsAll(Context context, int id, String name,
			String shopName, Double price, String coverpath, int num) {
		ContentValues cv = new ContentValues();
		SQLiteDatabase db = getBaseHelper(context).getWritableDatabase();

		cv.put(AppContants.KEY.GOODS_NAME, name);
		cv.put(AppContants.KEY.GOODS_SHOP_NAME, shopName);
		cv.put(AppContants.KEY.GOODS_PRICE, price);
		cv.put(AppContants.KEY.GOODS_IMAGE_URL, coverpath);
		cv.put(AppContants.KEY.GOODS_NUM, num);

		String[] args = { String.valueOf(id) };
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
		SQLiteDatabase db = getBaseHelper(context).getWritableDatabase();
		cv.put(AppContants.KEY.GOODS_SELECTED, isSelected ? 1 : 0);
		String[] args = { String.valueOf(id) };
		return db.update(NATIVE_GOODS_TABLE, cv, GOODS_ID + "=?", args);
	}

	/**
	 * 获取购物车所有的数据
	 * 
	 * @param context
	 * @return
	 */
	public ArrayList<ContentValues> getGoodsAllData(Context context) {
		ArrayList<ContentValues> data = new ArrayList<ContentValues>();
		;
		SQLiteDatabase db = getBaseHelper(context).getWritableDatabase();
		String temp = "";

		Cursor c = db.query(NATIVE_GOODS_TABLE, null, null, null, null, null,
				AppContants.KEY.GOODS_SHOP_NAME);

		while (c.moveToNext()) {
			ContentValues contentValues = new ContentValues();
			contentValues.put(AppContants.KEY.ID, c.getInt(0));
			contentValues.put(AppContants.KEY.GOODS_SHOP_NAME, c.getString(1));
			contentValues.put(AppContants.KEY.GOODS_NAME, c.getString(2));
			contentValues.put(AppContants.KEY.GOODS_PRICE, c.getFloat(3));
			contentValues.put(AppContants.KEY.GOODS_IMAGE_URL, c.getString(4));
			contentValues.put(AppContants.KEY.GOODS_NUM, c.getInt(5));
			contentValues.put(AppContants.KEY.GOODS_SELECTED,
					c.getInt(6) == 1 ? true : false);

			if (temp.equals(contentValues.getAsString(
					AppContants.KEY.GOODS_SHOP_NAME).trim())) {
				contentValues.put(AppContants.KEY.GOODS_FIRST, false);
			} else {
				contentValues.put(AppContants.KEY.GOODS_FIRST, true);
			}

			data.add(contentValues);
			temp = contentValues.getAsString(AppContants.KEY.GOODS_SHOP_NAME)
					.trim();
			contentValues = null;
		}
		if (data == null || data.size() <= 0) {
			return null;
		} else {
			return data;
		}
	}

	/**
	 * 获取摇一摇记录的所有数据
	 * 
	 * @param context
	 * @return
	 */
	public ArrayList<ContentValues> getShackGoodsAllData(Context context) {
		ArrayList<ContentValues> data = new ArrayList<ContentValues>();
		;
		SQLiteDatabase db = getBaseHelper(context).getWritableDatabase();
		Cursor c = db.query(SHACK_GOODS_TABLE, null, null, null, null, null,
				null);
		while (c.moveToNext()) {
			ContentValues contentValues = new ContentValues();

			contentValues.put(AppContants.KEY.ID, c.getInt(0));
			contentValues.put(AppContants.KEY.GOODS_SHOP_NAME, c.getString(1));
			contentValues.put(AppContants.KEY.GOODS_NAME, c.getString(2));
			contentValues.put(AppContants.KEY.GOODS_PRICE, c.getFloat(3));
			contentValues.put(AppContants.KEY.GOODS_IMAGE_URL, c.getString(4));
			contentValues.put(AppContants.KEY.GOODS_NUM, c.getInt(5));

			data.add(contentValues);
			contentValues = null;
		}
		return data;
	}

	/**
	 * 获取立刻购买的数据
	 * 
	 * @param context
	 * @return
	 */
	public ArrayList<ContentValues> getGoodsSelectedData(Context context) {
		ArrayList<ContentValues> data = new ArrayList<ContentValues>();
		;
		SQLiteDatabase db = getBaseHelper(context).getWritableDatabase();
		String temp = "";
		Cursor c = db.query(NATIVE_GOODS_TABLE, null, null, null, null, null,
				AppContants.KEY.GOODS_SHOP_NAME);
		while (c.moveToNext()) {
			ContentValues contentValues = new ContentValues();

			if (c.getInt(6) == 1 ? true : false) {
				contentValues.put(AppContants.KEY.ID, c.getInt(0));
				contentValues.put(AppContants.KEY.GOODS_SHOP_NAME,
						c.getString(1));
				contentValues.put(AppContants.KEY.GOODS_NAME, c.getString(2));
				contentValues.put(AppContants.KEY.GOODS_PRICE, c.getFloat(3));
				contentValues.put(AppContants.KEY.GOODS_IMAGE_URL,
						c.getString(4));
				contentValues.put(AppContants.KEY.GOODS_NUM, c.getInt(5));
				contentValues.put(AppContants.KEY.GOODS_SELECTED,
						c.getInt(6) == 1 ? true : false);
				if (temp.equals(contentValues
						.getAsString(AppContants.KEY.GOODS_SHOP_NAME))) {
					contentValues.put(AppContants.KEY.GOODS_FIRST, false);
				} else {
					contentValues.put(AppContants.KEY.GOODS_FIRST, true);
				}

				data.add(contentValues);
				temp = contentValues
						.getAsString(AppContants.KEY.GOODS_SHOP_NAME);
			}

			contentValues = null;
		}
		if (data == null || data.size() <= 0) {
			return null;
		} else {
			return data;
		}
	}

	/**
	 * 获取购物车餐品的数量
	 * 
	 * @param context
	 * @return
	 */
	public int getGoodsCount(Context context) {
		SQLiteDatabase db = getBaseHelper(context).getWritableDatabase();
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
		SQLiteDatabase db = getBaseHelper(context).getWritableDatabase();
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
