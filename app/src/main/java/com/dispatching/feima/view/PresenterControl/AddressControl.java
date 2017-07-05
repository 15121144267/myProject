package com.dispatching.feima.view.PresenterControl;

import com.dispatching.feima.entity.AddAddressRequest;
import com.dispatching.feima.entity.AddressResponse;

import java.util.List;

/**
 * Created by lei.he on 2017/6/28.
 */

public class AddressControl {
    public interface AddressView extends LoadDataView {
        void addressListSuccess(List<AddressResponse.DataBean> data);
        void deleteAddressSuccess();
    }

    public interface PresenterAddress extends Presenter<AddressView> {
        void requestAddressList(String phone);

        void requestDeleteAddress(AddAddressRequest request);
    }
}
