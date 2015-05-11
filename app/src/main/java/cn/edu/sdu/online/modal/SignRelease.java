package cn.edu.sdu.online.modal;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;

public class SignRelease implements Serializable {
	String name;
	String releaserank;
	String time;
	int zan;
//	@NoAutoIncrement
//	@Id(column = "rankid")
	int rankid;
	// int num;
	//
	// public int getNum() {
	// return num;
	// }
	//
	// public void setNum(int num) {
	// this.num = num;
	// }
	int numcomment;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	ArrayList<SignComment> commentlist;

	public ArrayList<SignComment> getCommentlist() {
		return commentlist;
	}

	public void setCommentlist(ArrayList<SignComment> commentlist) {
		this.commentlist = commentlist;
	}

	public String getReleaserank() {
		return releaserank;
	}

	public void setReleaserank(String releaserank) {
		this.releaserank = releaserank;
	}

	public String getTime() {
		return time;
	}

	@Override
	public String toString() {
		return "SignRelease [name=" + name + ", releaserank=" + releaserank
				+ ", time=" + time + ", zan=" + zan + ", rankid=" + rankid
				+ ", numcomment=" + numcomment + "]";
	}

	public void setTime(String time) {
		this.time = time;
	}

	public int getZan() {
		return zan;
	}

	public void setZan(int zan) {
		this.zan = zan;
	}

	public int getRankid() {
		return rankid;
	}

	public void setRankid(int rankid) {
		this.rankid = rankid;
	}

	public int getNumcomment() {
		return numcomment;
	}

	public void setNumcomment(int numcomment) {
		this.numcomment = numcomment;
	}

}
