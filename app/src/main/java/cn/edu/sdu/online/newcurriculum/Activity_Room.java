package cn.edu.sdu.online.newcurriculum;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.graphics.drawable.Drawable;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.LinearLayout;
import cn.edu.sdu.online.app.Main;

public class Activity_Room extends Activity {
	private WebView webView;
	private LinearLayout layout;
	private Main app;
	private ActionBar ActionBar;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		app = Main.getApp();
		setContentView(R.layout.activity_room);
		layout = (LinearLayout)findViewById(R.id.room);

		webView = new WebView(this);
		WebSettings webSettings = webView.getSettings();
		webSettings.setJavaScriptEnabled(true);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
			setAllow(webSettings);
		webView.loadUrl("file:///android_asset/shoujizixishi/index2.html");
		layout.addView(webView);
		NetworkInfo info = app.getConnectivityManager().getActiveNetworkInfo();

		
		if (info == null || !info.isAvailable()) {
			new AlertDialog.Builder(this).setTitle("没有网络的情况下无法查询").setIcon(android.R.drawable.ic_dialog_info).setPositiveButton("确定", new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {

				}
			}).show();
			
		} else {

		}
	}
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void setAllow(WebSettings settings) {
        settings.setAllowUniversalAccessFromFileURLs(true);
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
