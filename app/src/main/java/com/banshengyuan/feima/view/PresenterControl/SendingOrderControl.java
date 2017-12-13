package com.banshengyuan.feima.view.PresenterControl;

import com.banshengyuan.feima.entity.FairContentDetailResponse;

/**
 * Created by helei on 2017/5/3.
 * SendingOrderControl
 */

public class SendingOrderControl {
    public interface SendingOrderView extends LoadDataView{
        void getSendingOrderSuccess(FairContentDetailResponse response);
        void updateOrderStatusSuccess();
        void getOrderComplete();
        void getOrderError(Throwable throwable);
    }

    public interface PresenterSendingOrder extends Presenter<SendingOrderView> {
        void requestSendingOrder(String token, String uId);
        void requestCompleteOrder(String token, String uId, String deliveryId);
    }
}
