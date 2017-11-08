package com.banshengyuan.feima.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.dagger.component.DaggerFairDetailActivityComponent;
import com.banshengyuan.feima.dagger.module.FairDetailActivityModule;
import com.banshengyuan.feima.utils.ValueUtil;
import com.banshengyuan.feima.view.PresenterControl.FairDetailControl;
import com.banshengyuan.feima.view.adapter.MyOrderFragmentAdapter;
import com.banshengyuan.feima.view.fragment.CelebrityFragment;
import com.banshengyuan.feima.view.fragment.FollowFragment;
import com.banshengyuan.feima.view.fragment.TrendsFragment;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lei.he on 2017/6/5.
 * WelcomeActivity
 */

public class FairDetailActivity extends BaseActivity implements FairDetailControl.FairDetailView {


    public static Intent getIntent(Context context) {
        return new Intent(context, FairDetailActivity.class);
    }

    @BindView(R.id.toolbar_right_icon)
    ImageView mToolbarRightIcon;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.collapsingToolbarLayout)
    CollapsingToolbarLayout mCollapsingToolbarLayout;
    @BindView(R.id.fair_detail_tab_layout)
    TabLayout mFairDetailTabLayout;
    @BindView(R.id.fair_detail_view_pager)
    ViewPager mFairDetailViewPager;

    @Inject
    FairDetailControl.PresenterFairDetail mPresenter;
    private final String[] modules = {"最新", "商家", "产品"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fair_detail);
        ButterKnife.bind(this);
        supportActionBar(mToolbar, true);
        initializeInjector();
        initView();
        initData();
    }

    @Override
    public void showLoading(String msg) {
        showDialogLoading(msg);
    }

    @Override
    public void dismissLoading() {
        dismissDialogLoading();
    }

    @Override
    public void showToast(String message) {
        showBaseToast(message);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }

    private void initData() {

    }

    private void initView() {
        List<Fragment> mFragments = new ArrayList<>();
        mFragments.add(FollowFragment.newInstance());
        mFragments.add(CelebrityFragment.newInstance());
        mFragments.add(TrendsFragment.newInstance());
        MyOrderFragmentAdapter adapter = new MyOrderFragmentAdapter(getSupportFragmentManager(), mFragments, modules);
        mFairDetailViewPager.setOffscreenPageLimit(mFragments.size() - 1);
        mFairDetailViewPager.setAdapter(adapter);
        mFairDetailTabLayout.setupWithViewPager(mFairDetailViewPager);
        ValueUtil.setIndicator(mFairDetailTabLayout, 40, 40);
    }

    private void initializeInjector() {
        DaggerFairDetailActivityComponent.builder()
                .applicationComponent(getApplicationComponent())
                .fairDetailActivityModule(new FairDetailActivityModule(FairDetailActivity.this, this))
                .build().inject(this);
    }
}
