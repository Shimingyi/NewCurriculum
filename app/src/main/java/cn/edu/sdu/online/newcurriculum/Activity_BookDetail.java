package cn.edu.sdu.online.newcurriculum;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import cn.edu.sdu.online.network.NetWork;

public class Activity_BookDetail extends Activity {
	private TableLayout locationtable;
	private TextView bookname;
	private TextView bookid;
	private TextView booktotal;
	private TextView bookcanborrow;
	private TextView bookpublisher;
	private TextView publisher;
	private String[] book;
	private JSONArray locationjson;
	private NetWork network;
	private MyHandler message;
	private ActionBar actionBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bookdetail);
		init();

	}

	private void init() {
		Intent intent = getIntent();
		book = intent.getStringArrayExtra("book");
		message = new MyHandler();
		NetWorkdescribe thread = new NetWorkdescribe(book[4]);
		if (book[2].equals("馆藏复本：0")) {
			
		} else {
			thread.start();
		}
		locationtable = (TableLayout) findViewById(R.id.bookdetail_location);
		bookname = (TextView) findViewById(R.id.bookdetail_name);
		bookid = (TextView) findViewById(R.id.bookdetail_id);
		booktotal = (TextView) findViewById(R.id.bookdetail_total);
		bookcanborrow = (TextView) findViewById(R.id.bookdetail_canborrow);
		bookpublisher = (TextView) findViewById(R.id.bookdetail_publisher);
		publisher = (TextView) findViewById(R.id.bookdetail_p);
		bookname.setText(book[0]);
		bookid.setText(book[1]);
		booktotal.setText(book[2]);
		bookcanborrow.setText(book[3]);
		publisher.setVisibility(4);
	}

	@SuppressLint("HandlerLeak") 
	class MyHandler extends Handler {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			if (msg.what == 0x1010) {
				publisher.setVisibility(0);
				int length = locationjson.length() - 1;
				for (int i = 0; i < length; i++) {
					JSONObject object = new JSONObject();
					String locationstring = null, statestring = null;
					try {
						object = locationjson.getJSONObject(i);
						locationstring = object.getString("location");
						statestring = object.getString("state");
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					TextView location = new TextView(Activity_BookDetail.this);
					TextView state = new TextView(Activity_BookDetail.this);
					state.setPadding(50, 0, 0, 0);
					TableRow locationrow = new TableRow(Activity_BookDetail.this);
					location.setText(locationstring);
					state.setText(statestring);
					locationrow.addView(location);
					locationrow.addView(state);
					locationtable.addView(locationrow);
				}
				try {
					bookpublisher.setText(locationjson.getJSONObject(length)
							.getString("pulisher"));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					Log.i("MyHandler", "bookpublisher");
				}
			}
		}
	}

	private class NetWorkdescribe extends Thread {
		private String url;

		public NetWorkdescribe(String url) {
			this.url = url;
		}

		@Override
		public void run() {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("requesttype", "3"));
			params.add(new BasicNameValuePair("url", url));
			network = new NetWork(Activity_BookDetail.this);
			try {
				locationjson = network.GetResultFromNet(params);
				if (locationjson == null) {

				} else {
					Log.i("NetWorkdescribe", locationjson.toString());
					message.sendEmptyMessage(0x1010);
				}
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
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
