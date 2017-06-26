package com.dispatching.feima.view.PresenterControl;

/**
 * Created by helei on 2017/4/27.
 * LoginControl
 */

public class SignControl {
    public interface SignView extends LoadDataView{

    }

    public interface PresenterSign extends Presenter<SignView> {
        void onRequestVerifyCode(String phone);
    }

}
