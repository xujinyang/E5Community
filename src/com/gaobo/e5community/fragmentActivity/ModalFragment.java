package com.gaobo.e5community.fragmentActivity;

import android.content.ContentValues;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gaobo.e5community.R;
import com.gaobo.e5community.database.DataBaseDao;
import com.gaobo.e5community.ui.widget.Modal;
import com.gaobo.e5community.util.AppContants;

public class ModalFragment extends Fragment {

	private Modal mModal;
	private int mPosition;
	private String mGoodsName;
	private float mGoodsPrice;
	private int mGoodsNum;
	private ContentValues mItem;

	private NotifyDataChangeListener mNotifyNum;

	public interface NotifyDataChangeListener {
		public void onGoodsNumChanage(int position, int goodsNum);

		public void onDelete(int position);

		public void onWaitChanage(int position);
	}

	public void setNotify(NotifyDataChangeListener l) {
		mNotifyNum = l;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mModal = new Modal(getActivity());
		return mModal.getMidalContainer();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.app.Fragment#onViewCreated(android.view.View,
	 * android.os.Bundle)
	 */
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		Bundle args = getArguments();
		mItem = args.getParcelable("item");
		mPosition = args.getInt("position");

		mModal.resetDataAndView();
		if (args.getBoolean("isHidden")) {
			mModal.hideOperationContainer();
		} else {
			mModal.showOperationContainer();
			mModal.setOnWait(mItem.getAsBoolean("isSelected"));
		}
		// 判断是否显示,"暂不购买"提示
		DataBaseDao dBaseDao = new DataBaseDao();
		if (!dBaseDao.isSelectedGoods(getActivity(),
				mItem.getAsInteger(AppContants.KEY.ID))) {
			mModal.setVisibleNOBuy();
		}
		// 设置可以设置的购买数量值
		if (args.containsKey("minNum")) {
			mModal.setMinNum(args.getInt("minNum"));
		}
		// 设置图片
		mModal.setImageUrl(AppContants.API_URL.PIC_480X800
				+ mItem.getAsString(AppContants.KEY.GOODS_IMAGE_URL));
		mModal.setGoodsNum(mGoodsNum = mItem
				.getAsInteger(AppContants.KEY.GOODS_NUM));
		mModal.setGoodsName(mGoodsName = mItem
				.getAsString(AppContants.KEY.GOODS_NAME));
		mModal.setGoodsPrice(mGoodsPrice = mItem
				.getAsFloat(AppContants.KEY.GOODS_PRICE));
		mModal.setImageRes(R.drawable.ic_default_model_pic);
		mModal.setOnPicClickListener(new Modal.OnPicClickListener() {
			@Override
			public void onClick() {

			}
		});

		mModal.setOnPlusButtonClickListener(new Modal.OnPlusButtonClickListener() {

			@Override
			public void onClick(int num) {
				mGoodsNum++;

				chanageNoBuy();
				// mNotifyNum.onWaitChanage(mPosition);

				chanageDataBaseNum(num);
				mNotifyNum.onGoodsNumChanage(mPosition, num);
			}
		});

		mModal.setOnMinusButtonClickListener(new Modal.OnMinusButtonClickListener() {

			@Override
			public void onClick(int num) {
				mGoodsNum--;

				chanageNoBuy();
				// mNotifyNum.onWaitChanage(mPosition);

				chanageDataBaseNum(num);
				mNotifyNum.onGoodsNumChanage(mPosition, num);
			}
		});

		mModal.setOnCancleButtonClickListener(new Modal.OnCancleButtonClickListener() {

			@Override
			public void onClick() {
				mNotifyNum.onDelete(mPosition);

				dismiss();
			}
		});

		mModal.setOnWaitButtonClickListener(new Modal.OnWaitButtonClickListener() {

			@Override
			public void onClick() {
				mNotifyNum.onWaitChanage(mPosition);

				dismiss();
			}
		});

	}

	private void chanageDataBaseNum(int num) {
		DataBaseDao dataBaseDao = new DataBaseDao();
		if (num <= 0) {
			dataBaseDao.deleteGoods(getActivity(),
					mItem.getAsInteger(AppContants.KEY.ID));
			return;
		}
		if (!dataBaseDao.isExistNativeGoods(getActivity(),
				mItem.getAsInteger(AppContants.KEY.ID))) {
			dataBaseDao.addGoods(getActivity(),
					mItem.getAsInteger(AppContants.KEY.ID),
					mItem.getAsString(AppContants.KEY.GOODS_NAME),
					mItem.getAsString(AppContants.KEY.GOODS_SHOP_NAME),
					mItem.getAsDouble(AppContants.KEY.GOODS_PRICE),
					mItem.getAsString(AppContants.KEY.GOODS_IMAGE_URL), num);
		} else {
			dataBaseDao.updateGoodsNum(getActivity(),
					mItem.getAsInteger(AppContants.KEY.ID), num);
		}
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public void onStop() {
		super.onStop();
	}

	/**
	 * 改变isSelected为立即购买
	 */
	private void chanageNoBuy() {
		// 改变isSelected, 点击默认为立即购买
		DataBaseDao dBaseDao = new DataBaseDao();
		dBaseDao.updateGoodsSelected(getActivity(),
				mItem.getAsInteger(AppContants.KEY.ID), true);
		// Gone掉暂不购买提示
		mModal.setGoneNOBuy();
		mModal.setOnWait(true);
	}

	// 卸载弹出框
	private void dismiss() {
		FragmentManager ft = getFragmentManager();
		ft.popBackStackImmediate();
	}
}
