package com.banshengyuan.feima.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by helei on 2017/5/3.
 * MyFragmentAdapter
 */

public class MyFragmentAdapter extends FragmentPagerAdapter {
    private final List<Fragment> mList;
    public MyFragmentAdapter(FragmentManager fm, List<Fragment> fragmentList) {
        super(fm);
        mList = fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return mList.get(position);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

}
