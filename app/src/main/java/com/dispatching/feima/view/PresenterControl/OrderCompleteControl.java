package com.dispatching.feima.view.PresenterControl;

import com.dispatching.feima.entity.MyOrdersResponse;

/**
 * Created by lei.he on 2017/6/28.
 */

public class OrderCompleteControl {
    public interface OrderCompleteView extends LoadDataView {
        void loadFail(Throwable throwable);
        void getMyOrderListSuccess(MyOrdersResponse response);
    }

    public interface PresenterOrderComplete extends Presenter<OrderCompleteView> {
        void requestMyOrderList(Integer status,Integer pageNo,Integer pageSize);
    }
}
