package cn.edu.sdu.online.widget;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.IBinder;
import android.text.format.Time;
import android.widget.RemoteViews;

import java.util.List;

import cn.edu.sdu.online.app.Main;
import cn.edu.sdu.online.modal.Lesson;
import cn.edu.sdu.online.newcurriculum.R;

/**
 * Created by Yulin Sun 孙玉林
 */

public class Curriculum_Widget_41_service extends Service {

    private final String TAG = "Curriculum_Widget_41_service";
    private List<Lesson> lessonList;
    String action_temp = "";// action过渡变量
    final int ONECLASS = 110;// 每节课时间
    RemoteViews remote0;
    String[][] class_show = new String[][]{{"1-1", "8:00\n9:50"},
            {"2-2", "10:10\n12:00"}, {"3-3", "1:30\n3:20"},
            {"4-4", "3:30\n5:20"}, {"5-5", "6:30\n8:20"}};// 辅助显示
    String[] week_text = {"周一", "周二", "周三", "周四", "周五", "周六", "周日"};// 辅助显示
    int[] classover = new int[]{9 * 60 + 50, 12 * 60 + 00, 15 * 60 + 20,
            17 * 60 + 20, 20 * 60 + 20, 24 * 60 + 00};// 下课时间， 最后起辅助判断作用
    int when_index = 0;// 时间索引
    int week_index = 0;// 周索引
    boolean has_class = false;// 当天是否有课
    boolean is_firstrun = true;// 首次运行时
    String class_temp = "";// 课程过渡变量
    String all_class = "";// 全部课程
    String file_name = "/sdcard/LX_class/kechengbiao_test3.txt";// 课程文件路径

    public void onStart(Intent intent, int startId) {
        // 获取相关项
        ComponentName name0 = new ComponentName(this, Curriculum_Widget_41.class);
        AppWidgetManager manager0 = AppWidgetManager.getInstance(this);
        remote0 = new RemoteViews(getPackageName(), R.layout.widget_4_1_4);
        // 点击按钮时
        try {
            action_temp = intent.getAction();
        } catch (Exception e) {
            action_temp = null;
        }
        if (action_temp != null) {// 最左边与最右边为进入主程序，不必监听
            if (action_temp.equals("WID414_CLICK_LEFTTOUCH")) {
                update_last();
            } else if (action_temp.equals("WID414_CLICK_CENTERSHOW")) {
                update_now();// 立即更新
            } else if (action_temp.equals("WID414_CLICK_RIGHTTOUCH")) {
                update_next();
            }
        }
        // 注册更新
        manager0.updateAppWidget(name0, remote0);
    }

    public IBinder onBind(Intent arg0) {
        return null;
    }

    // 方法
    public void update_last() {
        if (load_class() == false)// 载入课程
            return;// 失败就直接返回
        // 索引向前
        when_index--;
        if (when_index < 0) {
            when_index = 4;
            week_index--;
            if (week_index < 0) {
                week_index = 6;
            }
        }
        // 更新组件
        for (int i0 = 0; i0 < 7; i0++) {// 未来7天内无课（即空课表），拒绝更新
            today_hasclass();// 处理无课的天，存入has_class变量
            if (!has_class) {// 如果无课
                remote0.setTextViewText(R.id.wid414_leftshow,
                        week_text[week_index]);
                remote0.setTextViewText(R.id.wid414_class, "今天没有课");
                remote0.setTextViewText(R.id.wid414_location, "好好休息一下吧");
                remote0.setTextViewText(R.id.wid414_rightshow,
                        week_text[week_index]);
                when_index = 0;

                break;
            }
            // 没有break就是那天有课
            for (int i = when_index; i > -1; i--) {// 逆向遍历找课

                Lesson lesson = findLesson(week_index, i);
                if (lesson == null) {
                    continue;
                }
                remote0.setTextViewText(R.id.wid414_leftshow,
                        week_text[week_index] + "\n"
                                + class_show[when_index][0]);
                remote0.setTextViewText(R.id.wid414_class, lesson.getClassName());
                remote0.setTextViewText(R.id.wid414_class, lesson.getClassPlace());
                remote0.setTextViewText(R.id.wid414_rightshow,
                        class_show[when_index][1]);
                class_temp = lesson.getClassName();
                break;
            }
            if (class_temp == null) {// 向上无课（如到了第1节再向上就无课了等 ），就向前一天找
                when_index = 4;
                week_index--;
                if (week_index < 0) {
                    week_index = 6;
                }
            } else {// 有课就结束循环
                break;
            }
        }
    }

