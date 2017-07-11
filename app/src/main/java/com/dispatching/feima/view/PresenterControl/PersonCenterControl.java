package com.dispatching.feima.view.PresenterControl;

import com.dispatching.feima.entity.PersonInfoResponse;

/**
 * Created by lei.he on 2017/6/28.
 */

public class PersonCenterControl {
    public interface PersonCenterView extends LoadDataView {
        void updatePersonInfoSuccess();
    }

    public interface PresenterPersonCenter extends Presenter<PersonCenterView> {
        void requestUpdatePersonInfo(PersonInfoResponse response);
    }
}
