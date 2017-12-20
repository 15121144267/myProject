package com.banshengyuan.feima.view.PresenterControl;

import com.banshengyuan.feima.entity.MyOrdersResponse;

/**
 * Created by lei.he on 2017/6/28.
 * WaitPayControl
 */

public class WaitPayControl {
    public interface WaitPayView extends LoadDataView {
        void loadFail(Throwable throwable);
        void getMyOrderListSuccess(MyOrdersResponse response);

        void getCancelOrderSuccess(boolean flag);
    }

    public interface PresenterWaitPay extends Presenter<WaitPayView> {
        void requestMyOrderList(Integer pageNo,Integer pageSize,String status,boolean flag,String token);

        void requestCancelOrder(String order_sn,String token);
    }
}
