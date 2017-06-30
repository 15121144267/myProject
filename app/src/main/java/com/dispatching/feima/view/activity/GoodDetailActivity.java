package com.dispatching.feima.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dispatching.feima.R;
import com.dispatching.feima.dagger.component.DaggerGoodsDetailActivityComponent;
import com.dispatching.feima.dagger.module.GoodsDetailActivityModule;
import com.dispatching.feima.view.PresenterControl.GoodsDetailControl;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lei.he on 2017/6/30.
 * GoodDetailActivity
 */

public class GoodDetailActivity extends BaseActivity implements GoodsDetailControl.GoodsDetailView {

    @BindView(R.id.toolbar_right_icon)
    ImageView mToolbarRightIcon;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.collapsingToolbarLayout)
    CollapsingToolbarLayout mCollapsingToolbarLayout;
    @BindView(R.id.goods_des)
    TextView mGoodsDes;
    @BindView(R.id.goods_name)
    TextView mGoodsName;
    @BindView(R.id.goods_price)
    TextView mGoodsPrice;
    @BindView(R.id.goods_specification)
    TextView mGoodsSpecification;
    @BindView(R.id.expand_good_detail)
    ImageButton mExpandGoodDetail;
    @BindView(R.id.goods_detail_button)
    Button mGoodsDetailButton;
    @BindView(R.id.goods_detail)
    ImageView mGoodsDetail;
    @BindView(R.id.goods_notice_before)
    TextView mGoodsNoticeBefore;
    @BindView(R.id.goods_detail_linear)
    LinearLayout mGoodsDetailLinear;
    @Inject
    GoodsDetailControl.PresenterGoodsDetail mPresenter;

    public static Intent getIntent(Context context) {
        return new Intent(context, GoodDetailActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_good_detail);
        ButterKnife.bind(this);
        initializeInjector();
        supportActionBar(mToolbar, true);
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
        mToolbarRightIcon.setVisibility(View.VISIBLE);
        RxView.clicks(mToolbarRightIcon).throttleFirst(1, TimeUnit.SECONDS).subscribe(v -> onBackPressed());
        RxView.clicks(mGoodsDetailButton).throttleFirst(1, TimeUnit.SECONDS).subscribe(v -> requestBugGoods());
        RxView.clicks(mExpandGoodDetail).throttleFirst(1, TimeUnit.SECONDS).subscribe(v -> requestShowDetail());
        RxView.clicks(mGoodsSpecification).throttleFirst(1, TimeUnit.SECONDS).subscribe(v -> requestGoodsSpecification());
        mCollapsingToolbarLayout.setTitle(" ");


    }

    private void initData() {

    }

    private void requestBugGoods() {

    }

    private void requestShowDetail() {
        if (mGoodsDetailLinear.getVisibility() == View.GONE) {
            mGoodsDetailLinear.setVisibility(View.VISIBLE);
            mExpandGoodDetail.setVisibility(View.GONE);
        } else {
            mGoodsDetailLinear.setVisibility(View.GONE);
            mExpandGoodDetail.setVisibility(View.VISIBLE);
        }
    }

    private void requestGoodsSpecification() {

    }

    private void initializeInjector() {
        DaggerGoodsDetailActivityComponent.builder()
                .applicationComponent(getApplicationComponent())
                .goodsDetailActivityModule(new GoodsDetailActivityModule(GoodDetailActivity.this, this))
                .build().inject(this);
    }
}
