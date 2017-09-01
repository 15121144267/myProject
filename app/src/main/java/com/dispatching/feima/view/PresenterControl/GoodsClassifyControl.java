package com.dispatching.feima.view.PresenterControl;

import android.view.View;

import com.dispatching.feima.entity.SortListResponse;
import com.example.mylibrary.adapter.BaseQuickAdapter;

/**
 * Created by lei.he on 2017/6/28.
 * GoodsClassifyControl
 */

public class GoodsClassifyControl {
    public interface GoodsClassifyView extends LoadDataView {
        void sortListSuccess(SortListResponse response);
        void onItemClick(BaseQuickAdapter adapter, View view, int position);
    }

    public interface PresenterGoodsClassify extends Presenter<GoodsClassifyView> {
        void requestSortList(String shopId, String nodeId,Integer deep, String sortName, Integer sortOrder,Integer pageSize,Integer pageNumber);
    }
}
