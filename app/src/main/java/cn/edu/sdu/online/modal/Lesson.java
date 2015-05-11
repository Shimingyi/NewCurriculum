package cn.edu.sdu.online.modal;

import java.io.Serializable;

public class Lesson implements Serializable{
	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getClassPlace() {
		return classPlace;
	}

	public void setClassPlace(String classPlace) {
		this.classPlace = classPlace;
	}

	public int getClassDayOfWeek() {
		return classDayOfWeek;
	}

	public void setClassDayOfWeek(int classDayOfWeek) {
		this.classDayOfWeek = classDayOfWeek;
	}

	public int getClassDayOfTime() {
		return classDayOfTime;
	}

	public void setClassDayOfTime(int classDayOfTime) {
		this.classDayOfTime = classDayOfTime;
	}

	public String toString() {
		return "[ " + className + " : " + classPlace + " , " + classDayOfWeek
				+ " , " + classDayOfTime + " ]";
	}

	private String className;
	private String classPlace;
	private int classDayOfWeek;
	private int classDayOfTime;
}
