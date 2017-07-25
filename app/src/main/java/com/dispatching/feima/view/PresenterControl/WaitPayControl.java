package com.dispatching.feima.view.PresenterControl;

import com.dispatching.feima.entity.MyOrdersResponse;

/**
 * Created by lei.he on 2017/6/28.
 */

public class WaitPayControl {
    public interface WaitPayView extends LoadDataView {
        void loadFail(Throwable throwable);
        void getMyOrderListSuccess(MyOrdersResponse response);
    }

    public interface PresenterWaitPay extends Presenter<WaitPayView> {
        void requestMyOrderList(Integer pageNo,Integer pageSize);
    }
}
