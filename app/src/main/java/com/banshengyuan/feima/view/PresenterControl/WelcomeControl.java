package com.banshengyuan.feima.view.PresenterControl;

import com.banshengyuan.feima.entity.AdResponse;

/**
 * Created by helei on 2017/4/27.
 * LoginControl
 */

public class WelcomeControl {
    public interface WelcomeView extends LoadDataView {

        void switchToGuide();

        void getAdSuccess(AdResponse response);

        void showGuideFinish(boolean isFinish);
    }

    public interface PresenterWelcome extends Presenter<WelcomeView> {
        void requestPic();
    }

}
