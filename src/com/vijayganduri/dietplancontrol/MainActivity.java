package com.vijayganduri.dietplancontrol;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.vijayganduri.embeddedtabs.R;

public class MainActivity extends SherlockFragmentActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		ActionBar actionBar = getSupportActionBar();
		
		setHasEmbeddedTabs(actionBar, true);
		
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        
        createNewTab(actionBar, R.drawable.device_access_mic);
        createNewTab(actionBar, R.drawable.collections_sort_by_size);
        
	}
	
	private Tab createNewTab(ActionBar actionBar, int iconResId){
		Tab tab = actionBar.newTab();
		tab.setIcon(iconResId);

        TabListener<SampleFragment> tl = new TabListener<SampleFragment>(this, SampleFragment.class);
        tab.setTabListener(tl);
		
        actionBar.addTab(tab);
		return tab;
	}
	
	public static void setHasEmbeddedTabs(Object inActionBar, final boolean inHasEmbeddedTabs)
	{
		// get the ActionBar class
		Class<?> actionBarClass = inActionBar.getClass();

		//This never works, as we are using actionbarsherlock
		// if it is a Jelly Bean implementation (ActionBarImplJB), get the super class (ActionBarImplICS)
		if ("android.support.v7.app.ActionBarImplJB".equals(actionBarClass.getName()))
		{
			actionBarClass = actionBarClass.getSuperclass();
		}

		try
		{
			// try to get the mActionBar field, because the current ActionBar is probably just a wrapper Class
			// if this fails, no worries, this will be an instance of the native ActionBar class or from the ActionBarImplBase class
			final Field actionBarField = actionBarClass.getDeclaredField("mActionBar");
			actionBarField.setAccessible(true);
			inActionBar = actionBarField.get(inActionBar);
			actionBarClass = inActionBar.getClass();
		}
		catch (IllegalAccessException e) {}
		catch (IllegalArgumentException e) {}
		catch (NoSuchFieldException e) {}

		try
		{
			// now call the method setHasEmbeddedTabs, this will put the tabs inside the ActionBar
			final Method method = actionBarClass.getDeclaredMethod("setHasEmbeddedTabs", new Class[] { Boolean.TYPE });
			method.setAccessible(true);
			method.invoke(inActionBar, new Object[]{ inHasEmbeddedTabs });
		}
		catch (NoSuchMethodException e)	{}
		catch (InvocationTargetException e) {}
		catch (IllegalAccessException e) {}
		catch (IllegalArgumentException e) {}
	}


    private class TabListener<T extends Fragment> implements
            ActionBar.TabListener {
        private Fragment mFragment;
        private final Activity mActivity;
        private final Class<T> mClass;
 
        /**
         * Constructor used each time a new tab is created.
         * 
         * @param activity
         *            The host Activity, used to instantiate the fragment
         * @param tag
         *            The identifier tag for the fragment
         * @param clz
         *            The fragment's Class, used to instantiate the fragment
         */
        public TabListener(Activity activity, Class<T> clz) {
            mActivity = activity;
            mClass = clz;
        }
 
        public void onTabSelected(Tab tab, FragmentTransaction ft) {
            // Check if the fragment is already initialized
            if (mFragment == null) {
                // If not, instantiate and add it to the activity
                mFragment = Fragment.instantiate(mActivity, mClass.getName());
                ft.add(android.R.id.content, mFragment);
            } else {
                // If it exists, simply attach it in order to show it
                ft.attach(mFragment);
            }
        }
 
        public void onTabUnselected(Tab tab, FragmentTransaction ft) {
            if (mFragment != null) {
                // Detach the fragment, because another one is being attached
                ft.detach(mFragment);
            }
        }
 
        public void onTabReselected(Tab tab, FragmentTransaction ft) {
            // User selected the already selected tab. Usually do nothing.
        }
    }
	
}