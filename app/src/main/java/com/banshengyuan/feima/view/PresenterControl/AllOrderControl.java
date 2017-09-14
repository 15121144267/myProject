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
    }

    public interface PresenterAllOrderView extends Presenter<AllOrderView> {
        void requestMyOrderList(Integer pageNo, Integer pageSize);
    }
}
