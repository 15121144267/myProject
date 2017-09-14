package com.banshengyuan.feima.view.PresenterControl;

import com.banshengyuan.feima.entity.AddAddressRequest;
import com.banshengyuan.feima.entity.AddressResponse;

import java.util.List;

/**
 * Created by lei.he on 2017/6/28.
 * AddressControl
 */

public class AddressControl {
    public interface AddressView extends LoadDataView {
        void addressListSuccess(List<AddressResponse.DataBean> data);
        void deleteAddressSuccess();
        void addressDefaultSuccess();
    }

    public interface PresenterAddress extends Presenter<AddressView> {
        void requestAddressList(String phone);

        void requestDeleteAddress(AddAddressRequest request);
        void requestAddressDefault(AddressResponse.DataBean request);
    }
}
