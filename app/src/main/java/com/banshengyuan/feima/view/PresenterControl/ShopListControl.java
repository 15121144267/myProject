package com.banshengyuan.feima.view.PresenterControl;

/**
 * Created by helei on 2017/4/27.
 * LoginControl
 */

public class ShopListControl {
    public interface ShopListView extends LoadDataView {
        void getCommentSuccess();
    }

    public interface PresenterShopList extends Presenter<ShopListView> {
        void requestPublishComment(String gId, String content,String token);
    }

}
