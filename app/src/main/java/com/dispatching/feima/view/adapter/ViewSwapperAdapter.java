package com.dispatching.feima.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.dispatching.feima.view.fragment.CompletedOrderFragment;
import com.dispatching.feima.view.fragment.PendingOrderFragment;
import com.dispatching.feima.view.fragment.SendingOrderFragment;
import com.example.mylibrary.adapter.FragmentStateAdapter;


public class ViewSwapperAdapter extends FragmentStateAdapter {

    private static final int INDEX_MAIN = 0;
    private static final int INDEX_ACTIVITY = 1;
    private static final int INDEX_PERSION= 2;

    public ViewSwapperAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case INDEX_MAIN:
                return PendingOrderFragment.newInstance();
            case INDEX_ACTIVITY:
                return SendingOrderFragment.newInstance();
            case INDEX_PERSION:
                return CompletedOrderFragment.newInstance();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }

}
