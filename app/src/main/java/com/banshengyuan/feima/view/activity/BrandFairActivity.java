package com.banshengyuan.feima.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.dagger.component.DaggerBrandFairActivityComponent;
import com.banshengyuan.feima.dagger.module.BrandFairActivityModule;
import com.banshengyuan.feima.view.PresenterControl.BrandFairControl;
import com.banshengyuan.feima.view.adapter.GalleryCardAdapter;
import com.banshengyuan.feima.view.customview.recycleviewgallery.CardScaleHelper;
import com.banshengyuan.feima.view.customview.recycleviewgallery.SpeedRecyclerView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lei.he on 2017/6/5.
 * WelcomeActivity
 */

public class BrandFairActivity extends BaseActivity implements BrandFairControl.BrandFairView {


    public static Intent getIntent(Context context) {
        return new Intent(context, BrandFairActivity.class);
    }

    @BindView(R.id.brand_fair_gallery)
    SpeedRecyclerView mBrandFairGallery;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbar_right_icon)
    ImageView mToolbarRightIcon;
    @Inject
    BrandFairControl.PresenterBrandFair mPresenter;

    private List<Integer> mList;
    private CardScaleHelper mCardScaleHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brand_fair);
        initializeInjector();
        ButterKnife.bind(this);
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

    private void initData() {

    }

    private void initView() {
        mList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            mList.add(R.mipmap.header);
        }
        mBrandFairGallery.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mBrandFairGallery.setAdapter(new GalleryCardAdapter(mList));
        mCardScaleHelper = new CardScaleHelper();
        mCardScaleHelper.setCurrentItemPos(0);
        mCardScaleHelper.attachToRecyclerView(mBrandFairGallery);
    }


    private void initializeInjector() {
        DaggerBrandFairActivityComponent.builder()
                .applicationComponent(getApplicationComponent())
                .brandFairActivityModule(new BrandFairActivityModule(BrandFairActivity.this, this))
                .build().inject(this);
    }
}
