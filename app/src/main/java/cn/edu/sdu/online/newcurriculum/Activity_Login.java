package cn.edu.sdu.online.newcurriculum;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import cn.edu.sdu.online.app.Main;
import cn.edu.sdu.online.network.NetWork;

public class Activity_Login extends Activity {
    private Button loginButton;// 登录按钮
    private EditText username, password;
    private LoginThread loginthread;
    private Main app;
    private Display display;
    private int screenWidth;
    private ProgressDialog progressDialog;
    private MyHandler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        app = Main.getApp();
        handler = new MyHandler();
        init();
        loginButton.setMinWidth((int) (screenWidth * 0.7));
    }

    @SuppressWarnings("deprecation")
    private void init() {
        loginButton = (Button) findViewById(R.id.login_Button);
        username = (EditText) findViewById(R.id.login_sid);
        password = (EditText) findViewById(R.id.login_pwd);

        loginButton.setOnClickListener(new ButtonListener());
        display = getWindowManager().getDefaultDisplay();
        screenWidth = display.getWidth();
    }


    // 对按钮的监听类
    public class ButtonListener implements OnClickListener {
        @Override
        public void onClick(View arg0) {
            switch (arg0.getId()) {
                // 暂时功能为转到feedback界面
                case R.id.login_Button:// 登录按钮被点击
                    String un = username.getText().toString();
                    String pwd = password.getText().toString();
                    if (un.equals("") && pwd.equals("")) {
                        showMessage("学号或密码为空");
                    } else if (isOpenNetwork()) {
                        app.clearAll();
                        progressDialog = ProgressDialog.show(Activity_Login.this,
                                "", "登录中……");
                        loginthread = new LoginThread(un, pwd);
                        loginthread.start();
                        break;
                    } else {
                        showMessage("无网络连接    (〜￣△￣)〜");
                    }
            }
        }
    }

    class LoginThread extends Thread {
        private String un, pwd;
        private NetWork network;
        private JSONObject logindata;
        private int b; // 登录状态

        private LoginThread(String un, String pwd) {
            this.un = un;
            this.pwd = pwd;
        }

        @Override
        public void run() {
            network = new NetWork(Activity_Login.this);
            logindata = new JSONObject();
            try {
                logindata.put("Type", "getInformation");
                logindata.put("username", un);
                logindata.put("password", pwd);
                try {
                    b = network.LoginNetWork(logindata);
                } catch (ClientProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                switch (b) {
                    case 0://登录成功
                        app.getDataStore().edit().putBoolean("login", true)
                                .commit();
                        app.getDataStore().edit().putString("stuid", un).commit();
                        app.getDataStore().edit().putString("password", pwd)
                                .commit();
                        progressDialog.dismiss();
                        Activity_Login.this.finish();
                        break;
                    case 1://用户名密码错误
                        Message message = Message.obtain();
                        message.what = 1;
                        handler.sendMessage(message);
                        break;
                    case 2: // 其他错误
                        Message message2 = Message.obtain();
                        message2.what = 2;
                        handler.sendMessage(message2);
                        break;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            super.run();
        }
    }

    private class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    progressDialog.dismiss();
                    showMessage("登录失败，用户名或密码错误");
                    break;
                case 2:
                    progressDialog.dismiss();
                    showMessage("登录失败  \r\n 　╯▽╰　");
                    break;
            }

        }
    }

    private void showMessage(String message) {
        // TODO Auto-generated method stub
        Toast.makeText(Activity_Login.this, message, Toast.LENGTH_SHORT).show();
    }

    private boolean isOpenNetwork() {
        ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connManager.getActiveNetworkInfo() != null) {
            return connManager.getActiveNetworkInfo().isAvailable();
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
        super.onDestroy();
    }

}
