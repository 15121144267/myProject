package com.banshengyuan.feima.view.PresenterImpl;

import android.content.Context;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.entity.MyCoupleResponse;
import com.banshengyuan.feima.view.PresenterControl.CouponAvailableControl;
import com.banshengyuan.feima.view.model.CoupleModel;
import com.banshengyuan.feima.view.model.ResponseData;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * Created by lei.he on 2017/6/26.
 * PresenterAddAddressImpl
 */

public class PresenterCouponAvailableImpl implements CouponAvailableControl.PresenterCouponAvailable {
    private CouponAvailableControl.CouponAvailableView mView;
    private Context mContext;
    private CoupleModel mModel;

    @Inject
    public PresenterCouponAvailableImpl(Context context, CouponAvailableControl.CouponAvailableView view, CoupleModel coupleModel) {
        mContext = context;
        mView = view;
        mModel = coupleModel;
    }

    @Override
    public void requestNoUseCouponList(String state, int page, int pageSize, String token) {
        mView.showLoading(mContext.getString(R.string.loading));
        Disposable disposable = mModel.myCoupleRequest(state, page, pageSize, token).compose(mView.applySchedulers())
                .subscribe(this::noUseCoupleSuccess, throwable -> mView.showErrMessage(throwable),
                        () -> mView.dismissLoading());
        mView.addSubscription(disposable);
    }

    private void noUseCoupleSuccess(ResponseData responseData) {
        if (responseData.resultCode == 200) {
            responseData.parseData(MyCoupleResponse.class);
            MyCoupleResponse response = (MyCoupleResponse) responseData.parsedData;
            mView.getNoUseCouponListSuccess(response);
        } else {
            mView.showToast(responseData.errorDesc);
        }
    }

    @Override
    public void onCreate() {
    }


    @Override
    public void onDestroy() {
        mView = null;
    }


}
