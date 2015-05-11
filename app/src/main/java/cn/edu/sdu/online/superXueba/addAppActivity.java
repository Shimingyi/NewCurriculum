package cn.edu.sdu.online.superXueba;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import cn.edu.sdu.online.newcurriculum.R;
import cn.edu.sdu.online.superXueba.BrowseApplicationInfoAdapter.ViewHolder;

public class addAppActivity extends Activity implements OnItemClickListener {

	private ListView listview = null;
	Button confirm, cancel;
	private List<AppInfo> mlistAppInfo = null;
	ArrayList<AppInfo> list = new ArrayList<AppInfo>();
	List<AppInfo> appInfos; 

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.browse_app_list);

		listview = (ListView) findViewById(R.id.listviewApp);
		appInfos = new ArrayList<AppInfo>(); // ������˲鵽��AppInfo
		mlistAppInfo = new ArrayList<AppInfo>();
		queryAppInfo(); // ��ѯ����Ӧ�ó�����Ϣ
		BrowseApplicationInfoAdapter browseAppAdapter = new BrowseApplicationInfoAdapter(
				this, mlistAppInfo);
		listview.setAdapter(browseAppAdapter);
		listview.setOnItemClickListener(this);
		confirm = (Button) findViewById(R.id.confirm_button);
		cancel = (Button) findViewById(R.id.cancel_button);
		confirm.setOnClickListener(new confirmListener());
		cancel.setOnClickListener(new concelListener());
	}

	class confirmListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(addAppActivity.this, StartActivity.class);
			Bundle bundle = new Bundle();
			bundle.putParcelableArrayList("list", list);
			intent.putExtras(bundle);
			setResult(RESULT_OK, intent);
			finish(); // �����˼�ǹرյ�ǰActivity��Ҳ����˵�㷵�ؼ����ز������Activity��.
		}

	}

	class concelListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			finish();
		}

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

		ViewHolder holder = (ViewHolder) arg1.getTag();
		// �ı�CheckBox��״̬
		holder.cb.toggle();
		// ��CheckBox��ѡ��״����¼����
		BrowseApplicationInfoAdapter.getIsSelected().put(arg2,
				holder.cb.isChecked());
		// ����ѡ����Ŀ
		if (holder.cb.isChecked() == true) {
			list.add(mlistAppInfo.get(arg2));
			// checkNum++;
		} else {
			// checkNum--;
			list.remove(mlistAppInfo.get(arg2));
		}
		// ��TextView��ʾ

	}

	PackageManager pm;

	// �����������Activity����Ϣ��������Launch����
	public void queryAppInfo() {
//		PackageManager pm = this.getPackageManager(); // ���PackageManager����
//		// ��ѯ�����Ѿ���װ��Ӧ�ó���
//		List<ApplicationInfo> listAppcations = pm
//				.getInstalledApplications(PackageManager.GET_UNINSTALLED_PACKAGES);
//		Collections.sort(listAppcations,
//				new ApplicationInfo.DisplayNameComparator(pm));// ����
//		
//		// ��������������
//		for (ApplicationInfo app : listAppcations) {
//			appInfos.clear();
//			appInfos.add(getAppInfo(app));
//		}
//
//	}

	 PackageManager pm = this.getPackageManager(); // ���PackageManager����
	 Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
	 mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
	 // ͨ����ѯ���������ResolveInfo����.
	 List<ResolveInfo> resolveInfos = pm.queryIntentActivities(mainIntent,
			 PackageManager.GET_UNINSTALLED_PACKAGES);
	 // ����ϵͳ���� �� ����name����
	 // ���������Ҫ������ֻ����ʾϵͳӦ�ã��������г�������Ӧ�ó���
	 Collections.sort(resolveInfos,
	 new ResolveInfo.DisplayNameComparator(pm));
	 if (mlistAppInfo != null) {
	 mlistAppInfo.clear();
	 for (ResolveInfo reInfo : resolveInfos) {
	 String activityName = reInfo.activityInfo.name; //
	 String pkgName = reInfo.activityInfo.packageName; // ���Ӧ�ó���İ���
	 String appLabel = (String) reInfo.loadLabel(pm); // ���Ӧ�ó����Label
	 BitmapDrawable icon = (BitmapDrawable) reInfo.loadIcon(pm); // ���Ӧ�ó���ͼ��
	 // ΪӦ�ó��������Activity ׼��Intent
	 Intent launchIntent = new Intent();
	 launchIntent.setComponent(new ComponentName(pkgName,
	 activityName));
	 // ����һ��AppInfo���󣬲���ֵ
	 AppInfo appInfo = new AppInfo();
	 appInfo.setAppLabel(appLabel);
	 appInfo.setPkgName(pkgName);
	 appInfo.setAppIcon(icon);
	 // appInfo.setIntent(launchIntent);
	 mlistAppInfo.add(appInfo); // ������б���
	 }
	 }
	 }
	// ����һ��AppInfo���� ������ֵ
//	private AppInfo getAppInfo(ApplicationInfo app) {
//		AppInfo appInfo = new AppInfo();
//		appInfo.setAppLabel((String) app.loadLabel(pm));
//		BitmapDrawable icon = (BitmapDrawable) app.loadIcon(pm);
//		appInfo.setAppIcon(icon);
//		appInfo.setPkgName(app.packageName);
//		return appInfo;
//	}
}