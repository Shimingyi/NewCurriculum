package cn.edu.sdu.online.view;

import cn.edu.sdu.online.newcurriculum.R;
import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Scroller;
import android.widget.TextView;

public class MessionSlideView extends LinearLayout {
    private LinearLayout myViewContent;
	private RelativeLayout myHolder;//此处暂且未用到
	private Scroller myScroller;
	private OnSlideListener myOnSlideListener;

	private int mHolderWidth = 120;

    private static final int TAN = 2;
	private int mLastX = 0;
	private int mLastY = 0;
    private int mDownX = 0;

	public interface OnSlideListener {
		public static final int SLIDE_STATUS_OFF = 0;
		public static final int SLIDE_STATUS_START_SCROLL = 1;
		public static final int SLIDE_STATUS_ON = 2;
		/**
		 * @param view
		 *            current SlideView
		 * @param status
		 *            SLIDE_STATUS_ON or SLIDE_STATUS_OFF
		 */
		public void onSlide(View view, int status);
	}
	public MessionSlideView(Context context) {
		super(context);
		initView();
	}
	public MessionSlideView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView();
	}
	private void initView() {
        Context myContext = getContext();
        myScroller = new Scroller(myContext);
		setOrientation(LinearLayout.HORIZONTAL);
		View.inflate(myContext, R.layout.mession_slide_view_merge, this);
        myViewContent = (LinearLayout) findViewById(R.id.view_content);
		mHolderWidth = Math.round(TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_DIP, mHolderWidth, getResources()
						.getDisplayMetrics()));
	}
    //此方法暂且没有用到
	public void setButtonText(CharSequence text) {
		((TextView) findViewById(R.id.content_delete)).setText(text);
	}
	public void addContentView(View view) {
        myViewContent.addView(view);
	}
	public void setOnSlideListener(OnSlideListener onSlideListener) {
        myOnSlideListener = onSlideListener;
	}
	public void shrink() {
		if (getScrollX() != 0) {
			this.smoothScrollTo(0);
		}
	}
	public boolean onRequireTouchEvent(MotionEvent event) {
		int x = (int) event.getX();
		int y = (int) event.getY();
		int scrollX = getScrollX();
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN: 
			mDownX = (int) event.getX();
			if (!myScroller.isFinished()) {
                myScroller.abortAnimation();
			}
			if (myOnSlideListener != null) {
                myOnSlideListener.onSlide(this,
						OnSlideListener.SLIDE_STATUS_START_SCROLL);
			}
			break;
		case MotionEvent.ACTION_MOVE:
			int deltaX = x - mLastX;
			int deltaY = y - mLastY;
			if (Math.abs(deltaX) < Math.abs(deltaY) * TAN) {
				break;
			}
			int newScrollX = scrollX - deltaX;
			if (deltaX != 0) {
				if (newScrollX < 0) {
					newScrollX = 0;
				} else if (newScrollX > mHolderWidth) {
					newScrollX = mHolderWidth;
				}
				this.scrollTo(newScrollX, 0);
			}
			break;
		case MotionEvent.ACTION_UP:
            int mUpX = (int) event.getX();
			int newScrollX1 = 0;
			if (scrollX - mHolderWidth * 0.75 > 0) {
				newScrollX1 = mHolderWidth;
			}
			this.smoothScrollTo(newScrollX1);
			if (myOnSlideListener != null) {
                myOnSlideListener.onSlide(this,
						newScrollX1 == 0 ? OnSlideListener.SLIDE_STATUS_OFF
								: OnSlideListener.SLIDE_STATUS_ON);
			}
			if (Math.abs(mUpX - mDownX) > 5) {
				mLastX = x;
				mLastY = y;
				return true;
			}
			break;
		default:
			break;
		}
		mLastX = x;
		mLastY = y;
		return onTouchEvent(event);
	}
	private void smoothScrollTo(int destX) {
		// 缓慢滚动到指定位置
		int scrollX = getScrollX();
		int delta = destX - scrollX;
        myScroller.startScroll(scrollX, 0, delta, 0, Math.abs(delta) * 3);
		invalidate();
	}
	@Override
	public void computeScroll() {
		if (myScroller.computeScrollOffset()) {
			scrollTo(myScroller.getCurrX(), myScroller.getCurrY());
			postInvalidate();
		}
	}

}
