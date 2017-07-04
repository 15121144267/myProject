package com.dispatching.feima.view.PresenterControl;

/**
 * Created by lei.he on 2017/6/28.
 */

public class AddressControl {
    public interface AddressView extends LoadDataView {

    }

    public interface PresenterAddress extends Presenter<AddressView> {
        void requestAddressList(String phone);
    }
}
