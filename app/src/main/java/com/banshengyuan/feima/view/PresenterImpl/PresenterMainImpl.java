package com.banshengyuan.feima.view.PresenterImpl;

import com.banshengyuan.feima.entity.QueryParam;
import com.banshengyuan.feima.view.PresenterControl.MainControl;
import com.banshengyuan.feima.view.model.MainModel;

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
        /*mMainModel.queryNoticeDb(queryParam).compose(mView.applySchedulers()).subscribe(this::querySuccess,
                throwable -> mView.showErrMessage(throwable));*/
    }

    private void querySuccess(Integer count){
//        mView.querySuccess(count);
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDestroy() {
        mView = null;
    }
}
