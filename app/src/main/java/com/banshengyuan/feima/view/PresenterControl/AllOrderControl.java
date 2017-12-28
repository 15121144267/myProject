package com.banshengyuan.feima.view.PresenterControl;

import com.banshengyuan.feima.entity.MyOrdersResponse;

/**
 * Created by lei.he on 2017/6/28.
 * AllOrderControl
 */

public class AllOrderControl {
    public interface AllOrderView extends LoadDataView {
        void loadFail(Throwable throwable);
        void getMyOrderListSuccess(MyOrdersResponse response);

        void getCancelOrderSuccess();

        void getComfirmOrderSuccess();

        void getDeleteOrderSuccess();
    }

    public interface PresenterAllOrderView extends Presenter<AllOrderView> {
        void requestMyOrderList(Integer pageNo, Integer pageSize,String search_status,boolean flag,String token);

        void requestCancelOrder(String order_sn,String token);

        void requestConfirmOrder(String order_sn,String token);

        void requestRemindSendGoods(String order_sn ,String token);

        void requestDeleteOrder(String order_sn ,String token);

    }
}
