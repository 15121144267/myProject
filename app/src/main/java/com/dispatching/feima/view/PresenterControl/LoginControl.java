package com.dispatching.feima.view.PresenterControl;

import com.dispatching.feima.entity.PersonInfoResponse;

/**
 * Created by helei on 2017/4/27.
 * LoginControl
 */

public class LoginControl {
    public interface LoginView extends LoadDataView{
        void loginSuccess( );
        void getPersonInfoSuccess(PersonInfoResponse response);
    }

    public interface PresenterLogin extends Presenter<LoginView> {
        void onRequestLogin(String phone,String passWord);
        void requestPersonInfo(String phone);
    }

}
