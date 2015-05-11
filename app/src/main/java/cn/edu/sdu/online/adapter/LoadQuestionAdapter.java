package cn.edu.sdu.online.adapter;

import java.util.ArrayList;
import java.util.List;
import cn.edu.sdu.online.app.Main;
import cn.edu.sdu.online.modal.MyHomework;
import cn.edu.sdu.online.modal.Question;
import cn.edu.sdu.online.newcurriculum.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class LoadQuestionAdapter extends BaseAdapter {
	LayoutInflater inflater;
List<Question>listitem;
	public LoadQuestionAdapter(Context context, List<Question> listItem) {
		this.listitem = listItem;
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listitem.size();
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
		convertView = inflater.inflate(R.layout.listitem_questionrelease, null);
		TextView username = (TextView) convertView.findViewById(R.id.username); // �û�ͷ��
		TextView creatdate = (TextView) convertView
				.findViewById(R.id.creatdate);// ʱ��
		TextView ask = (TextView) convertView.findViewById(R.id.mood);// ��������
		TextView answer = (TextView) convertView
				.findViewById(R.id.answer_hanzi);
		Question ques = listitem.get(position);
		username.setText(ques.getUserid());
		creatdate.setText(String.valueOf(ques.getTime()));
		ask.setText(ques.getBrief());
		answer.setText("回答("+ques.getAnswernum()+")");
		Main.getApp().getDataStore().edit()
		.putInt("flagaskoldest", listitem.get(listitem.size()-1).getQuesid())
		.commit();
		return convertView;
	}

}
