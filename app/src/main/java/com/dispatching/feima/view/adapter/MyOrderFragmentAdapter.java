package com.dispatching.feima.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by helei on 2017/5/3.
 * MyFragmentAdapter
 */

public class MyOrderFragmentAdapter extends FragmentPagerAdapter {
    private final List<Fragment> mList;
    private final String[] modules;
    public MyOrderFragmentAdapter(FragmentManager fm, List<Fragment> fragmentList, String[] array) {
        super(fm);
        mList = fragmentList;
        modules =array;
    }

    @Override
    public Fragment getItem(int position) {
        return mList.get(position);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return modules[position];
    }

}
