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
import com.gaobo.e5community.model.NearMarket;
import com.gaobo.e5community.model.Service;
import com.gaobo.e5community.util.photo.ImageLoader;
import com.loopj.android.image.SmartImageView;

/**
 * 菜市场的页面列表adapter
 * 
 * @author moblieXu
 * 
 */

public class NearMarketAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<NearMarket> nearMarketArrayList;

	public NearMarketAdapter(Context context,
			ArrayList<NearMarket> nearMarketArrayList) {
		this.context = context;
		this.nearMarketArrayList = nearMarketArrayList;
	}

	@Override
	public int getCount() {
		return nearMarketArrayList.size();
	}

	@Override
	public Object getItem(int arg0) {
		return nearMarketArrayList.get(arg0);
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
					R.layout.activity_nearmarket_list_item, null);
			holder = new GoodsHolder();
			holder.name = (TextView) convertView
					.findViewById(R.id.tv_nearmarket_name);
			holder.image = (SmartImageView) convertView
					.findViewById(R.id.sv_nearmarket_image);
			holder.price = (TextView) convertView
					.findViewById(R.id.tv_nearmarket_price);
			convertView.setTag(holder);
		} else {
			holder = (GoodsHolder) convertView.getTag();
		}
		holder.name.setText(nearMarketArrayList.get(arg0).getName());
		holder.image.setImageUrl(nearMarketArrayList.get(arg0).getPath());
		holder.price.setText(nearMarketArrayList.get(arg0).getPrice() + "元/个");
		return convertView;
	}

	public class GoodsHolder {
		private TextView name;
		private TextView price;
		private SmartImageView image;
	}
}
