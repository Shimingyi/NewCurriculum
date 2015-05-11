package cn.edu.sdu.online.modal;

import java.io.Serializable;

public class MyInfo implements Serializable {
	private String name;
	private String sex;
	private String talktome;
	private String aim;
	private String imagepath;
	private String password;
	private String ifsign;
	private int signrank;
	private int signcontinue;
	private int signall;
	private int day;

	public String getIfsign() {
		return ifsign;
	}

	public void setIfsign(String ifsign) {
		this.ifsign = ifsign;
	}

	public int getSignrank() {
		return signrank;
	}

	public void setSignrank(int signrank) {
		this.signrank = signrank;
	}

	public int getSigncontinue() {
		return signcontinue;
	}

	public void setSigncontinue(int signcontinue) {
		this.signcontinue = signcontinue;
	}

	public int getSignall() {
		return signall;
	}

	public void setSignall(int signall) {
		this.signall = signall;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return "MyInfo [name=" + name + ", sex=" + sex + ", talktome="
				+ talktome + ", aim=" + aim + ", imagepath=" + imagepath + "]";
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getTalktome() {
		return talktome;
	}

	public void setTalktome(String talktome) {
		this.talktome = talktome;
	}

	public String getAim() {
		return aim;
	}

	public void setAim(String aim) {
		this.aim = aim;
	}

	public String getImagepath() {
		return imagepath;
	}

	public void setImagepath(String imagepath) {
		this.imagepath = imagepath;
	}

}
