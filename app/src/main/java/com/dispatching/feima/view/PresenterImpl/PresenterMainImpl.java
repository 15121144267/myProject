package com.dispatching.feima.view.PresenterImpl;

import com.dispatching.feima.utils.SharePreferenceUtil;
import com.dispatching.feima.view.PresenterControl.MainControl;
import com.dispatching.feima.view.model.MainModel;

import java.net.ConnectException;

import javax.inject.Inject;

import retrofit2.HttpException;

/**
 * Created by helei on 2017/4/27.
 */

public class PresenterMainImpl implements MainControl.PresenterMain {
    private MainControl.MainView mView;
    private MainModel mMainModel;


    @Inject
    public PresenterMainImpl(MainModel model, SharePreferenceUtil sharePreferenceUtil) {
        mMainModel = model;
    }

    @Override
    public void requestOrderInfo() {
        mView.showLoading("加载中...");
        mMainModel.OrderInfoRequest("").compose(mView.applySchedulers())
                .subscribe(responseData -> {
                }, throwable -> {
                    showErrMessage(throwable);
                }, () -> {
                });
    }

    @Override
    public void setView(MainControl.MainView mainView) {
        mView = mainView;
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDestroy() {
        mView = null;
    }

    private void showErrMessage(Throwable e) {
        mView.dismissLoading();
        if (e instanceof HttpException || e instanceof ConnectException
                || e instanceof RuntimeException) {
            mView.showToast("请检查网络");
        }
    }
}
