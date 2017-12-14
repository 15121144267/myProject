package com.banshengyuan.feima.view.PresenterControl;

import com.banshengyuan.feima.entity.CollectionResponse;
import com.banshengyuan.feima.entity.FairContentDetailResponse;
import com.banshengyuan.feima.entity.FairPraiseResponse;
import com.example.mylibrary.adapter.BaseQuickAdapter;
import com.example.mylibrary.adapter.BaseViewHolder;

/**
 * Created by helei on 2017/5/3.
 * CompletedOrderControl
 */

public class WorkSummaryControl {

    public interface WorkSummaryView extends LoadDataView {
        void getFairDetailSuccess(FairContentDetailResponse response);

        void requestCollection(BaseQuickAdapter<FairContentDetailResponse.DetailBean.ProductBean, BaseViewHolder> adapter,
                               Integer position, FairContentDetailResponse.DetailBean.ProductBean bean);

        void getGoodCollectionSuccess(CollectionResponse response);

        void getFairCollectionSuccess(CollectionResponse response);

        void getFairPraiseSuccess(FairPraiseResponse response);
    }

    public interface PresenterWorkSummary extends Presenter<WorkSummaryView> {
        void requestFairDetail(Integer fairId);

        void requestGoodCollection(Integer fairId);

        void requestFairCollection(Integer fairId);

        void requestPraise(Integer fairId);
    }
}
