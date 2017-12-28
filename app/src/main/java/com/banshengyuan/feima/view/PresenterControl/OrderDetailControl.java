package com.banshengyuan.feima.view.PresenterControl;

import com.banshengyuan.feima.entity.OrderDetailResponse;

/**
 * Created by helei on 2017/4/27.
 * OrderDetailControl
 */

public class OrderDetailControl {
    public interface OrderDetailView extends LoadDataView {
        void loadFail(Throwable throwable);
        void getOrderDetailInfoSuccess(OrderDetailResponse orderDetailResponse);

        void getCancelOrderSuccess();

        void getComfirmOrderSuccess();

        void getDeleteOrderSuccess();
    }

    public interface PresenterOrderDetail extends Presenter<OrderDetailView> {
        void requestOrderDetailInfo(String order_sn, String token);//订单详情

        void requestCancelOrder(String order_sn, String token); //取消订单

        void requestConfirmOrder(String order_sn, String token); //确认订单

        void requestRemindSendGoods(String order_sn, String token);//提醒发货

        void requestDeleteOrder(String order_sn ,String token);//删除订单
    }

}
