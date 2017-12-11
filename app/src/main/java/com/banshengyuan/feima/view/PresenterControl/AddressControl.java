package com.banshengyuan.feima.view.PresenterControl;

import com.banshengyuan.feima.entity.AddAddressRequest;
import com.banshengyuan.feima.entity.AddressResponse;

/**
 * Created by lei.he on 2017/6/28.
 * AddressControl
 */

public class AddressControl {
    public interface AddressView extends LoadDataView {
        void listAddressSuccess(AddressResponse addressResponse);

        void deleteAddressSuccess();

        void updateAddressSuccess();
        void updateAddressFail();
        void updateAddressError(Throwable throwable);

    }

    public interface PresenterAddress extends Presenter<AddressView> {
        void requestAddressList(String phone);

        void requestDeleteAddress(String addressId, String token);

        void requestUpdateAddress(String addressId ,AddAddressRequest request ,String token);
    }
}
