package cn.edu.sdu.online.newcurriculum;

import android.app.ActionBar;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Display;
import android.view.Menu;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import cn.edu.sdu.online.adapter.LibViewPagerAdapter;

public class Activity_Lib extends FragmentActivity {
	private ViewPager viewPager;
	private LibViewPagerAdapter viewpageadapter;
	private int zero = 0;// 动画图片偏移量
	private int currIndex = 0;// 当前页卡编号
	private int one;// 单个水平动画位移
	private int white, lightBlue;
	private LinearLayout mTab1, mTab2;
	public boolean b = false;
	private ActionBar ActionBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_viewpager);
		InitWidth();
		InitTab();
		viewPager = (ViewPager) findViewById(R.id.libviewpager);

		viewpageadapter = new LibViewPagerAdapter(getSupportFragmentManager());
		viewPager.setAdapter(viewpageadapter);
		viewPager.setOnPageChangeListener(new MyOnPageChangeListener());
	}

	/**
	 * 初始化宽度
	 */
	private void InitWidth() {
		Display currDisplay = getWindowManager().getDefaultDisplay();// 获取屏幕当前分辨率
		@SuppressWarnings("deprecation")
		int displayWidth = currDisplay.getWidth();
		one = displayWidth / 2; // 设置水平动画平移大小
	}

	/**
	 * 初始化选项卡
	 */
	private void InitTab() {
		white = getResources().getColor(R.color.white);
		lightBlue = getResources().getColor(R.color.lightblue);

		mTab1 = (LinearLayout) findViewById(R.id.ll_main_search);
		mTab2 = (LinearLayout) findViewById(R.id.ll_main_my);

		mTab1.setOnClickListener(new MyOnClickListener(0));
		mTab2.setOnClickListener(new MyOnClickListener(1));

		mTab1.setBackgroundColor(lightBlue);
	}

	/**
	 * 头标点击监听
	 */
	public class MyOnClickListener implements View.OnClickListener {
		private int index = 0;

		public MyOnClickListener(int i) {
			index = i;
		}

		@Override
		public void onClick(View v) {
			viewPager.setCurrentItem(index);
		}
	};

	/*
	 * 页卡切换监听
	 */

	public class MyOnPageChangeListener implements OnPageChangeListener {
		@Override
		public void onPageSelected(int arg0) {
			Animation animation = null;
			switch (arg0) {
			case 0:
				mTab1.setBackgroundColor(lightBlue);
				if (currIndex == 1) {
					animation = new TranslateAnimation(one, 0, 0, 0);
					mTab2.setBackgroundColor(white);
				}
				break;
			case 1:
				mTab2.setBackgroundColor(lightBlue);
				if (currIndex == 0) {
					animation = new TranslateAnimation(zero, one, 0, 0);
					mTab1.setBackgroundColor(white);
				}
				break;

			}

			currIndex = arg0;
			animation.setFillAfter(true);// True:图片停在动画结束位置
			animation.setDuration(150);
			// mTabImg.startAnimation(animation);
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		ActionBar = getActionBar();
		// 设置ActionBar背景颜色
		Drawable backgroundcolor = getResources().getDrawable(
				R.color.actionbarbg);
		ActionBar.setBackgroundDrawable(backgroundcolor);
		return super.onCreateOptionsMenu(menu);
	}

}
