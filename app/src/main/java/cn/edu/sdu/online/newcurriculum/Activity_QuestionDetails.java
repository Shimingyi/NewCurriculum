package cn.edu.sdu.online.newcurriculum;

import java.util.ArrayList;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

import cn.edu.sdu.online.adapter.LoadAnswerAdapter;
import cn.edu.sdu.online.app.Main;
import cn.edu.sdu.online.modal.Answer;
import cn.edu.sdu.online.modal.Question;
import cn.edu.sdu.online.utils.DefineUtil;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class Activity_QuestionDetails extends Activity {

	TextView title, time, numanswer, details;
	EditText answer;
	Button release_answer;
	int quesid, ansnum;
	String ques_title, ques_time, ques_numanswer, ques_details;
	Main app;
	ListView answer_list;
	LayoutInflater inflater;
	ArrayList<Answer> answlist;
	Question ques;
	LoadAnswerAdapter adapter;
	ImageView back;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		inflater = LayoutInflater.from(Activity_QuestionDetails.this);
		app = Main.getApp();
		setContentView(R.layout.activity_questiondetails);
		getData();
		initView();

	}

	private void getData() {
		ques = (Question) getIntent().getSerializableExtra("question");
		ques_title = ques.getBrief();
		ques_time = String.valueOf(ques.getTime());
		ansnum = ques.getAnswernum();
		ques_numanswer = String.valueOf(ansnum);
		ques_details = ques.getQuestion();
		quesid = ques.getQuesid();
		answlist = ques.getAns();

	}

	private void initView() {
		// TODO Auto-generated method stub
		title = (TextView) findViewById(R.id.testview_quesbrief);
		time = (TextView) findViewById(R.id.testview_asktime);
		numanswer = (TextView) findViewById(R.id.testview_answer);
		details = (TextView) findViewById(R.id.testview_quesdetail);
//		back = (ImageView) findViewById(R.id.back_questiondetails);
//		back.setOnClickListener(new backListener());
		// answer = (EditText) findViewById(R.id.edittext_other_answer);
		// release_answer = (Button) findViewById(R.id.release_answer_button);
		// release_answer.setOnClickListener(new release_answer());
		title.setText(ques_title);
		time.setText(ques_time);
		numanswer.setOnClickListener(new answerListerer());
		// numanswer.setText("回答("+ques_numanswer+")");
		details.setText(ques_details);
		answer_list = (ListView) findViewById(R.id.answerlist_id);
		adapter = new LoadAnswerAdapter(answlist, Activity_QuestionDetails.this);
		answer_list.setAdapter(adapter);
	}

	class backListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			finish();
		}

	}

	class answerListerer implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
			Bundle bundle = new Bundle();
			bundle.putInt("id", quesid);
			intent.setClass(Activity_QuestionDetails.this, Activity_AnswerQuestion.class);
			intent.putExtras(bundle);
			startActivity(intent);
		}

	}
}
