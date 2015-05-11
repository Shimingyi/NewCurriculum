package cn.edu.sdu.online.widget;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.IBinder;
import android.text.format.Time;
import android.widget.RemoteViews;

import java.util.ArrayList;
import java.util.List;

import cn.edu.sdu.online.app.Main;
import cn.edu.sdu.online.modal.Lesson;
import cn.edu.sdu.online.newcurriculum.R;

/**
 * Created by Yulin Sun 孙玉林
 */

public class Curriculum_Widget_42_service extends Service {
    private final String TAG = "Curriculum_Widget_42_service";

    int[] class_over = new int[]{9 * 60 + 50, 12 * 60, 15 * 60 + 20,
            17 * 60 + 20, 20 * 60 + 20, 24 * 60};// 下课时间
    int[] wid_line = {R.id.wid42_linex1, R.id.wid42_linex2, R.id.wid42_linex3,
            R.id.wid42_linex4, R.id.wid42_liney1, R.id.wid42_liney2};// 边框ID
    int[] back_line = {R.drawable.curriculum_wid42_linex1, R.drawable.curriculum_wid42_linex2,
            R.drawable.curriculum_wid42_linex3, R.drawable.curriculum_wid42_linex1,
            R.drawable.curriculum_wid42_liney, R.drawable.curriculum_wid42_liney};// 边框图片
    String[] week = {"星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日"};// 星期显示
    String action_temp = "";
    int have_class[] = {0, 0, 0, 0, 0};// 今天第几节有课,从第1节开始
    int class_count = 0;// 当天课数
    int rest_count = 0;// 剩余课数
    int now_showclass = 0;// 现在第一行显示的课
    int now_week = 0;// 当前星期
    boolean first_run = true;// 是否首次运行
    RemoteViews remote0;// 便于更新

    private List<Lesson> lessonList;
    private List<Lesson> todayLessonList;

    public void onStart(Intent intent, int startId) {// TODO 启动服务
        // 获取相关项
        ComponentName name0 = new ComponentName(this, Curriculum_Widget_42.class);
        AppWidgetManager manager0 = AppWidgetManager.getInstance(this);
        remote0 = new RemoteViews(getPackageName(), R.layout.widget_4_2);

        lessonList = Main.getApp().getCurriculumList();
        todayLessonList = new ArrayList<Lesson>();

        // 点击按钮时（初始化属于点击星期按钮）
        try {
            action_temp = intent.getAction();
        } catch (Exception e) {
            action_temp = null;
        }
        if (action_temp != null) {
            if (action_temp.equals("WIDGET_4_2_CENTER")) {// 以今天初始化界面
                // 获取当前时间
                Time timenow = new Time();
                timenow.setToNow();
                now_week = timenow.weekDay;// 返回0-6，0是星期天
                now_week = now_week - 1;// 便于数组显示
                if (now_week < 0)
                    now_week += 7;
                int hour_now = timenow.hour;
                int min_now = timenow.minute;
                int time_now = hour_now * 60 + min_now;
                int when_index = 0;// 这几个暂且用局部变量吧
                for (int i = 0; i < 6; i++) {
                    if (time_now < class_over[i]) {
                        when_index = i;// 现在是第几节课或之前
                        break;
                    }
                }
                // 获取当天课程数组，确定 当天课数、剩余课数
                class_count = 0;
                rest_count = 0;
                // 现在第一行应该显示的课程，今天有课的第几节（此处已处理了范围约束，以后此变量可直接调用）
                now_showclass = class_count - rest_count;
                if (class_count - now_showclass == 0) {
                    now_showclass -= 2;
                } else if (class_count - now_showclass == 1) {
                    now_showclass -= 1;
                }
                if (now_showclass < 0) {
                    now_showclass = 0;
                }
                now_showclass++;
                // 更新
                update();
            } else if (action_temp.equals("WIDGET_4_2_LEFT")) {
                // 周向前
                now_week--;
                if (now_week < 0)
                    now_week += 7;
                // 获取当天课程数组，确定 当天课数、初始化显示课数
                // 与 以当天更新 小有差异，就没写成方法
                class_count = 0;
                for (int i = 0; i < 5; i++) {
                    Lesson lesson = FindClassByTime(now_week, i);
                    if (lesson == null) {
                        continue;
                    }
                    todayLessonList.add(lesson);
                    have_class[class_count] = i + 1;
                    class_count++;
                }
                now_showclass = 1;
                // 更新
                update();
            } else if (action_temp.equals("WIDGET_4_2_RIGHT")) {
                // 周向后
                now_week++;
                if (now_week > 6)
                    now_week -= 7;
                // 获取当天课程数组，确定 当天课数、初始化显示课数
                class_count = 0;
                for (int i = 0; i < 5; i++) {
                    Lesson lesson = FindClassByTime(now_week, i);
                    if (lesson == null) {
                        continue;
                    }
                    todayLessonList.add(lesson);
                    class_count++;
                }
                now_showclass = 1;
                // 更新
                update();
            } else if (action_temp.equals("WIDGET_4_2_UP")) {
                if (now_showclass > 1) {// 到顶之前
                    now_showclass--;
                    for (int i = 0; i < 5; i++) {
                        Lesson lesson = FindClassByTime(now_week, i);
                        if (lesson == null) {
                            continue;
                        }
                        todayLessonList.add(lesson);
                    }
                    update();
                }
            } else if (action_temp.equals("WIDGET_4_2_DOWN")) {
                if (now_showclass < class_count - 1) {// 到底之前
                    now_showclass++;
                    for (int i = 0; i < 5; i++) {
                        Lesson lesson = FindClassByTime(now_week, i);
                        if (lesson == null) {
                            continue;
                        }
                        todayLessonList.add(lesson);
                    }
                    update();
                }
            }
        }
        // 注册更新
        manager0.updateAppWidget(name0, remote0);
    }

