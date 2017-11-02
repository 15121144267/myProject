package com.banshengyuan.feima.view.PresenterImpl;

import android.content.Context;

import com.banshengyuan.feima.view.PresenterControl.BlockDetailControl;
import com.banshengyuan.feima.view.model.BlockDetailModel;

import javax.inject.Inject;

/**
 * Created by lei.he on 2017/6/26.
 * PresenterAddressImpl
 */

public class PresenterBlockDetailImpl implements BlockDetailControl.PresenterBlockDetail {
    private BlockDetailControl.BlockDetailView mView;
    private Context mContext;
    private BlockDetailModel mModel;

    @Inject
    public PresenterBlockDetailImpl(Context context, BlockDetailControl.BlockDetailView view, BlockDetailModel model) {
        mContext = context;
        mView = view;
        mModel = model;
    }

   /* @Override
    public void requestAddressList(String phone) {
        mView.showLoading(mContext.getString(R.string.loading));
        Disposable disposable = mModel.addressListRequest(phone).compose(mView.applySchedulers())
                .subscribe(this::addressListRequestSuccess, throwable -> mView.showErrMessage(throwable),
                        () -> mView.dismissLoading());
        mView.addSubscription(disposable);
    }

    private void addressListRequestSuccess(ResponseData responseData) {
        if(responseData.resultCode == 100){
            responseData.parseData(AddressResponse.class);
            AddressResponse response  = (AddressResponse) responseData.parsedData;
            mView.addressListSuccess(response.data);
        }else {
            mView.showToast(responseData.errorDesc);
        }
    }*/

    @Override
    public void onCreate() {

    }

    @Override
    public void onDestroy() {
        mView = null;
    }
}
