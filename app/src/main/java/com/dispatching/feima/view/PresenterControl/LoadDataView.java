/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 *
 * @author Fernando Cejas (the android10 coder)
 */
package com.dispatching.feima.view.PresenterControl;

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
