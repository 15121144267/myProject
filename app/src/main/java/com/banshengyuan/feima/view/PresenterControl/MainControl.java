package com.banshengyuan.feima.view.PresenterControl;

import com.banshengyuan.feima.entity.QueryParam;

/**
 * Created by helei on 2017/4/27.
 * MainControl
 */

public class MainControl {
    public interface MainView extends LoadDataView {
//        void querySuccess(Integer count);
    }

    public interface PresenterMain extends Presenter<MainView> {
        void requestNoticeCount(QueryParam queryParam);
    }

}
