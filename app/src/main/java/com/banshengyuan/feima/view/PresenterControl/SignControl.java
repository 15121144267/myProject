package com.banshengyuan.feima.view.PresenterControl;

/**
 * Created by helei on 2017/4/27.
 * LoginControl
 */

public class SignControl {
    public interface SignView extends LoadDataView {
        void setButtonEnable(boolean enable, Long integer);

        void signUpSuccess();
    }

    public interface PresenterSign extends Presenter<SignView> {
        void onRequestVerifyCode(String phone);

        void onRequestSign(String phone,String password,String verityCode);
    }

}
