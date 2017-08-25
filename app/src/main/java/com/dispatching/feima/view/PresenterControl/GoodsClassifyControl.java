package com.dispatching.feima.view.PresenterControl;

import com.dispatching.feima.entity.SortListResponse;

/**
 * Created by lei.he on 2017/6/28.
 */

public class GoodsClassifyControl {
    public interface GoodsClassifyView extends LoadDataView {
        void sortListSuccess(SortListResponse response);
    }

    public interface PresenterGoodsClassify extends Presenter<GoodsClassifyView> {
        void requestSortList(String shopId, String nodeId,Integer deep, String sortName, Integer sortOrder);
    }
}
