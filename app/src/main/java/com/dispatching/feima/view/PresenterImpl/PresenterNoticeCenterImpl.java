package com.dispatching.feima.view.PresenterImpl;

import android.content.Context;

import com.dispatching.feima.R;
import com.dispatching.feima.entity.QueryParam;
import com.dispatching.feima.view.PresenterControl.NoticeCenterControl;
import com.dispatching.feima.view.model.NoticeCenterModel;

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
    public PresenterNoticeCenterImpl(Context context, NoticeCenterModel model) {
        mContext = context;
        mNoticeCenterModel = model;
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
    public void setView(NoticeCenterControl.NoticeCenterView view) {
        mView = view;
    }


    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDestroy() {
        mView = null;
    }
}
