package com.banshengyuan.feima.view.PresenterControl;

import com.banshengyuan.feima.entity.AddAddressRequest;

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
