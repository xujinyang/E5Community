package com.gaobo.e5community.ui.widget;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gaobo.e5community.R;
import com.loopj.android.image.SmartImageView;

/**
 * 商品弹出框模型类
 * @author lwz
 *
 */
public class Modal implements View.OnClickListener{
	
	private Context mContext;
	private View mModalContainer;
	private View mPicContainer;
	private View mGoodsCountContainer;
	private View mOperationContainer;
	private SmartImageView mGoodsPic;
	private TextView mTextGoodsName;
	private TextView mTextGoodsPrice;
	private TextView mTextGoodsNum;
	private ImageButton mPlusButton;
	private ImageButton mMinusButton;
	private Button mWaitButton;
	private Button mCancleButton;
	private TextView mTvUnBuy;
	
	private int mGoodsNum;
	private int mMinNum = 0;
	private float mGoodsPrice;
	private OnPicClickListener mPicClickListener;
	private OnPlusButtonClickListener mPlusButtonClickListener;
	private OnMinusButtonClickListener mMinusButtonClickListener;
	private OnWaitButtonClickListener mWaitButtonClickListener;
	private OnCancleButtonClickListener mCancleButtonClickListener;
	
	/** 点击图片监听接口  */
	public interface OnPicClickListener{
		public void onClick();
	}
	
	/** 点击添加数量监听接口 */
	public interface OnPlusButtonClickListener{
		/** 
		 * @param num 当前商品数量
		 */
		public void onClick(int num);
	}
	
	/** 点击减少数量监听接口  */
	public interface OnMinusButtonClickListener{
		/** 
		 * @param num 当前商品数量
		 */
		public void onClick(int num);
	}
	
	/** 点击暂停购买监听接口  */
	public interface OnWaitButtonClickListener{
		public void onClick();
	}
	/** 点击删除监听接口 */
	public interface OnCancleButtonClickListener{
		public void onClick();
	}
	
	/**
	 * 设置点击图片监听
	 * @param l
	 */
	public void setOnPicClickListener(OnPicClickListener l){
		mPicClickListener = l;
	}
	
	/**
	 * 设置添加按钮点击监听
	 * @param l
	 */
	public void setOnPlusButtonClickListener(OnPlusButtonClickListener l){
		mPlusButtonClickListener = l;
	}
	
	/**
	 * 设置减少按钮点击监听
	 * @param l
	 */
	public void setOnMinusButtonClickListener(OnMinusButtonClickListener l){
		mMinusButtonClickListener = l;
	}
	
	/**
	 * 设置暂停购买按钮点击监听
	 * @param l
	 */
	public void setOnWaitButtonClickListener(OnWaitButtonClickListener l){
		mWaitButtonClickListener = l;
	}
	
	/**
	 * 设置删除按钮点击监听
	 * @param l
	 */
	public void setOnCancleButtonClickListener(OnCancleButtonClickListener l){
		mCancleButtonClickListener = l;
	}
	
	public Modal(Context context){
		mContext = context;
		findView();
	}
	
	private void findView(){
		mModalContainer = View.inflate(mContext, R.layout.fragment_modal, null);
		mPicContainer = mModalContainer.findViewById(R.id.modal_goods_pic_container);
		mGoodsCountContainer = mModalContainer.findViewById(R.id.modal_goods_count_container);
		mOperationContainer = mModalContainer.findViewById(R.id.modal_goods_operation_container);
		mGoodsPic = (SmartImageView)mModalContainer.findViewById(R.id.modal_goods_pic);
		mTextGoodsName = (TextView)mModalContainer.findViewById(R.id.modal_goods_name);
		mTextGoodsPrice = (TextView)mModalContainer.findViewById(R.id.modal_goods_price);
		mTextGoodsNum = (TextView)mModalContainer.findViewById(R.id.modal_goods_count_goods_num);
		mPlusButton = (ImageButton)mModalContainer.findViewById(R.id.modal_plus_button);
		mMinusButton = (ImageButton)mModalContainer.findViewById(R.id.modal_minus_button);
		mWaitButton = (Button)mModalContainer.findViewById(R.id.modal_wait_button);
		mCancleButton = (Button)mModalContainer.findViewById(R.id.modal_cancle_button);
		mTvUnBuy = (TextView)mModalContainer.findViewById(R.id.tvUnBuy); 
		
		setListener();
	}
	
	/**
	 * 重置数据和视图
	 */
	public void resetDataAndView(){
		setGoodsNum(mMinNum);
		setGoodsName("");
		setGoodsPrice(0);
	}
	
	/**
	 * 得到商品弹出框的容器视图
	 * @return 一个  view 对象
	 */
	public View getMidalContainer(){
		return mModalContainer;
	}
	
