package com.dispatching.feima.view.PresenterControl;

/**
 * Created by helei on 2017/4/27.
 * LoginControl
 */

public class SetNewPasswordControl {
    public interface SetNewPasswordView extends LoadDataView{
        void setPasswordSuccess();
    }

    public interface PresenterSetNewPassword extends Presenter<SetNewPasswordView> {
        void onRequestForSure(String phone,String smsCode,String password);
    }

}
