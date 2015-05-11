package cn.edu.sdu.online.fragement;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import cn.edu.sdu.online.adapter.RBookListAdapter;
import cn.edu.sdu.online.network.NetWork;
import cn.edu.sdu.online.newcurriculum.R;

public class ReborrowBookFragment extends Fragment {

	private View fragment;
	private NetWork network;
	private JSONArray books;
	private RBookListAdapter rbookadapter;
	private ListView booklistview;
	private PopupWindow pw;
	private MyHandler myHandler;
	private SharedPreferences acountpreferences;
	private SharedPreferences.Editor editor;
	private Button loginbutton, chooseallbutton, chooseothersbutton,
			cancelbutton, reborrowautobutton, confrimbutton;
	private int loginstate = 0;
	private String username, password;
	private String result;
	private BookListLongClickListener listclicklistener;
	private ButtonListener buttonlistener;
	private static final int LOGIN_SUCCESS = 0x111;
	private static final int LOGIN_FEILD = 0x120;
	private static final int START = 0x112;
	private static final int SHOWMESSAGE = 0x201;
	private static final int NO_BOOKS = 0x000;
	private boolean loginState = false;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		fragment = inflater.inflate(R.layout.activity_reborrowbook, container,
				false);

		findView();

		init();

		setContent();

		autoLogin();

