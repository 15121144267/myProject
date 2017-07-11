package com.dispatching.feima.view.customview;


import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ScrollView;

public class MoveScrollView extends ScrollView {

	private ImageView image;
	private Context mContext;
	// 记录首次按下位置
	private float mFirstPosition = 0;
	// 是否正在放大
	private Boolean mScaling = false;

	private DisplayMetrics metric;


	public MoveScrollView(Context context) {
		super(context);
		this.mContext = context;
		initView();
	}

	public MoveScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.mContext = context;
		initView();
	}

	public MoveScrollView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.mContext = context;
		initView();
	}

	private void initView() {
		metric = new DisplayMetrics();
		WindowManager mWm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
		mWm.getDefaultDisplay().getMetrics(metric);
	}

	public void setMoveImageView(ImageView imageView) {
		this.image = imageView;
		//(ViewGroup.LayoutParams)
		ViewGroup.LayoutParams lp = image.getLayoutParams();
		lp.width = metric.widthPixels;
		lp.height = metric.widthPixels * 9 / 16;
		image.setLayoutParams(lp);
	}
	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		ViewGroup.LayoutParams lp = image.getLayoutParams();
		switch (event.getAction() & MotionEvent.ACTION_MASK) {

			case MotionEvent.ACTION_DOWN:

				break;
			// 手指在屏幕上移动，改事件会被不断触发
			case MotionEvent.ACTION_MOVE:
				if (!mScaling) {
					if (this.getScrollY() == 0) {
						mFirstPosition = event.getY();
					} else {
						break;
					}
				}
				int distance = (int) ((event.getY() - mFirstPosition) * 0.6); // 滚动距离乘以一个系数
				if (distance < 0) { // 当前位置比记录位置要小，正常返回
					break;
				}

				// 处理放大
				mScaling = true;
				lp.width = metric.widthPixels + distance;
				lp.height = (metric.widthPixels + distance)* 9 / 16;
				image.setLayoutParams(lp);
//			return true; // 返回true表示已经完成触摸事件，不再处理
				break;
			// 手指离开屏幕
			case MotionEvent.ACTION_UP:
				//手指离开后恢复图片
				mScaling = false;
				mHandler.sendEmptyMessage(0);
			case MotionEvent.ACTION_POINTER_UP:

				break;
		}
		return super.dispatchTouchEvent(event);
	}
	private Handler mHandler = new Handler() {
		@TargetApi(Build.VERSION_CODES.HONEYCOMB)
		@Override
		public void handleMessage(Message msg) {

			switch (msg.what) {
				case 0:
					final ViewGroup.LayoutParams lp =  image.getLayoutParams();
					final float w = image.getLayoutParams().width;// 图片当前宽度
					final float h = image.getLayoutParams().height;// 图片当前高度
					final float newW = metric.widthPixels;// 图片原宽度
					final float newH = metric.widthPixels * 9 / 16;// 图片原高度

					// 设置动画
					ValueAnimator anim = ObjectAnimator.ofFloat(0.0F, 1.0F).setDuration(200);

					anim.addUpdateListener(new AnimatorUpdateListener() {
						@Override
						public void onAnimationUpdate(ValueAnimator animation) {
							float cVal = (Float) animation.getAnimatedValue();
							lp.width = (int) (w - (w - newW) * cVal);
							lp.height = (int) (h - (h - newH) * cVal);
							image.setLayoutParams(lp);
						}
					});
					anim.start();

					break;
				default:
					break;
			}
			super.handleMessage(msg);
		}
	};

}
