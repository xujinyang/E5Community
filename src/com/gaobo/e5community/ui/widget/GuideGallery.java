package com.gaobo.e5community.ui.widget;

import java.util.TimerTask;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.Gallery;

import com.gaobo.e5community.adapter.GalleryImageAdapter;
import com.gaobo.e5community.ui.MainActivity;

@SuppressWarnings("deprecation")
public class GuideGallery extends Gallery {

	private MainActivity m_iact;

	public GuideGallery(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public GuideGallery(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public GuideGallery(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public void setImageActivity(MainActivity iact) {
		m_iact = iact;
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		// TODO Auto-generated method stub
		int kEvent;
		if (isScrollingLeft(e1, e2)) { // Check if scrolling left
			kEvent = KeyEvent.KEYCODE_DPAD_LEFT;
			System.out.println("AAAA" + this.getSelectedItemPosition());
		} else { // Otherwise scrolling right
			kEvent = KeyEvent.KEYCODE_DPAD_RIGHT;
			System.out.println("BBB" + this.getSelectedItemPosition());
		}
		onKeyDown(kEvent, null);
		if (this.getSelectedItemPosition() == 0)
			this.setSelection(GalleryImageAdapter.imgs.length);
		System.out.println("DDD" + this.getSelectedItemPosition());
		new java.util.Timer().schedule(new TimerTask() {
			public void run() {
				m_iact.mTimeFlag = false;
				this.cancel();
			}
		}, 3000);
		return true;

	}

	private boolean isScrollingLeft(MotionEvent e1, MotionEvent e2) {
		System.out.println(this.getSelectedItemPosition());
		return e2.getX() > e1.getX();

	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		m_iact.mTimeTaks.timeCondition = false;
		return super.onScroll(e1, e2, distanceX, distanceY);
	}

}
