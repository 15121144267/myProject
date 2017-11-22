package com.banshengyuan.feima.view.PresenterControl;

import com.banshengyuan.feima.entity.CommentListResponse;

/**
 * Created by lei.he on 2017/6/28.
 * AddAddressControl
 */

public class CommentControl {
    public interface CommentView extends LoadDataView {
        void getCommentListSuccess(CommentListResponse response);
        void getCommentListFail();
    }

    public interface PresenterComment extends Presenter<CommentView> {
        void requestCommentList(Integer fairId);
    }
}
