package cn.edu.sdu.online.newcurriculum;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import cn.edu.sdu.online.adapter.LoadCommentAdapter;
import cn.edu.sdu.online.modal.SignComment;
import cn.edu.sdu.online.modal.SignRelease;
import cn.edu.sdu.online.network.NetWork;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

public class Activity_ConsultCenter_PinglunActivity extends Activity {

	Button releasebutton;
	int id;
	ImageView zanimage, backimage;
	String detail;
	String time;
	String name;
	TextView zan, pinglun;
	ArrayList<SignComment> signcom;
	ListView commentlist;
	LayoutInflater inflater;
	TextView zantext, nametest;
	LoadCommentAdapter adapter;
	InputMethodManager imm;
	LinearLayout layoutreply;
	
	private ActionBar actionBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detailedrank);
		inflater = LayoutInflater.from(Activity_ConsultCenter_PinglunActivity.this);
		
		getData();
		initView();
	}

	private void getData() {
		// TODO Auto-generated method stub
		SignRelease sign = (SignRelease) getIntent().getSerializableExtra(
				"sign");
		id = sign.getRankid();
		detail = sign.getReleaserank();
		signcom = sign.getCommentlist();
		time = sign.getTime();
		name = sign.getName();
	}
	
	private void initView() {
		// TODO Auto-generated method stub
//		layoutreply = (LinearLayout)findViewById(R.id.writereplylayout);
//		layoutreply.setVisibility(View.INVISIBLE);
		TextView formal = (TextView) findViewById(R.id.mood);
		formal.setText(detail);
		TextView timetext = (TextView) findViewById(R.id.creatdate);
		timetext.setText(time);
		nametest = (TextView) findViewById(R.id.username);
		nametest.setText(name);
		zantext = (TextView) findViewById(R.id.zan_hanzi);
//		backimage = (ImageView) findViewById(R.id.back_signstate);
//		backimage.setOnClickListener(new backListener());

		if (signcom != null) {
			commentlist = (ListView) findViewById(R.id.comment_list);
			adapter = new LoadCommentAdapter(Activity_ConsultCenter_PinglunActivity.this, signcom);
			commentlist.setAdapter(adapter);
		}
		zanimage = (ImageView) findViewById(R.id.zan_img);
		zantext.setOnClickListener(new zanListener());
		pinglun = (TextView) findViewById(R.id.pinglun_hanzi);
		pinglun.setOnClickListener(new PinlunListener());
//		pinglun.setOnTouchListener(new pinglunInput());
	}

	class backListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			finish();
		}

	}

	class PinlunListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
//			layoutreply = (LinearLayout)findViewById(R.id.writereplylayout);
//			layoutreply.setVisibility(View.VISIBLE);
//			EditText writereply =(EditText)layoutreply.findViewById(R.id.writereplylayout_edittext);
//			writereply.requestFocus();
//			Button sendreply = (Button)findViewById(R.id.sendreply_button);
//			sendreply.setOnClickListener(new sendReplyButtonListener());
//			Timer timer = new Timer();
//			timer.schedule(new TimerTask() {
//
//				@Override
//				public void run() {
//					// TODO Auto-generated method stub
//					imm = (InputMethodManager) pinglun.getContext()
//							.getSystemService(Context.INPUT_METHOD_SERVICE);
//					imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
//				}
//			}, 100);
//			CommentDialog comment = new CommentDialog(PinglunActivity.this,
//					R.style.commentDialog);
//			comment.show();
			Intent intent = new Intent();
			Bundle bundle = new Bundle();

			bundle.putInt("id", id);
			intent.setClass(Activity_ConsultCenter_PinglunActivity.this,
			WriteCommentActivity.class);
			intent.putExtras(bundle);
			startActivity(intent);
		}

	}
	
	class sendReplyButtonListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Toast.makeText(Activity_ConsultCenter_PinglunActivity.this, "点击了button", Toast.LENGTH_SHORT).show();
		}
		
	}


	class zanListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			zanupload(String.valueOf(id));
			zantext.setText("已赞");
			zanimage.setEnabled(false);
		}

	}

	private void zanupload(String id) {
		// TODO Auto-generated method stub
		RequestParams params = new RequestParams();
		params.addBodyParameter("Type", "rankzan");
		params.addBodyParameter("stateid", id);
		HttpUtils http = new HttpUtils();
		http.send(HttpRequest.HttpMethod.POST, NetWork.LOGIN_URL, params,
				new RequestCallBack<String>() {

					@Override
					public void onFailure(HttpException arg0, String arg1) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onSuccess(ResponseInfo<String> arg0) {
						// TODO Auto-generated method stub

					}
				});
	}

	/*private void release(String id, String content) {
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

					}

					@Override
					public void onSuccess(ResponseInfo<String> arg0) {
						// TODO Auto-generated method stub

					}
				});

	}*/
	
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
