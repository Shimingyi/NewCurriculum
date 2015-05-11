package cn.edu.sdu.online.modal;

import java.io.Serializable;

public class Course implements Serializable{
	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public int getClassYear() {
		return classYear;
	}

	public void setClassYear(int classYear) {
		this.classYear = classYear;
	}

	 public int getSemester() {
		return semester;
	}

	public void setSemester(int semester) {
		this.semester = semester;
	}

	public String getClassCredit() {
		return classCredit;
	}

	public 	void setClassCredit(String classCredit) {
		this.classCredit = classCredit;
	}

	public String getClassAttitude() {
		return classAttitude;
	}

	public void setClassAttitude(String classAttitude) {
		this.classAttitude = classAttitude;
	}

	public String getClassGrade() {
		return classGrade;
	}

	public void setClassGrade(String classGrade) {
		this.classGrade = classGrade;
	}

	public String toString() {
		return "[ " + className + " : " + classYear + " , " + semester + " , "
				+ classCredit + " , " + classGrade + " , " + classAttitude
				+ " ]";
	}

	private String className;// 科目名称
	private int classYear;// 确认该科目是哪一个学年的
	private int semester;// 上学学期,如果是上学期,其值为0,如果是下学期,其值为1
	private String classCredit;// 每科的学分,浮点因为学分不一定是int
	private String classAttitude;// 科目的属性:必修,选修,限选
	private String classGrade;// 每科的成绩
}
