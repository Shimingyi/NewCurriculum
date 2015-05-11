package cn.edu.sdu.online.tab;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import cn.edu.sdu.online.adapter.LoadQuestionAdapter;
import cn.edu.sdu.online.app.Main;
import cn.edu.sdu.online.listener.ActionBarListener;
import cn.edu.sdu.online.listener.OnRefreshListener;
import cn.edu.sdu.online.modal.Question;
import cn.edu.sdu.online.network.NetWork;
import cn.edu.sdu.online.newcurriculum.Activity_AnswerQuestion;
import cn.edu.sdu.online.newcurriculum.Activity_ConsultCenter;
import cn.edu.sdu.online.newcurriculum.Activity_QuestionDetails;
import cn.edu.sdu.online.newcurriculum.R;
import cn.edu.sdu.online.newcurriculum.ReleaseQuestion;
import cn.edu.sdu.online.utils.DefineUtil;
import cn.edu.sdu.online.view.RefreshListView;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

/**
 */
public class loadQuestion extends Fragment implements ActionBarListener{
	private LoadQuestionAdapter mAdapter;
	private List<Question> listItem;
	private RefreshListView mRefreshListView;
	View view;
	Main app;
	Activity activity;
	static String resultserver;
	static String upresultserver;
	PopupMenu popup = null;
	Button releaseHomework_button;
	ArrayList<Question> queslist;
    Activity_ConsultCenter center;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		app = Main.getApp();
		activity =  getActivity();
        center = (Activity_ConsultCenter)getActivity();
        setHasOptionsMenu(true);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		view = inflater.inflate(R.layout.frag_loadquestion, null);
//        center.setActionBarListener(this);
		initView();
		initData();
		return view;
	}

	public void initView() {
		mRefreshListView = (RefreshListView) view
				.findViewById(R.id.questionlist);
	}

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem menuitem = menu.add("hello");
        menuitem.setIcon(R.drawable.icon_question);
        menuitem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
//        if ("hello".equals("签到")) {
        menuitem.setOnMenuItemClickListener(new releaseQuestion());
    }

    private void initData() {
		listItem = app.getAskandAnswer("ASKANDANSWER");
		mAdapter = new LoadQuestionAdapter(getActivity(), listItem);
		mRefreshListView.setAdapter(mAdapter);
		mRefreshListView.setOnItemClickListener(new listClick());
		mRefreshListView.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				RequestParams params = new RequestParams();
				params.addBodyParameter("Type", "getaskdownrefresh");
				HttpUtils http = new HttpUtils();
				http.send(HttpRequest.HttpMethod.POST, NetWork.LoginURL, params,
						new RequestCallBack<String>() {

							@Override
							public void onFailure(HttpException arg0,
									String arg1) {
								// TODO Auto-generated method stub

							}

							@Override
							public void onSuccess(ResponseInfo<String> arg0) {
								// TODO Auto-generated method stub
								resultserver = arg0.result;
								Message msg = new Message();
								msg.what = 1;
								handler.sendMessage(msg);

							}
						});

			}

			@Override
			public void onLoadMoring() {
				// TODO Auto-generated method stub
				System.out.println("ִ��������ˢ��");
				RequestParams pa = new RequestParams();
				pa.addBodyParameter("Type", "getanswerupload");
				pa.addBodyParameter(
						"flagupquesid",
						String.valueOf(app.getDataStore().getInt(
								"flagaskoldest", 0)));
				HttpUtils http = new HttpUtils();
				http.send(HttpRequest.HttpMethod.POST, NetWork.LoginURL, pa,
						new RequestCallBack<String>() {

							@Override
							public void onFailure(HttpException arg0,
									String arg1) {
								// TODO Auto-generated method stub

							}

							@Override
							public void onSuccess(ResponseInfo<String> arg0) {
								// TODO Auto-generated method stub
								Message msg = new Message();
								upresultserver = arg0.result;
								msg.what = 2;
								handler.sendMessage(msg);
							}
						});

			}

		});
	}

    @Override
    public void onPrepare(Menu menu) {
        System.out.println("onPrepare");
        MenuItem menuitem = menu.add("hello");
        menuitem.setIcon(R.drawable.icon_question);
        menuitem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
//        if ("hello".equals("签到")) {
        menuitem.setOnMenuItemClickListener(new releaseQuestion());
    }

    class listClick implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
				long arg3) {
			// TODO Auto-generated method stub
			Question ques = listItem.get(position - 1);
			Intent intent = new Intent(activity, Activity_AnswerQuestion.class);
			Bundle bundle = new Bundle();
			bundle.putSerializable("question", ques);
			intent.setClass(activity, Activity_QuestionDetails.class);
			intent.putExtras(bundle);
			activity.startActivity(intent);
		}

	}

	
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			Gson gson = new Gson();

			switch (msg.what) {
			case 1:
				queslist = new ArrayList<Question>();
				queslist = gson.fromJson(resultserver,
						new TypeToken<ArrayList<Question>>() {
						}.getType());
				new AsyncTask<Void, Void, Void>() {
					protected Void doInBackground(Void... params) {
						SystemClock.sleep(3000);
						if (queslist.size() != 0) {
							listItem.clear();
							for (int i = 0; i < queslist.size(); i++) {
								listItem.add(0, queslist.get(i));
							}
							app.setAskAndAnswer("ASKANDANSWER", listItem);
						}
						return null;
					}

					protected void onPostExecute(Void result) {
						super.onPostExecute(result);
						mAdapter.notifyDataSetChanged();
						mRefreshListView.onRefreshFinish();
					}
				}.execute(new Void[] {});
				break;
			case 2:
				queslist = new ArrayList<Question>();
				queslist = gson.fromJson(upresultserver,
						new TypeToken<ArrayList<Question>>() {
						}.getType());
				new AsyncTask<Void, Void, Void>() {
					@Override
					protected Void doInBackground(Void... params) {
						SystemClock.sleep(3000);
						if (queslist.size() != 0) {
							for (int i = 0; i < queslist.size(); i++) {
								listItem.add(queslist.get(i));
							}
						}
						return null;
					}

					@Override
					protected void onPostExecute(Void result) {
						super.onPostExecute(result);
						mAdapter.notifyDataSetChanged();
						mRefreshListView.onRefreshFinish();
					}

				}.execute(new Void[] {});

			}
		}
	};

    class releaseQuestion implements MenuItem.OnMenuItemClickListener {

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            if (app.getDataStore().getBoolean("login", false)) {
                // TODO Auto-generated method stub
                Intent intent = new Intent();
                intent.setClass(getActivity(), ReleaseQuestion.class);
                getActivity().startActivity(intent);
            } else {
                Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT)
                        .show();
            }
            return true;
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        center.setActionBarListener(null);
    }
}
