package cn.edu.sdu.online.adapter;

import org.json.JSONArray;
import org.json.JSONException;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import cn.edu.sdu.online.newcurriculum.R;

public class RBookListAdapter extends BaseAdapter {
	private LayoutInflater listContainer;
	private JSONArray books = new JSONArray();

	private class ListItemView {
		public TextView title;
		public TextView bookid;
		public TextView bookdate;
		public CheckBox checkbox;
	}

	public RBookListAdapter(Context context, JSONArray books) {
		listContainer = LayoutInflater.from(context);
		this.setBooks(books);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if (getBooks() == null) {
			return 0;
		} else {
			return getBooks().length();
		}
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		try {
			return getBooks().getJSONObject(position);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		// TODO Auto-generated method stub
		/*
		 * ListItemView listitem = null; if (convertView == null) { listitem =
		 * new ListItemView(); convertView =
		 * listContainer.inflate(R.layout.bookitem, null); listitem.title =
		 * (TextView)convertView.findViewById(R.id.rbookname); listitem.bookid =
		 * (TextView)convertView.findViewById(R.id.rbookid); listitem.bookdate =
		 * (TextView)convertView.findViewById(R.id.rbookdate); } else { listitem
		 * = (ListItemView) convertView.getTag(); }
		 */

		ListItemView listitem = new ListItemView();
		convertView = listContainer.inflate(R.layout.listitem_reborrowbooklist, null);
		listitem.title = (TextView) convertView.findViewById(R.id.rbookname);
		listitem.bookid = (TextView) convertView.findViewById(R.id.rbookid);
		listitem.bookdate = (TextView) convertView.findViewById(R.id.rbookdate);
		listitem.checkbox = (CheckBox) convertView.findViewById(R.id.rbookcheckbox);
		
		try {
			listitem.title.setText(getBooks().getJSONObject(position).get("title")
					.toString());
			listitem.bookid.setText(getBooks().getJSONObject(position)
					.get("id").toString());
			listitem.bookdate.setText(getBooks().getJSONObject(position)
					.get("date").toString());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			Log.i("bookitemadapter", "JSONObject 出错");
			e.printStackTrace();
		}

		return convertView;
	}

	public JSONArray getBooks() {
		return books;
	}

	public void setBooks(JSONArray books) {
		this.books = books;
	}

}
