package cn.edu.sdu.online.newcurriculum;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class ReleaseQuestion extends Activity {
	Button release_question;
	EditText question_brief, question_details;
	Main app;
	ImageView back;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_releasequestion);
		app = Main.getApp();
		release_question = (Button) findViewById(R.id.ask_releaseask_button);
		release_question.setOnClickListener(new releaseQuestion());
		question_brief = (EditText) findViewById(R.id.ask_brief_edittext);
		question_details = (EditText) findViewById(R.id.ask_specific_edittext);
//		back = (ImageView) findViewById(R.id.back_releasequestion);
//		back.setOnClickListener(new backListener());
	}

	class backListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			finish();
		}

	}

	class releaseQuestion implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (question_brief.getText().toString().equals("")
					|| question_details.getText().toString().equals("")) {
				Toast.makeText(ReleaseQuestion.this, "请输入内容",
						Toast.LENGTH_SHORT).show();
			} else {
				uploadQuestion();
			}
		}

		private void uploadQuestion() {
			RequestParams params = new RequestParams();
			params.addBodyParameter("Type", "askquestion");
			params.addBodyParameter(
					"username",
					app.getDataStore().getString("Info_name",
							app.getDataStore().getString("stuname", "未知")));
			params.addBodyParameter("question_brief", question_brief.getText()
					.toString());
			params.addBodyParameter("question_details", question_details
					.getText().toString());
			HttpUtils http = new HttpUtils();
			http.send(HttpRequest.HttpMethod.POST, NetWork.LOGIN_URL, params,
					new RequestCallBack<String>() {

						@Override
						public void onFailure(HttpException arg0, String arg1) {
							// TODO Auto-generated method stub
							Toast.makeText(ReleaseQuestion.this,
									"发布失败，请检查网络设置", Toast.LENGTH_SHORT).show();
						}

						@Override
						public void onSuccess(ResponseInfo<String> arg0) {
							// TODO Auto-generated method stub
							Toast.makeText(ReleaseQuestion.this, "发布成功",
									Toast.LENGTH_SHORT).show();
							finish();
						}
					});
		}
	}
}
