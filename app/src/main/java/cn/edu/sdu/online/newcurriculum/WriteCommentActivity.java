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
import android.widget.TextView;
import android.widget.Toast;

public class WriteCommentActivity extends Activity {
	Button send, cancel;
	EditText writecomment;
	ImageView backimg;
	int id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_writecomment);

		initData();
		initView();
	}

	private void initView() {
		// TODO Auto-generated method stub
		send = (Button) findViewById(R.id.title_confirm);
		cancel = (Button) findViewById(R.id.title_cancel);
		writecomment = (EditText) findViewById(R.id.write_comment_edittext);
//		backimg = (ImageView) findViewById(R.id.back_sign);
//		backimg.setOnClickListener(new backListener());
		send.setOnClickListener(new sendListener());
		cancel.setOnClickListener(new cancel());
	}

	private void initData() {
		// TODO Auto-generated method stub
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		id = bundle.getInt("id");
	}

	class backListener implements OnClickListener {

		@Override
		public void onClick(View v) {

			finish();
		}

	}

	class sendListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (!writecomment.getText().toString().equals("")) {
				release(String.valueOf(id), writecomment.getText().toString());
			} else {
				Toast.makeText(WriteCommentActivity.this, "评论不能为空",
						Toast.LENGTH_SHORT).show();
			}
		}

	}

	class cancel implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			finish();

		}

	}

	private void release(String id, String content) {
		RequestParams params = new RequestParams();
		params.addBodyParameter("Type", "rankcomment");
		params.addBodyParameter("stateid", id);
		params.addBodyParameter("content", content);
		params.addBodyParameter(
				"username",
				Main.getApp()
						.getDataStore()
						.getString(
								"Info_name",
								Main.getApp().getDataStore()
										.getString("stuname", "未知")));
		HttpUtils http = new HttpUtils();
		http.send(HttpRequest.HttpMethod.POST, NetWork.LoginURL, params,
				new RequestCallBack<String>() {

					@Override
					public void onFailure(HttpException arg0, String arg1) {
						// TODO Auto-generated method stub
						Toast.makeText(WriteCommentActivity.this, "网络或其他异常",
								Toast.LENGTH_SHORT).show();
					}

					@Override
					public void onSuccess(ResponseInfo<String> arg0) {
						// TODO Auto-generated method stub
						Toast.makeText(WriteCommentActivity.this, "发送成功",
								Toast.LENGTH_SHORT).show();
						finish();
					}
				});

	}
}
