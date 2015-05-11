package cn.edu.sdu.online.modal;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Term implements Serializable{
	private String termStr;//
	private boolean now;//是否为当前学期
	private String GPA;//gpa
	private List<Course> courseList = new ArrayList<Course>();//��ѧ�ڿγ��б�
	public String getTermStr() {
		return termStr;
	}
	public void setTermStr(String termStr) {
		this.termStr = termStr;
	}
	public boolean isNow() {
		return now;
	}
	public void setNow(boolean now) {
		this.now = now;
	}
	
	public List<Course> getCourseList() {
		return courseList;
	}
	public void setCourseList(List<Course> courseList) {
		this.courseList = courseList;
	}
	public String getGPA() {
		return GPA;
	}
	public void setGPA(String gPA) {
		GPA = gPA;
	}

}
