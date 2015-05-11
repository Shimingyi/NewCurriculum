package cn.edu.sdu.online.superXueba;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;
import cn.edu.sdu.online.newcurriculum.R;

public class StartActivity extends Activity {
	ListView list;
	TextView addapp, startXueba;
	ArrayList<AppInfo> applist;
	GetSelectAppAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.getapplist);
		list = (ListView) findViewById(R.id.selected_app);
		applist = new ArrayList<AppInfo>();
		adapter = new GetSelectAppAdapter(this, applist);
		list.setAdapter(adapter);
		addapp = (TextView) findViewById(R.id.add_app);
		addapp.setOnClickListener(new addListener());
		startXueba = (TextView) findViewById(R.id.startxueba_button);
		startXueba.setOnClickListener(new startXueba());
	}

	class startXueba implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(StartActivity.this, SetTimeActivity.class);
			StartActivity.this.startActivity(intent);
		}

	}

	class addListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
			intent.setClass(StartActivity.this, addAppActivity.class);
			startActivityForResult(intent, 1000);
		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		ArrayList<AppInfo> list;
		super.onActivityResult(requestCode, resultCode, data);
		switch (resultCode) {
		case RESULT_OK:
			Bundle b = data.getExtras();
			list = b.getParcelableArrayList("list");
			for (int i = 0; i < list.size(); i++) {
				applist.add(list.get(i));
			}
			adapter.notifyDataSetChanged();
			break;

		default:
			break;
		}
	}
}
