package cn.edu.sdu.online.modal;

import java.io.Serializable;

public class Person implements Serializable{
	public String getMyName() {
		return myName;
	}

	public void setMyName(String myName) {
		this.myName = myName;
	}

	public String getMyStudentID() {
		return myStudentID;
	}

	public void setMyStudentID(String myStudentID) {
		this.myStudentID = myStudentID;
	}

	public String getMyAcademy() {
		return myAcademy;
	}

	public void setMyAcademy(String myAcademy) {
		this.myAcademy = myAcademy;
	}

	public String getMySpecialty() {
		return mySpecialty;
	}

	public void setMySpecialty(String specialty) {
		this.mySpecialty = specialty;
	}

	public String toString() {
		String result = myAcademy + " , " + mySpecialty + " , " + myName
				+ " , " + myStudentID + " , ";
		result += firstAveGrade + " , " + secondAveGrade + " , "
				+ thridAveGrade + " , " + forthAveGrade;
		return result;
	}

	public float getFirstAveGrade() {
		return firstAveGrade;
	}

	public void setFirstAveGrade(float firstAveGrade) {
		this.firstAveGrade = firstAveGrade;
	}

	public float getSecondAveGrade() {
		return secondAveGrade;
	}

	public void setSecondAveGrade(float secondAveGrade) {
		this.secondAveGrade = secondAveGrade;
	}

	public float getThridAveGrade() {
		return thridAveGrade;
	}

	public void setThridAveGrade(float thridAveGrade) {
		this.thridAveGrade = thridAveGrade;
	}

	public float getForthAveGrade() {
		return forthAveGrade;
	}

	public void setForthAveGrade(float forthAveGrade) {
		this.forthAveGrade = forthAveGrade;
	}

	private String myName;
	private String myStudentID;
	private String myAcademy;//
	private String mySpecialty;//
	private float firstAveGrade;
	private float secondAveGrade;
	private float thridAveGrade;
	private float forthAveGrade;
}
