package cn.edu.sdu.online.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import cn.edu.sdu.online.app.Main;
import cn.edu.sdu.online.modal.SignRelease;
import cn.edu.sdu.online.newcurriculum.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.sax.StartElementListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class LoadRankAdapter extends BaseAdapter {

	private Activity activity;
	// private ArrayList<HashMap<String, Object>> listDate;
	List<SignRelease> listDate;
	private static LayoutInflater inflater = null;

	public LoadRankAdapter(Activity a, List<SignRelease> listDate) {
		activity = a;
		this.listDate = listDate;
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public int getCount() {
		return listDate.size();
	}

	public Object getItem(int position) {
		return position;
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View vi = convertView;
		if (convertView == null)
			vi = inflater.inflate(R.layout.list_item, null);
		TextView username = (TextView) vi.findViewById(R.id.username); // 用户头像
		TextView creatdate = (TextView) vi.findViewById(R.id.creatdate);// 时间
		TextView mood = (TextView) vi.findViewById(R.id.mood);// 心情内容
		TextView zan = (TextView) vi.findViewById(R.id.zan_hanzi);
		TextView pinglun = (TextView) vi.findViewById(R.id.pinglun_hanzi);
		SignRelease sign = listDate.get(position);
		username.setText(sign.getName());
		creatdate.setText(String.valueOf(sign.getTime()));
		mood.setText(sign.getReleaserank());
		zan.setText("赞（" + sign.getZan() + "）");
		pinglun.setText("评论（" + sign.getNumcomment() + "）");
		System.out.println(listDate.get(listDate.size()-1).getRankid()+"ooooooooo");
		Main.getApp().getDataStore().edit()
				.putInt("flagrankidoldest", listDate.get(listDate.size()-1).getRankid())
				.commit();

		return vi;
	}

}
