package cn.edu.sdu.online.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.Spinner;

import cn.edu.sdu.online.newcurriculum.R;

/**
 * Created by Hao_S on 2015/4/15.
 */

public class ClassTimeView extends LinearLayout {

    private Spinner spinnerWeek, spinnerTime;
    private LayoutInflater layoutInflater;

    public ClassTimeView(Context context) {
        super(context);
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.add_classtimeview, this);
        spinnerWeek = (Spinner) findViewById(R.id.add_class_spinner_week);
        spinnerTime = (Spinner) findViewById(R.id.add_class_spinner_time);

    }
    public ClassTimeView(Context context,  AttributeSet attrs) {
        super(context,attrs);
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.add_classtimeview, this);
        spinnerWeek = (Spinner) findViewById(R.id.add_class_spinner_week);
        spinnerTime = (Spinner) findViewById(R.id.add_class_spinner_time);
    }
    public int getClassWeek() {
        return spinnerWeek.getSelectedItemPosition();
    }
    public int getClassTime() {
        return spinnerTime.getSelectedItemPosition();
    }
}
