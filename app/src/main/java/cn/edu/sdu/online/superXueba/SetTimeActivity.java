package cn.edu.sdu.online.superXueba;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;
import android.widget.Toast;
import cn.edu.sdu.online.app.Main;
import cn.edu.sdu.online.newcurriculum.R;
import cn.edu.sdu.online.utils.DateUtil;

public class SetTimeActivity extends Activity {
	Button confirm, cancle;
	TimePicker time;
	int hour;
	int min;
	Main app;
	BroadcastMain receiver;
	ImageView image;
	private ActionBar actionBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settime);
		receiver = new BroadcastMain();
		app = Main.getApp();
		image =(ImageView)findViewById(R.id.back_settime);
		image.setOnClickListener(new backListener());
		time = (TimePicker) findViewById(R.id.timePicker);
		time.setIs24HourView(true);//是否显示24小时制？默认false
		time.setCurrentHour(12);
		time.setCurrentMinute(00);
		// 设置显示时间为12：00
		time.setOnTimeChangedListener(tpLis);
		// 时间改变时触发
		confirm = (Button) findViewById(R.id.confirmtime_button);
		cancle = (Button) findViewById(R.id.canceltime_button);
		confirm.setOnClickListener(new confirmListener());
		cancle.setOnClickListener(new CancelListener());
	}

	class backListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			finish();
		}
		
	}
	private OnTimeChangedListener tpLis = new OnTimeChangedListener() {
		public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {

			hour = view.getCurrentHour();
			min = view.getCurrentMinute();
			app.getDataStore().edit().putInt("xuebahour", hour)
					.putInt("xuebamin", min).commit();

		}
	};

	class confirmListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			DateUtil data = new DateUtil();
			int h = data.getHour();
			int m = data.getMinute();
			Log.i("时间0", h + "{{{" + m);
			Log.i("时间1", hour + "{{{" + min);
			if (h > hour || (h == hour && m >= min)) {
				System.out.println("zhi xingl  e if");
				Toast.makeText(SetTimeActivity.this, "时间输入不合理",
						Toast.LENGTH_SHORT).show();
				// vib.vibrate(300);
			} else {

				Intent intent = new Intent(SetTimeActivity.this,
						XuebaService.class);
				startService(intent);
				Intent intent2 = new Intent(SetTimeActivity.this,
						InterceptActivity.class);
				startActivity(intent2);
				AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
				Intent intent1 = new Intent(SetTimeActivity.this,
						BroadCastActivity.class);
				intent.setAction("ALARM_ACTION");
				PendingIntent pendingIntent = PendingIntent.getActivity(
						SetTimeActivity.this, 0, intent1, 0);
				am.set(AlarmManager.RTC, System.currentTimeMillis()
						+ (int) (3600000 * (hour - h) + 60000 * (min - m)),
						pendingIntent);
			}
		}

	}

	class CancelListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			finish();
		}

	}

	public class BroadcastMain extends BroadcastReceiver {
		// 必须要重载的方法，用来监听是否有广播发送
		@Override
		public void onReceive(Context context, Intent intent) {
			System.out.println("收到了广播");
			if (intent.getStringExtra("SEND").equals("stop")) {
				Intent in = new Intent(SetTimeActivity.this, XuebaService.class);
				context.stopService(in);
			}
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		actionBar = getActionBar();
		// 设置ActionBar背景颜色
		Drawable backgroundcolor = getResources().getDrawable(
				R.color.actionbarbg);
		actionBar.setBackgroundDrawable(backgroundcolor);
		return super.onCreateOptionsMenu(menu);
	}
}
