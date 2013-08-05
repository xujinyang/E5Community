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
import com.gaobo.e5community.model.Goods;
import com.gaobo.e5community.util.photo.ImageLoader;

/**
 * 菜市场的页面列表adapter
 * 
 * @author moblieXu
 * 
 */

public class GoodsAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<Goods> goodsArrayList;
	public ImageLoader imageLoader;

	public GoodsAdapter(Context context, ArrayList<Goods> goodsArrayList) {
		this.context = context;
		this.goodsArrayList = goodsArrayList;
		imageLoader = new ImageLoader(context);
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
					R.layout.fragment_rentalinfo_item, null);
			holder = new GoodsHolder();
			holder.name = (TextView) convertView
					.findViewById(R.id.vagetable_name);
			holder.image = (ImageView) convertView
					.findViewById(R.id.vagetable_image);
			holder.price = (TextView) convertView
					.findViewById(R.id.vagetable_price);
			convertView.setTag(holder);
		} else {
			holder = (GoodsHolder) convertView.getTag();
		}
		holder.name.setText(goodsArrayList.get(arg0).getName());
		imageLoader.DisplayImage(goodsArrayList.get(arg0).getImage(),
				holder.image);
		holder.price.setText(goodsArrayList.get(arg0).getPrice() + "元/个");
		return convertView;
	}

	public class GoodsHolder {
		private TextView name;
		private TextView price;
		private ImageView image;
	}
}
