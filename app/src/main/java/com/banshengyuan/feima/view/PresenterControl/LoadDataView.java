
package com.banshengyuan.feima.view.PresenterControl;

import android.content.Context;

import io.reactivex.ObservableTransformer;
import io.reactivex.disposables.Disposable;

public interface LoadDataView {

    void showLoading(String msg);

    void dismissLoading();

    void showToast(String message);

    <T> ObservableTransformer<T, T> applySchedulers();

    void addSubscription(Disposable disposable);

    void showErrMessage(Throwable e);

    Context getContext();

}
