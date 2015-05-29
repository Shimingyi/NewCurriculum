package cn.edu.sdu.online.newcurriculum;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import cn.edu.sdu.online.app.Main;
import cn.edu.sdu.online.network.NetWork;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class Activity_AnswerQuestion extends Activity {
	EditText answer;
	Button cancle, confirm;
	ImageView back;
	int quesid;
	Main app;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_answerquestion);
		app = Main.getApp();
		initData();
		initView();
	}

	private void initView() {
		// TODO Auto-generated method stub
		answer = (EditText) findViewById(R.id.write_answer_edittext);
		cancle = (Button) findViewById(R.id.answer_cancel);
//		back = (ImageView) findViewById(R.id.back_answer);
//		back.setOnClickListener(new backListener());
		cancle.setOnClickListener(new CancleListener());
		confirm = (Button) findViewById(R.id.answer_confirm);
		confirm.setOnClickListener(new confirmListener());
	}

	private void initData() {
		// TODO Auto-generated method stub
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		quesid = bundle.getInt("id");
	}

	class confirmListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (answer.getText().toString().equals("")) {
				Toast.makeText(Activity_AnswerQuestion.this, "请输入回答", Toast.LENGTH_SHORT)
						.show();
			} else {
				uploadanswer(String.valueOf(quesid), answer.getText()
						.toString());
			}
		}

	}

	class CancleListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			finish();
		}

	}

	class backListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			finish();
		}

	}

	private void uploadanswer(String id, String content) {
		// TODO Auto-generated method stub
		RequestParams params = new RequestParams();
		params.addBodyParameter("Type", "uploadanswer");
		params.addBodyParameter("id", id);
		params.addBodyParameter("content", content);
		params.addBodyParameter(
				"username",
				app.getDataStore().getString("Info_name",
						app.getDataStore().getString("stuname", "未知")));
		HttpUtils http = new HttpUtils();
		http.send(HttpRequest.HttpMethod.POST, NetWork.LOGIN_URL, params,
				new RequestCallBack<String>() {

					@Override
					public void onFailure(HttpException arg0, String arg1) {
						// TODO Auto-generated method stub
						Toast.makeText(Activity_AnswerQuestion.this, "发送失败，请检查网络设置",
								Toast.LENGTH_SHORT).show();
					}

					@Override
					public void onSuccess(ResponseInfo<String> arg0) {
						// TODO Auto-generated method stub
						Toast.makeText(Activity_AnswerQuestion.this, "发送成功",
								Toast.LENGTH_SHORT).show();
						finish();
					}
				});
	}
}
