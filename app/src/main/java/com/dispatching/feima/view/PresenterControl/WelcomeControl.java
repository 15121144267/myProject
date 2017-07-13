package com.dispatching.feima.view.PresenterControl;

import com.dispatching.feima.entity.PersonInfoResponse;

/**
 * Created by helei on 2017/4/27.
 * LoginControl
 */

public class WelcomeControl {
    public interface WelcomeView extends LoadDataView{
        void getPersonInfoSuccess(PersonInfoResponse response);
    }

    public interface PresenterWelcome extends Presenter<WelcomeView> {
        void requestPersonInfo(String phone);
    }

}
