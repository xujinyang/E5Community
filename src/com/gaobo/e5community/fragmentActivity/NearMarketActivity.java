package com.gaobo.e5community.fragmentActivity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.LayoutParams;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.gaobo.e5community.R;
import com.gaobo.e5community.adapter.GoodsAdapter;
import com.gaobo.e5community.adapter.NearMarketAdapter;
import com.gaobo.e5community.adapter.ServiceAdapter;
import com.gaobo.e5community.database.DataBaseDao;
import com.gaobo.e5community.model.Category;
import com.gaobo.e5community.model.Goods;
import com.gaobo.e5community.model.NearMarket;
import com.gaobo.e5community.model.Service;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

/**
 * 菜市场的总布局
 * 
 * @author mobileXu
 * 
 */
public class NearMarketActivity extends SherlockFragmentActivity {
	/*
	 * 全局变量
	 */
	private PullToRefreshListView mRv_nearMarket;
	private ArrayList<NearMarket> mAl_nearMarket;
	private NearMarketAdapter mA_nearMarket;
	private ActionBar mActionBar;
	private RelativeLayout mListNavLayout;
	private Spinner mSpinner;

	private List<Category> mCategoryList;
	private String[] mCategory = { "通下水道", "充煤气", "维修电路", "修家电" };
	private DataBaseDao myDb;

	private int mPageNum = 1;
	private String mUrl = "";
	private String mTitle = "";
	private boolean isLast = false;
	private int mLastPos = 0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nearmarket);
		setActionBar();
		init();
		setListener();
		setCategory();
		setListNavigationDataAdapter(mCategory);
		initVagetableData();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	private void setCategory() {
		for (int i = 0; i < 6; i++) {
			Category c = new Category();
			c.setId(i);
			c.setName("水果" + i);
			mCategoryList.add(c);
		}
	}

	/**
	 * 设置ActionBar
	 */
	private void setActionBar() {
		mActionBar = getSupportActionBar();
		mActionBar.setDisplayHomeAsUpEnabled(true);
		mActionBar.setDisplayShowTitleEnabled(true);
		mActionBar.setDisplayShowHomeEnabled(true);
		mActionBar.setTitle("社区服务");
	}

	/**
	 * 初始化
	 */
	private void init() {
		myDb = new DataBaseDao(getApplicationContext());
		mRv_nearMarket = (PullToRefreshListView) findViewById(R.id.rv_nearmarket);
		mAl_nearMarket = new ArrayList<NearMarket>();
		mCategoryList = new ArrayList<Category>();
		mA_nearMarket = new NearMarketAdapter(NearMarketActivity.this,
				mAl_nearMarket);
		mRv_nearMarket.setAdapter(mA_nearMarket);
	}

	/**
	 * 模拟数据
	 */
	private void setTestDate() {
		for (int i = 0; i < 10; i++) {
			NearMarket v = new NearMarket();
			v.setName("七天酒店特价优惠大酬宾" + i);
			v.setPrice(5 * i);
			v.setId(i);
			v.setPath("http://www.427400.com/pic/201004/9/201049125635602.jpg");
			mAl_nearMarket.add(v);
		}
		mRv_nearMarket.onRefreshComplete();
	}

	/**
	 * 加载网络数据
	 */
	private void initVagetableData() {
		setTestDate();
		mA_nearMarket.notifyDataSetChanged();
	}

	/**
	 * 设置监听
	 */
	private void setListener() {
		mRv_nearMarket.setMode(Mode.PULL_FROM_START);
		// 上滑刷新
		mRv_nearMarket
				.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {

					@Override
					public void onRefresh(
							PullToRefreshBase<ListView> refreshView) {
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
		mRv_nearMarket
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

		mRv_nearMarket.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent vagetableIntent = new Intent(NearMarketActivity.this,
						NearMarketDetailActivity.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("nearMarket", mAl_nearMarket.get(arg2));
				vagetableIntent.putExtras(bundle);
				startActivity(vagetableIntent);
			}
		});
	}

	/**
	 * 得到 Actionbar 导航下拉菜单数据适配器
	 * 
	 * @return
	 */
	private void setListNavigationDataAdapter(String[] categorys) {
		mListNavLayout = createCustomNavigationList(new Spinner.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});

		mActionBar.setCustomView(mListNavLayout, new ActionBar.LayoutParams(
				Gravity.RIGHT));
		mActionBar.setDisplayShowCustomEnabled(true);
	}

	/**
	 * 创建 Actionbar 自定义导航下拉菜单
	 * 
	 * @param spinner
	 *            IcsSpinner 对象
	 * @param l
	 *            子项选择监听
	 * @return 自定义下拉菜单对象
	 */
	private RelativeLayout createCustomNavigationList(
			Spinner.OnItemSelectedListener l) {
		RelativeLayout listNavLayout = (RelativeLayout) getLayoutInflater()
				.inflate(R.layout.custom_spinner_style, null);
		mSpinner = (Spinner) listNavLayout
				.findViewById(R.id.sp_custonm_spinner);
		ArrayAdapter<CharSequence> listAdapter = new ArrayAdapter<CharSequence>(
				getApplicationContext(), android.R.layout.simple_spinner_item,
				mCategory);
		listAdapter
				.setDropDownViewResource(R.layout.custom_spinner_drop_down_item_style);
		mSpinner.setAdapter(listAdapter);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
		params.gravity = Gravity.CENTER;
		mSpinner.setOnItemSelectedListener(l);
		return listNavLayout;
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
