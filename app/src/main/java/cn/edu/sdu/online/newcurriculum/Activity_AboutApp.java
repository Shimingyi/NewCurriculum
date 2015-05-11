package cn.edu.sdu.online.newcurriculum;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;

public class Activity_AboutApp extends Activity {

	private ActionBar ActionBar;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_aboutapp);
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