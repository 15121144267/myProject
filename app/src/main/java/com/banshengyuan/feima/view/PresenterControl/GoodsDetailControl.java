package com.banshengyuan.feima.view.PresenterControl;

import com.banshengyuan.feima.entity.CollectionResponse;
import com.banshengyuan.feima.entity.GoodsInfoResponse;
import com.banshengyuan.feima.entity.SkuProductResponse;
import com.banshengyuan.feima.entity.SpecificationResponse;

import java.util.TreeMap;

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

        void checkProductId(TreeMap<Integer, String> selectProMap);

        void addToShoppingCard(String sku,Integer mCount);

        void closeSpecificationDialog(TreeMap<Integer, String> selectProMap, TreeMap<Integer, Integer> skuProMap, String content,GoodsInfoResponse.InfoBean infoBean,boolean doFlag);

        void closeSpecificationDialog2(SkuProductResponse.InfoBean mSkuInfoBean, TreeMap<Integer, String> selectProMap, TreeMap<Integer, Integer> skuProMap, String content,GoodsInfoResponse.InfoBean infoBean,boolean doFlag);
    }

    public interface PresenterGoodsDetail extends Presenter<GoodsDetailView> {
        void requestGoodInfo(Integer productId);

        void requestGoodsCollection(String productId, String type);

        void requestUniqueGoodInfo(Integer productId, String sku);

        void requestAddShoppingCard(String productId,String sku,Integer count);

        void requestGoodsSpecification(String productId);
    }

}
