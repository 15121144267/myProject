package com.banshengyuan.feima.view.PresenterControl;

import com.banshengyuan.feima.entity.PersonInfoResponse;

/**
 * Created by helei on 2017/4/27.
 * LoginControl
 */

public class WelcomeControl {
    public interface WelcomeView extends LoadDataView {
        void getPersonInfoSuccess(PersonInfoResponse response);

        void switchToGuide();

        void showGuideFinish(boolean isFinish);
    }

    public interface PresenterWelcome extends Presenter<WelcomeView> {
        void requestPersonInfo(String phone);
    }

}
