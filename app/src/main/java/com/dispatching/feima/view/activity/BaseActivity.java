package com.dispatching.feima.view.activity;

import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Window;
import android.view.WindowManager;

import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.dispatching.feima.DaggerApplication;
import com.dispatching.feima.R;
import com.dispatching.feima.dagger.component.ApplicationComponent;
import com.dispatching.feima.entity.BuProcessor;
import com.dispatching.feima.gen.DaoSession;
import com.dispatching.feima.help.DialogFactory;
import com.dispatching.feima.utils.SharePreferenceUtil;
import com.dispatching.feima.utils.ToastUtils;

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

    private Dialog mProgressDialog;
    private CompositeDisposable mDisposable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getApplicationComponent().inject(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mDisposable != null) {
            mDisposable.clear();
        }
    }

    public <T> ObservableTransformer<T, T> applySchedulers() {
        //noinspection unchecked
        return (ObservableTransformer<T, T>) schedulersTransformer;
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

    private final ObservableTransformer schedulersTransformer = (observable) -> (
            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()));

    protected void initSystemBar(int color) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(ContextCompat.getColor(this,color));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
