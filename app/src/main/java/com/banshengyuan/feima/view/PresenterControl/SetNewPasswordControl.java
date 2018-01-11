package com.banshengyuan.feima.view.PresenterControl;

import com.banshengyuan.feima.entity.SetPasswordRequest;

/**
 * Created by helei on 2017/4/27.
 * LoginControl
 */

public class SetNewPasswordControl {
    public interface SetNewPasswordView extends LoadDataView{
        void setPasswordSuccess();
    }

    public interface PresenterSetNewPassword extends Presenter<SetNewPasswordView> {
        void onRequestForSure(SetPasswordRequest request );
    }

}
