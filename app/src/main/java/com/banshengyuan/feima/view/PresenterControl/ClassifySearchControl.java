package com.banshengyuan.feima.view.PresenterControl;

import com.banshengyuan.feima.entity.ClassifySearchListResponse;

/**
 * Created by helei on 2017/4/27.
 * LoginControl
 */

public class ClassifySearchControl {
    public interface ClassifySearchView extends LoadDataView {
        void getProductListSuccess(ClassifySearchListResponse response);
    }

    public interface PresenterClassifySearch extends Presenter<ClassifySearchView> {
        void requestClassifySearchRequest(String shopId, String nodeId,Integer deep, String sortName, Integer sortOrder,Integer type,Integer pageSize,Integer pageNumber);
    }

}
