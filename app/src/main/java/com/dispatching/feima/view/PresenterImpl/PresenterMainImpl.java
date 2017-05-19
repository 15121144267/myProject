package com.dispatching.feima.view.PresenterImpl;

import com.dispatching.feima.utils.SharePreferenceUtil;
import com.dispatching.feima.view.PresenterControl.MainControl;
import com.dispatching.feima.view.model.MainModel;

import java.net.ConnectException;

import javax.inject.Inject;

import retrofit2.HttpException;

/**
 * Created by helei on 2017/4/27.
 * PresenterMainImpl
 */

public class PresenterMainImpl implements MainControl.PresenterMain {
    private MainControl.MainView mView;


    @Inject
    public PresenterMainImpl(MainModel model, SharePreferenceUtil sharePreferenceUtil) {

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
