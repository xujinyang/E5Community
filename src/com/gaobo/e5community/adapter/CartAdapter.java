package com.gaobo.e5community.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.gaobo.e5community.R;
import com.gaobo.e5community.model.Shopping;
import com.gaobo.e5community.model.Vagetable;
import com.loopj.android.image.SmartImageView;

/**
 * 菜市场的页面列表adapter
 * 
 * @author moblieXu
 * 
 */
public class CartAdapter extends BaseAdapter {
	private Context context;
	private List<Shopping> shoppingArrayList;

	public CartAdapter(Context context, List<Shopping> mShopArrayList) {
		this.context = context;
		this.shoppingArrayList = mShopArrayList;
	}

	@Override
	public int getCount() {
		return shoppingArrayList.size();
	}

	@Override
	public Object getItem(int arg0) {
		return shoppingArrayList.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int arg0, View convertView, ViewGroup arg2) {
		CartHolder holder;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.activity_cart_list_item, null);
			holder = new CartHolder();
			holder.name = (TextView) convertView
					.findViewById(R.id.tv_shopping_name);
			holder.image = (SmartImageView) convertView
					.findViewById(R.id.sv_shopping_image);
			holder.price = (TextView) convertView
					.findViewById(R.id.tv_shopping_price);
			convertView.setTag(holder);
		} else {
			holder = (CartHolder) convertView.getTag();
		}
		holder.name.setText(shoppingArrayList.get(arg0).getName());
		holder.image.setImageUrl(shoppingArrayList.get(arg0).getPath());
		holder.price.setText(shoppingArrayList.get(arg0).getPrice() + "元/斤");
		return convertView;
	}
	public class CartHolder {
		private TextView name;
		private TextView price;
		private SmartImageView image;
	}
}
