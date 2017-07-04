package com.dispatching.feima.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;

import com.dispatching.feima.R;
import com.dispatching.feima.view.adapter.GuideAdapter;
import com.dispatching.feima.view.customview.CirclePageIndicator;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lei.he on 2017/6/5.
 * WelcomeActivity
 */

public class GuideActivity extends BaseActivity {
    @BindView(R.id.guide_pager)
    ViewPager mGuidePager;
    @BindView(R.id.guide_indicator)
    CirclePageIndicator mGuideIndicator;
    private GuideAdapter mAdapter;
    public static Intent getIntent(Context context) {
        return new Intent(context, GuideActivity.class);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        ButterKnife.bind(this);

        mAdapter = new GuideAdapter(getSupportFragmentManager());
        mGuidePager.setAdapter(mAdapter);
        mGuideIndicator.setViewPager(mGuidePager);
    }
}
