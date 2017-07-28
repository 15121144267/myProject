package com.dispatching.feima.view.PresenterControl;

import com.dispatching.feima.entity.GoodsInfoResponse;
import com.dispatching.feima.entity.SpecificationResponse;

import java.util.HashMap;

/**
 * Created by helei on 2017/4/27.
 * OrderDetailControl
 */

public class GoodsDetailControl {
    public interface GoodsDetailView extends LoadDataView {
        void getGoodsInfoSuccess(GoodsInfoResponse data);
        void goodInfoSpecificationSuccess(SpecificationResponse data);
        void closeSpecificationDialog(HashMap<String,String> hashMap,String count);
    }

    public interface PresenterGoodsDetail extends Presenter<GoodsDetailView> {
        void requestGoodInfo(String productId);
        void requestGoodsSpecification(String productId);
    }

}
