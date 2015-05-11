package cn.edu.sdu.online.newcurriculum;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.umeng.fb.FeedbackAgent;

public class Activity_Feedback extends Activity {
	private ActionBar ActionBar;
	private Button sendButton;// 分别为返回按钮和发送按钮
	private FeedbackAgent fb;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_feedback);
		// 获取组件
		sendButton = (Button) findViewById(R.id.sendButton);
		// 为按钮设置监听
		sendButton.setOnClickListener(new ButtonListener());
		
		 setUpUmengFeedback();
	}

	public class ButtonListener implements OnClickListener {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			switch (arg0.getId()) {
			case R.id.sendButton:// 发送按钮被点击
//				FeedbackAgent agent = new FeedbackAgent(Activity_Feedback.this);
//				agent.sync();
//				agent.startFeedbackActivity();
	            fb.startFeedbackActivity();
				Log.i("Activity_Feedback", "ButtonListener");
				break;
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		ActionBar = getActionBar();
		// 设置ActionBar背景颜色
		Drawable backgroundcolor = getResources().getDrawable(
				R.color.actionbarbg);
		ActionBar.setBackgroundDrawable(backgroundcolor);
		return super.onCreateOptionsMenu(menu);
	}
	 private void setUpUmengFeedback() {
	        fb = new FeedbackAgent(this);
	        // check if the app developer has replied to the feedback or not.
	        fb.sync();
	        fb.openAudioFeedback();
	        fb.openFeedbackPush();
//	        PushAgent.getInstance(this).enable();

	        //fb.setWelcomeInfo();
	        //  fb.setWelcomeInfo("Welcome to use umeng feedback app");
//	        FeedbackPush.getInstance(this).init(true);
//	        PushAgent.getInstance(this).setPushIntentServiceClass(MyPushIntentService.class);
	    }
	
}