    public void update_now() {
        if (load_class() == false)// 载入课程
            return;// 失败就直接返回
        // 获取当前时间
        Time timenow = new Time();
        timenow.setToNow();
        int week_now = timenow.weekDay;// 返回0-6，0是星期天
        week_index = week_now - 1;
        if (week_index < 0)
            week_index += 7;// 将0定为星期一
        int hour_now = timenow.hour;
        int min_now = timenow.minute;
        int time_now = hour_now * 60 + min_now;
        // 计算第几节课
        for (int i = 0; i < 6; i++) {
            if (time_now < classover[i]) {
                when_index = i;// 可能为5，即第六节课
                break;
            }
        }
        today_hasclass();// 处理无课的天
        // 更新组件
        if (has_class) {// 如果当天有课
            // 从现在的索引向下找最近的的课
            for (int i = when_index; i < 5; i++) {
                Lesson lesson = findLesson(week_index, i);
                if (lesson == null) {
                    continue;
                }
                when_index = i;
                remote0.setTextViewText(R.id.wid414_leftshow,
                        week_text[week_index] + "\n"
                                + class_show[when_index][0]);
                remote0.setTextViewText(R.id.wid414_class, lesson.getClassName());
                remote0.setTextViewText(R.id.wid414_location, lesson.getClassPlace());
                remote0.setTextViewText(R.id.wid414_rightshow,
                        class_show[when_index][1]);
                when_index = i;
                break;
            }
            // 没有找到，说明今天的可上完了（因为今天已判定有课）
            if (class_temp == null) {// 这个为空格说明所有的可都结束了
                remote0.setTextViewText(R.id.wid414_leftshow,
                        week_text[week_index]);
                remote0.setTextViewText(R.id.wid414_class, "今天课程已结束");
                remote0.setTextViewText(R.id.wid414_location, "可以休息啦");
                remote0.setTextViewText(R.id.wid414_rightshow,
                        week_text[week_index]);
                when_index = 5;
            }
        } else {// 如果当天无课
            remote0.setTextViewText(R.id.wid414_leftshow, week_text[week_index]);
            remote0.setTextViewText(R.id.wid414_class, "今天没有课");
            remote0.setTextViewText(R.id.wid414_location, " 好好休息一下吧");
            remote0.setTextViewText(R.id.wid414_rightshow,
                    week_text[week_index]);
        }
    }

    public void update_next() {
        if (load_class() == false)// 载入课程
            return;// 失败就直接返回
        // 索引向后
        when_index++;
        if (when_index > 4) {
            when_index = 0;
            week_index++;
            if (week_index > 6) {
                week_index = 0;
            }
        }
        // 更新组件
        // 这里与向前更新代码基本类似，不再详述，由于遍历方向相反，所以没有写成方法
        for (int i0 = 0; i0 < 7; i0++) {// 未来7天内无课（即空课表），拒绝更新
            today_hasclass();// 处理无课的天
            if (!has_class) {
                remote0.setTextViewText(R.id.wid414_leftshow,
                        week_text[week_index]);
                remote0.setTextViewText(R.id.wid414_class, "今天没有课 \n 好好休息一下吧");
                remote0.setTextViewText(R.id.wid414_rightshow,
                        week_text[week_index]);
                when_index = 5;
                break;
            }
            for (int i = when_index; i < 5; i++) {// 正向遍历找课
                Lesson lesson = findLesson(week_index, i);
                if (lesson == null) {
                    continue;
                }
                when_index = i;
                remote0.setTextViewText(R.id.wid414_leftshow,
                        week_text[week_index] + "\n"
                                + class_show[when_index][0]);
                remote0.setTextViewText(R.id.wid414_class, lesson.getClassName());
                remote0.setTextViewText(R.id.wid414_location, lesson.getClassPlace());
                remote0.setTextViewText(R.id.wid414_rightshow,
                        class_show[when_index][1]);
                break;
            }
            if (class_temp == null) {// 今天无课，再向下找
                when_index = 0;
                week_index++;
                if (week_index > 6) {
                    week_index = 0;
                }
            } else {// 找到课了就结束循环
                break;
            }
        }
    }

    // 加载课程
    public boolean load_class() {
        lessonList = Main.getApp().getCurriculumList();
        return true;
    }

    // 判断当天是否有课并处理
    public void today_hasclass() {
        has_class = false;
        for (int i = 0; i < 5; i++) {
            for (Lesson lesson : lessonList) {
                if (lesson.getClassDayOfWeek() == (week_index + 1)) {
                    has_class = true;
                    break;
                }
            }

        }
    }

    private Lesson findLesson(int week, int time) {
        for (Lesson lesson : lessonList) {
            if (lesson.getClassDayOfTime() == (time + 1) && lesson.getClassDayOfWeek() == (week + 1)) {
                return lesson;
            }
        }
        return null;
    }
}
