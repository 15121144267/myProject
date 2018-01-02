package com.banshengyuan.feima.view.PresenterImpl;

import android.content.Context;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.entity.MyCollectionProductsResponse;
import com.banshengyuan.feima.view.PresenterControl.CollectionProductControl;
import com.banshengyuan.feima.view.model.CollectionModel;
import com.banshengyuan.feima.view.model.ResponseData;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * Created by lei.he on 2017/6/26.
 * PresenterAddAddressImpl
 */

public class PresenterCollectionProductImpl implements CollectionProductControl.PresenterCollectionProduct {
    private CollectionProductControl.CollectionProductView mView;
    private Context mContext;
    private CollectionModel mModel;

    @Inject
    public PresenterCollectionProductImpl(Context context, CollectionProductControl.CollectionProductView view, CollectionModel model) {
        mContext = context;
        mView = view;
        mModel = model;
    }

   @Override
   public void requestCollectionProductList(int page, int pageSize,String token) {
       mView.showLoading(mContext.getString(R.string.loading));
       Disposable disposable = mModel.collectionProduceRequest(page, pageSize,token).compose(mView.applySchedulers())
               .subscribe(this::getCollectionProductSuccess, throwable -> mView.showErrMessage(throwable),
                       () -> mView.dismissLoading());
       mView.addSubscription(disposable);
   }

    private void getCollectionProductSuccess(ResponseData responseData) {
        if (responseData.resultCode == 200) {
            responseData.parseData(MyCollectionProductsResponse.class);
            MyCollectionProductsResponse response = (MyCollectionProductsResponse) responseData.parsedData;
            mView.getMyCollectionListSuccess(response);
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
