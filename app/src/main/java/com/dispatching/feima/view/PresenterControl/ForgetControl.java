package com.dispatching.feima.view.PresenterControl;

/**
 * Created by helei on 2017/4/27.
 * LoginControl
 */

public class ForgetControl {
    public interface ForgetView extends LoadDataView{
       void setButtonEnable(boolean enable, long time);
    }

    public interface PresenterForget extends Presenter<ForgetView> {
        void onRequestVerifyCode(String phone);
    }

}
