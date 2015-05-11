package cn.edu.sdu.online.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import cn.edu.sdu.online.database.MessionDBHelper;
import cn.edu.sdu.online.modal.MessionMessageItem;
import cn.edu.sdu.online.newcurriculum.R;
import cn.edu.sdu.online.view.MessionSlideView;
import cn.edu.sdu.online.view.MessionSlideView.OnSlideListener;

public class MessionSlideAdapter extends BaseAdapter implements OnSlideListener {
	private Context context;
	private LayoutInflater mInflater;
	private List<MessionMessageItem> mMessageItems;
	private MessionSlideView mLastSlideViewWithStatusOn;// 判断是否已经滑动动
	private MessionDBHelper dbhelper;

	public MessionSlideAdapter(Context context, MessionDBHelper dbhelper,
			List<MessionMessageItem> mMessageItems) {
		super();
		this.context = context;
		this.mMessageItems = mMessageItems;
		this.dbhelper = dbhelper;
		mInflater = LayoutInflater.from(context);
	}

	class ViewHolder {
		public ImageView icon;
		public TextView msg;
		public ViewGroup deleteHolder;

		ViewHolder(View view) {
			icon = (ImageView) view.findViewById(R.id.daka_img_icon);
			msg = (TextView) view.findViewById(R.id.daka_tv_msg);
			deleteHolder = (ViewGroup) view.findViewById(R.id.daka_rl_holder);
		}
	}

	@Override
	public int getCount() {
		return dbhelper.getSize();
	}

	@Override
	public Object getItem(int position) {
		return mMessageItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		MessionSlideView slideView = (MessionSlideView) convertView;
		if (slideView == null) {
			View itemView = mInflater.inflate(R.layout.mession_list_item, null);
			slideView = new MessionSlideView(context);
			slideView.addContentView(itemView);
			holder = new ViewHolder(slideView);
			slideView.setOnSlideListener(this);
			slideView.setTag(holder);
		} else {
			holder = (ViewHolder) slideView.getTag();
		}
		MessionMessageItem item = mMessageItems.get(position);
		if (mMessageItems.get(position).getStatus() == 0) {
			holder.icon.setImageResource(R.drawable.daka_progress);
		} else {
			holder.icon.setImageResource(R.drawable.daka_task_ok);
		}
		holder.msg.setText(item.msg);

		return slideView;
	}

	@Override
	public void onSlide(View view, int status) {
		if (mLastSlideViewWithStatusOn != null
				&& mLastSlideViewWithStatusOn != view) {
			mLastSlideViewWithStatusOn.shrink();
		}
		if (status == SLIDE_STATUS_ON) {
			mLastSlideViewWithStatusOn = (MessionSlideView) view;
		}
	}

}
