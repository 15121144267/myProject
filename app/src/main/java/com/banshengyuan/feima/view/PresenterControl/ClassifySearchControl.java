package com.banshengyuan.feima.view.PresenterControl;

import com.banshengyuan.feima.entity.ClassifySearchListResponse;

/**
 * Created by helei on 2017/4/27.
 * LoginControl
 */

public class ClassifySearchControl {
    public interface ClassifySearchView extends LoadDataView {
        void getCommentListSuccess(ClassifySearchListResponse response);

        void loadError(Throwable error);

        void getCommentListFail();
    }

    public interface PresenterClassifySearch extends Presenter<ClassifySearchView> {
        void requestCommentList(Integer fairId, Integer page, Integer pageSize);
    }

}
