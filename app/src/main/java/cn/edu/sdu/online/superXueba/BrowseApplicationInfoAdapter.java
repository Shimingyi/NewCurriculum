package cn.edu.sdu.online.superXueba;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import cn.edu.sdu.online.newcurriculum.R;

//自定义适配器类，提供给listView的自定义view
public class BrowseApplicationInfoAdapter extends BaseAdapter {

	private List<AppInfo> mlistAppInfo = null;

	LayoutInflater infater = null;
	private static HashMap<Integer, Boolean> isSelected;

	public static HashMap<Integer, Boolean> getIsSelected() {
		return isSelected;
	}

	public static void setIsSelected(HashMap<Integer, Boolean> isSelected) {
		BrowseApplicationInfoAdapter.isSelected = isSelected;
	}

	public BrowseApplicationInfoAdapter(Context context, List<AppInfo> apps) {
		infater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mlistAppInfo = apps;
		isSelected = new HashMap<Integer, Boolean>();

		initDate();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mlistAppInfo.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mlistAppInfo.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	// 初始化isSelected的数据
	private void initDate() {
		for (int i = 0; i < mlistAppInfo.size(); i++) {
			getIsSelected().put(i, false);
		}
	}

	@Override
	public View getView(int position, View convertview, ViewGroup arg2) {
		View view = null;
		ViewHolder holder = null;
		if (convertview == null || convertview.getTag() == null) {
			view = infater.inflate(R.layout.listviewitem, null);
			holder = new ViewHolder(view);
			view.setTag(holder);
		} else {
			view = convertview;
			holder = (ViewHolder) convertview.getTag();
		}
		AppInfo appInfo = (AppInfo) getItem(position);
		holder.appIcon.setImageDrawable(appInfo.getAppIcon());
		holder.tvAppLabel.setText(appInfo.getAppLabel());
		holder.cb.setChecked(getIsSelected().get(position));
		// holder.tvPkgName.setText(appInfo.getPkgName());
		return view;
	}

	class ViewHolder {
		ImageView appIcon;
		TextView tvAppLabel;
		CheckBox cb;

		// TextView tvPkgName;

		public ViewHolder(View view) {
			this.appIcon = (ImageView) view.findViewById(R.id.appicon);
			this.tvAppLabel = (TextView) view.findViewById(R.id.appname);
			this.cb = (CheckBox) view.findViewById(R.id.item_cb);
			// this.tvPkgName = (TextView) view.findViewById(R.id.tvPkgName);
		}
	}
}