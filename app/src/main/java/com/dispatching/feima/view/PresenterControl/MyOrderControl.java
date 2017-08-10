package com.dispatching.feima.view.PresenterControl;

/**
 * Created by helei on 2017/4/27.
 * MainControl
 */

public class MyOrderControl {
    public interface MyOrderView extends LoadDataView {
        /*void loadFail(Throwable throwable);
        void getMyOrderListSuccess(MyOrdersResponse response);*/
    }

    public interface PresenterMyOrder extends Presenter<MyOrderView> {
        //void requestMyOrderList(Integer pageNo,Integer pageSize);
    }

}
