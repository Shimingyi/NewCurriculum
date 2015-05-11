package cn.edu.sdu.online.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import cn.edu.sdu.online.newcurriculum.R;

public class GetVersion {
	private Context context;
	
	public GetVersion(Context context) {
		this.context = context;
	}
	/**
	 * 获取版本号
	 * @return 当前应用的版本号
	 */
	public String getVersion() {
	    try {
	        PackageManager manager = context.getPackageManager();
	        PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
	        String version = info.versionName;
	        return context.getString(R.string.app_name) + version;
	    } catch (Exception e) {
	        e.printStackTrace();
	        return context.getString(R.string.app_name);
	    }
	}
}
