package cn.edu.sdu.online.view;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils.TruncateAt;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import cn.edu.sdu.online.modal.Course;
import cn.edu.sdu.online.modal.Term;
import cn.edu.sdu.online.newcurriculum.R;

public class GpaView extends RelativeLayout {
	private static String TAG = "GpaView";
	TableLayout.LayoutParams params;
	private ImageView im_sun;
	private TextView tv_term, tv_now, tv_gpa;
	private TableLayout tl_gpa;
	String test;

	// private TableLayout tableLayout;
	@SuppressLint("NewApi")
	public GpaView(Context context) {
		super(context);
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.new_gpa_board, this);
		im_sun = (ImageView) findViewById(R.id.im_sun);
		tv_term = (TextView) findViewById(R.id.tv_term);
		tv_now = (TextView) findViewById(R.id.tv_now);
		tv_gpa = (TextView) findViewById(R.id.tv_gpa);
		tl_gpa = (TableLayout) findViewById(R.id.tl_gpa);

		params = new TableLayout.LayoutParams();
		params.setMargins(1, 1, 1, 1);

	}

	public void initView(Term term) {
		String termStr = term.getTermStr();
		tv_term.setText(termStr);
		boolean now = term.isNow();
		if (now) {
			tv_now.setVisibility(View.VISIBLE);
			im_sun.setImageResource(R.drawable.gpa_redsun);
		} else {
			tv_now.setVisibility(View.GONE);
			im_sun.setImageResource(R.drawable.gpa_bluesun);
		}
		String GPA = term.getGPA();
		tv_gpa.setText(GPA);
		List<Course> courseList = term.getCourseList();
		for (int i = 0; i < courseList.size(); i++) {
			TableRow row = new TableRow(getContext());

			TextView tv_cname = new TextView(getContext());
			tv_cname.setEllipsize(TruncateAt.MARQUEE);
			tv_cname.setMaxEms(10);
			tv_cname.setMarqueeRepeatLimit(5);
			tv_cname.setSingleLine(true);
			tv_cname.setHorizontallyScrolling(true);
			String name = courseList.get(i).getClassName();
			setTextView(name, tv_cname);
			row.addView(tv_cname);

			TextView tv_credit = new TextView(getContext());
			String credit = courseList.get(i).getClassCredit();
			setTextView(credit, tv_credit);
			row.addView(tv_credit);

			TextView tv_score = new TextView(getContext());
			String score = courseList.get(i).getClassGrade();
			setTextView(score, tv_score);
			row.addView(tv_score);

			TextView tv_attr = new TextView(getContext());
			String attr = courseList.get(i).getClassAttitude();
			setTextView(attr, tv_attr);
			row.addView(tv_attr);

			tl_gpa.addView(row);

		}

		// TextView textView = new TextView(getContext());
		// Log.v(TAG, "textView:"+textView.toString());
		// textView.setText(test);

	}

	private void setTextView(String text, TextView tv_cname) {

		tv_cname.setBackgroundColor(0xFFF1F2E2);
		tv_cname.setGravity(Gravity.CENTER);
		tv_cname.setText(text);
		tv_cname.setTextSize(10);
	}

	public GpaView(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.gpa_board, this);
		im_sun = (ImageView) findViewById(R.id.im_sun);
		tv_term = (TextView) findViewById(R.id.tv_term);
		tv_now = (TextView) findViewById(R.id.tv_now);

	}

	private void setIm_sunRes(boolean now) {
		if (now) {
			im_sun.setImageResource(R.drawable.gpa_redsun);
		} else {
			im_sun.setImageResource(R.drawable.gpa_bluesun);
		}
	}

	public void setTvTermText(String term) {
		tv_term.setText(term);
	}

	public void setTvNowVisible(boolean now) {
		if (now) {
			tv_now.setVisibility(View.VISIBLE);
		} else {
			tv_now.setVisibility(View.GONE);
		}
	}

	public void setTvGpaText(String gpa) {
		tv_gpa.setText(gpa);
	}

	public ImageView getIm_sun() {
		return im_sun;
	}

	public void setIm_sun(ImageView im_sun) {
		this.im_sun = im_sun;
	}

	public TextView getTv_term() {
		return tv_term;
	}

	public void setTv_term(TextView tv_term) {
		this.tv_term = tv_term;
	}

	public TextView getTv_now() {
		return tv_now;
	}

	public void setTv_now(TextView tv_now) {
		this.tv_now = tv_now;
	}

	public TextView getTv_gpa() {
		return tv_gpa;
	}

	public void setTv_gpa(TextView tv_gpa) {
		this.tv_gpa = tv_gpa;
	}

}
