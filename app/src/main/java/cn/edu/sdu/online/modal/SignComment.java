package cn.edu.sdu.online.modal;

import java.io.Serializable;
import java.sql.Timestamp;

public class SignComment implements Serializable {

	int commentid;
	int rankid;
	String comment;
	Timestamp time;
	String username;

	public int getCommentid() {
		return commentid;
	}

	@Override
	public String toString() {
		return "SignComment [commentid=" + commentid + ", rankid=" + rankid
				+ ", comment=" + comment + ", time=" + time + ", username="
				+ username + "]";
	}

	public void setCommentid(int commentid) {
		this.commentid = commentid;
	}

	public int getRankid() {
		return rankid;
	}

	public void setRankid(int rankid) {
		this.rankid = rankid;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Timestamp getTime() {
		return time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
