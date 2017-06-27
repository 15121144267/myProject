package com.dispatching.feima.view.PresenterControl;

/**
 * Created by helei on 2017/4/27.
 * LoginControl
 */

public class ForgetControl {
    public interface ForgetView extends LoadDataView{

    }

    public interface PresenterForget extends Presenter<ForgetView> {
        void onRequestVerifyCode(String phone);
    }

}
