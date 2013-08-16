package com.gaobo.e5community.fragmentActivity;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.gaobo.e5community.R;
import com.gaobo.e5community.adapter.GoodsAdapter;
import com.gaobo.e5community.model.Goods;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

/**
 * 菜市场的总布局
 * 
 * @author mobileXu
 * 
 */
@SuppressLint("NewApi")
public class GoodsFragment extends Fragment {
	/*
	 * 全局变量
	 */
	private PullToRefreshListView mRv_goods;
	private ArrayList<Goods> mAl_goods;
	private GoodsAdapter mAp_goods;
	private int mPageNum = 1;
	private String mUrl = "";
	private String mTitle = "";

	private boolean isLast = false;
	private int mLastPos = 0;

	public GoodsFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		((SherlockFragmentActivity) getActivity()).getSupportActionBar()
				.setTitle("日用百货");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.activity_vagetable,
				container, false);
		mRv_goods = (PullToRefreshListView) rootView
				.findViewById(R.id.rv_vagetable);
		mAl_goods = new ArrayList<Goods>();
		mAp_goods = new GoodsAdapter(getActivity(), mAl_goods);
		mRv_goods.setAdapter(mAp_goods);
		initVagetableData();
		reflashListener();
		return rootView;
	}

	/**
	 * 模拟数据
	 */
	private void setTestDate() {
		for (int i = 0; i < 10; i++) {
			Goods g = new Goods();
			g.setName("鞋子" + i);
			g.setPrice(3 * i);
			g.setImage("http://img3.vipshop.com/upload/merchandise/24287/CAMPNO-CAM11005603-2.jpg");
			mAl_goods.add(g);
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
	 * 刷新监听
	 */
	private void reflashListener() {
		mRv_goods.setMode(Mode.PULL_FROM_START);
		// 上滑刷新
		mRv_goods.setOnRefreshListener(new OnRefreshListener<ListView>() {
			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				String label = DateUtils.formatDateTime(getActivity(),
						System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME
								| DateUtils.FORMAT_SHOW_DATE
								| DateUtils.FORMAT_ABBREV_ALL);

				refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(
						"最近刷新：" + label);
				// 刷新
				mPageNum = 1;
				// mLastPos = 0;
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
				Intent intent = new Intent(getActivity(),
						GoodsDetailActivity.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("goods", mAl_goods.get(arg2));
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});
	}
}
