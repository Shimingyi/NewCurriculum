package cn.edu.sdu.online.view;

import cn.edu.sdu.online.newcurriculum.R;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

/**
 * 自定义popupWindow
 */
public class AddPopWindow extends PopupWindow {

    public AddPopWindow(final Activity context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View conentView = inflater.inflate(R.layout.mession_add_popup_dialog, null);//convert view
        conentView.setBackgroundColor(Color.rgb(35, 112, 141));
        // 设置SelectPicPopupWindow的View
        this.setContentView(conentView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        // 刷新状态
        this.update();
        this.setFocusable(true);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0);
        // 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
        this.setBackgroundDrawable(dw);
        // mPopupWindow.setAnimationStyle(android.R.style.Animation_Dialog);
        // 设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.AnimationPreview);
        LinearLayout shareLayout = (LinearLayout) conentView
                .findViewById(R.id.daka_share_layout);
        LinearLayout logout_layout = (LinearLayout) conentView
                .findViewById(R.id.daka_popup_logout);
        shareLayout.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT,
                        "天天打卡，完成任务，学生在线新功能打卡出现了~~!");
                sendIntent.setType("text/plain");
                context.startActivity(sendIntent);
                AddPopWindow.this.dismiss();
            }
        });

        logout_layout.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                AddPopWindow.this.dismiss();
            }
        });
    }

    /**
     * 显示popupWindow
     */
    public void showPopupWindow(View parent) {
        if (!this.isShowing()) {
            // 以下拉方式显示popupwindow
            this.showAsDropDown(parent, -1, 37);
        } else {
            this.dismiss();
        }
    }
}
