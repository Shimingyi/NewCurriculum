package cn.edu.sdu.online.utils;

import java.util.List;

import android.support.v4.app.Fragment;

public class LibFragments {
	
	private List<String> title;
	private List<Fragment> fragments;
	public List<String> getTitle() {
		return title;
	}
	public void setTitle(List<String> title) {
		this.title = title;
	}
	public List<Fragment> getFragments() {
		return fragments;
	}
	public void setFragments(List<Fragment> fragments) {
		this.fragments = fragments;
	}

}
