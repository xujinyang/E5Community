package com.gaobo.e5community.util;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

public class FragmentUtil {
	public static void getFragment(Class<? extends Fragment> clazz, int r,
			String tag, FragmentManager fm, FragmentTransaction fg) {
		Fragment f;
		fg = fm.beginTransaction();
		f = (Fragment) fm.findFragmentByTag(tag);
		Log.i("FragmentUtil", clazz.getSimpleName());
		if (f == null) {
			try {
				f = clazz.newInstance();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
			// fg.add(r, f, tag);
			// fg.addToBackStack(null);
		}
		fg.replace(r, f, tag);
		fg.commit();
	}
}
