package com.dispatching.feima.view.PresenterImpl;

import android.content.Context;

import com.dispatching.feima.utils.SharePreferenceUtil;
import com.dispatching.feima.view.PresenterControl.ForgetControl;

import javax.inject.Inject;

/**
 * Created by lei.he on 2017/6/26.
 */

public class PresenterForgetImpl implements ForgetControl.PresenterForget {

    @Inject
    public PresenterForgetImpl(Context context, ForgetControl.ForgetView view, SharePreferenceUtil sharePreferenceUtil){

    }



    @Override
    public void onCreate() {

    }

    @Override
    public void onRequestVerifyCode(String phone) {

    }

    @Override
    public void onDestroy() {

    }
}
