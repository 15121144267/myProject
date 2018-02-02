package com.banshengyuan.feima.view.PresenterControl;

import com.banshengyuan.feima.entity.MyOrdersResponse;

/**
 * Created by lei.he on 2017/6/28.
 * PayCompleteControl
 */

public class PayCompleteControl {
    public interface PayCompleteView extends LoadDataView {
        void loadFail(Throwable throwable);
        void getMyOrderListSuccess(MyOrdersResponse response);

        void getComfirmOrderSuccess();

        void getDeleteOrderSuccess();
    }

    public interface PresenterPayComplete extends Presenter<PayCompleteView> {
        void requestMyOrderList(Integer pageNo,Integer pageSize,String status,String token);

        void requestConfirmOrder(String order_sn,String token);

        void requestRemindSendGoods(String order_sn ,String token);

        void requestDeleteOrder(String order_sn ,String token);
    }
}
