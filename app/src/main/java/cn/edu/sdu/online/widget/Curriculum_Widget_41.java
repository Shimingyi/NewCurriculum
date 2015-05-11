package cn.edu.sdu.online.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.widget.RemoteViews;

import java.util.Timer;
import java.util.TimerTask;

import cn.edu.sdu.online.newcurriculum.Activity_curriculumDetail;
import cn.edu.sdu.online.newcurriculum.R;

/**
 * Created by Yulin Sun 孙玉林
 */

public class Curriculum_Widget_41  extends AppWidgetProvider{
    final int WHEN_TIME = 0x414;// 时钟标志
    Context context0;
    Intent intent0;// 便于时钟调用

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager,
                         int[] appWidgetIds) {
        context0 = context;
        // 获取相关项
        RemoteViews remote0 = new RemoteViews(context.getPackageName(),
                R.layout.widget_4_1_4);
        ComponentName name0 = new ComponentName(context, Curriculum_Widget_41.class);
        // 添加按键响应
        Intent int1 = new Intent(context, Activity_curriculumDetail.class);
        PendingIntent pend1 = PendingIntent.getActivity(context, 0, int1, 0);
        remote0.setOnClickPendingIntent(R.id.wid414_leftshow, pend1);
        remote0.setOnClickPendingIntent(R.id.wid414_rightshow, pend1);// 左右进主程序
        Intent int2 = new Intent();
        int2.setAction("WID414_CLICK_LEFTTOUCH");
        PendingIntent pend2 = PendingIntent.getService(context, 0, int2, 0);
        remote0.setOnClickPendingIntent(R.id.wid414_lefttouch, pend2);// 上一节
        Intent int3 = new Intent();
        int3.setAction("WID414_CLICK_CENTERSHOW");
        PendingIntent pend3 = PendingIntent.getService(context, 0, int3, 0);
        remote0.setOnClickPendingIntent(R.id.wid414_class, pend3);// 中间立即更新
        Intent int4 = new Intent();
        int4.setAction("WID414_CLICK_RIGHTTOUCH");
        PendingIntent pend4 = PendingIntent.getService(context, 0, int4, 0);
        remote0.setOnClickPendingIntent(R.id.wid414_righttouch, pend4);// 下一节
        // 注册更新
        appWidgetManager.updateAppWidget(name0, remote0);
        // 初始化服务
        intent0 = new Intent();
        intent0.setAction("WID414_CLICK_CENTERSHOW");
        // 启动时钟
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                time_handler.sendEmptyMessage(WHEN_TIME); // 发送空消息，通知界面更新
            }
        }, 0, 60000);// 每分钟更新
    }

    Handler time_handler = new Handler() {// 每分钟接收消息，启动服务进行更新
        public void handleMessage(Message msg) {
            if (msg.what == WHEN_TIME) {
                context0.startService(intent0);
            }
            super.handleMessage(msg);
        }
    };
}
