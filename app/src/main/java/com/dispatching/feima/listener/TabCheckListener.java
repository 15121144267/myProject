package com.dispatching.feima.listener;

import android.support.design.widget.TabLayout;

/**
 * Created by helei on 2017/4/29.
 */

public abstract class TabCheckListener implements TabLayout.OnTabSelectedListener {

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        onMyTabSelected(tab);
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    public abstract void onMyTabSelected(TabLayout.Tab tab);
}
