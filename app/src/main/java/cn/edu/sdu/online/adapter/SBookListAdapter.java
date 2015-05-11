package cn.edu.sdu.online.adapter;

import org.json.JSONArray;
import org.json.JSONException;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import cn.edu.sdu.online.newcurriculum.R;

public class SBookListAdapter extends BaseAdapter {
	private LayoutInflater listContainer;
	public JSONArray books;

	private class ListItemView {
		public TextView title;
		public TextView bookId;
		public TextView bookNum;
		public TextView bookCanborrow;
	}

	public SBookListAdapter(Context context, JSONArray books) {
		listContainer = LayoutInflater.from(context);
		this.books = books;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if (books == null) {
			return 0;
		} else {
			return books.length();
		}
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		try {
			return books.getJSONObject(arg0);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		// TODO Auto-generated method stub
		/*
		 * ListItemView listitem = null; if (convertView == null) { listitem =
		 * new ListItemView(); convertView =
		 * listContainer.inflate(R.layout.bookitem, null); listitem.title =
		 * (TextView)convertView.findViewById(R.id.bookname); listitem.bookid =
		 * (TextView)convertView.findViewById(R.id.bookid); listitem.booknum =
		 * (TextView)convertView.findViewById(R.id.booknum);
		 * listitem.bookcanborrow =
		 * (TextView)convertView.findViewById(R.id.bookcanborrow); } else {
		 * listitem = (ListItemView) convertView.getTag(); }
		 */

		ListItemView listitem = new ListItemView();
		convertView = listContainer.inflate(R.layout.listitem_searchbooklist, null);
		listitem.title = (TextView) convertView.findViewById(R.id.sbookname);
		listitem.bookId = (TextView) convertView.findViewById(R.id.sbookid);
		listitem.bookNum = (TextView) convertView.findViewById(R.id.sbooknum);
		listitem.bookCanborrow = (TextView) convertView
				.findViewById(R.id.sbookcanborrow);

		try {
			listitem.title.setText(books.getJSONObject(position).get("title")
					.toString());
			listitem.bookId.setText(books.getJSONObject(position)
                    .get("booknumber").toString());
			listitem.bookNum.setText(books.getJSONObject(position).get("total")
					.toString());
			listitem.bookCanborrow.setText(books.getJSONObject(position)
					.get("canborrow").toString());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			Log.i("bookitemadapter", "JSONObject 出错");
			e.printStackTrace();
		}

		return convertView;
	}

}
