package cn.edu.sdu.online.modal;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;

public class MyHomework implements Serializable {
	private String name;
	private String homework;
	private String deadline;
	private String subject;
	private String time;
	private String ReleasePerson;
	private int commentnum;

	public int getCommentnum() {
		return commentnum;
	}

	public void setCommentnum(int commentnum) {
		this.commentnum = commentnum;
	}

	private int homeworkid;
	private ArrayList<MyHomeworkComment> homeworklist;

	public ArrayList<MyHomeworkComment> getHomeworklist() {
		return homeworklist;
	}

	public void setHomeworklist(ArrayList<MyHomeworkComment> homeworklist) {
		this.homeworklist = homeworklist;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getReleasePerson() {
		return ReleasePerson;
	}

	public void setReleasePerson(String releasePerson) {
		ReleasePerson = releasePerson;
	}

	public int getHomeworkid() {
		return homeworkid;
	}

	public void setHomeworkid(int homeworkid) {
		this.homeworkid = homeworkid;
	}

	@Override
	public String toString() {
		return "MyHomework [name=" + name + ",  deadline=" + deadline
				+ ", homework=" + homework + "]";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDeadline() {
		return deadline;
	}

	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}

	public String getHomework() {
		return homework;
	}

	public void setHomework(String homework) {
		this.homework = homework;
	}

}
