package com.gaobo.e5community.ui.widget;

import java.util.TimerTask;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

@SuppressLint("HandlerLeak")
public class ImageTimerTask extends TimerTask {
	private GuideGallery images_ga;
	public volatile boolean timeCondition = true;
	int gallerypisition = 0;
	final Handler autoGalleryHandler = new Handler() {
		public void handleMessage(Message message) {
			super.handleMessage(message);
			switch (message.what) {
			case 1:
				images_ga.setSelection(message.getData().getInt("pos"));
				break;
			}
		}
	};
	public ImageTimerTask(GuideGallery images_ga) {
		this.images_ga = images_ga;
	}
	public void run() {
		synchronized (this) {
			while (!timeCondition) {
				try {
					Thread.sleep(100);
					wait();
				} catch (InterruptedException e) {
					Thread.interrupted();
				}
			}
		}
		try {
			gallerypisition = images_ga.getSelectedItemPosition() + 1;
			System.out.println(gallerypisition + "");
			Message msg = new Message();
			Bundle date = new Bundle();
			date.putInt("pos", gallerypisition);
			msg.setData(date);
			msg.what = 1;
			autoGalleryHandler.sendMessage(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}