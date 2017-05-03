package com.dispatching.feima.view.PresenterControl;

import com.dispatching.feima.entity.LoginResponse;

/**
 * Created by helei on 2017/4/27.
 */

public class LoginControl {
    public interface LoginView extends LoadDataView{
        void setButtonEnable(boolean enable,Long integer);
        void loginSuccess(LoginResponse loginResponse);
    }

    public interface PresenterLogin extends Presenter<LoginView> {
        void onRequestLogin(String phone,String passWord);
        void onRequestVerifyCode(String phone);
    }

}
