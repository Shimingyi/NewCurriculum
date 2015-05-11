package cn.edu.sdu.online.newcurriculum;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.TabWidget;
import cn.edu.sdu.online.app.Main;
import cn.edu.sdu.online.listener.ActionBarListener;
import cn.edu.sdu.online.tab.ReleaseHomework;
import cn.edu.sdu.online.tab.SignTest;
import cn.edu.sdu.online.tab.loadQuestion;
import cn.edu.sdu.online.utils.DateUtil;

public class Activity_ConsultCenter extends FragmentActivity {

	TabHost mTabHost;
	ViewPager mViewPager;
	TabsAdapter mTabsAdapter;
	List<View> views;
	static Main app;
    static FragmentActivity mactivity;
	static DateUtil utils;
    private ActionBarListener actionBarListener;
    private ActionBar ActionBar;



	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.sub_fragment_tab_pager);
		utils = new DateUtil();
//        setActionBarListener(this);
//        this.invalidateOptionsMenu();
		mTabHost = (TabHost) findViewById(android.R.id.tabhost);
		mTabHost.setup();
		mViewPager = (ViewPager)findViewById(R.id.sub_pager);

		mTabsAdapter = new TabsAdapter(this, mTabHost, mViewPager);

		mTabsAdapter.addTab(mTabHost.newTabSpec("rank").setIndicator("签到"),
				SignTest.class, null);
		mTabsAdapter.addTab(mTabHost.newTabSpec("homework").setIndicator("问答"),
				loadQuestion.class, null);
		mTabsAdapter.addTab(mTabHost.newTabSpec("release").setIndicator("作业"),
				ReleaseHomework.class, null);

		if (savedInstanceState != null) {
			mTabHost.setCurrentTabByTag(savedInstanceState.getString("tab"));
		}
		
		ActionBar  = getActionBar();
		Drawable backgroundcolor = getResources().getDrawable(
				R.color.actionbarbg);
		ActionBar.setBackgroundDrawable(backgroundcolor);
	}

    public ActionBarListener getActionBarListener() {
        return actionBarListener;
    }

    public void setActionBarListener(ActionBarListener actionBarListener) {
        this.actionBarListener = actionBarListener;
    }
//    @Override
//    public void onPrepare(Menu menu) {
//
//        // TODO Auto-generated method stub
//        System.out.println("onPrepare");
//        MenuItem menuitem = menu.add("hello");
//        menuitem.setIcon(R.drawable.icon_back);
//        menuitem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
//        if ("hello".equals("签到")) {
//            menuitem.setOnMenuItemClickListener(new SignLister());
//        } else if ("hello".equals("问答")) {
//            menuitem.setOnMenuItemClickListener(new releaseQuestion());
//        } else {
//            menuitem.setOnMenuItemClickListener(new releseHomeworkListener());
//        }
//
//    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (actionBarListener != null)
            actionBarListener.onPrepare(menu);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        if (item.getItemId() == android.R.id.home)
//            if (homeButtonListener == null)
//                toggle();
//            else {
//                homeButtonListener.onHomeButtonClick();
//            }
        return super.onOptionsItemSelected(item);
    }

//
//	@Override
//	public void onDetach() {
//		// TODO Auto-generated method stub
//		super.onDetach();
//		try {
//			Field childFragmentManager = Fragment.class
//					.getDeclaredField("mChildFragmentManager");
//			childFragmentManager.setAccessible(true);
//			childFragmentManager.set(this, null);
//
//		} catch (NoSuchFieldException e) {
//			throw new RuntimeException(e);
//		} catch (IllegalAccessException e) {
//			throw new RuntimeException(e);
//		}
//	}



	/**
	 * This is a helper class that implements the management of tabs and all
	 * details of connecting a ViewPager with associated TabHost. It relies on a
	 * trick. Normally a tab host has a simple API for supplying a View or
	 * Intent that each tab will show. This is not sufficient for switching
	 * between pages. So instead we make the content part of the tab host 0dp
	 * high (it is not shown) and the TabsAdapter supplies its own dummy view to
	 * show as the tab content. It listens to changes in tabs, and takes care of
	 * switch to the correct paged in the ViewPager whenever the selected tab
	 * changes.
	 */

	public static class TabsAdapter extends FragmentPagerAdapter implements
			TabHost.OnTabChangeListener, ViewPager.OnPageChangeListener
            {
		private final Context mContext;
		private final TabHost mTabHost;
		private final ViewPager mViewPager;
		private final ArrayList<TabInfo> mTabs = new ArrayList<TabInfo>();
		String[] title = new String[] { "签到", "问答", "作业" };
		int[] icon = new int[] { R.drawable.icon_sign,
				R.drawable.icon_question, R.drawable.icon_homework };

		static String singletitle;
		static int singleicon;





        static final class TabInfo {
			@SuppressWarnings("unused")
			private final String tag;
			private final Class<?> clss;
			private final Bundle args;

			TabInfo(String _tag, Class<?> _class, Bundle _args) {
				tag = _tag;
				clss = _class;
				args = _args;
			}
		}

		static class DummyTabFactory implements TabHost.TabContentFactory {
			private final Context mContext;

			public DummyTabFactory(Context context) {
				mContext = context;
			}

			@Override
			public View createTabContent(String tag) {
				View v = new View(mContext);
				v.setMinimumWidth(0);
				v.setMinimumHeight(0);
				return v;
			}
		}

		public TabsAdapter(FragmentActivity activity, TabHost tabHost, ViewPager pager) {
			super(activity.getSupportFragmentManager());
			mContext = activity;
			mTabHost = tabHost;
			mViewPager = pager;
			mTabHost.setOnTabChangedListener(this);
			mViewPager.setAdapter(this);
			mViewPager.setOnPageChangeListener(this);

		}

		public void addTab(TabHost.TabSpec tabSpec, Class<?> clss, Bundle args) {
			tabSpec.setContent(new DummyTabFactory(mContext));
			String tag = tabSpec.getTag();

			TabInfo info = new TabInfo(tag, clss, args);
			mTabs.add(info);
			mTabHost.addTab(tabSpec);
			notifyDataSetChanged();
		}

		@Override
		public int getCount() {
			return mTabs.size();
		}

		@Override
		public Fragment getItem(int position) {
			TabInfo info = mTabs.get(position);
			return Fragment.instantiate(mContext, info.clss.getName(),
					info.args);
		}

		@SuppressLint("NewApi")
		@Override
		public void onTabChanged(String tabId) {
			int position = mTabHost.getCurrentTab();
			mViewPager.setCurrentItem(position);
			singletitle = title[position];
			singleicon = icon[position];

//            setActionBarListener(this);
//			mactivity.invalidateOptionsMenu();

		}

		@Override
		public void onPageScrolled(int position, float positionOffset,
				int positionOffsetPixels) {
		}

		@Override
		public void onPageSelected(int position) {
			// Unfortunately when TabHost changes the current tab, it kindly
			// also takes care of putting focus on it when not in touch mode.
			// The jerk.
			// This hack tries to prevent this from pulling focus out of our
			// ViewPager.
			TabWidget widget = mTabHost.getTabWidget();
			int oldFocusability = widget.getDescendantFocusability();
			widget.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
			mTabHost.setCurrentTab(position);
			widget.setDescendantFocusability(oldFocusability);

		}

		@Override
		public void onPageScrollStateChanged(int state) {
		}


	}

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
            }

