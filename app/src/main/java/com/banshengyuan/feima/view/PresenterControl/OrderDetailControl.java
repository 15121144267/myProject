package com.banshengyuan.feima.view.PresenterControl;

import com.banshengyuan.feima.entity.OrderDetailResponse;

/**
 * Created by helei on 2017/4/27.
 * OrderDetailControl
 */

public class OrderDetailControl {
    public interface OrderDetailView extends LoadDataView {
      void  getOrderDetailInfoSuccess(OrderDetailResponse orderDetailResponse);
    }

    public interface PresenterOrderDetail extends Presenter<OrderDetailView> {
        void requestOrderDetailInfo(String order_sn,String token);
    }

}
