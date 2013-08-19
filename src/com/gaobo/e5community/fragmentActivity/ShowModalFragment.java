package com.gaobo.e5community.fragmentActivity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gaobo.e5community.model.BaseGoods;
import com.gaobo.e5community.model.Shopping;
import com.gaobo.e5community.ui.widget.Modal;

public class ShowModalFragment extends Fragment {
	private Modal mModal;
	private int mPosition;
	private String mGoodsName;
	private float mGoodsPrice;
	private int mGoodsNum;
	private NotifyDataChangeListener mNotifyNum;
	private BaseGoods goods;

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

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		Bundle bundle = getArguments();
		mPosition = bundle.getInt("position");
		goods = (BaseGoods) bundle.getSerializable("goods");
		mModal.resetDataAndView();
		mModal.setGoodsName(goods.getName());
		mModal.setGoodsPrice(goods.getPrice());
		mModal.setImageUrl(goods.getPath());
		mModal.setOnMinusButtonClickListener(new Modal.OnMinusButtonClickListener() {
			@Override
			public void onClick(int num) {
				// mGoodsNum++;
				// chanageNoBuy();
				// mNotifyNum.onWaitChanage(mPosition);
				// chanageDataBaseNum(num);
				mNotifyNum.onGoodsNumChanage(mPosition, num);
			}
		});
		mModal.setOnPlusButtonClickListener(new Modal.OnPlusButtonClickListener() {
			@Override
			public void onClick(int num) {
				mNotifyNum.onGoodsNumChanage(mPosition, num);
			}
		});
	}

	protected void chanageDataBaseNum(int num) {

	}

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public void onStop() {
		super.onStop();
	}

}
