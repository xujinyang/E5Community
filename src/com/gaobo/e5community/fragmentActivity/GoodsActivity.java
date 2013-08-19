package com.gaobo.e5community.fragmentActivity;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.format.DateUtils;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.LayoutParams;
import com.actionbarsherlock.app.ActionBar.OnNavigationListener;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.internal.widget.IcsAdapterView;
import com.actionbarsherlock.internal.widget.IcsLinearLayout;
import com.actionbarsherlock.internal.widget.IcsSpinner;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.gaobo.e5community.R;
import com.gaobo.e5community.adapter.GoodsAdapter;
import com.gaobo.e5community.adapter.VagetableAdapter;
import com.gaobo.e5community.database.DataBaseDao;
import com.gaobo.e5community.model.BaseGoods;
import com.gaobo.e5community.model.Category;
import com.gaobo.e5community.model.Goods;
import com.gaobo.e5community.model.Shopping;
import com.gaobo.e5community.model.Vagetable;
import com.gaobo.e5community.ui.CartActivity;
import com.gaobo.e5community.util.AppContants;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

/**
 * 菜市场的总布局
 * 
 * @author mobileXu
 * 
 */
public class GoodsActivity extends SherlockFragmentActivity {
	/*
	 * 全局变量
	 */
	private PullToRefreshListView mRv_goods;
	private ArrayList<Goods> mAl_goods;
	private GoodsAdapter mAp_goods;
	private ActionBar mActionBar;
	private TextView mTvCartsTotalNum;
	private TextView mTvCartsTotalPrice;
	private Button mBtnCartsAccount;
	private RelativeLayout mListNavLayout;
	private Spinner mSpinner;

	private List<Category> mCategoryList;
	private String[] mCategory = { "零食", "洗漱", "手机", "家电" };
	private DataBaseDao myDb;

	private int mPageNum = 1;
	private String mUrl = "";
	private String mTitle = "";
	private boolean isLast = false;
	private int mLastPos = 0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_goods);
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
		mTvCartsTotalNum.setText(myDb.getCartTotalCount() + "");
		mTvCartsTotalPrice.setText(+myDb.getCartTotalPrice() + "");
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
		mActionBar.setTitle("日用百货");
	}

	/**
	 * 初始化
	 */
	private void init() {
		myDb = new DataBaseDao(getApplicationContext());
		mRv_goods = (PullToRefreshListView) findViewById(R.id.rv_goods);
		mAl_goods = new ArrayList<Goods>();
		mCategoryList = new ArrayList<Category>();
		mAp_goods = new GoodsAdapter(GoodsActivity.this, mAl_goods);
		mRv_goods.setAdapter(mAp_goods);
		mTvCartsTotalNum = (TextView) findViewById(R.id.tvCartsTotalNum);
		mTvCartsTotalPrice = (TextView) findViewById(R.id.tvCartsTotalPrice);
		mBtnCartsAccount = (Button) findViewById(R.id.btnCartsAccount);
	}

	/**
	 * 模拟数据
	 */
	private void setTestDate() {
		for (int i = 0; i < 10; i++) {
			Goods v = new Goods();
			v.setName("牙刷" + i);
			v.setPrice(5 * i);
			v.setId(i);
			v.setPath("http://pic.5jjdw.com/qy/2008/10/86196002/20101230/20101230100119_4680.jpg");
			mAl_goods.add(v);
		}
		mRv_goods.onRefreshComplete();
	}

	/**
	 * 加载网络数据
	 */
	private void initVagetableData() {
		setTestDate();
		mAp_goods.notifyDataSetChanged();
	}

	/**
	 * 设置监听
	 */
	private void setListener() {
		mRv_goods.setMode(Mode.PULL_FROM_START);
		// 上滑刷新
		mRv_goods
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
		mRv_goods.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {
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
		mRv_goods.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				showFragment(arg2, mAl_goods.get(arg2));
				// Intent vagetableIntent=new
				// Intent(VagetableActivity.this,VagetableDetailActivity.class);
				// Bundle bundle=new Bundle();
				// bundle.putSerializable("vagetable", mAl_vagetable.get(arg2));
				// vagetableIntent.putExtras(bundle);
				// startActivity(vagetableIntent);
			}
		});
		mBtnCartsAccount.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent cartIntent = new Intent(GoodsActivity.this,
						CartActivity.class);
				startActivity(cartIntent);
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

	/**
	 * 显示展示商品的Fragment
	 * 
	 * @param position
	 * @param vagetable
	 */
	private void showFragment(int position, final BaseGoods goods) {
		Bundle args = new Bundle();
		args.putInt("position", position);
		args.putSerializable("goods", goods);
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ShowModalFragment modalFragment = new ShowModalFragment();
		modalFragment
				.setNotify(new ShowModalFragment.NotifyDataChangeListener() {
					@Override
					public void onGoodsNumChanage(int position, int goodsNum) {
						if (goodsNum > 0) {
							Shopping s = new Shopping();
							s.setGoods_id(goods.getId());
							s.setCategory_id(goods.getCategory_Id());
							s.setCommunity_id(goods.getCommunity_id());
							s.setAlias(goods.getAlias());
							s.setContent(goods.getContent());
							s.setIsPay(0);
							s.setName(goods.getName());
							s.setPath(goods.getPath());
							s.setPrice(goods.getPrice());
							s.setCount(goodsNum);
							myDb.addGoods(s);
						}
						setGoodsNum(position, goodsNum);
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

	public void setItemData(int position, int goodsNum) {
		mAl_goods.get(position).setCount(goodsNum);
		mAp_goods.notifyDataSetChanged();
	}

	protected void setGoodsNum(int position, int goodsNum) {
		setItemData(position, goodsNum);
		mTvCartsTotalNum.setText(myDb.getCartTotalCount() + "");
		mTvCartsTotalPrice.setText(myDb.getCartTotalPrice() + "");
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
