package com.banshengyuan.feima.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.dagger.component.DaggerFairProductDetailActivityComponent;
import com.banshengyuan.feima.dagger.module.FairProductDetailActivityModule;
import com.banshengyuan.feima.view.PresenterControl.FairProductDetailControl;

import javax.inject.Inject;

import butterknife.ButterKnife;

/**
 * Created by lei.he on 2017/6/28.
 * AddAddressActivity
 */

public class FairProductDetailActivity extends BaseActivity implements FairProductDetailControl.FairProductDetailView {

    public static Intent getIntent(Context context) {
        Intent intent = new Intent(context, FairProductDetailActivity.class);
        return intent;
    }

    @Inject
    FairProductDetailControl.PresenterFairProductDetail mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);
        ButterKnife.bind(this);
        initializeInjector();
       /* supportActionBar(mToolbar, true);
        mMiddleName.setText("新增收货地址");*/
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

    }

    private void initializeInjector() {
        DaggerFairProductDetailActivityComponent.builder()
                .applicationComponent(getApplicationComponent())
                .fairProductDetailActivityModule(new FairProductDetailActivityModule(FairProductDetailActivity.this, this))
                .build().inject(this);
    }
}
