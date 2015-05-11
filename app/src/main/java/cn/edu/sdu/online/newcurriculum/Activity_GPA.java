package cn.edu.sdu.online.newcurriculum;

import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.LinearLayout;
import cn.edu.sdu.online.app.Main;
import cn.edu.sdu.online.modal.Course;
import cn.edu.sdu.online.modal.Term;
import cn.edu.sdu.online.view.GpaView;

public class Activity_GPA extends Activity {
	private LinearLayout ll_table_board;
	private List<Term> termList = new ArrayList<Term>();
	private Main app;
	private ActionBar ActionBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gpa);
		app = Main.getApp();
		initView();
	}

	private void initView() {
		float year1 = app.getDataStore().getFloat("year_1", 0);
		float year2 = app.getDataStore().getFloat("year_2", 0);
		float year3 = app.getDataStore().getFloat("year_3", 0);
		float year4 = app.getDataStore().getFloat("year_4", 0);
		String semname[] = { "第一学年", "第二学年", "第三学年", "第四学年" };
		float sempoint[] = { year1, year2, year3, year4 };
		Log.v("activity_gpa", app.getMap().size() + "size");
		int a = 0;
		int size = app.getMap().size();
		Log.i("Activity_GPA", "size:" + size);
		for (int semsize = 1; semsize <= size; semsize++) {
			setLl_table_board((LinearLayout) findViewById(R.id.ll_table_board));
			Term term = new Term();

			if (semsize % 2 != 0) {
				term.setTermStr(semname[a]);
				term.setNow(false);
				term.setGPA(String.valueOf("绩点:" + sempoint[a]));
				a++;
			}
			if (semsize == size) {
				term.setNow(true);
			} else if (app.getCompulsoryList(semsize + 1).size() == 0) {
				term.setNow(true);
			}

			List<Course> courses = app.getCompulsoryList(semsize);
			if (courses.size() > 0) {
				for (int coursize = 0; coursize < courses.size(); coursize++) {
					Course course = new Course();
					course.setClassName(courses.get(coursize).getClassName());
					course.setClassAttitude(courses.get(coursize)
							.getClassAttitude());
					course.setClassCredit(courses.get(coursize)
							.getClassCredit());
					course.setClassGrade(courses.get(coursize).getClassGrade());
				}

				term.setCourseList(courses);
				GpaView gv = new GpaView(Activity_GPA.this);
				gv.initView(term);
				ll_table_board.addView(gv);
			}

		}

	}

	/*
	 * private void addTerm() { for (int i = 0; i < 8; i++) { Term term =
	 * termList.get(i); GpaView gv = new GpaView(Activity_GPA.this);
	 * gv.initView(term); ll_table_board.addView(gv); }
	 * 
	 * }
	 */

	public LinearLayout getLl_table_board() {
		return ll_table_board;
	}

	public void setLl_table_board(LinearLayout ll_table_board) {
		this.ll_table_board = ll_table_board;
	}

	public List<Term> getTermList() {
		return termList;
	}

	public void setTermList(List<Term> termList) {
		this.termList = termList;
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
