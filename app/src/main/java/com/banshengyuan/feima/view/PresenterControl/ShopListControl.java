package com.banshengyuan.feima.view.PresenterControl;

import com.banshengyuan.feima.entity.GoodsCommentContentRequest;

import java.util.List;

/**
 * Created by helei on 2017/4/27.
 * LoginControl
 */

public class ShopListControl {
    public interface ShopListView extends LoadDataView {
        void getCommentSuccess();
    }

    public interface PresenterShopList extends Presenter<ShopListView> {
        void requestPublishComment(List<GoodsCommentContentRequest> mList, String token);
    }

}
