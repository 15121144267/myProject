package com.banshengyuan.feima.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.view.fragment.GuideFragment;


public class GuideAdapter extends FragmentPagerAdapter implements IconPagerAdapter {
    protected static final String[] CONTENT = new String[] { "1", "2", "3" };
    protected static final int[] ICONS = new int[] {
            R.mipmap.logo,
            R.mipmap.logo,
            R.mipmap.logo
    };

    private int mCount = CONTENT.length;

    public GuideAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return GuideFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return mCount;
    }

    @Override
    public CharSequence getPageTitle(int position) {
      return GuideAdapter.CONTENT[position % CONTENT.length];
    }

    @Override
    public int getIconResId(int index) {
      return ICONS[index % ICONS.length];
    }

}