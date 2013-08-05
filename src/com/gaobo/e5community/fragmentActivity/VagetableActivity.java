package com.gaobo.e5community.fragmentActivity;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.SpinnerAdapter;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.OnNavigationListener;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.gaobo.e5community.R;
import com.gaobo.e5community.adapter.VagetableAdapter;
import com.gaobo.e5community.model.BaseGoods;
import com.gaobo.e5community.model.Vagetable;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;

/**
 * 菜市场的总布局
 * 
 * @author mobileXu
 * 
 */
public class VagetableActivity extends SherlockFragmentActivity {

	/*
	 * 全局变量
	 */
	private PullToRefreshGridView mRv_vagetable;
	private ArrayList<Vagetable> mAl_vagetable;
	private VagetableAdapter mAp_vagetable;
	private ActionBar mActionBar;
	private int mPageNum = 1;
	private String mUrl = "";
	private String mTitle = "";

	private boolean isLast = false;
	private int mLastPos = 0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_vagetablemarket);
		setActionBar();
		init();
		setListener();
		initVagetableData();
	}

	/**
	 * 设置ActionBar
	 */
	private void setActionBar() {
		mActionBar = getSupportActionBar();
		mActionBar.setDisplayHomeAsUpEnabled(true);
		mActionBar.setDisplayShowTitleEnabled(true);
		mActionBar.setDisplayShowHomeEnabled(false);
		mActionBar.setTitle("菜市场                                           ");

		SpinnerAdapter adapter = ArrayAdapter.createFromResource(
				getApplicationContext(), R.array.vagetable_category,
				R.layout.custom_spinner_drop_down_item_style);
		mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
		mActionBar.setListNavigationCallbacks(adapter,
				new OnNavigationListener() {
					@Override
					public boolean onNavigationItemSelected(int itemPosition,
							long itemId) {
						return true;
					}
				});
	}

	/**
	 * 初始化
	 */
	private void init() {
		mRv_vagetable = (PullToRefreshGridView) findViewById(R.id.rv_vagetable);
		mAl_vagetable = new ArrayList<Vagetable>();
		mAp_vagetable = new VagetableAdapter(VagetableActivity.this,
				mAl_vagetable);
		mRv_vagetable.setAdapter(mAp_vagetable);
	}

	/**
	 * 模拟数据
	 */
	private void setTestDate() {
		for (int i = 0; i < 10; i++) {
			Vagetable v = new Vagetable();
			v.setName("蔬菜" + i);
			v.setPrice(5 * i);
			v.setImage("http://pic16.nipic.com/20110818/2786001_174059683000_2.jpg");
			mAl_vagetable.add(v);
		}
		mRv_vagetable.onRefreshComplete();
	}

	/**
	 * 加载网络数据
	 */
	private void initVagetableData() {
		setTestDate();
		mAp_vagetable.notifyDataSetChanged();
	}

	/**
	 * 设置监听
	 */
	private void setListener() {
		mRv_vagetable.setMode(Mode.PULL_FROM_START);
		// 上滑刷新
		mRv_vagetable
				.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<GridView>() {

					@Override
					public void onRefresh(
							PullToRefreshBase<GridView> refreshView) {
						String label = DateUtils.formatDateTime(
								getApplicationContext(),
								System.currentTimeMillis(),
								DateUtils.FORMAT_SHOW_TIME
										| DateUtils.FORMAT_SHOW_DATE
										| DateUtils.FORMAT_ABBREV_ALL);

						refreshView.getLoadingLayoutProxy()
								.setLastUpdatedLabel("最近刷新：" + label);
						// 刷新
						mPageNum = 1;
						isLast = false;
						mLastPos = 0;
						initVagetableData();
					}

				});
		// 下滑刷新
		mRv_vagetable
				.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {
					@Override
					public void onLastItemVisible() {
						if (!isLast) {
							// 加载更多
							++mPageNum;
							// mLastPos = mShopList.size();
							initVagetableData();
						}
					}
				});

		mRv_vagetable.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				showFragment(mAl_vagetable.get(arg2));
			}
		});
	}

	private void showFragment(BaseGoods vagetable) {
		Bundle args = new Bundle();
		args.putSerializable("goods", vagetable);
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ModalFragment modalFragment = new ModalFragment();
		modalFragment.setArguments(args);
		modalFragment.setNotify(new ModalFragment.NotifyDataChangeListener() {
			@Override
			public void onGoodsNumChanage(int position, int goodsNum) {

			}

			@Override
			public void onDelete(int position) {

			}

			@Override
			public void onWaitChanage(int position) {

			}
		});
		modalFragment.setArguments(args);
		ft.setCustomAnimations(R.anim.bottom_enter, R.anim.bottom_exit,
				R.anim.bottom_enter, R.anim.bottom_exit);
		ft.add(R.id.real_container, modalFragment, "modal");
		ft.addToBackStack(null);
		ft.commit();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.other, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
}
