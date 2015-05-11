package cn.edu.sdu.online.fragement;

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
import android.content.Intent;
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
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import cn.edu.sdu.online.adapter.SBookListAdapter;
import cn.edu.sdu.online.network.NetWork;
import cn.edu.sdu.online.newcurriculum.Activity_BookDetail;
import cn.edu.sdu.online.newcurriculum.R;

public class SearchBookFragment extends Fragment {
	private View fragment;
	private NetWork network;
	private JSONArray books;
	private SBookListAdapter bookAdapter;
	private Spinner searchType;
	private Button searchBookButton;
	private EditText bookKeyword;
	private ListView bookListview;
	private listItemListener listListener;
	private MyHandler myhandler;
	private TextView searchMessage;
	private View moreView;
	private int lastItem;
	private int count;
	private int bookpage;
	private int cpage = 1;
	private int booknum;
	private addThread addthread;
	private static final int NOT_FIND = 0x110;
	private static final int SEARCH_SUCCESS = 0x111;
	private static final int ADD_MORE = 0x112;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		fragment = inflater.inflate(R.layout.searchbook_fragment, container,
				false);

		searchType = (Spinner) fragment.findViewById(R.id.searchbooktype);

		bookKeyword = (EditText) fragment.findViewById(R.id.searchkeyword);

		searchBookButton = (Button) fragment
				.findViewById(R.id.searchbookbutton);

		bookListview = (ListView) fragment.findViewById(R.id.booklist);

		searchMessage = (TextView) fragment.findViewById(R.id.searchmessage);

		moreView = getActivity().getLayoutInflater().inflate(
				R.layout.listitem_searchbookrefresh, null);

		books = new JSONArray();

		myhandler = new MyHandler();

		listListener = new listItemListener();

		bookAdapter = new SBookListAdapter(getActivity()
				.getApplicationContext(), books);

		moreView.setVisibility(View.GONE);

		bookListview.addFooterView(moreView);

		bookListview.setAdapter(bookAdapter);

		bookListview.setOnItemClickListener(listListener);

		bookListview.setOnScrollListener(new SListListener());

		searchBookButton.setOnClickListener(new OnClickListener() {

            @SuppressLint("NewApi")
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                String keyword = bookKeyword.getText().toString().trim();
                int type = searchType.getSelectedItemPosition();
                new SearchThread(type, keyword)
                        .start();
            }
        });

		return fragment;
	}

	class listItemListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> arg0, View view, int position,
				long arg3) {
			// TODO Auto-generated method stub
			String[] book = new String[5];
			try {
				book[0] = books.getJSONObject(position).getString("title");
				book[1] = books.getJSONObject(position).getString("booknumber");
				book[2] = books.getJSONObject(position).getString("total");
				book[3] = books.getJSONObject(position).getString("canborrow");
				book[4] = books.getJSONObject(position).getString("detailurl");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Intent intent = new Intent(getActivity().getApplicationContext(),
					Activity_BookDetail.class);
			intent.putExtra("book", book);
			startActivity(intent);
		}

	}

	class MyHandler extends Handler {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			case NOT_FIND:
				searchMessage.setGravity(Gravity.CENTER);
				searchMessage.setText("暂无记录");
				searchMessage.setVisibility(View.VISIBLE);
				bookAdapter.books = null;
				bookAdapter.notifyDataSetChanged();
				break;
			case SEARCH_SUCCESS:
				cpage = 1;
				searchMessage.setGravity(Gravity.LEFT);
				searchMessage.setText("共找到" + booknum + "条记录");
				searchMessage.setVisibility(View.VISIBLE);
				count = bookAdapter.books.length();
				bookAdapter.notifyDataSetChanged();
				break;
			case ADD_MORE:
				count = bookAdapter.books.length();
				bookAdapter.notifyDataSetChanged();
				break;
			}
		}
	}

	class SearchThread extends Thread {
		private String keyword = null;
		private int searchtype;

		public SearchThread(int searchtype, String keyword) {
			// TODO Auto-generated constructor stub
			this.keyword = keyword;
			this.searchtype = searchtype;
		}

		@Override
		public void run() {
			Log.i("SearchThread", "searchtype:"+ searchtype);
			network = new NetWork(getActivity().getApplicationContext());
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("requesttype", "2"));
			params.add(new BasicNameValuePair("searchtype", "" + searchType));
			params.add(new BasicNameValuePair("keyword", keyword));
			try {
				books = network.GetResultFromNet(params);
				if (books == null) {
					myhandler.sendEmptyMessage(NOT_FIND);
				} else {
					bookAdapter.books = AnalysisJson(books);
					myhandler.sendEmptyMessage(SEARCH_SUCCESS);
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

	class addThread extends Thread {

		@Override
		public void run() {
			network = new NetWork(getActivity().getApplicationContext());
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("requesttype", "4"));
			params.add(new BasicNameValuePair("page", ++cpage + ""));
			try {
				JSONArray addbooks = network.GetResultFromNet(params);
				if (books == null) {
					myhandler.sendEmptyMessage(NOT_FIND);
				} else {
					bookAdapter.books = books = addArray(books, addbooks);
					myhandler.sendEmptyMessage(ADD_MORE);
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

	class SListListener implements OnScrollListener {

		@Override
		public void onScroll(AbsListView view, int firstVisibleItem,
				int visibleItemCount, int totalItemCount) {
			// TODO Auto-generated method stub
			lastItem = firstVisibleItem + visibleItemCount - 1;
		}

		@Override
		public void onScrollStateChanged(AbsListView view, int scrollState) {
			// TODO Auto-generated method stub
			if (lastItem == count && scrollState == this.SCROLL_STATE_IDLE
					&& cpage < bookpage) {
				moreView.setVisibility(View.VISIBLE);
				addthread = new addThread();
				addthread.start();
			} else {
				moreView.setVisibility(View.GONE);
			}
		}
	}

	private JSONArray AnalysisJson(JSONArray jsonarray) {
		int length = jsonarray.length();
		try {
			JSONObject object = jsonarray.getJSONObject(length - 1);
			bookpage = Integer.parseInt(object.get("pages").toString());
			booknum = Integer.parseInt(object.get("num").toString());
			JSONArray bookarray = new JSONArray();
			for (int i = 0; i < length - 1; i++) {
				bookarray.put(jsonarray.getJSONObject(i));
			}
			books = bookarray;
			return bookarray;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.i("AnalysisJson", "JSONObject获取失败");
			return null;
		}
	}

	private JSONArray addArray(JSONArray jsonarray0, JSONArray jsonarray1) {
		int length = jsonarray1.length();
		try {
			for (int i = 0; i < length; i++) {
				jsonarray0.put(jsonarray1.getJSONObject(i));
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonarray0;
	}
}
