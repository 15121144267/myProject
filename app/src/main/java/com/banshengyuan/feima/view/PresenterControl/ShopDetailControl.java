package com.banshengyuan.feima.view.PresenterControl;

import com.banshengyuan.feima.entity.GoodsCommentResponse;

/**
 * Created by lei.he on 2017/6/28.
 * ShopDetailControl
 */

public class ShopDetailControl {
    public interface ShopDetailView extends LoadDataView {
        void loadError(Throwable throwable);
        void loadFail();
        void getGoodsCommentSuccess(GoodsCommentResponse response);
    }

    public interface PresenterShopDetail extends Presenter<ShopDetailView> {
        void requestGoodsComment(Integer goodsId,Integer page,Integer pageSize);
    }
}
