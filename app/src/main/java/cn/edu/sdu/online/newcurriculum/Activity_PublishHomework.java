package cn.edu.sdu.online.newcurriculum;

import java.util.Calendar;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import cn.edu.sdu.online.app.Main;
import cn.edu.sdu.online.network.NetWork;
import cn.edu.sdu.online.utils.DefineUtil;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Activity_PublishHomework extends Activity {
	private Button publishbutton;
	private EditText course, content;
	private DatePicker deadline;
	private int year, month, day;
	String massage = null;
	Main app;
	ImageView image;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_publishhomework);
		app = Main.getApp();
		/*image = (ImageView) findViewById(R.id.back_homework_icon);
		image.setOnClickListener(new backListener());*/
		publishbutton = (Button) findViewById(R.id.publishhomework);
		publishbutton.setOnClickListener(new PublishbuttonListener());
		course = (EditText) findViewById(R.id.coursename);
		content = (EditText) findViewById(R.id.homeworkcontent);
		deadline = (DatePicker) findViewById(R.id.deadline);
		Calendar c = Calendar.getInstance();
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH);
		day = c.get(Calendar.DAY_OF_MONTH);
		deadline.init(year, month, day, new OnDateChangedListener() {
			public void onDateChanged(DatePicker arg0, int year, int month,
					int day) {
				Activity_PublishHomework.this.year = year;
				Activity_PublishHomework.this.month = month;
				Activity_PublishHomework.this.day = day;

			}

		});
	}

	class backListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			System.out.println("di ji le back");
			finish();
		}

	}

	class PublishbuttonListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (course.getText().toString().equals("")) {
				Toast.makeText(Activity_PublishHomework.this, "请输入科目",
						Toast.LENGTH_SHORT).show();
			} else if (content.getText().toString().equals("")) {
				Toast.makeText(Activity_PublishHomework.this, "请输入作业内容",
						Toast.LENGTH_SHORT).show();
			} else {
				uploadHomework();
			}
		}
	}

	private void ShowMassage() {
		Toast.makeText(this, massage, Toast.LENGTH_SHORT).show();
	}

	private void uploadHomework() {
		String id = app.getDataStore().getString("username", "");
		String cs = course.getText().toString().trim();
		String ct = content.getText().toString().trim();
		String months = null;
		if (++month < 10) {
			months = "0" + month;
		} else {
			months = "" + month;
		}
		String deadline = year + "-" + months + "-" + day;
		RequestParams params = new RequestParams();
		params.addBodyParameter("Type", "releasehomework");
		params.addBodyParameter("userid", id);
		params.addBodyParameter("subject", cs);
		params.addBodyParameter("content", ct);
		params.addBodyParameter("deadline", deadline);
		HttpUtils http = new HttpUtils();
		http.send(HttpRequest.HttpMethod.POST, NetWork.LoginURL, params,
				new RequestCallBack<String>() {

					@Override
					public void onFailure(HttpException arg0, String arg1) {
						// TODO Auto-generated method stub
						Toast.makeText(Activity_PublishHomework.this, "网络或其他异常",
								Toast.LENGTH_SHORT).show();
					}

					@Override
					public void onSuccess(ResponseInfo<String> arg0) {
						// TODO Auto-generated method stub
						Toast.makeText(Activity_PublishHomework.this, "发布成功",
								Toast.LENGTH_SHORT).show();
						finish();
					}
				});
	}
}
