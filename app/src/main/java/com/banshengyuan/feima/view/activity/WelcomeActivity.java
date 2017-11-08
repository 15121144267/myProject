package com.banshengyuan.feima.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.dagger.component.DaggerWelcomeActivityComponent;
import com.banshengyuan.feima.dagger.module.WelcomeActivityModule;
import com.banshengyuan.feima.entity.PersonInfoResponse;
import com.banshengyuan.feima.view.PresenterControl.WelcomeControl;
import com.bumptech.glide.Glide;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lei.he on 2017/6/5.
 * WelcomeActivity
 */

public class WelcomeActivity extends BaseActivity implements WelcomeControl.WelcomeView, Handler.Callback {
    @Inject
    WelcomeControl.PresenterWelcome mPresenter;
    @BindView(R.id.welcome_back)
    ImageView mWelcomeBack;
    private boolean mShowGuideFinish = false;
    private Handler mHandler = new Handler(this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getWindow().setBackgroundDrawable(null);
        initStatus();
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawable(ContextCompat.getDrawable(this, R.mipmap.welcome_bg));
        initializeInjector();
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);
        mPresenter.onCreate();
        initData();
    }

    private void initData() {
        switchToMain();
    }

    @Override
    public boolean handleMessage(Message msg) {
        if(msg.what == 1 ){
            if (mShowGuideFinish) {
                startActivity(MainActivity.getMainIntent(WelcomeActivity.this));
                finish();
            }
        }
        return false;
    }

    @Override
    public void showGuideFinish(boolean isFinish) {
        mShowGuideFinish = isFinish;
    }

    @Override
    public void switchToGuide() {
        startActivityForResult(GuideActivity.getIntent(this), 99);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case 100:
                mShowGuideFinish = true;
                if (mShowGuideFinish) {
                    switchToMain();
                } else {
                    initData();
                }
                break;

            default:
                break;
        }

    }

    public void switchToMain() {
        Glide.with(WelcomeActivity.this).load( R.mipmap.welcome_bg)
                .into(mWelcomeBack);
        mHandler.sendEmptyMessageDelayed(1,3000);
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
