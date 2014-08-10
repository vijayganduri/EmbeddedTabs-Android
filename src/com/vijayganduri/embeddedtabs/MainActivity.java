package com.vijayganduri.embeddedtabs;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager.OnPageChangeListener;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.ActionBar.TabListener;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.matthieu.ViewPagerParallax;
import com.vijayganduri.embeddedtabs.utils.ActionBarUtils;

public class MainActivity extends SherlockFragmentActivity implements TabListener, OnPageChangeListener{

	DemoCollectionPagerAdapter mDemoCollectionPagerAdapter;
	ViewPagerParallax mViewPager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		ActionBar actionBar = getSupportActionBar();

		//This code forces the actionbar tabs into the actionbar, as it is observed in landscape or tablet mode 
		ActionBarUtils.setHasEmbeddedTabs(actionBar, true);

		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		
		createNewTab(actionBar, R.drawable.tab_icon_mic_selector);
		createNewTab(actionBar, R.drawable.tab_icon_history_selector);

		mDemoCollectionPagerAdapter = new DemoCollectionPagerAdapter(getSupportFragmentManager());
		mViewPager = (ViewPagerParallax) findViewById(R.id.pager);		
		mViewPager.setOnPageChangeListener(this);
		mViewPager.set_max_pages(2);
		mViewPager.setBackgroundAsset(R.drawable.green_blue_blur);
		mViewPager.setAdapter(mDemoCollectionPagerAdapter);
		mViewPager.setCurrentItem(0);
	}

	private void createNewTab(ActionBar actionBar, int iconResId){
		Tab tab = actionBar.newTab();
		tab.setIcon(iconResId);
		tab.setTabListener(this);
		actionBar.addTab(tab);
	}


	public class DemoCollectionPagerAdapter extends FragmentStatePagerAdapter {
		public DemoCollectionPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int i) {	       
			return i==0?new FirstFragment():new SecondFragment();
		}

		@Override
		public int getCount() {
			return 2;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return String.format("PAGE %d", (position + 1));
		}
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		if(mViewPager!=null){
			mViewPager.setCurrentItem(tab.getPosition(), true);
		}
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageSelected(int position) {
		getSupportActionBar().setSelectedNavigationItem(position);
	}
	
}