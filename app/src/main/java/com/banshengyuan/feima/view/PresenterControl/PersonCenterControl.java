package com.banshengyuan.feima.view.PresenterControl;

import com.banshengyuan.feima.entity.PersonInfoResponse;

/**
 * Created by lei.he on 2017/6/28.
 * PersonCenterControl
 */

public class PersonCenterControl {
    public interface PersonCenterView extends LoadDataView {
        void updatePersonInfoSuccess();
    }

    public interface PresenterPersonCenter extends Presenter<PersonCenterView> {
        void requestUpdatePersonInfo(PersonInfoResponse response,String token,boolean avatalFlag);
    }
}
