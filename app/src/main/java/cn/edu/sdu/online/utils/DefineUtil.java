package cn.edu.sdu.online.utils;

import android.os.Environment;

public interface DefineUtil {
	static final int IMAGE_REQUEST_CODE = 0;
	static final int CAMERA_REQUEST_CODE = 1;
	static final int RESULT_REQUEST_CODE = 2;
	static final int RESULT_CANCELED = 3;
	static final String IMAGE_FILE_NAME = "faceImage.jpg";
	static final int IMAGEVIEW_HEIGHT=100;
	static final int IMAGEVIEW_WIDTH=100;

	static final String SDPATH = Environment.getExternalStorageDirectory() + "";
	static final String PATH = "http://211.87.226.218:8080/CurriculumServer/login";
//	static final String PATH = "http://202.194.14.195:8080/CurriculumServer/login";
}
