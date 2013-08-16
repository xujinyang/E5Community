package com.gaobo.e5community.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.gaobo.e5community.R;
import com.gaobo.e5community.model.Vagetable;
import com.loopj.android.image.SmartImageView;

/**
 * 菜市场的页面列表adapter
 * 
 * @author moblieXu
 * 
 */
public class VagetableAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<Vagetable> vagetableArrayList;

	public VagetableAdapter(Context context,
			ArrayList<Vagetable> vagetableArrayList) {
		this.context = context;
		this.vagetableArrayList = vagetableArrayList;
	}

	@Override
	public int getCount() {
		return vagetableArrayList.size();
	}

	@Override
	public Object getItem(int arg0) {
		return vagetableArrayList.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int arg0, View convertView, ViewGroup arg2) {
		VagetableHolder holder;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.activity_vagetable_list_item, null);
			holder = new VagetableHolder();
			holder.name = (TextView) convertView
					.findViewById(R.id.vagetable_name);
			holder.image = (SmartImageView) convertView
					.findViewById(R.id.vagetable_image);
			holder.price = (TextView) convertView
					.findViewById(R.id.vagetable_price);
			convertView.setTag(holder);
		} else {
			holder = (VagetableHolder) convertView.getTag();
		}
		holder.name.setText(vagetableArrayList.get(arg0).getName());
		holder.image.setImageUrl(vagetableArrayList.get(arg0).getPath());
		holder.price.setText(vagetableArrayList.get(arg0).getPrice() + "元/斤");
		return convertView;
	}

	public class VagetableHolder {
		private TextView name;
		private TextView price;
		private SmartImageView image;
	}
}
