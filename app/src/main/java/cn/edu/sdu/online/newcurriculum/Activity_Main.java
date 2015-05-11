package cn.edu.sdu.online.newcurriculum;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.umeng.update.UmengUpdateAgent;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import cn.edu.sdu.online.app.Main;
import cn.edu.sdu.online.utils.Main_Curriculum_tv_Id;
import cn.edu.sdu.online.view.ActionbarPopupmenu;

public class Activity_Main extends Activity {

	private static int SCREEN_WIDTH = 0;// 屏幕宽
	private static int SCREEN_HEIGHT = 0;// 屏幕高
	private static int ACTIONBAR_HETGHT = 0;// actionbar高
	private Animation anim;
	private Main_Curriculum_tv_Id Textid;
	private LinearLayout classimage, libimage, scoreimage, getupimage;
	private TableLayout curriculumtable;
	// private Main_Curriculum_tv_bg colors;
	private int position;
	private viewOnclickListener viewonClickListener;
	private animationListener animListener;
	private TextView curriculumTitle;
	private ActionBar ActionBar;
	private ActionbarPopupmenu actionbarPopupmenu;
	private final static String hello = "Hello";
	private String name = "Friend";
	private String classNameList[];
	private Drawable[] colors;
	private TextView textview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
        UmengUpdateAgent.update(Activity_Main.this);//友盟的自动更新
		classNameList = Main.getApp().getCurriculumArray();
		findview();
		init();
		setListener();
	}

	@Override
	protected void onResume() {
		super.onResume();
		classNameList = Main.getApp().getCurriculumArray();
		setTextView();
	}

	@Override
	protected void onPause() {
		Log.v("pause", "onpause");
		// TODO Auto-generated method stub
		if (actionbarPopupmenu != null && actionbarPopupmenu.isShowing()) {
			actionbarPopupmenu.dismiss();
			actionbarPopupmenu = null;
		}

		name = Main.getApp().getDataStore().getString("stuname", "Friend");
		ActionBar.setTitle(hello + "," + name);

		super.onPause();
		classNameList = Main.getApp().getCurriculumArray();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.actionbar, menu);
		ActionBar = getActionBar();
		// 设置ActionBar题目
		name = Main.getApp().getDataStore().getString("stuname", "Friend");
		ActionBar.setTitle(hello + "," + name);
		// 取消icon
		ActionBar.setDisplayUseLogoEnabled(false);
		ActionBar.setDisplayShowHomeEnabled(false);
		// 设置ActionBar背景颜色
		Drawable backgroundcolor = getResources().getDrawable(
				R.color.actionbarbg);
		ActionBar.setBackgroundDrawable(backgroundcolor);
		return super.onCreateOptionsMenu(menu);
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		View popView = LayoutInflater.from(this).inflate(
				R.layout.activity_main, null);
		Rect frame = new Rect();
		switch (item.getItemId()) {
		case R.id.show_popupmenu:
			ACTIONBAR_HETGHT = getActionBar().getHeight();
			getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
			int statusBarHeight = frame.top;
			if (actionbarPopupmenu == null) {
				// 实例化ActionBarPopupmenu
				actionbarPopupmenu = new ActionbarPopupmenu(this);
				actionbarPopupmenu.setOutsideTouchable(true);
//                actionbarPopupmenu.showAsDropDown(popView,20,ACTIONBAR_HETGHT + statusBarHeight+ 10,Gravity.END|Gravity.TOP);
				actionbarPopupmenu.showAtLocation(popView, Gravity.RIGHT
						| Gravity.TOP, 20, ACTIONBAR_HETGHT + statusBarHeight
						+ 10);
				actionbarPopupmenu.setFocusable(false);
				actionbarPopupmenu.setBackgroundDrawable(new BitmapDrawable());
			} else {
				// 二次点击使其消失
				actionbarPopupmenu.dismiss();
				actionbarPopupmenu = null;
			}
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		if (actionbarPopupmenu != null && actionbarPopupmenu.isShowing()) {
			actionbarPopupmenu.dismiss();
			actionbarPopupmenu = null;
		}
		return super.onTouchEvent(event);
	}

	private void dismissPopupMenu() {
		if (actionbarPopupmenu != null) {
			actionbarPopupmenu.dismiss();
			actionbarPopupmenu = null;
		}
	}

	private void findview() {

		curriculumtable = (TableLayout) findViewById(R.id.main_curriculumtable);

		findViewById(R.id.activity_main_layout).setOnTouchListener(
				new OnTouchListener() {
					@Override
					public boolean onTouch(View v, MotionEvent event) {
						dismissPopupMenu();
						return false;
					}
				});

		classimage = (LinearLayout) findViewById(R.id.main_ll_class);
		libimage = (LinearLayout) findViewById(R.id.main_ll_lib);

		scoreimage = (LinearLayout) findViewById(R.id.main_ll_gpa);
		getupimage = (LinearLayout) findViewById(R.id.main_ll_mession);
		curriculumTitle = (TextView) findViewById(R.id.main_title);

	}

	private void setListener() {
		anim.setAnimationListener(animListener);

		curriculumTitle.setOnClickListener(viewonClickListener);
		curriculumtable.setOnClickListener(viewonClickListener);
		scoreimage.setOnClickListener(viewonClickListener);
		libimage.setOnClickListener(viewonClickListener);
		classimage.setOnClickListener(viewonClickListener);
		getupimage.setOnClickListener(viewonClickListener);

	}

	@SuppressWarnings("deprecation")
	private void init() {
		Textid = new Main_Curriculum_tv_Id();
		// colors = new Main_Curriculum_tv_bg();
		viewonClickListener = new viewOnclickListener();
		animListener = new animationListener();
		Display mDisplay = getWindowManager().getDefaultDisplay();
		SCREEN_WIDTH = mDisplay.getWidth();
		SCREEN_HEIGHT = mDisplay.getHeight();
		anim = AnimationUtils.loadAnimation(this, R.anim.main_image_anim);
		anim.setFillAfter(false);
		setBound();
		colors = Main.getApp().getColors();
	}

	@SuppressWarnings("deprecation")
	private void setTextView() {
		int length = Textid.getId().length;
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			textview = (TextView) findViewById(Textid.getId()[i]);
			textview.setText(classNameList[i]);
			if (textview.getText().equals("")) {
				textview.setBackgroundColor(Color.parseColor("#FFFFFF"));
			} else {
				textview.setBackgroundDrawable(colors[random
						.nextInt(colors.length)]);
			}
		}
	}

	
	private void setBound() {
		int imageviewwidth = (SCREEN_WIDTH - 40) / 2;
		int tableheight = (SCREEN_HEIGHT - 180) / 2;
		curriculumtable.setMinimumHeight(tableheight);
		classimage.setMinimumWidth(imageviewwidth);
		classimage.setMinimumHeight((int) (imageviewwidth / 1.6f));
		libimage.setMinimumWidth(imageviewwidth);
		libimage.setMinimumHeight((int) (imageviewwidth / 1.6f));
		scoreimage.setMinimumWidth(imageviewwidth);
		scoreimage.setMinimumHeight((int) (imageviewwidth / 1.6f));
		getupimage.setMinimumWidth(imageviewwidth);
		getupimage.setMinimumHeight((int) (imageviewwidth / 1.6f));
	}

	class viewOnclickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			if (actionbarPopupmenu != null) {
				dismissPopupMenu();
				return;
			}
			;
            Intent intent;
			switch (v.getId()) {
			case R.id.main_ll_lib:
                intent = new Intent(Activity_Main.this, Activity_Lib.class);
                startActivity(intent);
//				libimage.startAnimation(anim);
//				position = 1;
				break;
			case R.id.main_title:
				setTextView();
				break;
			case R.id.main_curriculumtable:
				intent = new Intent(Activity_Main.this,
						Activity_curriculumDetail.class);
				startActivity(intent);
//				position = -1;
				break;
			case R.id.main_ll_gpa:
                intent = new Intent(Activity_Main.this, Activity_GPA.class);
                startActivity(intent);
//				scoreimage.startAnimation(anim);
//				position = 5;
				break;

			case R.id.main_ll_class:
                intent = new Intent(Activity_Main.this, Activity_Room.class);
                startActivity(intent);
//				classimage.startAnimation(anim);
//				position = 6;
				break;
			case R.id.main_ll_mession:
                intent = new Intent(Activity_Main.this, Activity_Mession.class);
                startActivity(intent);
//				getupimage.startAnimation(anim);
//				position = 2;
				break;
			}
		}

	}

	class animationListener implements AnimationListener {

		@Override
		public void onAnimationEnd(Animation animation) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onAnimationRepeat(Animation animation) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onAnimationStart(Animation animation) {
			// TODO Auto-generated method stub
			Intent intent;
			switch (position) {
			case 0:
				break;
			case 1:
				intent = new Intent(Activity_Main.this, Activity_Lib.class);
				startActivity(intent);
				position = -1;
				break;
			case 2:
				intent = new Intent(Activity_Main.this, Activity_Mession.class);
				startActivity(intent);
				position = -1;
				break;
			case 3:
				break;
			case 4:

				break;
			case 5:
				intent = new Intent(Activity_Main.this, Activity_GPA.class);
				startActivity(intent);
				position = -1;
				break;
			case 6:
				intent = new Intent(Activity_Main.this, Activity_Room.class);
				startActivity(intent);
				position = -1;
				break;
			}
		}

	}

	/**
	 * 菜单、返回键响应
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			exitBy2Click(); // 调用双击退出函数
		}
		return false;
	}

	/**
	 * 双击退出函数
	 */
	private static Boolean isExit = false;

	private void exitBy2Click() {
		Timer tExit = null;
		if (isExit == false) {
			isExit = true; // 准备退出
			Toast.makeText(this, "再按一次退出口袋学霸", Toast.LENGTH_SHORT).show();
			tExit = new Timer();
			tExit.schedule(new TimerTask() {
				@Override
				public void run() {
					isExit = false; // 取消退出
				}
			}, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务

		} else {
			finish();
			System.exit(0);
		}
	}

}