		return fragment;
	}

	private void findView() {

		booklistview = (ListView) fragment.findViewById(R.id.reborrowbooklist);

		loginbutton = (Button) fragment.findViewById(R.id.rebloginbutton);

		chooseallbutton = (Button) fragment
				.findViewById(R.id.reborrowchooseall);

		chooseothersbutton = (Button) fragment
				.findViewById(R.id.reborrowchooseothers);

		cancelbutton = (Button) fragment.findViewById(R.id.reborrowcancel);

		reborrowautobutton = (Button) fragment
				.findViewById(R.id.reborrowallbutton);

		confrimbutton = (Button) fragment.findViewById(R.id.reborrowconfrim);
	}

	private void init() {
		books = new JSONArray();

		rbookadapter = new RBookListAdapter(getActivity()
				.getApplicationContext(), books);

		myHandler = new MyHandler();

		listclicklistener = new BookListLongClickListener();

		buttonlistener = new ButtonListener();
	}

	private void setContent() {
		booklistview.setAdapter(rbookadapter);

		booklistview.setOnItemClickListener(listclicklistener);

		acountpreferences = getActivity().getSharedPreferences("acount",
				getActivity().getApplicationContext().MODE_PRIVATE);

		editor = acountpreferences.edit();

		loginbutton.setOnClickListener(buttonlistener);

		chooseallbutton.setOnClickListener(buttonlistener);

		chooseothersbutton.setOnClickListener(buttonlistener);

		cancelbutton.setOnClickListener(buttonlistener);

		confrimbutton.setOnClickListener(buttonlistener);
	}

	private void autoLogin() {
		if (acountpreferences.getBoolean("has", false)) {
			// 自动登录，开始查询所接书籍
			// 登录图书馆~
			String username = acountpreferences.getString("username", null);
			String password = acountpreferences.getString("password", null);
			new ReBorrowLoginThread(username, password).start();
		}
	}

	class MyHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			case LOGIN_SUCCESS:
				loginbutton.setText("登录成功，点击注销");
				loginState = true;
				rbookadapter.notifyDataSetChanged();
				break;
			case LOGIN_FEILD:
				ShowMessage("登录失败,用户名或密码错误");
				break;
			case START:
				new ReBorrowLoginThread(username, password).start();
				ShowMessage(result);
				break;
			case SHOWMESSAGE:
				ShowMessage("请选择需要续借的图书");
				break;
			case NO_BOOKS:
				ShowMessage("同学~你还没有借书哦~");
				break;
			}
		}
	}

	class BookListLongClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> arg0, View view, int position,
				long arg3) {
			// TODO Auto-generated method stub
			CheckBox checkbox = (CheckBox) view
					.findViewById(R.id.rbookcheckbox);
			checkbox.setChecked(true);
		}

	}

	class ReBorrowLoginThread extends Thread {
		private String username;
		private String password;

		public ReBorrowLoginThread(String username, String password) {
			// TODO Auto-generated constructor stub
			this.username = username;
			this.password = password;
		}

		@Override
		public void run() {

			network = new NetWork(getActivity().getApplicationContext());
			JSONObject object = new JSONObject();
			try {
				object.put("requesttype", "0");
				object.put("username", username);
				object.put("password", password);
				books = network.NetWorkParams(object);
				if (books == null) {
					myHandler.sendEmptyMessage(NO_BOOKS);
				} else if (books.getJSONObject(0).optBoolean("state")) {
					myHandler.sendEmptyMessage(LOGIN_FEILD);
				} else {
					Log.i("ReBorrowLoginThread", books.toString());
					rbookadapter.setBooks(books);
					myHandler.sendEmptyMessage(LOGIN_SUCCESS);
				}

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	class ReborrowThread extends Thread {

		private JSONArray rbooksArray;

		public ReborrowThread(JSONArray rbooksArray) {
			this.rbooksArray = rbooksArray;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			network = new NetWork(getActivity().getApplicationContext());
			JSONObject object = new JSONObject();
			try {
				object.put("requesttype", "1");
				object.put("id", rbooksArray.toString());
				JSONArray message = network.NetWorkParams(object);
				result = analysisData(message);
				myHandler.sendEmptyMessage(START);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			super.run();
		}
	}

	private String analysisData(JSONArray message) {
		String result = "";
		int x = 0, y = 0, z = 0, w = 0;
		try {
			int length = message.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = message.getJSONObject(i);
				char r = object.toString().charAt(2);
				switch (r) {
				case 'x':
					x++;
					break;
				case 'y':
					y++;
					break;
				case 'z':
					z++;
					break;
				case 'w':
					w++;
					break;
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.e("analysisData", "JSON格式错误");
		}
		if (x > 0) {
			result = result + "续借成功：" + x + "本。\r\n";
		}
		if (y > 0) {
			result = result + "未到续借时间：" + y + "本。\r\n";
		}
		if (z > 0) {
			result = result + "超出最大续借次数：" + z + "本。\r\n";
		}
		if (w > 0) {
			result = "图书超期！\r\n 续借失败！";
		}
		return result;
	}

	class ButtonListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.rebloginbutton:
				switch (loginstate) {
				case 0:
					ShowPopupWindow();
					break;
				case 1:
					loginstate = 0;
					rbookadapter.setBooks(null);
					loginbutton.setText("点击登录");
					loginState = false;
					rbookadapter.notifyDataSetChanged();
					break;
				}
				break;
			case R.id.reborrowconfrim:
				if (loginState)
					confrimreborrow();
				break;
			case R.id.reborrowcancel:
				choosecancel();
				break;
			case R.id.reborrowchooseothers:
				chooseothers();
				break;
			case R.id.reborrowchooseall:
				chooseall();
				break;
			case R.id.reborrowallbutton:
				if (loginState)
					reBorrowAll();
				break;
			}

		}
	}

	private void confrimreborrow() {
		int num = booklistview.getChildCount();
		JSONArray rBooksArray = new JSONArray();
		if (num > 0)
			for (int i = 0; i < num; i++) {
				CheckBox checkbox = (CheckBox) booklistview.getChildAt(i)
						.findViewById(R.id.rbookcheckbox);
				if (checkbox.isChecked()) {
					TextView tv = (TextView) booklistview.getChildAt(i)
							.findViewById(R.id.rbookid);
					JSONObject book = new JSONObject();
					try {
						book.put("id", tv.getText().toString());
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						Log.i("confrimreborrow",
								"tv.getText().toString()==null");
						e.printStackTrace();
					}
					rBooksArray.put(book);
				}
			}
		if (rBooksArray == null) {
			myHandler.sendEmptyMessage(SHOWMESSAGE);
		} else {
			ReborrowThread reThread = new ReborrowThread(rBooksArray);
			reThread.start();
		}
	}

	private void reBorrowAll() {
		chooseall();
		confrimreborrow();
	}

	private void chooseall() {

		int num = booklistview.getChildCount();
		if (num > 0)
			for (int i = 0; i < num; i++) {
				CheckBox checkbox = (CheckBox) booklistview.getChildAt(i)
						.findViewById(R.id.rbookcheckbox);
				checkbox.setChecked(true);
			}
	}

	private void choosecancel() {
		int num = booklistview.getChildCount();
		if (num > 0)
			for (int i = 0; i < num; i++) {
				CheckBox checkbox = (CheckBox) booklistview.getChildAt(i)
						.findViewById(R.id.rbookcheckbox);
				checkbox.setChecked(false);
			}
	}

	private void chooseothers() {
		int num = booklistview.getChildCount();
		if (num > 0)
			for (int i = 0; i < num; i++) {
				CheckBox checkbox = (CheckBox) booklistview.getChildAt(i)
						.findViewById(R.id.rbookcheckbox);
				if (checkbox.isChecked()) {
					checkbox.setChecked(false);
				} else {
					checkbox.setChecked(true);
				}
			}
	}

	public void login(String username, String password, boolean b) {

		if (username.equals("") && password.equals("")) {
			ShowMessage("用户名或密码不能为空");
		} else {
			editor.putBoolean("has", b);
			editor.putString("username", username);
			editor.putString("password", username);
			new ReBorrowLoginThread(username, password).start();
		}
	}

	private void ShowPopupWindow() {
		Context context = getActivity().getApplicationContext();
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		final View vPopupWindow = inflater.inflate(
				R.layout.reborrowbook_dialog_login, null, false);
		pw = new PopupWindow(vPopupWindow, LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT, true);
		pw.setOutsideTouchable(true);
		Drawable drawable = new ColorDrawable(Color.TRANSPARENT);
		vPopupWindow.setBackgroundDrawable(drawable);
		Button confrimbutton = (Button) vPopupWindow
				.findViewById(R.id.Dialog_ConfrimButton);
		confrimbutton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				EditText getun = (EditText) vPopupWindow
						.findViewById(R.id.rloginun);
				EditText getpwd = (EditText) vPopupWindow
						.findViewById(R.id.rloginpwd);
				CheckBox checkbox = (CheckBox) vPopupWindow
						.findViewById(R.id.hasacountcheckbox);
				username = getun.getText().toString();
				password = getpwd.getText().toString();
				if (username.equals("") && password.equals("")) {
					ShowMessage("账户或密码不能为空");
				} else {
					login(username, password, checkbox.isChecked() ? true
							: false);
					pw.dismiss();
					loginstate = 1;
				}

			}
		});

		Button cancelbutton = (Button) vPopupWindow
				.findViewById(R.id.Dialog_CancelButton);
		cancelbutton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				pw.dismiss();
			}
		});

		pw.showAtLocation(booklistview, Gravity.CENTER, 0, -180);
	}

	protected void ShowMessage(String msg) {
		// TODO Auto-generated method stub
		Toast.makeText(getActivity().getApplicationContext(), msg,
				Toast.LENGTH_SHORT).show();
	}

}
