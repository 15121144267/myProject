package com.banshengyuan.feima.view.PresenterControl;

import android.view.View;

import com.banshengyuan.feima.entity.AllFairListResponse;
import com.example.mylibrary.adapter.BaseQuickAdapter;

/**
 * Created by lei.he on 2017/6/28.
 * GoodsClassifyControl
 */

public class GoodsClassifyControl {
    public interface GoodsClassifyView extends LoadDataView {
        void getAllFairListSuccess(AllFairListResponse response);
        void getAllFairListFail();
        void onItemClick(BaseQuickAdapter adapter, View view, int position);
    }

    public interface PresenterGoodsClassify extends Presenter<GoodsClassifyView> {
        void requestAllFairList();
    }
}
