package cn.edu.sdu.online.newcurriculum;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import cn.edu.sdu.online.adapter.LoadCurriculumnAdapter;
import cn.edu.sdu.online.app.Main;
import cn.edu.sdu.online.listener.OmniScrollListener;
import cn.edu.sdu.online.modal.Lesson;
import cn.edu.sdu.online.view.OmniScrollView;
import cn.edu.sdu.online.view.PassiveHScrollView;
import cn.edu.sdu.online.view.PassiveVScrollView;
import cn.edu.sdu.online.view.composerLayout;

public class Activity_curriculumDetail extends FragmentActivity implements
        OmniScrollListener {

    private final static String TAG = "Activity_curriculumDetail";

    private ActionBar ActionBar;
    private GridView gridView;
    private PassiveHScrollView upBoard;
    private PassiveVScrollView leftBoard;
    private OmniScrollView mainBoard;

    private int gridheight;
    private int gridwidth;
    private Main app;

    private String[] classNameList;
    private List<Lesson> lessonList;
    private Drawable[] colors;
    private int[] intColors;
    private LoadCurriculumnAdapter adapter;
    private int offset;

    private composerLayout composerLayout;

    private CurriculumMessage message;

    private final int MyHandlerMsg_remove = 0x0001;
    private final int MyHandlerMsg_refresh = 0x0002;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_curriculum);
        app = Main.getApp();
        colors = app.getColors();
        intColors = app.getIntColors();
        app.getDataStore().edit().remove("flagrankidlatest")
                .remove("flagrankidoldest").commit();
        classNameList = Main.getApp().getCurriculumArray();
        lessonList = Main.getApp().getCurriculumList();


        gridwidth = (int) (getResources().getDisplayMetrics().density * 80 + 0.5f);
        gridheight = (int) (getResources().getDisplayMetrics().density * 120 + 0.5f);

        upBoard = (PassiveHScrollView) findViewById(R.id.up_board);
        leftBoard = (PassiveVScrollView) findViewById(R.id.left_board);
        mainBoard = (OmniScrollView) findViewById(R.id.main_board);
        upBoard.setHorizontalScrollBarEnabled(false);
        leftBoard.setVerticalScrollBarEnabled(false);
        mainBoard.setScrollViewListener(this);

        gridView = (GridView) findViewById(R.id.dataGrid);
        gridView.setOnItemClickListener(new gridListener());

        adapter = new LoadCurriculumnAdapter(this, lessonList, classNameList, colors,
                intColors, gridheight, gridwidth);

        gridView.setAdapter(adapter);
        // storeclassNum();
        // activity.setActionBarListener(this);
        // activity.invalidateOptionsMenu();

