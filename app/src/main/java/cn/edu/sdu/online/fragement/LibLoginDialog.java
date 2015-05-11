package cn.edu.sdu.online.fragement;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import cn.edu.sdu.online.newcurriculum.R;

public class LibLoginDialog extends DialogFragment {

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		LayoutInflater inf = getActivity().getLayoutInflater();
		View dialoglayout = inf.inflate(R.layout.reborrowbook_dialog_login, null);
		final EditText un = (EditText) dialoglayout.findViewById(R.id.rloginun);
		final EditText pwd = (EditText) dialoglayout
				.findViewById(R.id.rloginpwd);
		final CheckBox hasacount = (CheckBox) dialoglayout
				.findViewById(R.id.hasacountcheckbox);
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle("登录到我的图书馆").setView(dialoglayout)
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						String username = un.getText().toString().trim();
						String password = pwd.getText().toString().trim();
						if (username.equals("") && password.equals("")) {
							ShowMessage("账户或密码不能为空");
						} else {
							
						}

					}

				}).setNegativeButton("取消", null);
		return builder.create();
	}



	private void ShowMessage(String msg) {
		// TODO Auto-generated method stub
		Toast.makeText(getActivity().getApplicationContext(), msg,
				Toast.LENGTH_SHORT);
	}


}
