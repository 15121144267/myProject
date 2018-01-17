package com.banshengyuan.feima.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.dagger.component.DaggerWelcomeActivityComponent;
import com.banshengyuan.feima.dagger.module.WelcomeActivityModule;
import com.banshengyuan.feima.entity.AdResponse;
import com.banshengyuan.feima.view.PresenterControl.WelcomeControl;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.banshengyuan.feima.R.id.welcome_number;

/**
 * Created by lei.he on 2017/6/5.
 * WelcomeActivity
 */

public class WelcomeActivity extends BaseActivity implements WelcomeControl.WelcomeView {
    @Inject
    WelcomeControl.PresenterWelcome mPresenter;
    @BindView(R.id.welcome_back)
    ImageView mWelcomeBack;
    @BindView(welcome_number)
    TextView mWelcomeNumber;

    private boolean mShowGuideFinish = false;
    private boolean mShowTime = true;
    private AtomicInteger mCutDownTime = new AtomicInteger();
    public static final Integer CUT_DOWN_TIME = 6;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getWindow().setBackgroundDrawable(null);
        initStatus();
        super.onCreate(savedInstanceState);
        initializeInjector();
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);
        mPresenter.onCreate();
        initView();
    }

    private void initView() {
        RxView.clicks(mWelcomeNumber).throttleFirst(5, TimeUnit.SECONDS).subscribe(o -> {
            if (mHandler.hasMessages(1)) {
                mHandler.removeMessages(1);
            }
            if (mShowGuideFinish) {
                startActivity(MainActivity.getMainIntent(WelcomeActivity.this));
                finish();
            }
        });
    }


    @Override
    public boolean handleMessage(Message msg) {

        if (msg.what == 1) {
            if (mCutDownTime.get() < 1) {
                if (mHandler.hasMessages(1)) {
                    mHandler.removeMessages(1);
                }
                if (mShowGuideFinish) {
                    startActivity(MainActivity.getMainIntent(WelcomeActivity.this));
                    finish();
                }
            } else {
                if (mShowTime) {
                    mWelcomeNumber.setVisibility(View.VISIBLE);
                    mShowTime = false;
                }
                mWelcomeNumber.setText("" + mCutDownTime.decrementAndGet() + "跳过");
                mHandler.sendEmptyMessageDelayed(1, 1000);
            }
        }
        return super.handleMessage(msg);
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
                switchToMain();
                break;
        }

    }

    public void switchToMain() {
        mPresenter.requestPic();
    }

    @Override
    public void getAdSuccess(AdResponse response) {
        Glide.with(this).load(response.string)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .animate(R.anim.zoom_in)
                .error(R.mipmap.activity_welcome)
                .placeholder(R.mipmap.activity_welcome)
                .centerCrop()
                .into(new GlideDrawableImageViewTarget(mWelcomeBack) {
                    @Override
                    public void onResourceReady(GlideDrawable drawable, GlideAnimation anim) {
                        super.onResourceReady(drawable, anim);
                        mCutDownTime.set(CUT_DOWN_TIME);
                        mHandler.sendEmptyMessage(1);
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        switchToMain();
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
        if (mHandler.hasMessages(1)) {
            mHandler.removeMessages(1);
        }
        mPresenter.onDestroy();
    }

    private void initializeInjector() {
        DaggerWelcomeActivityComponent.builder()
                .applicationComponent(getApplicationComponent())
                .welcomeActivityModule(new WelcomeActivityModule(WelcomeActivity.this, this))
                .build().inject(this);
    }
}
