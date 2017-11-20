package com.banshengyuan.feima.view.PresenterImpl;

import android.content.Context;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.entity.VistaListResponse;
import com.banshengyuan.feima.view.PresenterControl.VistaControl;
import com.banshengyuan.feima.view.model.MainModel;
import com.banshengyuan.feima.view.model.ResponseData;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * Created by helei on 2017/5/3.
 * PresenterPendingImpl
 */

public class PresenterVistaImpl implements VistaControl.PresenterVista {
    private final MainModel mMainModel;
    private VistaControl.VistaView mView;
    private final Context mContext;

    @Inject
    public PresenterVistaImpl(Context context, MainModel model, VistaControl.VistaView view) {
        mContext = context;
        mMainModel = model;
        mView = view;
    }

    @Override
    public void requestVistaList(double longitude, double latitude) {
        mView.showLoading(mContext.getString(R.string.loading));
        Disposable disposable = mMainModel.vistaListRequest(longitude, latitude).compose(mView.applySchedulers())
                .subscribe(this::getVistaListRequestSuccess
                        , throwable -> mView.showErrMessage(throwable), () -> mView.dismissLoading());
        mView.addSubscription(disposable);
    }
    private void getVistaListRequestSuccess(ResponseData responseData) {
        if (responseData.resultCode == 200) {
            responseData.parseData(VistaListResponse.class);
            VistaListResponse response = (VistaListResponse) responseData.parsedData;
            mView.getVistaListSuccess(response);
        } else {
            mView.getVistaListFail();
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
