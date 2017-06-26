package com.dispatching.feima.view.PresenterImpl;

import android.content.Context;

import com.dispatching.feima.utils.SharePreferenceUtil;
import com.dispatching.feima.view.PresenterControl.SignControl;

import javax.inject.Inject;

/**
 * Created by lei.he on 2017/6/26.
 */

public class PresenterSignImpl implements SignControl.PresenterSign {

    @Inject
    public PresenterSignImpl(Context context, SignControl.SignView view, SharePreferenceUtil sharePreferenceUtil){

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
