package cn.edu.sdu.online.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import cn.edu.sdu.online.modal.Lesson;
import cn.edu.sdu.online.newcurriculum.R;

public class LoadCurriculumnAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private String[] classNameList;
    private Drawable[] colors;
    private int[] intColors;
    private int gridheight;
    private int gridwidth;
    private int[] mark;
    private int listPosition = 0;
    private List<Lesson> lessonList;

    public LoadCurriculumnAdapter(Context context, List<Lesson> lessonList, String[] classNameList,
                                  Drawable[] colors, int[] intColors, int gridheight, int gridwidth) {

        this.inflater = LayoutInflater.from(context);
        this.classNameList = classNameList;
        this.colors = colors;
        this.intColors = intColors;
        this.gridheight = gridheight;
        this.gridwidth = gridwidth;
        this.lessonList = lessonList;

        System.out.print("listSize:"+lessonList.size());

        mark = new int[35];

        for (int i = 0; i < 35; i++) {
            mark[i] = 0;
        }

        for (int i = 0; i < lessonList.size(); i++) {
            Lesson lesson = lessonList.get(i);
            mark[(lesson.getClassDayOfTime() - 1) * 7 + (lesson.getClassDayOfWeek() - 1)]++;
        }


    }

    public void setLessonList(List<Lesson> lessonList) {
        this.lessonList = lessonList;
    }

    @Override
    public int getCount() {
        return 35;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("NewApi")
    @SuppressWarnings("deprecation")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        RelativeLayout view = (RelativeLayout) inflater.inflate(
                R.layout.grid_item, null);
        TextView aView = (TextView) view.findViewById(R.id.course_name);


        int weekOfClass = (position % 7)+1;
        int timeOfClass = ((int) position / 7 )+1;
        aView.setText(" ");


        if (mark[position] > 0) {
            for (int i = 0; i < mark[position]; i++) {
                for(Lesson lesson:lessonList) {
                    if(lesson.getClassDayOfWeek()==weekOfClass&&lesson.getClassDayOfTime()==timeOfClass) {
                        String classContent = aView.getText().toString().trim();
                        aView.setText(classContent+"\n"+lesson.getClassName()+"\n"+lesson.getClassPlace());
                    }
                }

            }
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN)
                view.setBackgroundDrawable(colors[intColors[position]]);
            else {
                view.setBackground(colors[intColors[position]]);
            }
        }


        /*if (classNameList[position] != null) {
            aView.setText(classNameList[position]);

            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN)
                view.setBackgroundDrawable(colors[intColors[position]]);
            else {
                view.setBackground(colors[intColors[position]]);
            }
        } else {
            aView.setText(" ");
        }*/
        view.setLayoutParams(new GridView.LayoutParams(gridwidth, gridheight));
        aView.setGravity(Gravity.CENTER);
        return view;
    }
}
