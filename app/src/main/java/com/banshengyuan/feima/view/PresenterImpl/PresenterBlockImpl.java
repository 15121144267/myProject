package com.banshengyuan.feima.view.PresenterImpl;

import android.content.Context;

import com.banshengyuan.feima.view.PresenterControl.BlockControl;
import com.banshengyuan.feima.view.model.BlockModel;

import javax.inject.Inject;

/**
 * Created by lei.he on 2017/6/26.
 * PresenterAddressImpl
 */

public class PresenterBlockImpl implements BlockControl.PresenterBlock {
    private BlockControl.BlockView mView;
    private Context mContext;
    private BlockModel mModel;

    @Inject
    public PresenterBlockImpl(Context context, BlockControl.BlockView view, BlockModel model) {
        mContext = context;
        mView = view;
        mModel = model;
    }

   /* @Override
    public void requestDeleteAddress(AddAddressRequest request) {
        mView.showLoading(mContext.getString(R.string.loading));
        Disposable disposable = mModel.deleteAddressRequest(request).compose(mView.applySchedulers())
                .subscribe(this::deleteAddressSuccess, throwable -> mView.showErrMessage(throwable),
                        () -> mView.dismissLoading());
        mView.addSubscription(disposable);
    }

    private void deleteAddressSuccess(ResponseData responseData) {
        if(responseData.resultCode == 100){
            mView.deleteAddressSuccess();
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
