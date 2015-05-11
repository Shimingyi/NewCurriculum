package cn.edu.sdu.online.superXueba;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import cn.edu.sdu.online.app.Main;

import android.app.ActivityManager;
import android.app.ActivityManager.RecentTaskInfo;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.text.format.Time;
import android.util.Log;
import android.widget.Toast;

public class XuebaService extends Service {

	private ActivityManager activityManager;

	private String lastTaskName;
	String recentTaskName;
	private Timer timer;
	SharedPreferences settings;
	public static final String ACTION = "STOP_SERVICE";
	public static final String MSG_KEY = "SEND";
	private TimerTask task = new TimerTask() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			if (activityManager == null) {
				activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
			}

			List<RecentTaskInfo> recentTasks = activityManager.getRecentTasks(
					2, ActivityManager.RECENT_WITH_EXCLUDED);

			RecentTaskInfo recentInfo = recentTasks.get(0);
			Intent intent = recentInfo.baseIntent;
			recentTaskName = intent.getComponent().getPackageName();
			String pack = getPackageName();
			if (lastTaskName != null) {
				if (!lastTaskName.equals(recentTaskName)
						&& !recentTaskName.equals(pack)
						&& !recentTaskName.equals("com.android.launcher")
						&& ifEnable()) {
					Intent intentNewActivity = new Intent(XuebaService.this,
							InterceptActivity.class);
					intentNewActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					startActivity(intentNewActivity);
				}
			}

			lastTaskName = recentTaskName;
			Time time = new Time();
			time.setToNow();
			endhour = Main.getApp().getDataStore().getInt("xuebahour", 0);
			endmin = Main.getApp().getDataStore().getInt("xuebamin", 0);
			if (time.hour == endhour && time.minute == endmin
					&& time.second == 0) {
				timer.cancel();
			}

		}

	};

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		timer = new Timer();
		timer.schedule(task, 0, 20);
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<String> getAppPackageName(String filename) {

		if (Main.getApp().getDataStore().getBoolean(filename, false)) {
			try {
				FileInputStream in = openFileInput(filename);
				ObjectInputStream is = new ObjectInputStream(in);
				ArrayList<String> list = (ArrayList<String>) is.readObject();
				is.close();
				in.close();
				return list;
			} catch (FileNotFoundException e) {
			} catch (StreamCorruptedException e) {
			} catch (IOException e) {
			} catch (ClassNotFoundException e) {
			}
		} else {
			return new ArrayList<String>();
		}
		return new ArrayList<String>();
	}

	private boolean ifEnable() {
		boolean enApp = true;
		ArrayList<String> list = getAppPackageName("APPPACKAGENAME");
		for (int i = 0; i < list.size(); i++) {
			if (recentTaskName.equals(list.get(i))) {
				enApp = false;
				return enApp;
			} else {
				enApp = true;
			}
		}
		return enApp;
	}

	int endhour;
	int endmin;
}
