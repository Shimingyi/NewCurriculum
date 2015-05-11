package cn.edu.sdu.online.adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import cn.edu.sdu.online.fragement.ReborrowBookFragment;
import cn.edu.sdu.online.fragement.SearchBookFragment;



public class LibViewPagerAdapter extends FragmentPagerAdapter{
	private SearchBookFragment searchfragment;
	private ReborrowBookFragment reborrowbookfragment;
	
	
	public LibViewPagerAdapter(FragmentManager fragmentManager) {
		super(fragmentManager);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Fragment getItem(int position) {
		// TODO Auto-generated method stub
		switch (position){
		case 0:
			searchfragment = new SearchBookFragment();
			return searchfragment;
		case 1:
			reborrowbookfragment = new ReborrowBookFragment();
			return reborrowbookfragment;
			
		}
		return null;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 2;
	}

}
