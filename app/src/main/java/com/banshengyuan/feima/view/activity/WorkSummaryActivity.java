package com.banshengyuan.feima.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.dagger.component.DaggerWorkSummaryComponent;
import com.banshengyuan.feima.dagger.module.WorkSummaryActivityModule;
import com.banshengyuan.feima.entity.OrderDeliveryResponse;
import com.banshengyuan.feima.view.PresenterControl.WorkSummaryControl;
import com.banshengyuan.feima.view.adapter.WorkSummaryAdapter;

import javax.inject.Inject;

import butterknife.ButterKnife;

/**
 * Created by helei on 2017/4/27.
 * WorkSummaryActivity
 */

public class WorkSummaryActivity extends BaseActivity implements WorkSummaryControl.WorkSummaryView {
    @Inject
    WorkSummaryControl.PresenterWorkSummary mPresenter;
    private WorkSummaryAdapter mAdapter;

    public static Intent getSummaryIntent(Context context) {
        return new Intent(context, WorkSummaryActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_summary);
        initializeInjector();
        ButterKnife.bind(this);
        initView();
        initData();
    }

    @Override
    public void getAllOrderSuccess(OrderDeliveryResponse response) {

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
        DaggerWorkSummaryComponent.builder()
                .applicationComponent(getApplicationComponent())
                .workSummaryActivityModule(new WorkSummaryActivityModule(WorkSummaryActivity.this, this))
                .build().inject(this);
    }
}
