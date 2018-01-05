package com.banshengyuan.feima.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.dagger.component.DaggerMyOrderActivityComponent;
import com.banshengyuan.feima.dagger.module.MyOrderActivityModule;
import com.banshengyuan.feima.utils.ValueUtil;
import com.banshengyuan.feima.view.PresenterControl.MyOrderControl;
import com.banshengyuan.feima.view.adapter.MyOrderFragmentAdapter;
import com.banshengyuan.feima.view.fragment.AllOrderFragment;
import com.banshengyuan.feima.view.fragment.OrderCompleteFragment;
import com.banshengyuan.feima.view.fragment.PayCompleteOrderFragment;
import com.banshengyuan.feima.view.fragment.WaitPayOrderFragment;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lei.he on 2017/6/27.
 * MyOrderActivity
 */

public class MyOrderActivity extends BaseActivity implements MyOrderControl.MyOrderView {


    public static Intent getIntent(Context context) {
        return new Intent(context, MyOrderActivity.class);
    }

    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.order_viewpager)
    ViewPager mOrderViewpager;
    @BindView(R.id.middle_name)
    TextView mMiddleName;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @Inject
    MyOrderControl.PresenterMyOrder mPresenter;

    private final String[] orderModules = {"全部", "待付款", "待收货", "待评价"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);
        ButterKnife.bind(this);
        initializeInjector();
        supportActionBar(mToolbar, true);
        mMiddleName.setText("我的订单");
        initView();
        initData();
    }

    private void initData() {
    }

    private void initView() {
        List<Fragment> mFragments = new ArrayList<>();
        mFragments.add(AllOrderFragment.newInstance());
        mFragments.add(WaitPayOrderFragment.newInstance());
        mFragments.add(PayCompleteOrderFragment.newInstance());
        mFragments.add(OrderCompleteFragment.newInstance());
        MyOrderFragmentAdapter adapter = new MyOrderFragmentAdapter(getSupportFragmentManager(), mFragments, orderModules);
        mOrderViewpager.setOffscreenPageLimit(mFragments.size() - 1);
        mOrderViewpager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mOrderViewpager);
        ValueUtil.setIndicator(mTabLayout,20,20);
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
        DaggerMyOrderActivityComponent.builder()
                .applicationComponent(getApplicationComponent())
                .myOrderActivityModule(new MyOrderActivityModule(MyOrderActivity.this, this))
                .build().inject(this);
    }
}
