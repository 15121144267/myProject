package com.banshengyuan.feima.view.PresenterControl;

import com.banshengyuan.feima.database.OrderNotice;
import com.banshengyuan.feima.entity.NoticeResponse;
import com.banshengyuan.feima.entity.QueryParam;

import java.util.List;

/**
 * Created by helei on 2017/5/3.
 * PendingOrderControl
 */

public class NoticeCenterControl {
    public interface NoticeCenterView extends LoadDataView{
        void loadFail(Throwable throwable);
        void querySuccess(List<OrderNotice> list);
        void updateSuccess();
        void queryNoticeListSuccess(NoticeResponse noticeResponse);
    }

    public interface PresenterNoticeCenter extends Presenter<NoticeCenterView> {
        void requestDbNotices(QueryParam param);
        void updateNoticeDB(OrderNotice orderNotice);

        void requestNoticeList(int page,int pageSize,String token);
    }
}
