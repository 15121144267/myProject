package com.banshengyuan.feima.view.PresenterImpl;

import android.content.Context;

import com.banshengyuan.feima.entity.AdResponse;
import com.banshengyuan.feima.entity.SpConstant;
import com.banshengyuan.feima.help.RetryWithDelay;
import com.banshengyuan.feima.utils.SharePreferenceUtil;
import com.banshengyuan.feima.view.PresenterControl.WelcomeControl;
import com.banshengyuan.feima.view.model.ResponseData;
import com.banshengyuan.feima.view.model.WelcomeModel;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * Created by helei on 2017/4/27.
 * PresenterLoginImpl
 */

public class PresenterWelcomeImpl implements WelcomeControl.PresenterWelcome {

    private WelcomeControl.WelcomeView mView;
    private final WelcomeModel mModel;
    private SharePreferenceUtil mSharePreferenceUtil;
    private final Context mContext;
    private boolean firstOpen = false;

    @Inject
    public PresenterWelcomeImpl(Context context, WelcomeModel model, WelcomeControl.WelcomeView view, SharePreferenceUtil sharePreferenceUtil) {
        mContext = context;
        mModel = model;
        mView = view;
        mSharePreferenceUtil = sharePreferenceUtil;
    }

    @Override
    public void requestPic() {
        Disposable disposable = mModel.requestPic()
                .compose(mView.applySchedulers())
                .retryWhen(new RetryWithDelay(5, 3000))
                .subscribe(this::getPicSuccess
                        , throwable -> mView.showErrMessage(throwable));
        mView.addSubscription(disposable);
    }

    private void getPicSuccess(ResponseData responseData) {
        if (responseData.resultCode == 200) {
            responseData.parseData(AdResponse.class);
            AdResponse response = (AdResponse) responseData.parsedData;
            mView.getAdSuccess(response);
        } else {
            mView.showToast(responseData.errorDesc);
        }
    }

    @Override
    public void onCreate() {
        firstOpen = mSharePreferenceUtil.getBooleanValue(SpConstant.FIRST_OPEN_KEY, true);
        if (firstOpen) {
            mView.switchToGuide();
            mView.showGuideFinish(false);
        }else {
            mView.showGuideFinish(true);
        }
    }

    @Override
    public void onDestroy() {
        mView = null;
    }

}
