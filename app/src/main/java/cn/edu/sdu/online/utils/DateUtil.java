package cn.edu.sdu.online.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

	int hour;
	int minute;
	int second;
	int year;
	int month;
	int day;
	int secondday;

	public int getSecondday() {
		return secondday;
	}

	public void setSecondday(int secondday) {
		this.secondday = secondday;
	}

	public DateUtil() {
		calDate();
	}

	public void calDate() {
		long startTime = System.currentTimeMillis();
		Date date = new Date(startTime);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String[] a = sdf.format(date).split(" ");
		String aaa[] = a[0].split("-");
		String[] aa = a[1].split(":");
		this.setHour(Integer.valueOf(aa[0]));
		this.setMinute(Integer.valueOf(aa[1]));
		this.setSecond(Integer.valueOf(aa[2]));
		this.setYear(Integer.parseInt(aaa[0]));
		this.setMonth(Integer.parseInt(aaa[1]));
		this.setDay(Integer.parseInt(aaa[2]));

	}

	public int SecondDay() {
		Calendar ca = Calendar.getInstance();
		ca.setTime(new Date());
		ca.add(Calendar.DATE, 1);
		Date dd = ca.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// ��ȡ�ڶ��������
		String[] a = sdf.format(dd).split(" ");
		String aaa[] = a[0].split("-");
		String[] aa = a[1].split(":");
		this.setSecondday(Integer.parseInt(aaa[2]));
		return secondday;
	}

	public int getHour() {
		return hour;
	}

	public void setHour(int hour) {
		this.hour = hour;
	}

	public int getMinute() {
		return minute;
	}

	public void setMinute(int minute) {
		this.minute = minute;
	}

	public int getSecond() {
		return second;
	}

	public void setSecond(int second) {
		this.second = second;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

}
