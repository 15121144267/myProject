package com.banshengyuan.feima.view.PresenterControl;

import com.banshengyuan.feima.entity.ShopDetailCouponListResponse;
import com.banshengyuan.feima.entity.ShopDetailProductListResponse;
import com.banshengyuan.feima.entity.StoreDetailResponse;

/**
 * Created by lei.he on 2017/6/28.
 * AddAddressControl
 */

public class ShopProductDetailControl {
    public interface ShopProductDetailView extends LoadDataView {
        void getStoreDetailSuccess(StoreDetailResponse response);

        void getStoreDetailFail();

        void getStoreProductListSuccess(ShopDetailProductListResponse response);

        void getStoreProductListFail();

        void getStoreCouponListSuccess(ShopDetailCouponListResponse response);

        void getStoreCouponListFail();
    }

    public interface PresenterShopProductDetail extends Presenter<ShopProductDetailView> {
        void requestStoreDetail(Integer shopId);

        void requestStoreProductList(Integer shopId);

        void requestStoreCouponList(Integer shopId);
    }
}
