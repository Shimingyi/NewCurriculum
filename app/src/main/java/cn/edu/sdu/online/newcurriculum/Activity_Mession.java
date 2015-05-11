package cn.edu.sdu.online.newcurriculum;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import cn.edu.sdu.online.adapter.MessionSlideAdapter;
import cn.edu.sdu.online.database.MessionDBHelper;
import cn.edu.sdu.online.modal.MessionMessageItem;
import cn.edu.sdu.online.view.MessoinListViewCompat;

public class Activity_Mession extends Activity implements OnItemLongClickListener,
        OnClickListener {

    private List<MessionMessageItem> myMessageItems;

    private Button btn_popupConfirm;

    private EditText et_edit;
    private EditText et_popupedittext;

    private LinearLayout ll_handle;

    protected View view_popupedit;
    private PopupWindow popup;

    private MessionSlideAdapter myAdapter;
    private MessionDBHelper dbhelper;
    
    private Display display;
    private int width;
    private int height;
    private ActionBar ActionBar;
    

    @SuppressWarnings("deprecation")
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mession);
        initView();
        checkTime(myMessageItems);
        display = getWindowManager().getDefaultDisplay();
        width = (int) (display.getWidth()*0.75);
        height = (int) (display.getHeight()*0.4);
    }

    private void initView() {
        view_popupedit = this.getLayoutInflater().inflate(R.layout.mession_popup_edit, null);

        btn_popupConfirm = (Button) view_popupedit.findViewById(R.id.daka_btn_popupConfirm);
        Button btn_popupCancel = (Button) view_popupedit.findViewById(R.id.daka_btn_popupCancel);
        Button btn_confirm = (Button) findViewById(R.id.daka_btn_confirm);
        Button btn_cancel = (Button) findViewById(R.id.daka_btn_cancel);
        btn_confirm.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);
        btn_popupCancel.setOnClickListener(this);


        et_edit = (EditText) this.findViewById(R.id.daka_et_edit);
        et_popupedittext = (EditText) view_popupedit.findViewById(R.id.daka_et_popup);

        ll_handle = (LinearLayout) this.findViewById(R.id.daka_ll_handle);

        popup = new PopupWindow(view_popupedit, 480, 660);
        popup.setFocusable(true);

        MessoinListViewCompat myListView = (MessoinListViewCompat) findViewById(R.id.daka_listview);
        dbhelper = new MessionDBHelper(this);
        myMessageItems = dbhelper.getList();
        myAdapter = new MessionSlideAdapter(Activity_Mession.this, dbhelper, myMessageItems);
        myListView.setlist(myMessageItems);
        myListView.setAdapter(myAdapter);
        myListView.setOnItemLongClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.daka_btn_confirm://
                String str = et_edit.getText().toString();
                if (str.length() == 0)
                    new AlertDialog.Builder(Activity_Mession.this)
                            .setTitle("您输入的内容不能为空喔~").setPositiveButton("确 定",
                            new Dialog.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                }
                            }).create().show();
                else {
                    et_edit.setText(null);
                    MessionMessageItem item = new MessionMessageItem();
                    item.setMsg(str);
                    dbhelper.insert(item.getUuid(), item.getMsg(),
                            item.getCreateTime());
                    myMessageItems.add(item);
                    myAdapter.notifyDataSetChanged();
                }
                ll_handle.performClick();
                break;
            case R.id.daka_btn_cancel:
                ll_handle.performClick();
                break;
            case R.id.daka_btn_popupConfirm:
                popup.dismiss();
                break;
            case R.id.daka_btn_popupCancel:
                popup.dismiss();
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view,
                                   final int position, long id) {
    	popup.setWidth(width);
    	popup.setHeight(height);
        popup.showAtLocation(parent, Gravity.CENTER, 0, -300);
        et_popupedittext.setText(myMessageItems.get(position).getMsg());
        btn_popupConfirm.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                String uuid = myMessageItems.get(position).getUuid();
                String content = et_popupedittext.getText().toString();
                myMessageItems.get(position).setMsg(et_popupedittext.getText().toString());
                dbhelper.changeItem(uuid, content);
                myAdapter.notifyDataSetChanged();
                popup.dismiss();
            }
        });
        return false;
    }

    @SuppressLint("SimpleDateFormat") 
    public void checkTime(List<MessionMessageItem> items) {
        SimpleDateFormat format = new SimpleDateFormat("MMdd");
        Date date = new Date();
        int time = Integer.parseInt(format.format(date));
        for (int i = 0; i < myMessageItems.size(); i++) {
            if (items.get(i).getCreateTime() != time && items.get(i).getStatus() == 1) {
                dbhelper.delete(items.get(i).getUuid());
                myMessageItems.remove(i);
            }
        }
        myAdapter.notifyDataSetChanged();
    }
    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		ActionBar = getActionBar();
		// 设置ActionBar背景颜色
		Drawable backgroundcolor = getResources().getDrawable(
				R.color.actionbarbg);
		ActionBar.setBackgroundDrawable(backgroundcolor);
		return super.onCreateOptionsMenu(menu);
	}

}
