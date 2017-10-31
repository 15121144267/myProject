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
import com.banshengyuan.feima.dagger.component.DaggerCoupleActivityComponent;
import com.banshengyuan.feima.dagger.module.CoupleActivityModule;
import com.banshengyuan.feima.view.PresenterControl.CoupleControl;
import com.banshengyuan.feima.view.adapter.MyOrderFragmentAdapter;
import com.banshengyuan.feima.view.fragment.CouponAvailableFragment;
import com.banshengyuan.feima.view.fragment.CouponNotAvailableFragment;
import com.banshengyuan.feima.view.fragment.CouponPastFragment;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lei.he on 2017/6/28.
 * AddressActivity
 */

public class CoupleActivity extends BaseActivity implements CoupleControl.CoupleView {



    public static Intent getIntent(Context context) {
        return new Intent(context, CoupleActivity.class);
    }


    @BindView(R.id.middle_name)
    TextView mMiddleName;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.coupon_viewpager)
    ViewPager mCouponViewpager;
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;

    @Inject
    CoupleControl.PresenterCouple mPresenter;
    private List<Fragment> mFragments;
    private final String[] modules = {"可使用", "已使用", "已过期"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_couple);
        ButterKnife.bind(this);
        initializeInjector();
        supportActionBar(mToolbar, true);
        mMiddleName.setText("我的卡券");
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
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }

    @Override
    public Context getContext() {
        return this;
    }


    private void initView() {
        mFragments = new ArrayList<>();
        mFragments.add(CouponAvailableFragment.newInstance());
        mFragments.add(CouponNotAvailableFragment.newInstance());
        mFragments.add(CouponPastFragment.newInstance());
        MyOrderFragmentAdapter adapter = new MyOrderFragmentAdapter(getSupportFragmentManager(), mFragments, modules);
        mCouponViewpager.setOffscreenPageLimit(mFragments.size() - 1);
        mCouponViewpager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mCouponViewpager);
    }

    private void initData() {

    }


    private void initializeInjector() {
        DaggerCoupleActivityComponent.builder()
                .applicationComponent(getApplicationComponent())
                .coupleActivityModule(new CoupleActivityModule(CoupleActivity.this, this))
                .build().inject(this);
    }
}