	/**
	 * 设置监听
	 */
	private void setListener(){
		mModalContainer.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				return true;
			}
		});
		mPlusButton.setOnClickListener(this);
		mMinusButton.setOnClickListener(this);
		mWaitButton.setOnClickListener(this);
		mCancleButton.setOnClickListener(this); 
		mGoodsPic.setOnClickListener(this);
		
	}
	
	/**
	 * 显示暂不购买
	 */
	public void setVisibleNOBuy() {
		mTvUnBuy.setVisibility(View.VISIBLE);
	}
	
	/**
	 * 消失暂不购买
	 */
	public void setGoneNOBuy() {
		mTvUnBuy.setVisibility(View.GONE);
	}
	
	/**
	 * 设置图片的地址
	 * @param url
	 */
	public void setImageUrl(String url){
		mGoodsPic.setImageUrl(url);
		// TO-DO  先得到图片对象（从缓存中? webImage）， 再得到屏幕的尺寸，按比例缩放图片大小，
//		Drawable pic = mGoodsPic.getDrawable();
//		setPicContainerSize(pic.getIntrinsicWidth(), pic.getIntrinsicHeight());
	}
	
	public void setImageRes(int res){
		mGoodsPic.setImageResource(res);
	}
	
	/**
	 * 设置图片容器视图的大小
	 * @param width
	 * @param height
	 */
	private void setPicContainerSize(int width, int height){
		mPicContainer.setLayoutParams(new LinearLayout.LayoutParams(width, height));
	}
	
	/**
	 * 设置商品的名字
	 * @param num
	 */
	public void setGoodsName(String goodsName){
		mTextGoodsName.setText(goodsName);
	}
	
	/**
	 * 设置商品的数量
	 * @param num
	 */
	public void setGoodsNum(int num){
		mGoodsNum = num;
		mTextGoodsNum.setText(mGoodsNum + "");
		if(num > 0){
			mGoodsCountContainer.setBackgroundResource(R.drawable.modal_goods_bright_bg);
		}else{
			mGoodsCountContainer.setBackgroundResource(R.drawable.modal_goods_dim_bg);
		}
	}
	
	/**
	 * 得到商品个数
	 * @return int 
	 */
	public int getGoodsNum(){
		return mGoodsNum;
	}
	
	/**
	 * 设置商品的价格
	 * @param goodsPrice
	 */
	public void setGoodsPrice( float goodsPrice){
		mGoodsPrice = goodsPrice;
		mTextGoodsPrice.setText("￥" + mGoodsPrice);
	}
	
	/**
	 * 显示操作容器视图
	 */
	public void showOperationContainer(){
		mOperationContainer.setVisibility(View.VISIBLE);
	}
	/**
	 * 设置商品最小个数
	 * @param minNum
	 */
	public void setMinNum(int minNum){
		mMinNum = minNum;
	}
	
	/**
	 * 隐藏操作容器视图
	 */
	public void hideOperationContainer(){
		mOperationContainer.setVisibility(View.GONE);
	}
	
	/**
	 * 添加商品个数按钮
	 */
	public void onPlus(){
		setGoodsNum( ++ mGoodsNum);
		mGoodsCountContainer.setBackgroundResource(R.drawable.modal_goods_bright_bg);
		if( mPlusButtonClickListener != null ){
			mPlusButtonClickListener.onClick( mGoodsNum);
		}
	}
	
	/**
	 * 减少商品个数按钮
	 * <br>当数量为 0 时，设置背景为灰色
	 */
	public void onMinus(){
		if( mGoodsNum > mMinNum ){
			setGoodsNum( -- mGoodsNum);
			if( mMinusButtonClickListener != null ){
				mMinusButtonClickListener.onClick( mGoodsNum);
			}
		}else{
			if(mGoodsNum<=0) {
				mGoodsCountContainer.setBackgroundResource(R.drawable.modal_goods_dim_bg);
			}
		}
	}
	
	/**
	 * 设置购买状态
	 * @param isWait
	 */
	public void setOnWait(boolean isWait){
		if(isWait) {
			mWaitButton.setText("暂不购买");
		}else{
			mWaitButton.setText("立即购买");
		}
	}
	
	/**
	 * 暂停购买
	 */
	public void onWait(){
		if( mWaitButtonClickListener != null ){
			mWaitButtonClickListener.onClick();
		}
	}
	/**
	 * 删除按钮
	 */
	public void onCancle(){
		if( mCancleButtonClickListener != null ){
			mCancleButtonClickListener.onClick();
		}
	}
	
	/**
	 * 卸载商品弹出框
	 */
	private void onDismiss(){
		if( mPicClickListener != null ){
			mPicClickListener.onClick();
		}
		FragmentManager ft = ((FragmentActivity)mContext).getSupportFragmentManager();
		ft.popBackStackImmediate();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.modal_plus_button:
				onPlus();
				break;
			case R.id.modal_minus_button:
				onMinus();			
				break;
			case R.id.modal_wait_button:
				onWait();
				break;
			case R.id.modal_cancle_button:
				onCancle();
				break;
			case R.id.modal_goods_pic:
				onDismiss();
				break;
		}
		
	}
}
