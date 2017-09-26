package com.banshengyuan.feima.view.PresenterControl;

/**
 * Created by helei on 2017/5/3.
 * PendingOrderControl
 */

public class VistaControl {
    public interface VistaView extends LoadDataView{
    }

    public interface PresenterVista extends Presenter<VistaView> {
    }
}
