package com.gaobo.e5community.ui;

import java.util.Timer;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.actionbarsherlock.ActionBarSherlock.OnCreateOptionsMenuListener;
import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.gaobo.e5community.R;
import com.gaobo.e5community.adapter.GalleryImageAdapter;
import com.gaobo.e5community.fragmentActivity.RentalInfoActivity;
import com.gaobo.e5community.fragmentActivity.VagetableActivity;
import com.gaobo.e5community.ui.widget.GuideGallery;
import com.gaobo.e5community.ui.widget.ImageTimerTask;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

/***
 * 抽屉交互界面
 * 
 * @author moblieXu
 * 
 */
public class MainActivity extends SlidingFragmentActivity implements
		OnCreateOptionsMenuListener {
	/**
	 * 全局变量
	 */
	private ActionBar mActionBar;
	private SlidingMenu mSlidingMenu;
	private int width;// 屏幕宽度
	/**
	 * 主页面控件
	 */
	private Button mBtn_rentalInfo;
	private Button mBtn_jobInfo;
	private Button mBtn_secondHandInfo;
	private Button mBtn_groupPurchaseInfo;
	private LinearLayout mLv_vagetable;
	private LinearLayout mLv_goods;
	private LinearLayout mLv_community;
	private LinearLayout mLv_market;
	/**
	 * 右抽屉控件
	 */
	private ImageView mIv_headPhoto;
	private TextView mTv_location;
	private Button mBtn_settings;
	private Button mBtn_exit;
	/**
	 * 轮播控件
	 */
	public GuideGallery mGG_images;
	public int mPositon = 0;
	public Thread mTimeThread = null;
	public boolean mTimeFlag = true;
	public boolean isExit = false;
	public ImageTimerTask mTimeTaks = null;
	public Timer mAutoGallery = new Timer();
	public LinearLayout mPointLinear;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setBehindContentView(R.layout.menu_frame);
		setActionBar();
		setSlidingMenu();
		ContentViewInit();
		rightSlidingInit();
		galleryViewinit();
		setContentViewListener();
		setRightSgListener();
		startGallery();
	}

	/**
	 * 设置ActionBar
	 */
	private void setActionBar() {
		mActionBar = getSupportActionBar();
		mActionBar.setDisplayHomeAsUpEnabled(false);
		mActionBar.setDisplayShowTitleEnabled(true);
		mActionBar.setDisplayShowHomeEnabled(true);
		mActionBar.setTitle("社区");
	}
	/***
	 * 设置SlidingMenu和Actionbar
	 */
	private void setSlidingMenu() {
		mSlidingMenu = getSlidingMenu();
		mSlidingMenu.setMode(SlidingMenu.RIGHT);
		mSlidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		mSlidingMenu.setShadowWidthRes(R.dimen.shadow_width);
		mSlidingMenu.setShadowDrawable(R.drawable.shadow);
		mSlidingMenu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		mSlidingMenu.setFadeDegree(0.35f);
		mSlidingMenu.setSecondaryMenu(R.layout.slidingmenu_right);
		mSlidingMenu.setSecondaryShadowDrawable(R.drawable.shadowright);
	}

	/**
	 * 初始化主頁面
	 */
	private void ContentViewInit() {
		mBtn_rentalInfo = (Button) findViewById(R.id.btn_rentalInfo);
		mBtn_jobInfo = (Button) findViewById(R.id.btn_jobInfo);
		mBtn_secondHandInfo = (Button) findViewById(R.id.btn_secondHandInfo);
		mBtn_groupPurchaseInfo = (Button) findViewById(R.id.btn_groupPurchaseInfo);
		mLv_vagetable = (LinearLayout) findViewById(R.id.lv_vagetable);
		mLv_goods = (LinearLayout) findViewById(R.id.lv_goods);
		mLv_community = (LinearLayout) findViewById(R.id.lv_community);
		mLv_market = (LinearLayout) findViewById(R.id.lv_market);
	}

	/**
	 * 设置主页面监听
	 */
	private void setContentViewListener() {
		mLv_vagetable.setOnClickListener(new OnLvClickListener());
		mLv_goods.setOnClickListener(new OnLvClickListener());
		mLv_community.setOnClickListener(new OnLvClickListener());
		mLv_market.setOnClickListener(new OnLvClickListener());
		mBtn_rentalInfo.setOnClickListener(new OnLvClickListener());
		mBtn_jobInfo.setOnClickListener(new OnLvClickListener());
		mBtn_secondHandInfo.setOnClickListener(new OnLvClickListener());
		mBtn_groupPurchaseInfo.setOnClickListener(new OnLvClickListener());
		mIv_headPhoto.setOnClickListener(new OnLvClickListener());
	}

	/**
	 * 右边抽屉初始化
	 */
	private void rightSlidingInit() {
		mIv_headPhoto = (ImageView) findViewById(R.id.iv_headphoto);
		mTv_location = (TextView) findViewById(R.id.tv_location);
		mBtn_settings = (Button) findViewById(R.id.btn_settings);
		mBtn_exit = (Button) findViewById(R.id.btn_exit);
	}

	/**
	 * 右边抽屉监听
	 */
	private void setRightSgListener() {
		mIv_headPhoto.setOnClickListener(new OnLvClickListener());
		mTv_location.setOnClickListener(new OnLvClickListener());
		mBtn_settings.setOnClickListener(new OnLvClickListener());
		mBtn_exit.setOnClickListener(new OnLvClickListener());
	}

	/**
	 * 自定义点击监听类
	 */
	public class OnLvClickListener implements android.view.View.OnClickListener {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.lv_vagetable:
				Intent vagetableIntent = new Intent(MainActivity.this,
						VagetableActivity.class);
				startActivity(vagetableIntent);
				MainActivity.this.overridePendingTransition(R.anim.input,
						R.anim.out);
				break;
			case R.id.btn_rentalInfo:
				// mBtn_rentalInfo.setAnimation(AnimationUtils.loadAnimation(
				// getApplicationContext(), R.anim.input));
				Intent rentalInfoIntent = new Intent(MainActivity.this,
						RentalInfoActivity.class);
				startActivity(rentalInfoIntent);
				MainActivity.this.overridePendingTransition(R.anim.input,
						R.anim.out);
				break;
			case R.id.btn_jobInfo:
				break;
			case R.id.btn_secondHandInfo:
				break;
			case R.id.btn_groupPurchaseInfo:
				break;
			case R.id.iv_headphoto:
				Intent headIntent = new Intent(MainActivity.this,
						LoginActivity.class);
				startActivity(headIntent);
				break;
			case R.id.tv_location:
				Intent locationIntent = new Intent(MainActivity.this,
						LocationActivity.class);
				startActivity(locationIntent);
				break;
			case R.id.btn_settings:
				Intent settingsIntent = new Intent(MainActivity.this,
						SettingsActivity.class);
				startActivity(settingsIntent);
				break;
			case R.id.btn_exit:
				Intent startMain = new Intent(Intent.ACTION_MAIN);
				startMain.addCategory(Intent.CATEGORY_HOME);
				startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(startMain);
				System.exit(0);
				break;
			default:
				break;
			}
		}
	}

	/**
	 * 轮播图片控件初始化
	 */
	private void galleryViewinit() {
		mGG_images = (GuideGallery) findViewById(R.id.image_wall_gallery);
		mGG_images.setImageActivity(this);

		GalleryImageAdapter imageAdapter = new GalleryImageAdapter(this);
		mGG_images.setAdapter(imageAdapter);
		mPointLinear = (LinearLayout) findViewById(R.id.gallery_point_linear);
		for (int i = 0; i < 4; i++) {
			ImageView pointView = new ImageView(this);
			if (i == 0) {
				pointView.setBackgroundResource(R.drawable.feature_point_cur);
			} else
				pointView.setBackgroundResource(R.drawable.feature_point);
			mPointLinear.addView(pointView);
		}
	}

	/**
	 * 轮播点变化
	 */
	public void changePointView(int cur) {
		View view = mPointLinear.getChildAt(mPositon);
		View curView = mPointLinear.getChildAt(cur);
		if (view != null && curView != null) {
			ImageView pointView = (ImageView) view;
			ImageView curPointView = (ImageView) curView;
			pointView.setBackgroundResource(R.drawable.feature_point);
			curPointView.setBackgroundResource(R.drawable.feature_point_cur);
			mPositon = cur;
		}
	}

	/**
	 * 开启轮播图片线程
	 */
	private void startGallery() {
		mTimeTaks = new ImageTimerTask(mGG_images);
		mAutoGallery.scheduleAtFixedRate(mTimeTaks, 5000, 5000);
		mTimeThread = new Thread() {
			public void run() {
				while (!isExit) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					synchronized (mTimeTaks) {
						if (!mTimeFlag) {
							mTimeTaks.timeCondition = true;
							mTimeTaks.notifyAll();
						}
					}
					mTimeFlag = true;
				}
			};
		};
		mTimeThread.start();
	}

	/**
	 * 旋转动画
	 */
	public static Animation getRotateAnimation(float fromDegrees,
			float toDegrees, int durationMillis) {
		RotateAnimation rotate = new RotateAnimation(fromDegrees, toDegrees,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		rotate.setDuration(durationMillis);
		rotate.setFillAfter(true);
		return rotate;
	}

	/**
	 * 监听返回键
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			finish();
		}
		return true;
	}

	@Override
	protected void onResume() {
		super.onResume();
		mTimeFlag = false;
	}

	@Override
	protected void onPause() {
		super.onPause();
		mTimeTaks.timeCondition = false;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.userToogle:
			mSlidingMenu.toggle();
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
}
