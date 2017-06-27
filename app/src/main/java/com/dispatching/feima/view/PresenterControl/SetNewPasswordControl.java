package com.dispatching.feima.view.PresenterControl;

/**
 * Created by helei on 2017/4/27.
 * LoginControl
 */

public class SetNewPasswordControl {
    public interface SetNewPasswordView extends LoadDataView{

    }

    public interface PresenterSetNewPassword extends Presenter<SetNewPasswordView> {
        void onRequestForSure(String phone);
    }

}
