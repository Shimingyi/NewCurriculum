package cn.edu.sdu.online.modal;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;

public class Question implements Serializable {

	String question;
	String brief;
	int quesid;
	String userid;
	String time;
	int answernum;

	public int getAnswernum() {
		return answernum;
	}

	public void setAnswernum(int answernum) {
		this.answernum = answernum;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getBrief() {
		return brief;
	}

	@Override
	public String toString() {
		return "Question [question=" + question + ", brief=" + brief
				+ ", quesid=" + quesid + ", ans=" + ans + "]";
	}

	public void setBrief(String brief) {
		this.brief = brief;
	}

	public int getQuesid() {
		return quesid;
	}

	public void setQuesid(int quesid) {
		this.quesid = quesid;
	}

	public ArrayList<Answer> getAns() {
		return ans;
	}

	public void setAns(ArrayList<Answer> ans) {
		this.ans = ans;
	}

	ArrayList<Answer> ans;
}
