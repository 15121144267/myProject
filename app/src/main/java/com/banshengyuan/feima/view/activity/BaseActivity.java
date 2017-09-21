package com.banshengyuan.feima.view.activity;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.banshengyuan.feima.DaggerApplication;
import com.banshengyuan.feima.R;
import com.banshengyuan.feima.dagger.component.ApplicationComponent;
import com.banshengyuan.feima.dagger.component.BaseActivityComponent;
import com.banshengyuan.feima.dagger.component.DaggerBaseActivityComponent;
import com.banshengyuan.feima.dagger.module.BaseActivityModule;
import com.banshengyuan.feima.entity.BuProcessor;
import com.banshengyuan.feima.gen.DaoSession;
import com.banshengyuan.feima.help.DialogFactory;
import com.banshengyuan.feima.help.GlideHelper.ImageLoaderHelper;
import com.banshengyuan.feima.utils.SharePreferenceUtil;
import com.banshengyuan.feima.utils.ToastUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.net.ConnectException;

import javax.inject.Inject;

import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;

/**
 * Created by helei on 2017/4/26.
 * BaseActivity
 */

public class BaseActivity extends AppCompatActivity {
    @Inject
    protected SharePreferenceUtil mSharePreferenceUtil;
    @Inject
    protected DaoSession mDaoSession;
    @Inject
    protected BuProcessor mBuProcessor;
    @Inject
    protected AMapLocationClient mAMapLocationClient;
    @Inject
    protected AMapLocationClientOption mAMapLocationClientOption;
    @Inject
    protected ImageLoaderHelper mImageLoaderHelper;

    protected RxPermissions mRxPermissions;
    private Dialog mProgressDialog;
    private CompositeDisposable mDisposable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BaseActivityComponent component = DaggerBaseActivityComponent.builder()
                .applicationComponent(getApplicationComponent())
                .baseActivityModule(new BaseActivityModule(BaseActivity.this))
                .build();
        mRxPermissions = component.rxPermissions();
        component.inject(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mDisposable != null) {
            mDisposable.clear();
        }
    }

    void showBaseToast(String message) {
        ToastUtils.showShortToast(message);
    }

    public void showErrMessage(Throwable e) {
        dismissDialogLoading();
        String mErrMessage;
        if (e instanceof HttpException || e instanceof ConnectException
                || e instanceof RuntimeException) {
            mErrMessage = getString(R.string.text_check_internet);
        } else {
            mErrMessage = getString(R.string.text_wait_try);
        }
        showBaseToast(mErrMessage);
    }

    ApplicationComponent getApplicationComponent() {
        return ((DaggerApplication) getApplication()).getApplicationComponent();
    }

    public void addSubscription(Disposable subscription) {
        if (mDisposable == null) {
            mDisposable = new CompositeDisposable();
        }
        mDisposable.add(subscription);
    }

    void supportActionBar(Toolbar toolbar, boolean isShowIcon) {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }
        if (isShowIcon) {
            toolbar.setNavigationIcon(R.drawable.vector_arrow_left);
            toolbar.setNavigationOnClickListener(v -> onBackPressed());
        }
    }

    void showDialogLoading(String msg) {
        dismissDialogLoading();
        mProgressDialog = DialogFactory.showLoadingDialog(this, msg);
        mProgressDialog.show();
    }

    void dismissDialogLoading() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
        mProgressDialog = null;
    }

    public <T> ObservableTransformer<T, T> applySchedulers() {
        //noinspection unchecked
        return (ObservableTransformer<T, T>) schedulersTransformer;
    }

    private final ObservableTransformer schedulersTransformer = (observable) -> (
            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()));

    protected void hideSoftInput(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
