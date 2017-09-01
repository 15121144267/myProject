package com.dispatching.feima.view.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.dispatching.feima.dagger.component.DaggerWelcomeActivityComponent;
import com.dispatching.feima.dagger.module.WelcomeActivityModule;
import com.dispatching.feima.entity.PersonInfoResponse;
import com.dispatching.feima.entity.SpConstant;
import com.dispatching.feima.view.PresenterControl.WelcomeControl;

import javax.inject.Inject;

/**
 * Created by lei.he on 2017/6/5.
 * WelcomeActivity
 */

public class WelcomeActivity extends BaseActivity implements WelcomeControl.WelcomeView {
    @Inject
    WelcomeControl.PresenterWelcome mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeInjector();
    }

    private void initData() {
        boolean mFirstOpen = mSharePreferenceUtil.getBooleanValue("isFirstOpen", true);
        if (mFirstOpen) {
            startActivity(GuideActivity.getIntent(this));
            finish();
            return;
        }

        String userPhone = mSharePreferenceUtil.getStringValue(SpConstant.USER_NAME);
        if (TextUtils.isEmpty(userPhone)) {
            startActivity(LoginActivity.getLoginIntent(this));
            finish();
        } else {
            mPresenter.requestPersonInfo(userPhone);
        }
    }

    @Override
    public void getPersonInfoSuccess(PersonInfoResponse response) {
        mBuProcessor.setUserId(response.memberId);
        mBuProcessor.setUserPhone(response.phone);
        mBuProcessor.setPartnerId(response.partnerId);
        mBuProcessor.setPersonInfo(response);
        startActivity(MainActivity.getMainIntent(this));
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
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

    private void initializeInjector() {
        DaggerWelcomeActivityComponent.builder()
                .applicationComponent(getApplicationComponent())
                .welcomeActivityModule(new WelcomeActivityModule(WelcomeActivity.this, this))
                .build().inject(this);
    }
}
