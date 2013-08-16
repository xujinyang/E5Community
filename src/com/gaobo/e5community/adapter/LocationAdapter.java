package com.gaobo.e5community.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.gaobo.e5community.R;
import com.gaobo.e5community.model.Community;

/**
 * 定位页面位置列表的Adapter
 * 
 * @author moblieXu
 * 
 */
public class LocationAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<Community> commnuntyArrayList;

	public LocationAdapter(Context context,
			ArrayList<Community> commnuntyArrayList) {
		this.context = context;
		this.commnuntyArrayList = commnuntyArrayList;
	}

	@Override
	public int getCount() {
		return commnuntyArrayList.size();
	}

	@Override
	public Object getItem(int arg0) {
		return commnuntyArrayList.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int arg0, View convertView, ViewGroup arg2) {
		CommunityHolder holder;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.adapter_item_location, null);
			holder = new CommunityHolder();
			holder.name = (TextView) convertView
					.findViewById(R.id.community_name);
			convertView.setTag(holder);
		} else {
			holder = (CommunityHolder) convertView.getTag();
		}
		holder.name.setText(commnuntyArrayList.get(arg0).getName());
		return convertView;
	}

	public class CommunityHolder {
		private TextView name;
	}
}
