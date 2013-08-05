package com.gaobo.e5community.adapter;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

import com.gaobo.e5community.R;
import com.gaobo.e5community.ui.MainActivity;

public class GalleryImageAdapter extends BaseAdapter {
	private List<String> imageUrls; // 图片地址list
	private Context context;
	private GalleryImageAdapter self;
	Uri uri;
	Intent intent;
	ImageView imageView;
	public static Integer[] imgs = { R.drawable.flag1, R.drawable.flag2,
			R.drawable.flag3, R.drawable.flag4, R.drawable.flag5 };

	public GalleryImageAdapter(Context context) {
		this.context = context;
		this.self = this;
	}

	public int getCount() {
		return Integer.MAX_VALUE;
	}

	public Object getItem(int position) {
		return imageUrls.get(position % imgs.length);
	}

	public long getItemId(int position) {
		return position;
	}

	@SuppressWarnings("unused")
	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			try {
				switch (msg.what) {
				case 0: {
					self.notifyDataSetChanged();
				}
					break;
				}
				super.handleMessage(msg);
			} catch (Exception e) {
			}
		}
	};

	public View getView(int position, View convertView, ViewGroup parent) {

		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.gallery_item, null); // 实例化convertView
			Gallery.LayoutParams params = new Gallery.LayoutParams(
					Gallery.LayoutParams.WRAP_CONTENT,
					Gallery.LayoutParams.WRAP_CONTENT);
			convertView.setLayoutParams(params);
			convertView.setTag(imgs);
		} else {
			// image = (Bitmap) convertView.getTag();
		}
		imageView = (ImageView) convertView.findViewById(R.id.gallery_image);
		imageView.setImageResource(imgs[position % imgs.length]);
		// 设置缩放比例：保持原样
		imageView.setScaleType(ImageView.ScaleType.FIT_XY);
		((MainActivity) context).changePointView(position % imgs.length);
		return convertView;
	}
}
