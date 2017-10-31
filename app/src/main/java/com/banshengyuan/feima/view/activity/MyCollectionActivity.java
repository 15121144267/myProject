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
import com.banshengyuan.feima.dagger.component.DaggerCollectionActivityComponent;
import com.banshengyuan.feima.dagger.module.CollectionActivityModule;
import com.banshengyuan.feima.view.PresenterControl.CollectionControl;
import com.banshengyuan.feima.view.adapter.MyOrderFragmentAdapter;
import com.banshengyuan.feima.view.fragment.CollectionBlockFragment;
import com.banshengyuan.feima.view.fragment.CollectionFairFragment;
import com.banshengyuan.feima.view.fragment.CollectionHotFragment;
import com.banshengyuan.feima.view.fragment.CollectionProductFragment;
import com.banshengyuan.feima.view.fragment.CollectionShopFragment;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lei.he on 2017/6/28.
 * AddAddressActivity
 */

public class MyCollectionActivity extends BaseActivity implements CollectionControl.CollectionView {

    public static Intent getIntent(Context context) {
        return new Intent(context, MyCollectionActivity.class);
    }

    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.coupon_viewpager)
    ViewPager mCouponViewpager;
    @BindView(R.id.middle_name)
    TextView mMiddleName;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @Inject
    CollectionControl.PresenterCollection mPresenter;
    private List<Fragment> mFragments;
    private final String[] modules = {"市集", "产品", "热闹", "商家", "街区"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);
        ButterKnife.bind(this);
        initializeInjector();
        supportActionBar(mToolbar, true);
        mMiddleName.setText("我的收藏");
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

    private void initView() {

    }

    private void initData() {
        mFragments = new ArrayList<>();
        mFragments.add(CollectionFairFragment.newInstance());
        mFragments.add(CollectionProductFragment.newInstance());
        mFragments.add(CollectionHotFragment.newInstance());
        mFragments.add(CollectionShopFragment.newInstance());
        mFragments.add(CollectionBlockFragment.newInstance());
        MyOrderFragmentAdapter adapter = new MyOrderFragmentAdapter(getSupportFragmentManager(), mFragments, modules);
        mCouponViewpager.setOffscreenPageLimit(mFragments.size() - 1);
        mCouponViewpager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mCouponViewpager);
    }

    private void initializeInjector() {
        DaggerCollectionActivityComponent.builder()
                .applicationComponent(getApplicationComponent())
                .collectionActivityModule(new CollectionActivityModule(MyCollectionActivity.this, this))
                .build().inject(this);
    }
}
