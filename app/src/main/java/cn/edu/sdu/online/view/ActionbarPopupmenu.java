package cn.edu.sdu.online.view;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import cn.edu.sdu.online.newcurriculum.Activity_About;
import cn.edu.sdu.online.newcurriculum.Activity_IndividualCenter;
import cn.edu.sdu.online.newcurriculum.Activity_Login;
import cn.edu.sdu.online.newcurriculum.Activity_ConsultCenter;
import cn.edu.sdu.online.newcurriculum.R;
import cn.edu.sdu.online.superXueba.StartActivity;

public class ActionbarPopupmenu extends PopupWindow implements OnClickListener {
	private ImageButton btnHead, btnTag, btnClose, btnSuperXueba, btnShare;
	private Context context;
	private Intent intent;

	public ActionbarPopupmenu(Context context) {
		// 获取传入的Context
		this.context = context;
		initPopupmenu();
	}

	private void initPopupmenu() {
		LayoutInflater inflater = LayoutInflater.from(context);
		View layout = inflater.inflate(R.layout.actionbar_popupmenu, null);
		setContentView(layout);

		// 获取popupwindow上的ImageButton并加监听器
		btnHead = (ImageButton) layout.findViewById(R.id.popupwindow_me);
		btnShare = (ImageButton) layout.findViewById(R.id.popupwindow_login);
		btnTag = (ImageButton) layout.findViewById(R.id.popupwindow_score);
		btnClose = (ImageButton) layout.findViewById(R.id.popupwindow_setting);
		btnSuperXueba = (ImageButton) layout
				.findViewById(R.id.popupwindow_super);
		btnHead.setOnClickListener(this);
		btnShare.setOnClickListener(this);
		btnTag.setOnClickListener(this);
		btnSuperXueba.setOnClickListener(this);
		btnClose.setOnClickListener(this);
		// 设置popupwindow大小自适应
		setHeight(android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
		setWidth(android.view.ViewGroup.LayoutParams.WRAP_CONTENT);

	}

	@Override
	public void onClick(View v) {
		// 按从上到下顺序排列
		switch (v.getId()) {
		case R.id.popupwindow_me:
			// TODO
			intent = new Intent(context, Activity_IndividualCenter.class);
			context.startActivity(intent);
			break;
		case R.id.popupwindow_login:
			// TODO
			intent = new Intent(context, Activity_Login.class);
			context.startActivity(intent);
			break;
		case R.id.popupwindow_score:
			intent = new Intent(context, Activity_ConsultCenter.class);
			context.startActivity(intent);
			// TODO
			break;
		case R.id.popupwindow_super:
			intent = new Intent(context, StartActivity.class);
			context.startActivity(intent);
			break;
		case R.id.popupwindow_setting:
			// TODO
			Intent intent = new Intent(context, Activity_About.class);
			context.startActivity(intent);
			break;
		default:
			break;
		}

	}
}
