package cn.edu.sdu.online.superXueba;

import java.io.Serializable;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;

//Model类 ，用来存储应用程序信息
public class AppInfo implements Parcelable {

	private String appLabel; // 应用程序标签
	private BitmapDrawable appIcon; // 应用程序图像
	// private Intent intent; // 启动应用程序的Intent
	// ，一般是Action为Main和Category为Lancher的Activity
	private String pkgName; // 应用程序所对应的包名

	public AppInfo() {
	}

	public AppInfo(Parcel in) {
		appLabel = in.readString();
		Bitmap bit = in.readParcelable(null);
		// appIcon = in.readParcelable(null);
		appIcon = new BitmapDrawable(bit);
		// BitmapDrawable bd=new BitmapDrawable(bm);
		// BitmapDrawable bd= new BitmapDrawable(getResource(), bm);
		pkgName = in.readString();
	}

	public String getAppLabel() {
		return appLabel;
	}

	public void setAppLabel(String appName) {
		this.appLabel = appName;
	}

	// public Intent getIntent() {
	// return intent;
	// }
	//
	// public void setIntent(Intent intent) {
	// this.intent = intent;
	// }

	public BitmapDrawable getAppIcon() {
		return appIcon;
	}

	public void setAppIcon(BitmapDrawable appIcon) {
		this.appIcon = appIcon;
	}

	public String getPkgName() {
		return pkgName;
	}

	public void setPkgName(String pkgName) {
		this.pkgName = pkgName;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeString(appLabel);
		Bitmap bitmap = ((BitmapDrawable) appIcon).getBitmap();
		dest.writeParcelable(bitmap, flags);
		dest.writeString(pkgName);
	}

	public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
		public AppInfo createFromParcel(Parcel in) {
			return new AppInfo(in);
		}

		public AppInfo[] newArray(int size) {
			return new AppInfo[size];
		}
	};

	// public static Bitmap drawableToBitmap(Drawable drawable) {
	// Bitmap bitmap = Bitmap
	// .createBitmap(
	// drawable.getIntrinsicWidth(),
	// drawable.getIntrinsicHeight(),
	// drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
	// : Bitmap.Config.RGB_565);
	// Canvas canvas = new Canvas(bitmap);
	// // canvas.setBitmap(bitmap);
	// drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
	// drawable.getIntrinsicHeight());
	// drawable.draw(canvas);
	// return bitmap;
	// }
}
