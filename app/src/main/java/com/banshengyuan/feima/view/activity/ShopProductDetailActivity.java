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
import android.view.View;
import android.widget.ImageView;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.dagger.component.DaggerShopProductDetailActivityComponent;
import com.banshengyuan.feima.dagger.module.ShopProductDetailActivityModule;
import com.banshengyuan.feima.help.GlideLoader;
import com.banshengyuan.feima.utils.ValueUtil;
import com.banshengyuan.feima.view.PresenterControl.ShopProductDetailControl;
import com.banshengyuan.feima.view.adapter.MyOrderFragmentAdapter;
import com.banshengyuan.feima.view.fragment.CouponListFragment;
import com.banshengyuan.feima.view.fragment.ProductListFragment;
import com.banshengyuan.feima.view.fragment.SummaryFragment;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lei.he on 2017/6/5.
 * WelcomeActivity
 */

public class ShopProductDetailActivity extends BaseActivity implements ShopProductDetailControl.ShopProductDetailView {


    @BindView(R.id.shop_detail_detail_banner)
    Banner mShopDetailDetailBanner;
    @BindView(R.id.toolbar_right_icon)
    ImageView mToolbarRightIcon;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.collapsingToolbarLayout)
    CollapsingToolbarLayout mCollapsingToolbarLayout;
    @BindView(R.id.shop_detail_tab_layout)
    TabLayout mShopDetailTabLayout;
    @BindView(R.id.shop_detail_view_pager)
    ViewPager mShopDetailViewPager;

    public static Intent getActivityDetailIntent(Context context) {
        Intent intent = new Intent(context, ShopProductDetailActivity.class);
        return intent;
    }

    private final String[] modules = {"产品", "优惠", "简介"};
    @Inject
    ShopProductDetailControl.PresenterShopProductDetail mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_product_detail);
        initializeInjector();
        ButterKnife.bind(this);
        supportActionBar(mToolbar, true);
        mToolbarRightIcon.setVisibility(View.VISIBLE);
        initView();
        initData();

    }

    private void initData() {

    }

    private void initView() {
        List<Integer> mImageList = new ArrayList<>();
        mImageList.add(R.mipmap.header);
        mImageList.add(R.mipmap.header);
        mImageList.add(R.mipmap.header);
        mShopDetailDetailBanner.isAutoPlay(false);
        mShopDetailDetailBanner.setImages(mImageList).setImageLoader(new GlideLoader()).start();

        List<Fragment> mFragments = new ArrayList<>();
        mFragments.add(ProductListFragment.newInstance());
        mFragments.add(CouponListFragment.newInstance());
        mFragments.add(SummaryFragment.newInstance());
        MyOrderFragmentAdapter adapter = new MyOrderFragmentAdapter(getSupportFragmentManager(), mFragments, modules);
        mShopDetailViewPager.setOffscreenPageLimit(mFragments.size() - 1);
        mShopDetailViewPager.setAdapter(adapter);
        mShopDetailTabLayout.setupWithViewPager(mShopDetailViewPager);
        ValueUtil.setIndicator(mShopDetailTabLayout, 40, 40);
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
        DaggerShopProductDetailActivityComponent.builder()
                .applicationComponent(getApplicationComponent())
                .shopProductDetailActivityModule(new ShopProductDetailActivityModule(ShopProductDetailActivity.this, this))
                .build().inject(this);
    }
}
