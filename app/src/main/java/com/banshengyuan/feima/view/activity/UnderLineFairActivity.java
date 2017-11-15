package com.banshengyuan.feima.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.dagger.component.DaggerUnderLineFairActivityComponent;
import com.banshengyuan.feima.dagger.module.UnderLineFairActivityModule;
import com.banshengyuan.feima.utils.ValueUtil;
import com.banshengyuan.feima.view.PresenterControl.UnderLineFairControl;
import com.banshengyuan.feima.view.adapter.ActivityUnderLineFairAdapter;
import com.banshengyuan.feima.view.adapter.MyOrderFragmentAdapter;
import com.banshengyuan.feima.view.fragment.UnderLineFairFragment;
import com.banshengyuan.feima.view.fragment.UnderLineProductListFragment;
import com.banshengyuan.feima.view.fragment.UnderLineShopFragment;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lei.he on 2017/6/5.
 * WelcomeActivity
 */

public class UnderLineFairActivity extends BaseActivity implements UnderLineFairControl.UnderLineFairView {


    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.collapsingToolbarLayout)
    CollapsingToolbarLayout mCollapsingToolbarLayout;
    @BindView(R.id.fair_under_line_tab_layout)
    TabLayout mFairUnderLineTabLayout;
    @BindView(R.id.fair_under_line_view_pager)
    ViewPager mFairUnderLineViewPager;
    @BindView(R.id.fair_under_line_recyclerView)
    RecyclerView mFairUnderLineRecyclerView;

    public static Intent getActivityDetailIntent(Context context) {
        Intent intent = new Intent(context, UnderLineFairActivity.class);
        return intent;
    }

    @Inject
    UnderLineFairControl.PresenterUnderLineFair mPresenter;

    private final String[] modules = {"市集", "商家", "产品"};
    private ActivityUnderLineFairAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_under_line_fair);
        initializeInjector();
        ButterKnife.bind(this);
        supportActionBar(mToolbar, true);
        initView();
        initData();

    }

    private void initData() {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        mAdapter.setNewData(list);
    }

    private void initView() {
        mCollapsingToolbarLayout.setTitle("线下市集");
        mCollapsingToolbarLayout.setExpandedTitleColor(ContextCompat.getColor(this, R.color.white));
        mCollapsingToolbarLayout.setCollapsedTitleTextColor(ContextCompat.getColor(this, R.color.black));
        mCollapsingToolbarLayout.setCollapsedTitleGravity(Gravity.CENTER);

        mFairUnderLineRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        mAdapter = new ActivityUnderLineFairAdapter(null,this);
        mFairUnderLineRecyclerView.setAdapter(mAdapter);
        List<Fragment> mFragments = new ArrayList<>();
        mFragments.add(UnderLineFairFragment.newInstance());
        mFragments.add(UnderLineShopFragment.newInstance());
        mFragments.add(UnderLineProductListFragment.newInstance());
        MyOrderFragmentAdapter adapter = new MyOrderFragmentAdapter(getSupportFragmentManager(), mFragments, modules);
        mFairUnderLineViewPager.setOffscreenPageLimit(mFragments.size() - 1);
        mFairUnderLineViewPager.setAdapter(adapter);
        mFairUnderLineTabLayout.setupWithViewPager(mFairUnderLineViewPager);
        ValueUtil.setIndicator(mFairUnderLineTabLayout, 40, 40);
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

    private void initializeInjector() {
        DaggerUnderLineFairActivityComponent.builder()
                .applicationComponent(getApplicationComponent())
                .underLineFairActivityModule(new UnderLineFairActivityModule(UnderLineFairActivity.this, this))
                .build().inject(this);
    }
}
