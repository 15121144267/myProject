package com.dispatching.feima.view.PresenterImpl;

import android.content.Context;

import com.dispatching.feima.view.PresenterControl.SetNewPasswordControl;
import com.dispatching.feima.view.model.NewPasswordModel;

import javax.inject.Inject;

/**
 * Created by lei.he on 2017/6/26.
 */

public class PresenterSetNewPasswordImpl implements SetNewPasswordControl.PresenterSetNewPassword {

    @Inject
    public PresenterSetNewPasswordImpl(Context context, SetNewPasswordControl.SetNewPasswordView view, NewPasswordModel model){

    }

    @Override
    public void onRequestForSure(String phone) {

    }

    @Override
    public void onCreate() {

    }



    @Override
    public void onDestroy() {

    }
}
