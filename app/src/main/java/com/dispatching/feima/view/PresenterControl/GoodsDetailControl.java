package com.dispatching.feima.view.PresenterControl;

import com.dispatching.feima.entity.AddShoppingCardRequest;
import com.dispatching.feima.entity.GoodsInfoResponse;
import com.dispatching.feima.entity.SpecificationResponse;

import java.util.HashMap;
import java.util.List;

/**
 * Created by helei on 2017/4/27.
 * OrderDetailControl
 */

public class GoodsDetailControl {
    public interface GoodsDetailView extends LoadDataView {
        void getGoodsInfoSuccess(GoodsInfoResponse data);

        void goodInfoSpecificationSuccess(SpecificationResponse data);

        void getUniqueGoodInfoSuccess(SpecificationResponse data);

        void addShoppingCardSuccess();

        void checkProductId(HashMap<String, String> selectProMap);

        void addToShoppingCard(Integer mCount);

        void closeSpecificationDialog(HashMap<String, String> hashMap, String count, List<String> list1, List<String> list2, List<String> list3);
    }

    public interface PresenterGoodsDetail extends Presenter<GoodsDetailView> {
        void requestGoodInfo(String productId);

        void requestUniqueGoodInfo(String productId);

        void requestAddShoppingCard(AddShoppingCardRequest request);

        void requestGoodsSpecification(String productId);
    }

}
