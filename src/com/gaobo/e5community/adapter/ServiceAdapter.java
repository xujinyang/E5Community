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
import com.gaobo.e5community.model.Service;
import com.gaobo.e5community.util.photo.ImageLoader;
import com.loopj.android.image.SmartImageView;

/**
 * 菜市场的页面列表adapter
 * 
 * @author moblieXu
 * 
 */

public class ServiceAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<Service> serviceArrayList;

	public ServiceAdapter(Context context, ArrayList<Service> serviceArrayList) {
		this.context = context;
		this.serviceArrayList = serviceArrayList;
	}

	@Override
	public int getCount() {
		return serviceArrayList.size();
	}

	@Override
	public Object getItem(int arg0) {
		return serviceArrayList.get(arg0);
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
					R.layout.activity_service_list_item, null);
			holder = new GoodsHolder();
			holder.name = (TextView) convertView
					.findViewById(R.id.tv_service_name);
			holder.image = (SmartImageView) convertView
					.findViewById(R.id.sv_service_image);
			holder.price = (TextView) convertView
					.findViewById(R.id.tv_service_price);
			convertView.setTag(holder);
		} else {
			holder = (GoodsHolder) convertView.getTag();
		}
		holder.name.setText(serviceArrayList.get(arg0).getName());
		holder.image.setImageUrl(serviceArrayList.get(arg0).getPath());
		holder.price.setText(serviceArrayList.get(arg0).getPrice() + "元/个");
		return convertView;
	}

	public class GoodsHolder {
		private TextView name;
		private TextView price;
		private SmartImageView image;
	}
}
