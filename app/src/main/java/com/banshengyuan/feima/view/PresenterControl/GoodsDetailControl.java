package com.banshengyuan.feima.view.PresenterControl;

import com.banshengyuan.feima.entity.AddShoppingCardRequest;
import com.banshengyuan.feima.entity.CollectionResponse;
import com.banshengyuan.feima.entity.GoodsInfoResponse;
import com.banshengyuan.feima.entity.SkuProductResponse;
import com.banshengyuan.feima.entity.SpecificationResponse;

import java.util.HashMap;

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

        void getUniqueGoodInfoSuccess(SkuProductResponse data);

        void getUniqueGoodInfoFail(String des);

        void addShoppingCardSuccess();

        void checkProductId(HashMap<Integer, Integer> selectProMap);

        void addToShoppingCard(Integer mCount);

        void closeSpecificationDialog(HashMap<Integer, String> selectProMap, HashMap<Integer, Integer> skuProMap, String content);

        void closeSpecificationDialog2(SkuProductResponse.InfoBean mSkuInfoBean, HashMap<Integer, String> selectProMap, HashMap<Integer, Integer> skuProMap, String content);
    }

    public interface PresenterGoodsDetail extends Presenter<GoodsDetailView> {
        void requestGoodInfo(Integer productId);

        void requestGoodsCollection(String productId, String type);

        void requestUniqueGoodInfo(Integer productId, String sku);

        void requestAddShoppingCard(AddShoppingCardRequest request);

        void requestGoodsSpecification(String productId);
    }

}
