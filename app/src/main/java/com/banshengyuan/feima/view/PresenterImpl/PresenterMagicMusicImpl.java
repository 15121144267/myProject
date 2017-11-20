package com.banshengyuan.feima.view.PresenterImpl;

import android.content.Context;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.entity.MusicListResponse;
import com.banshengyuan.feima.view.PresenterControl.MagicMusicControl;
import com.banshengyuan.feima.view.model.MainModel;
import com.banshengyuan.feima.view.model.ResponseData;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * Created by helei on 2017/5/3.
 * PresenterPendingImpl
 */

public class PresenterMagicMusicImpl implements MagicMusicControl.PresenterMagicMusic {
    private final MainModel mMainModel;
    private MagicMusicControl.MagicMusicView mView;
    private final Context mContext;

    @Inject
    public PresenterMagicMusicImpl(Context context, MainModel model, MagicMusicControl.MagicMusicView view) {
        mContext = context;
        mMainModel = model;
        mView = view;
    }

    @Override
    public void requestMusicList() {
        mView.showLoading(mContext.getString(R.string.loading));
        Disposable disposable = mMainModel.musicListRequest().compose(mView.applySchedulers())
                .subscribe(this::getMusicListSuccess
                        , throwable -> mView.showErrMessage(throwable), () -> mView.dismissLoading());
        mView.addSubscription(disposable);
    }

    private void getMusicListSuccess(ResponseData responseData) {
        if (responseData.resultCode == 200) {
            responseData.parseData(MusicListResponse.class);
            MusicListResponse response = (MusicListResponse) responseData.parsedData;
            mView.getMusicListSuccess(response);
        } else {
            mView.getMusicListFail();
        }
    }

    /* @Override
        public void requestShopId(String scoreCode, Integer type) {
            mView.showLoading(mContext.getString(R.string.loading));
            Disposable disposable = mShopListModel.shopIdRequest(scoreCode, type).compose(mView.applySchedulers())
                    .subscribe(this::getShopSuccess
                            , throwable -> mView.showErrMessage(throwable), () -> mView.dismissLoading());
            mView.addSubscription(disposable);
        }

        private void getShopSuccess(ResponseData responseData) {
            if (responseData.resultCode == 0) {
                responseData.parseData(ShopResponse.class);
                ShopResponse response = (ShopResponse) responseData.parsedData;
                mView.getShopSuccess(response);
            } else {
                mView.showToast(responseData.errorDesc);
            }
        }
    */
    @Override
    public void onCreate() {

    }

    @Override
    public void onDestroy() {
        mView = null;
    }
}
