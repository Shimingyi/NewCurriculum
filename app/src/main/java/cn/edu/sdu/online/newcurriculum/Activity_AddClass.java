package cn.edu.sdu.online.newcurriculum;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import cn.edu.sdu.online.app.Main;
import cn.edu.sdu.online.modal.Lesson;
import cn.edu.sdu.online.view.ClassTimeView;

/**
 * Created by Hao_S on 2015/4/15.
 */

public class Activity_AddClass extends Activity {
    private final String TAG = "Activity_AddClass";
    private TextView addTimeViewTV;
    private EditText className, classLocation;
    private Button confrimButton, cancelButton;
    private LinearLayout timeLinearLayout;
    private final int ADDVIEW = 0x001;
    private MyHandler myHandler;
    private List<Lesson> lessonList;
    private ViewOnClickListener viewOnClickListener;

    private ActionBar ActionBar;
    private int indexCaidan = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_curriculum);

        findView();//注册组件

        init();//实例化要用的对象

        setListener();//添加监听
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        ActionBar = getActionBar();
        ActionBar.setBackgroundDrawable(getResources().getDrawable(
                R.color.actionbarbg));
        return super.onCreateOptionsMenu(menu);
    }



    private void findView() {
        addTimeViewTV = (TextView) findViewById(R.id.add_class_addTimeView);
        className = (EditText) findViewById(R.id.add_class_et_name);
        classLocation = (EditText) findViewById(R.id.add_class_et_location);
        timeLinearLayout = (LinearLayout) findViewById(R.id.add_class_time_ll);
        cancelButton = (Button) findViewById(R.id.add_class_bt_cancel);
        confrimButton = (Button) findViewById(R.id.add_class_bt_confrim);
    }

    private void init() {
        myHandler = new MyHandler();
        lessonList = Main.getApp().getCurriculumList();
        viewOnClickListener = new ViewOnClickListener();
    }

    private void setListener() {
        addTimeViewTV.setOnClickListener(viewOnClickListener);
        cancelButton.setOnClickListener(viewOnClickListener);
        confrimButton.setOnClickListener(viewOnClickListener);
    }

    private void ShowToast(String message) {
        Toast.makeText(Activity_AddClass.this, message, Toast.LENGTH_SHORT).show();
    }

    class ViewOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.add_class_addTimeView:
                    myHandler.sendEmptyMessage(ADDVIEW);
                    break;
                case R.id.add_class_bt_cancel:
                    Activity_AddClass.this.finish();
                    break;
                case R.id.add_class_bt_confrim:
                    int index = timeLinearLayout.getChildCount();
                    String classNameStr = className.getText().toString().trim();
                    String classLocationStr = classLocation.getText().toString().trim();
                    if ((!classNameStr.equals("")) && (!classLocationStr.equals(""))) {
                        for (int i = 0; i < index; i++) {
                            ClassTimeView classTimeView = (ClassTimeView) timeLinearLayout.getChildAt(i);
                            int weekInt = ((Spinner) classTimeView.findViewById(R.id.add_class_spinner_week)).getSelectedItemPosition() + 1;
                            int timeInt = ((Spinner) classTimeView.findViewById(R.id.add_class_spinner_time)).getSelectedItemPosition() + 1;
                            Log.i(TAG, "weekInt:"+weekInt+":timeInt"+timeInt);
                            Lesson lesson = new Lesson();
                            lesson.setClassName(classNameStr);
                            lesson.setClassPlace(classLocationStr);
                            lesson.setClassDayOfWeek(weekInt);
                            lesson.setClassDayOfTime(timeInt);
                            lessonList.add(lesson);
                        }
                        Log.i(TAG, "lessonList.length:"+lessonList.size());
                        Main.getApp().setCurriculumArray(lessonList);
                        ShowToast("添加成功！");
                        Activity_AddClass.this.finish();
                    } else {
                        ShowToast("课程信息不完整！");
                    }
                    break;
            }
        }
    }

    class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case ADDVIEW:
                    ClassTimeView classTimeView = new ClassTimeView(Activity_AddClass.this);
                    classTimeView.setPadding(0,0,0,10);
                    timeLinearLayout.addView(classTimeView);
                    indexCaidan++;
                    if(indexCaidan==3) {
                        ShowToast("卧槽，这门课一周上三次，够苦逼");
                    }
                    if(indexCaidan ==7) {
                        ShowToast("同学，你不要玩了……");
                    }
                    break;
            }
        }
    }

}
