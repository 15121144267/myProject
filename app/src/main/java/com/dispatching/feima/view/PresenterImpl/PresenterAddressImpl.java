package com.dispatching.feima.view.PresenterImpl;

import android.content.Context;

import com.dispatching.feima.view.PresenterControl.AddressControl;
import com.dispatching.feima.view.model.AddressModel;

import javax.inject.Inject;

/**
 * Created by lei.he on 2017/6/26.
 */

public class PresenterAddressImpl implements AddressControl.PresenterAddress {
    AddressControl.AddressView mView;

    @Inject
    public PresenterAddressImpl(Context context, AddressControl.AddressView view, AddressModel model) {
        mView = view;
    }


    @Override
    public void onCreate() {

    }


    @Override
    public void onDestroy() {

    }
}
