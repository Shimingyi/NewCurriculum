package cn.edu.sdu.online.newcurriculum;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import cn.edu.sdu.online.app.Main;

import com.umeng.fb.FeedbackAgent;

public class Activity_About extends Activity implements OnClickListener {
	private ActionBar ActionBar;
	private FrameLayout flOne, flTwo;
	private final static String hello = "Hello";
	private String name = "小明";
	private Intent intent;
	private FeedbackAgent fb;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
		
		fb = new FeedbackAgent(Activity_About.this);
		fb.sync();
		//空行内为ActionBar设置部分
		// 获取ActionBar
		/*ActionBar = getActionBar();
		// 设置ActionBar题目
		ActionBar.setTitle(hello+name);
		// 取消icon
		ActionBar.setDisplayUseLogoEnabled(false);
		ActionBar.setDisplayShowHomeEnabled(false);
		//设置ActionBar背景颜色
		Drawable backgroundcolor = getResources().getDrawable(R.color.actionbarbg);
		ActionBar.setBackgroundDrawable(backgroundcolor);
		// 将自定义的Button加在ActionBar上
		View actionbarLayout = LayoutInflater.from(this).inflate(
				R.layout.actionbar, null);
		ActionBar.setDisplayShowCustomEnabled(true);
		ActionBar.setCustomView(actionbarLayout);*/

		flOne = (FrameLayout) findViewById(R.id.flOne);
		flTwo = (FrameLayout) findViewById(R.id.flTwo);
		flOne.setOnClickListener(this);
		flTwo.setOnClickListener(this);

	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		ActionBar = getActionBar();
		// 设置ActionBar题目
		name = Main.getApp().getDataStore().getString("stuname", "Friend");
		ActionBar.setTitle(hello + "," + name);
		// 设置ActionBar背景颜色
		Drawable backgroundcolor = getResources().getDrawable(
				R.color.actionbarbg);
		ActionBar.setBackgroundDrawable(backgroundcolor);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		// 关于口袋学霸
		case R.id.flOne:
			intent = new Intent(Activity_About.this, Activity_AboutApp.class);
			startActivity(intent);
			break;
		// 反馈给我
		case R.id.flTwo:
//			intent = new Intent(Activity_About.this, Activity_Feedback.class);
//			startActivity(intent);
			fb.startFeedbackActivity();
			break;

		}
	}

}
