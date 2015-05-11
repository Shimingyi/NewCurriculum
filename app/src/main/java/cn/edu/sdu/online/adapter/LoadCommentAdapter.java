package cn.edu.sdu.online.adapter;

import java.util.ArrayList;
import cn.edu.sdu.online.modal.SignComment;
import cn.edu.sdu.online.newcurriculum.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class LoadCommentAdapter extends BaseAdapter{
	ArrayList<SignComment>list;
	LayoutInflater inflater;
	public LoadCommentAdapter(Context context,ArrayList<SignComment>list){
		this.list = list;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		convertView = inflater.inflate(R.layout.list_comment_rank, null);
		TextView textname = (TextView) convertView
				.findViewById(R.id.username);
		TextView texttime = (TextView) convertView
				.findViewById(R.id.creatdate);
		TextView textcomment = (TextView) convertView
				.findViewById(R.id.mood);
		textname.setText(list.get(position).getUsername());
		texttime.setText(String.valueOf(list.get(position).getTime()));
		textcomment.setText(list.get(position).getComment());
		return convertView;
	}
}
