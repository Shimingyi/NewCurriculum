package cn.edu.sdu.online.modal;

import java.io.Serializable;
import java.sql.Timestamp;

public class MyHomeworkComment implements Serializable {

	@Override
	public String toString() {
		return "MyHomeworkComment [comment=" + comment + ", name=" + name
				+ ", time=" + time + ", homeworkid=" + homeworkid
				+ ", commentid=" + commentid + "]";
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public int getHomeworkid() {
		return homeworkid;
	}

	public void setHomeworkid(int homeworkid) {
		this.homeworkid = homeworkid;
	}

	public int getCommentid() {
		return commentid;
	}

	public void setCommentid(int commentid) {
		this.commentid = commentid;
	}

	private String comment;
	private String name;
	private String time;
	private int homeworkid;
	private int commentid;
}
