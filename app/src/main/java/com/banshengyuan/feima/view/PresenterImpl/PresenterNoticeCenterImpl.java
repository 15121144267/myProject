package com.banshengyuan.feima.view.PresenterImpl;

import android.content.Context;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.database.OrderNotice;
import com.banshengyuan.feima.entity.NoticeResponse;
import com.banshengyuan.feima.entity.QueryParam;
import com.banshengyuan.feima.help.RetryWithDelay;
import com.banshengyuan.feima.view.PresenterControl.NoticeCenterControl;
import com.banshengyuan.feima.view.model.NoticeCenterModel;
import com.banshengyuan.feima.view.model.ResponseData;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * Created by helei on 2017/4/28.
 * PresenterOrderDetailImpl
 */

public class PresenterNoticeCenterImpl implements NoticeCenterControl.PresenterNoticeCenter {

    private NoticeCenterControl.NoticeCenterView mView;
    private final Context mContext;
    private final NoticeCenterModel mNoticeCenterModel;


    @Inject
    public PresenterNoticeCenterImpl(Context context, NoticeCenterModel model, NoticeCenterControl.NoticeCenterView view) {
        mContext = context;
        mNoticeCenterModel = model;
        mView = view;
    }

    @Override
    public void updateNoticeDB(OrderNotice orderNotice) {
        Disposable disposable = mNoticeCenterModel.updateNoticeDB(orderNotice).compose(mView.applySchedulers())
                .subscribe(flag -> {
                            if (flag == 1) {
                                mView.updateSuccess();
                            }
                        }
                        , throwable -> mView.showErrMessage(throwable));
        mView.addSubscription(disposable);
    }

    @Override
    public void requestNoticeList(int page, int pageSize, String token) {
        mView.showLoading(mContext.getString(R.string.loading));

        Disposable disposable = mNoticeCenterModel.noticeListRequest(page, pageSize, token).retryWhen(new RetryWithDelay(10, 3000)).compose(mView.applySchedulers())
                .subscribe(this::getNoticeInfoSuccess
                        , throwable -> mView.showErrMessage(throwable),() -> mView.dismissLoading());
        mView.addSubscription(disposable);
    }

    private void getNoticeInfoSuccess(ResponseData responseData) {
        if (responseData.resultCode == 200) {
            responseData.parseData(NoticeResponse.class);
            NoticeResponse response = (NoticeResponse) responseData.parsedData;
            mView.queryNoticeListSuccess(response);
        } else {
            mView.showToast(responseData.errorDesc);
        }
    }

    @Override
    public void requestDbNotices(QueryParam param) {
        mView.showLoading(mContext.getString(R.string.loading));
        Disposable disposable = mNoticeCenterModel.queryNoticeDB(param).compose(mView.applySchedulers())
                .subscribe(notices ->
                                mView.querySuccess(notices)
                        , throwable -> mView.showErrMessage(throwable),
                        () -> mView.dismissLoading());
        mView.addSubscription(disposable);
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDestroy() {
        mView = null;
    }
}
