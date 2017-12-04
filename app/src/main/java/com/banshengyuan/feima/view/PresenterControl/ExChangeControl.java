package com.banshengyuan.feima.view.PresenterControl;

import com.banshengyuan.feima.entity.ExChangeResponse;

/**
 * Created by lei.he on 2017/6/28.
 * AddAddressControl
 */

public class ExChangeControl {
    public interface ExChangeView extends LoadDataView {
        void getHotFairInfoSuccess(ExChangeResponse response);
    }

    public interface PresenterExChange extends Presenter<ExChangeView> {
        void requestHotFairInfo(String street_id);//请求热闹列表数据
    }


}
