package com.dispatching.feima.view.fragment;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;

import com.dispatching.feima.R;
import com.dispatching.feima.dagger.HasComponent;
import com.dispatching.feima.entity.BuProcessor;
import com.dispatching.feima.help.DialogFactory;
import com.dispatching.feima.utils.ToastUtils;

import java.net.ConnectException;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;

/**
 * Created by helei on 2017/5/3.
 */

public class BaseFragment extends Fragment {
    protected CompositeDisposable mDisposable;
    protected Dialog mProgressDialog;
    protected IntentFilter mFilter = new IntentFilter();
    @Inject
    BuProcessor mBuProcessor;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        addFilter();
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mReceiver, mFilter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mDisposable != null) {
            mDisposable.clear();
        }
        if (mReceiver != null){
            LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(mReceiver);
        }
    }

    protected BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            onReceivePro(context, intent);
        }
    };

    protected void onReceivePro(Context context, Intent intent) {
    }

    protected void addFilter() {
    }

    final ObservableTransformer schedulersTransformer = (observable) -> (
            ((Observable) observable).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()));

    public <T> ObservableTransformer<T, T> applySchedulers() {
        return (ObservableTransformer<T, T>) schedulersTransformer;
    }

    public void addSubscription(Disposable subscription) {
        if (mDisposable == null) {
            mDisposable = new CompositeDisposable();
        }
        mDisposable.add(subscription);
    }

    protected <C> C getComponent(Class<C> componentType) {
        return componentType.cast(((HasComponent<C>) getActivity()).getComponent());
    }

    protected void showDialogLoading(String msg) {
        dismissDialogLoading();
        mProgressDialog = DialogFactory.showLoadingDialog(getActivity(), msg);
        mProgressDialog.show();
    }

    protected void dismissDialogLoading() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
        mProgressDialog = null;
    }

    public void showBaseToast(String message) {
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


}
