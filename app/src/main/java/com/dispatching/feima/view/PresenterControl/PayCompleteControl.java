package com.dispatching.feima.view.PresenterControl;

import com.dispatching.feima.entity.MyOrdersResponse;

/**
 * Created by lei.he on 2017/6/28.
 */

public class PayCompleteControl {
    public interface PayCompleteView extends LoadDataView {
        void loadFail(Throwable throwable);
        void getMyOrderListSuccess(MyOrdersResponse response);
    }

    public interface PresenterPayComplete extends Presenter<PayCompleteView> {
        void requestMyOrderList(Integer pageNo,Integer pageSize);
    }
}
