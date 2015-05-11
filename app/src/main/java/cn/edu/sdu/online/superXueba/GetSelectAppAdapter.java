package cn.edu.sdu.online.superXueba;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import cn.edu.sdu.online.app.Main;
import cn.edu.sdu.online.newcurriculum.R;

public class GetSelectAppAdapter extends BaseAdapter {
	ArrayList<AppInfo> list;
	LayoutInflater inflater;
	ArrayList<String> paklist = new ArrayList<String>();

	public GetSelectAppAdapter(Context context, ArrayList<AppInfo> list) {
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
		// TODO Auto-generated method stub
		convertView = inflater.inflate(R.layout.select_list_item, null);
		ImageView icon = (ImageView) convertView.findViewById(R.id.select_icon);
		TextView name = (TextView) convertView.findViewById(R.id.select_name);
		AppInfo app = list.get(position);
		name.setText(app.getAppLabel());
		icon.setImageDrawable(app.getAppIcon());
		paklist.add(app.getPkgName());
		Main.getApp().setAppPackageName("APPPACKAGENAME", paklist);
		return convertView;
	}

}
