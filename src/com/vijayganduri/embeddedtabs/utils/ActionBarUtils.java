package com.vijayganduri.embeddedtabs.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ActionBarUtils {

	public static void setHasEmbeddedTabs(Object inActionBar, final boolean inHasEmbeddedTabs){
		// get the ActionBar class
		Class<?> actionBarClass = inActionBar.getClass();

		//This never works, as we are using actionbarsherlock
		// if it is a Jelly Bean implementation (ActionBarImplJB), get the super class (ActionBarImplICS)
		if ("android.support.v7.app.ActionBarImplJB".equals(actionBarClass.getName())){
			actionBarClass = actionBarClass.getSuperclass();
		}

		try{
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

		try{
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

}