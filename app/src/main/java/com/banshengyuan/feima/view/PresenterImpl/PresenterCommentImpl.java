package com.banshengyuan.feima.view.PresenterImpl;

import android.content.Context;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.entity.CommentListResponse;
import com.banshengyuan.feima.view.PresenterControl.CommentControl;
import com.banshengyuan.feima.view.model.FairDetailModel;
import com.banshengyuan.feima.view.model.ResponseData;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * Created by lei.he on 2017/6/26.
 * PresenterAddAddressImpl
 */

public class PresenterCommentImpl implements CommentControl.PresenterComment {
    private CommentControl.CommentView mView;
    private Context mContext;
    private FairDetailModel mModel;

    @Inject
    public PresenterCommentImpl(Context context, CommentControl.CommentView view, FairDetailModel model) {
        mContext = context;
        mView = view;
        mModel = model;
    }

    @Override
    public void requestCommentList(Integer fairId) {
        mView.showLoading(mContext.getString(R.string.loading));
        Disposable disposable = mModel.commentListRequest(fairId).compose(mView.applySchedulers())
                .subscribe(this::getCommentListSuccess, throwable -> mView.showErrMessage(throwable),
                        () -> mView.dismissLoading());
        mView.addSubscription(disposable);
    }

    private void getCommentListSuccess(ResponseData responseData) {
        if (responseData.resultCode == 200) {
            responseData.parseData(CommentListResponse.class);
            CommentListResponse response = (CommentListResponse) responseData.parsedData;
            mView.getCommentListSuccess(response);
        } else {
            mView.getCommentListFail();
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
