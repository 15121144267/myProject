package com.dispatching.feima.view.PresenterControl;

import com.dispatching.feima.database.OrderNotice;
import com.dispatching.feima.entity.QueryParam;

import java.util.List;

/**
 * Created by helei on 2017/5/3.
 * PendingOrderControl
 */

public class NoticeCenterControl {
    public interface NoticeCenterView extends LoadDataView{
        void querySuccess(List<OrderNotice> list);
        void updateSuccess();
    }

    public interface PresenterNoticeCenter extends Presenter<NoticeCenterView> {
        void requestDbNotices(QueryParam param);
        void updateNoticeDB(OrderNotice orderNotice);
    }
}
