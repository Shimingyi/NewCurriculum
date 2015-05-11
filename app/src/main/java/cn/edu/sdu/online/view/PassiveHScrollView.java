package cn.edu.sdu.online.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;

public class PassiveHScrollView extends HorizontalScrollView {

	public PassiveHScrollView(Context context) {
		super(context);
	}

	public PassiveHScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public PassiveHScrollView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		return false;
	}

}
