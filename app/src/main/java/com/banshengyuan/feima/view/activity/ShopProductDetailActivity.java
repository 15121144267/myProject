package com.banshengyuan.feima.view.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.dagger.component.DaggerShopProductDetailActivityComponent;
import com.banshengyuan.feima.dagger.module.ShopProductDetailActivityModule;
import com.banshengyuan.feima.entity.ShopDetailCouponListResponse;
import com.banshengyuan.feima.entity.ShopDetailProductListResponse;
import com.banshengyuan.feima.entity.StoreDetailResponse;
import com.banshengyuan.feima.help.GlideLoader;
import com.banshengyuan.feima.listener.AppBarStateChangeListener;
import com.banshengyuan.feima.utils.ValueUtil;
import com.banshengyuan.feima.view.PresenterControl.ShopProductDetailControl;
import com.banshengyuan.feima.view.adapter.MyOrderFragmentAdapter;
import com.banshengyuan.feima.view.fragment.CouponListFragment;
import com.banshengyuan.feima.view.fragment.ProductListFragment;
import com.banshengyuan.feima.view.fragment.SummaryFragment;
import com.jakewharton.rxbinding2.view.RxView;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
    @BindView(R.id.shop_detail_name)
    TextView mShopDetailName;
    @BindView(R.id.shop_detail_phone)
    ImageView mShopDetailPhone;
    @BindView(R.id.shop_detail_collection)
    ImageView mShopDetailCollection;
    @BindView(R.id.shop_detail_address)
    TextView mShopDetailAddress;
    @BindView(R.id.middle_name)
    TextView mMiddleName;
    @BindView(R.id.appBarLayout)
    AppBarLayout mAppBarLayout;

    public static Intent getActivityDetailIntent(Context context, Integer shopId) {
        Intent intent = new Intent(context, ShopProductDetailActivity.class);
        intent.putExtra("shopId", shopId);
        return intent;
    }

    private final String[] modules = {"产品", "优惠", "简介"};
    @Inject
    ShopProductDetailControl.PresenterShopProductDetail mPresenter;
    private Integer mShopId;
    private StoreDetailResponse.InfoBean mInfoBean;

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

    @Override
    public void getStoreDetailSuccess(StoreDetailResponse response) {
        mInfoBean = response.info;
        if (mInfoBean != null) {
            if (mInfoBean.top_img != null && mInfoBean.top_img.size() > 0) {
                mShopDetailDetailBanner.setImages(mInfoBean.top_img).setImageLoader(new GlideLoader()).start();
            }
            mShopDetailName.setText(mInfoBean.name);
            mMiddleName.setText(mInfoBean.name);
            mShopDetailAddress.setText(mInfoBean.address);
            ((SummaryFragment) getSupportFragmentManager().getFragments().get(2)).setSummaryText(mInfoBean.summary);
        } else {
            mShopDetailPhone.setVisibility(View.GONE);
        }
    }

    @Override
    public void getStoreDetailFail() {
        showToast("获取数据异常");
    }

    private void initData() {
        //请求店铺详情
        mPresenter.requestStoreDetail(mShopId);
    }

    private void initView() {
        mShopId = getIntent().getIntExtra("shopId", 0);
        mShopDetailDetailBanner.isAutoPlay(false);
        List<Fragment> mFragments = new ArrayList<>();
        mFragments.add(ProductListFragment.newInstance());
        mFragments.add(CouponListFragment.newInstance());
        mFragments.add(SummaryFragment.newInstance());
        MyOrderFragmentAdapter adapter = new MyOrderFragmentAdapter(getSupportFragmentManager(), mFragments, modules);
        mShopDetailViewPager.setOffscreenPageLimit(mFragments.size() - 1);
        mShopDetailViewPager.setAdapter(adapter);
        mShopDetailTabLayout.setupWithViewPager(mShopDetailViewPager);
        ValueUtil.setIndicator(mShopDetailTabLayout, 40, 40);
        RxView.clicks(mShopDetailPhone).throttleFirst(1, TimeUnit.SECONDS).subscribe(o -> {
            try {
                Uri uri = Uri.parse("tel:" + mInfoBean.mobile);
                Intent intent = new Intent(Intent.ACTION_DIAL, uri);
                startActivity(intent);
            } catch (Exception e) {
                showToast("该设备无打电话功能");
            }
        });
        mAppBarLayout.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                if (state == State.EXPANDED) {
                    //展开状态
                    mMiddleName.setVisibility(View.GONE);
                } else if (state == State.COLLAPSED) {
                    //折叠状态
                    mMiddleName.setVisibility(View.VISIBLE);
                } else {
                    //中间状态
                    mMiddleName.setVisibility(View.GONE);
                }
            }
        });
    }

    public Integer getShopId() {
        return mShopId;
    }

    @Override
    public void getStoreProductListSuccess(ShopDetailProductListResponse response) {

    }

    @Override
    public void getStoreProductListFail() {

    }

    @Override
    public void getStoreCouponListSuccess(ShopDetailCouponListResponse response) {

    }

    @Override
    public void getStoreCouponListFail() {

    }

    @Override
    public void getCouponInfoSuccess() {

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
