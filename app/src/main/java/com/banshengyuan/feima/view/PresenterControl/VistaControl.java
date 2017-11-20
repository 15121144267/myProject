package com.banshengyuan.feima.view.PresenterControl;

import com.banshengyuan.feima.entity.VistaListResponse;

/**
 * Created by helei on 2017/5/3.
 * PendingOrderControl
 */

public class VistaControl {
    public interface VistaView extends LoadDataView{
        void getVistaListSuccess(VistaListResponse vistaListResponse);
        void getVistaListFail();
    }

    public interface PresenterVista extends Presenter<VistaView> {
        void requestVistaList(double longitude,double latitude);
    }
}
