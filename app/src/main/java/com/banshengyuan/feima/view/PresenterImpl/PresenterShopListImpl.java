package com.banshengyuan.feima.view.PresenterImpl;

import android.content.Context;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.help.RetryWithDelay;
import com.banshengyuan.feima.view.PresenterControl.ShopListControl;
import com.banshengyuan.feima.view.model.ResponseData;
import com.banshengyuan.feima.view.model.ShopListModel;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * Created by lei.he on 2017/6/26.
 * PresenterShopListImpl
 */

public class PresenterShopListImpl implements ShopListControl.PresenterShopList {
    private Context mContext;
    private ShopListControl.ShopListView mView;
    private ShopListModel mModel;

    @Inject
    public PresenterShopListImpl(Context context, ShopListControl.ShopListView view, ShopListModel model) {
        mContext = context;
        mView = view;
        mModel = model;
    }

    @Override
    public void requestPublishComment(String oId, String content, String token) {
        mView.showLoading(mContext.getString(R.string.loading));
        Disposable disposable = mModel.publishCommentRequest(oId, content, token).retryWhen(new RetryWithDelay(10, 3000)).compose(mView.applySchedulers())
                .subscribe(this::getCommentSuccess
                        , throwable -> mView.dismissLoading());
        mView.addSubscription(disposable);
    }

    private void getCommentSuccess(ResponseData responseData) {
        if (responseData.resultCode == 100) {
            mView.getCommentSuccess();
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
