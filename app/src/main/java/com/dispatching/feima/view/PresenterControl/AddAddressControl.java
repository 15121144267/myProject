package com.dispatching.feima.view.PresenterControl;

import com.dispatching.feima.entity.AddAddressRequest;

/**
 * Created by lei.he on 2017/6/28.
 * AddAddressControl
 */

public class AddAddressControl {
    public interface AddAddressView extends LoadDataView {
        void addAddressSuccess();
    }

    public interface PresenterAddAddress extends Presenter<AddAddressView> {
        void requestAddAddress(AddAddressRequest request);
    }
}
