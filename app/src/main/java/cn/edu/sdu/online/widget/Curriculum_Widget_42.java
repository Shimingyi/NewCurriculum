package cn.edu.sdu.online.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import cn.edu.sdu.online.newcurriculum.Activity_curriculumDetail;
import cn.edu.sdu.online.newcurriculum.R;

/**
 * Created by Yulin Sun 孙玉林
 */

public class Curriculum_Widget_42 extends AppWidgetProvider{
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager,
                         int[] appWidgetIds) {
        // 初始化变量
        String[] onclick_text = { "WIDGET_4_2_LEFT", "WIDGET_4_2_RIGHT",
                "WIDGET_4_2_UP", "WIDGET_4_2_DOWN" };
        int[] wid_touch = new int[] { R.id.wid42_left, R.id.wid42_right,
                R.id.wid42_up, R.id.wid42_down };
        Intent[] int_click_text = new Intent[4];
        PendingIntent[] pend_click_text = new PendingIntent[4];
        // 获取相关项
        RemoteViews remote0 = new RemoteViews(context.getPackageName(),
                R.layout.widget_4_2);
        ComponentName name0 = new ComponentName(context, Curriculum_Widget_42.class);
        // 触碰进入主程序
        Intent int_main = new Intent(context, Activity_curriculumDetail.class);
        PendingIntent pend_main = PendingIntent.getActivity(context, 0,
                int_main, 0);
        remote0.setOnClickPendingIntent(R.id.wid42_icon, pend_main);
        remote0.setOnClickPendingIntent(R.id.wid42_center11, pend_main);
        remote0.setOnClickPendingIntent(R.id.wid42_center21, pend_main);
        // 触碰服务响应（初始化以 触碰星期 为事件即可）
        for (int i = 0; i < 4; i++) {
            int_click_text[i] = new Intent();
            int_click_text[i].setAction(onclick_text[i]);
            pend_click_text[i] = PendingIntent.getService(context, 0,
                    int_click_text[i], 0);
            remote0.setOnClickPendingIntent(wid_touch[i], pend_click_text[i]);
        }
        // 单独写这个，因为要用它启动服务线程
        Intent int_click_center = new Intent();
        int_click_center.setAction("WIDGET_4_2_CENTER");
        PendingIntent pend_click_center = PendingIntent.getService(context, 0,
                int_click_center, 0);
        remote0.setOnClickPendingIntent(R.id.wid42_center, pend_click_center);
        // 注册更新
        appWidgetManager.updateAppWidget(name0, remote0);
        // 启动服务
        context.startService(int_click_center);
    }
}
