package cn.edu.sdu.online.adapter;

import java.util.ArrayList;
import cn.edu.sdu.online.modal.Answer;
import cn.edu.sdu.online.modal.Question;
import cn.edu.sdu.online.newcurriculum.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class LoadAnswerAdapter extends BaseAdapter {

	Question ques;
	int count;
	LayoutInflater inflater;
	ArrayList<Answer> list;

	public LoadAnswerAdapter(ArrayList<Answer> list, Context context) {
		inflater = LayoutInflater.from(context);
		this.list = list;
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
		convertView = inflater.inflate(R.layout.list_otheranser, null);
		TextView textname = (TextView) convertView.findViewById(R.id.username);
		TextView texttime = (TextView) convertView.findViewById(R.id.creatdate);
		TextView textcomment = (TextView) convertView.findViewById(R.id.mood);
		textname.setText(list.get(position).getUserid());
		texttime.setText(String.valueOf(list.get(position).getTime()));
		textcomment.setText(list.get(position).getAnswer());
		return convertView;
	}

}
