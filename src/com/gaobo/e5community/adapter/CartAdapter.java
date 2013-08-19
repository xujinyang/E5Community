package com.gaobo.e5community.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gaobo.e5community.R;
import com.gaobo.e5community.database.DataBaseDao;
import com.gaobo.e5community.model.Shopping;
import com.gaobo.e5community.util.ToastUtil;
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
	private DataBaseDao myDb;
	private onCountChangeListener mCountChangeListener;

	public interface onCountChangeListener {
		public void onCartCountChange();
	};

	public CartAdapter(Context context, List<Shopping> mShopArrayList) {
		this.context = context;
		this.shoppingArrayList = mShopArrayList;
		myDb = new DataBaseDao(context);
	}

	public void setCountChangeListener(onCountChangeListener l) {
		mCountChangeListener = l;
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
	public View getView(final int arg0, View convertView, ViewGroup arg2) {
		final CartHolder holder;
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
			holder.minus = (ImageView) convertView
					.findViewById(R.id.iv_cart_list_minus);
			holder.plus = (ImageView) convertView
					.findViewById(R.id.iv_cart_list_plus);
			holder.count = (TextView) convertView
					.findViewById(R.id.tv_cart_list_count);
			convertView.setTag(holder);
		} else {
			holder = (CartHolder) convertView.getTag();
		}
		holder.name.setText(shoppingArrayList.get(arg0).getName());
		holder.image.setImageUrl(shoppingArrayList.get(arg0).getPath());
		holder.price.setText(shoppingArrayList.get(arg0).getPrice() + "元/斤");
		holder.count.setText(shoppingArrayList.get(arg0).getCount() + "");
		holder.plus.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				int count = Integer.parseInt(holder.count.getText().toString());
				count++;
				holder.count.setText(count + "");
				shoppingArrayList.get(arg0).setCount(count);
				myDb.addGoods(shoppingArrayList.get(arg0));
				mCountChangeListener.onCartCountChange();
			}
		});
		holder.minus.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				int count = Integer.parseInt(holder.count.getText().toString());
				if (count > 0) {
					count--;
					holder.count.setText(count + "");
					shoppingArrayList.get(arg0).setCount(count);
					myDb.addGoods(shoppingArrayList.get(arg0));
					mCountChangeListener.onCartCountChange();
				} else {
					ToastUtil.show(context, "已经为0");
				}
			}
		});
		return convertView;
	}

	public class CartHolder {
		private TextView name;
		private TextView price;
		private SmartImageView image;
		private ImageView plus;
		private ImageView minus;
		private TextView count;
	}
}