//        composerLayout = (composerLayout) findViewById(R.id.curriculum_composer);
//        composerLayoust.init(new int[] {R.drawable.curriculum_del_bt_icon, R.drawable.curriculum_add_bt_icon},
//                R.drawable.curriculum_composer_icon_unpressed, R.drawable.curriculum_composer_icon_pressed, composerLayout.RIGHTBOTTOM, 180, 300);
    }

    @Override
    protected void onResume() {
        super.onResume();
        new Myhandler(new Lesson()).sendEmptyMessage(MyHandlerMsg_refresh);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // TODO Auto-generated method stub
        getMenuInflater().inflate(R.menu.curriculum_menu, menu);

        ActionBar = getActionBar();
        // 取消icon
        ActionBar.setDisplayUseLogoEnabled(false);
        ActionBar.setDisplayShowHomeEnabled(false);
        // 设置ActionBar题目
        String name = Main.getApp().getDataStore()
                .getString("stuname", "Friend");
        ActionBar.setTitle("Hello" + "," + name);
        // 设置ActionBar背景颜色
        Drawable backgroundcolor = getResources().getDrawable(
                R.color.actionbarbg);
        ActionBar.setBackgroundDrawable(backgroundcolor);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.actionbar_curriculum_add:
                Intent intent= new Intent(Activity_curriculumDetail.this, Activity_AddClass.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onScrollChanged(OmniScrollView scrollView, int x, int y,
                                int oldx, int oldy) {
        upBoard.scrollTo(x, 0);
        leftBoard.scrollTo(0, y);

    }

    // @Override
    // public boolean onMenuItemClick(MenuItem item) {
    // Intent intent = new Intent();
    // intent.setClass(this, StartActivity.class);
    // getActivity().startActivity(intent);
    // return true;
    // }

    // @Override
    // public void onPrepare(Menu menu) {
    // MenuItem changeColors = menu.add("����ѧ��ģʽ");
    // changeColors.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
    // changeColors.setOnMenuItemClickListener(this);
    // }

    public class gridListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            if (classNameList[position] != null) {
                message = new CurriculumMessage(position);
                message.show(getSupportFragmentManager(), "curriculummessage");
            } else {
                return;
            }
        }
    }

    @SuppressLint("ValidFragment")
    public class CurriculumMessage extends DialogFragment {
        int position;

        @SuppressLint("ValidFragment")
        public CurriculumMessage(int position) {
            this.position = position;
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            setStyle(STYLE_NO_TITLE, 0);
        }

        @SuppressLint("NewApi")
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            final View view = inflater.inflate(R.layout.dialog_curriculum, container,
                    false);
            TextView textView = ((TextView) view
                    .findViewById(R.id.curriculum_dialog));
            textView.setText(classNameList[position]);
            textView.setBackground(colors[intColors[position]]);
            Button delButton = (Button) view.findViewById(R.id.curriculum_dialog_del_bt);
            delButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    String className = classNameList[position];
                    final int weekOfClass = (position % 7) + 1;
                    final int timeOfClass = ((int) position / 7) + 1;
                    String[] classArrayTemp = new String[1];
                    for (Lesson lesson : lessonList) {
                        if (lesson.getClassDayOfWeek() == weekOfClass && lesson.getClassDayOfTime() == timeOfClass) {
                            if (classArrayTemp[0] != null) {
                                int length = classArrayTemp.length;
                                String temp = classArrayTemp[length - 1];
                                classArrayTemp = new String[++length];
                                classArrayTemp[0] = temp;
                                classArrayTemp[1] = lesson.getClassName();
                            } else {
                                classArrayTemp[0] = lesson.getClassName();
                            }

                        }
                    }

                    final String[] classArray = classArrayTemp;
                    final boolean[] checked = new boolean[classArray.length];
                    new AlertDialog.Builder(Activity_curriculumDetail.this)
                            .setTitle("删除课程")
                            .setMultiChoiceItems(classArray, null, new DialogInterface.OnMultiChoiceClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                    checked[which] = true;
                                }
                            })
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    for (int i = 0; i < checked.length; i++) {
                                        if (checked[i]) {
                                            String temp = classArray[i];
                                            for (Lesson lesson : lessonList) {
                                                if (temp.equals(lesson.getClassName()) && weekOfClass == lesson.getClassDayOfWeek() && timeOfClass == lesson.getClassDayOfTime()) {
                                                    new Myhandler(lesson).sendEmptyMessage(MyHandlerMsg_remove);
                                                }
                                            }
                                        }
                                    }

                                }
                            }).setNegativeButton("取消", null).show();
                }
            });

            return view;
        }

    }

    class Myhandler extends Handler {
        private Lesson lesson;

        public Myhandler(Lesson lesson) {
            this.lesson = lesson;
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MyHandlerMsg_remove:
                    lessonList.remove(lesson);
                    Main.getApp().setCurriculumArray(lessonList);
                    adapter.setLessonList(lessonList);
                    adapter.notifyDataSetChanged();
                    if (message != null)
                        message.dismiss();
                    break;
                case MyHandlerMsg_refresh:
                    adapter.setLessonList(lessonList);
                    adapter.notifyDataSetChanged();
                    break;
            }

        }
    }


    private void showToast(String message) {
        Toast.makeText(Activity_curriculumDetail.this, message, Toast.LENGTH_SHORT).show();
    }

    // public int numClasses(int iWay) {
    // for (int i = 0; i < 5; i++) {
    // if ((app.curriculumArray[i * 7 + (iWay) - 1]) != null) {
    // numClasses++;
    //
    // }
    // }
    // System.out.println("numClass��numClassee�����Ϊ" + numClasses);
    // return numClasses;
    // }

    // @SuppressLint("SimpleDateFormat")
    // public void storeclassNum() {
    // String we[] = new String[] { "", "Monday", "Tuesday", "Wednesday",
    // "Thursday", "Friday", "Saturday", "Sunday" };
    // int iWay = 0;
    // if (app.getDataStore().getBoolean("isLogin", false)) {
    // for (iWay = 1; iWay <= 7; iWay++) {
    // numClasses = 0;
    // app.getDataStore().edit().putInt(we[iWay], numClasses(iWay))
    // .commit();
    // }
    // // app.caldate();
    //
    // AlarmManager am = (AlarmManager) getActivity().getSystemService(
    // Context.ALARM_SERVICE);
    // Intent intent = new Intent(getActivity(), NotificationService.class);
    // intent.setAction("ALARM_ACTION");
    // PendingIntent pendingIntent = PendingIntent.getService(
    // getActivity(), 0, intent, 0);
    // am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()
    // + app.caloffset(), 24 * 60 * 60 * 1000, pendingIntent);
    //
    // }
    // }

    // @SuppressLint("NewApi")
    // @Override
    // public void onDestroyView() {
    // // TODO Auto-generated method stub
    // activity.setActionBarListener(null);
    // activity.invalidateOptionsMenu();
    // super.onDestroyView();
    // }
}