    public IBinder onBind(Intent arg0) {
        return null;
    }

    // TODO 方法，据课组，课数，当前首行应该显示索引 确定更新
    public void update() {
        //本想通过全局变量记录上一天课数，减少边框显示运算，测试时发现内存不足会导致服务重启，影响效果。
        if (class_count != 0) {//有课，显示边框
            // 显示边框，清除图片
            for (int i = 0; i < 6; i++) {
                remote0.setImageViewResource(wid_line[i], back_line[i]);
            }
            remote0.setImageViewResource(R.id.wid42_noclass,
                    R.drawable.color_20);
        }
        if (class_count == 0) {
            // 清空全部
            remote0.setImageViewResource(R.id.wid42_centerimage11,
                    R.drawable.transparent);
            remote0.setImageViewResource(R.id.wid42_centerimage12,
                    R.drawable.transparent);
            remote0.setImageViewResource(R.id.wid42_centerimage21,
                    R.drawable.transparent);
            remote0.setImageViewResource(R.id.wid42_centerimage22,
                    R.drawable.transparent);
            remote0.setTextViewText(R.id.wid42_left1, "");
            remote0.setTextViewText(R.id.wid42_center11, "");
            remote0.setTextViewText(R.id.wid42_center12, "");
            remote0.setTextViewText(R.id.wid42_left2, "");
            remote0.setTextViewText(R.id.wid42_center21, "");
            remote0.setTextViewText(R.id.wid42_center22, "");
            remote0.setImageViewResource(R.id.wid42_up, R.drawable.color_20);
            remote0.setImageViewResource(R.id.wid42_down,
                    R.drawable.color_20);
            for (int i = 0; i < 6; i++) {// 清除边框
                remote0.setImageViewResource(wid_line[i],
                        R.drawable.color_20);
            }
            // 设置图片
            remote0.setImageViewResource(R.id.wid42_noclass, R.drawable.curriculum_widget42_noclass);
        } else if (class_count == 1) {// 如果当天只有一节课
            // 显示课程名前的标志
            remote0.setImageViewResource(R.id.wid42_centerimage11,
                    R.drawable.curriculum_widget42_book);
            remote0.setImageViewResource(R.id.wid42_centerimage12,
                    R.drawable.curriculum_widget42_location);
            remote0.setImageViewResource(R.id.wid42_centerimage21,
                    R.drawable.color_20);
            remote0.setImageViewResource(R.id.wid42_centerimage22,
                    R.drawable.color_20);
            // 更新课程与地点
            remote0.setTextViewText(R.id.wid42_left1,
                    (have_class[now_showclass - 1]) + "-"
                            + (have_class[now_showclass - 1]));
            remote0.setTextViewText(R.id.wid42_center11, todayLessonList.get(0).getClassName());
            remote0.setTextViewText(R.id.wid42_center12, todayLessonList.get(0).getClassPlace());
            remote0.setTextViewText(R.id.wid42_left2, "");
            remote0.setTextViewText(R.id.wid42_center21, "");
            remote0.setTextViewText(R.id.wid42_center22, "");
            // 更新箭头
            remote0.setImageViewResource(R.id.wid42_up,
                    R.drawable.curriculum_jiantou_upnot);
            remote0.setImageViewResource(R.id.wid42_down,
                    R.drawable.curriculum_jiantou_downnot);
        } else {// 当天的课有2节及以上时
            // 显示课程名前的标志
            remote0.setImageViewResource(R.id.wid42_centerimage11,
                    R.drawable.curriculum_widget42_book);
            remote0.setImageViewResource(R.id.wid42_centerimage12,
                    R.drawable.curriculum_widget42_location);
            remote0.setImageViewResource(R.id.wid42_centerimage21,
                    R.drawable.curriculum_widget42_book);
            remote0.setImageViewResource(R.id.wid42_centerimage22,
                    R.drawable.curriculum_widget42_location);
            // 更新课程与地点
            remote0.setTextViewText(R.id.wid42_left1,
                    (have_class[now_showclass - 1]) + "-"
                            + (have_class[now_showclass - 1]));
            for (int i = now_showclass; i <= 5; i++) {
                Lesson lesson0 = FindClassOfTheDay(i);
                if (lesson0 != null) {
                    remote0.setTextViewText(R.id.wid42_center11, lesson0.getClassName());
                    remote0.setTextViewText(R.id.wid42_center12, lesson0.getClassPlace());
                    break;
                }
            }


            remote0.setTextViewText(R.id.wid42_left2,
                    (have_class[now_showclass]) + "-"
                            + (have_class[now_showclass]));


            for (int i = now_showclass + 1; i <= 5; i++) {
                Lesson lesson1 = FindClassOfTheDay(i);
                if (lesson1 != null) {
                    remote0.setTextViewText(R.id.wid42_center21, lesson1.getClassName());
                    remote0.setTextViewText(R.id.wid42_center22, lesson1.getClassPlace());
                    lesson1 = null;
                    break;
                }
            }

            // 更新箭头（先做判定）
            if (now_showclass == 1 && class_count == 2) {// 只有两行时
                remote0.setImageViewResource(R.id.wid42_up,
                        R.drawable.curriculum_jiantou_upnot);
                remote0.setImageViewResource(R.id.wid42_down,
                        R.drawable.curriculum_jiantou_downnot);
            } else if (now_showclass == 1) {// 已滚动到顶
                remote0.setImageViewResource(R.id.wid42_up,
                        R.drawable.curriculum_jiantou_upnot);
                remote0.setImageViewResource(R.id.wid42_down,
                        R.drawable.curriculum_jiantou_down);// 箭头有效
            } else if (class_count - now_showclass == 1) {// 已滚动到底
                remote0.setImageViewResource(R.id.wid42_up,
                        R.drawable.curriculum_jiantou_up);// 箭头有效
                remote0.setImageViewResource(R.id.wid42_down,
                        R.drawable.curriculum_jiantou_downnot);
            } else {// 滚动到中间
                remote0.setImageViewResource(R.id.wid42_up,
                        R.drawable.curriculum_jiantou_up);
                remote0.setImageViewResource(R.id.wid42_down,
                        R.drawable.curriculum_jiantou_down);
            }
        }
        remote0.setTextViewText(R.id.wid42_center, week[now_week]);// 更新星期显示
    }

    private Lesson FindClassOfTheDay(int time) {
        for (Lesson lesson : todayLessonList) {
            if (lesson.getClassDayOfTime() == time) {
                return lesson;
            }
        }
        return null;
    }

    private Lesson FindClassByTime(int week, int time) {
        for (Lesson lesson : lessonList) {
            if (lesson.getClassDayOfWeek() == (week + 1) && lesson.getClassDayOfTime() == (time + 1)) {
                return lesson;
            }
        }
        return null;
    }
}
