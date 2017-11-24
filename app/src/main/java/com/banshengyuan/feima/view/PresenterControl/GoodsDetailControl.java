package com.banshengyuan.feima.view.PresenterControl;

import com.banshengyuan.feima.entity.AddShoppingCardRequest;
import com.banshengyuan.feima.entity.CollectionResponse;
import com.banshengyuan.feima.entity.GoodsInfoResponse;
import com.banshengyuan.feima.entity.SpecificationResponse;

import java.util.HashMap;
import java.util.List;

/**
 * Created by helei on 2017/4/27.
 * OrderDetailControl
 */

public class GoodsDetailControl {
    public interface GoodsDetailView extends LoadDataView {
        void getGoodsInfoSuccess(GoodsInfoResponse data);

        void getGoodsInfoFail(String data);

        void getGoodsCollectionSuccess(CollectionResponse response);

        void goodInfoSpecificationSuccess(SpecificationResponse data);

        void getUniqueGoodInfoSuccess(SpecificationResponse data);

        void addShoppingCardSuccess();

        void checkProductId(HashMap<String, String> selectProMap);

        void addToShoppingCard(Integer mCount);

        void closeSpecificationDialog(HashMap<String, String> hashMap, String count, List<String> list1, List<String> list2, List<String> list3);
    }

    public interface PresenterGoodsDetail extends Presenter<GoodsDetailView> {
        void requestGoodInfo(Integer productId);

        void requestGoodsCollection(String productId, String type);

        void requestUniqueGoodInfo(String productId);

        void requestAddShoppingCard(AddShoppingCardRequest request);

        void requestGoodsSpecification(String productId);
    }

}
