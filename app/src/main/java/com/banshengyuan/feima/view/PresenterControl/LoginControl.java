package com.banshengyuan.feima.view.PresenterControl;

import com.banshengyuan.feima.entity.LoginResponse;
import com.banshengyuan.feima.entity.PersonInfoResponse;

/**
 * Created by helei on 2017/4/27.
 * LoginControl
 */

public class LoginControl {
    public interface LoginView extends LoadDataView {
        void loginSuccess(LoginResponse response);

        void getPersonInfoSuccess(PersonInfoResponse response);
    }

    public interface PresenterLogin extends Presenter<LoginView> {
        void onRequestLogin(String phone, String passWord);

        void requestPersonInfo(String phone);
    }

}
