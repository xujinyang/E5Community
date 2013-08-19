package com.gaobo.e5community.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gaobo.e5community.R;
import com.gaobo.e5community.database.DataBaseDao;
import com.gaobo.e5community.model.Goods;
import com.gaobo.e5community.util.photo.ImageLoader;
import com.loopj.android.image.SmartImageView;

/**
 * 菜市场的页面列表adapter
 * 
 * @author moblieXu
 * 
 */

public class GoodsAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<Goods> goodsArrayList;
	private DataBaseDao myDb;

	public GoodsAdapter(Context context, ArrayList<Goods> goodsArrayList) {
		this.context = context;
		this.goodsArrayList = goodsArrayList;
		myDb = new DataBaseDao(context);
	}

	@Override
	public int getCount() {
		return goodsArrayList.size();
	}

	@Override
	public Object getItem(int arg0) {
		return goodsArrayList.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int arg0, View convertView, ViewGroup arg2) {
		GoodsHolder holder;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.activity_goods_list_item, null);
			holder = new GoodsHolder();
			holder.name = (TextView) convertView
					.findViewById(R.id.tv_goods_name);
			holder.image = (SmartImageView) convertView
					.findViewById(R.id.sv_goods_image);
			holder.price = (TextView) convertView
					.findViewById(R.id.tv_goods_price);
			holder.count = (TextView) convertView
					.findViewById(R.id.tv_goods_cartcount);
			convertView.setTag(holder);
		} else {
			holder = (GoodsHolder) convertView.getTag();
		}
		holder.name.setText(goodsArrayList.get(arg0).getName());
		holder.image.setImageUrl(goodsArrayList.get(arg0).getPath());
		holder.price.setText(goodsArrayList.get(arg0).getPrice() + "元/个");
		int count = 0;
		count = myDb.getGoodsCount(goodsArrayList.get(arg0).getId());
		if (count > 0) {
			holder.count.setVisibility(View.VISIBLE);
			holder.count.setText(count + "");
		}
		return convertView;
	}

	public class GoodsHolder {
		private TextView name;
		private TextView price;
		private SmartImageView image;
		private TextView count;
	}
}
