package com.dispatching.feima.view.PresenterImpl;

import com.dispatching.feima.entity.QueryParam;
import com.dispatching.feima.view.PresenterControl.MainControl;
import com.dispatching.feima.view.model.MainModel;

import javax.inject.Inject;

/**
 * Created by helei on 2017/4/27.
 * PresenterMainImpl
 */

public class PresenterMainImpl implements MainControl.PresenterMain {

    private MainControl.MainView mView;
    private final MainModel mMainModel;

    @Inject
    public PresenterMainImpl(MainModel model,MainControl.MainView view) {
        mMainModel = model;
        mView = view;
    }

    @Override
    public void requestNoticeCount(QueryParam queryParam) {
        mMainModel.queryNoticeDb(queryParam).compose(mView.applySchedulers()).subscribe(this::querySuccess,
                throwable -> mView.showErrMessage(throwable));
    }

    private void querySuccess(Integer count){
        mView.querySuccess(count);
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDestroy() {
        mView = null;
    }
}
