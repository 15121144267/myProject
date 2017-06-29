package com.dispatching.feima.view.PresenterControl;

/**
 * Created by helei on 2017/4/27.
 * LoginControl
 */

public class LoginControl {
    public interface LoginView extends LoadDataView{
        void loginSuccess( );
    }

    public interface PresenterLogin extends Presenter<LoginView> {
        void onRequestLogin(String phone,String passWord);
    }

}
