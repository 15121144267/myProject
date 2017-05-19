package com.dispatching.feima.view.model;

import com.dispatching.feima.database.OrderNotice;
import com.dispatching.feima.entity.QueryParam;
import com.dispatching.feima.gen.OrderNoticeDao;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;

/**
 * Created by helei on 2017/4/28.
 * LoginModel
 */

public class NoticeCenterModel {
    private final OrderNoticeDao mOrderNoticeDao;
    @Inject
    public NoticeCenterModel(OrderNoticeDao orderNoticeDao) {
        mOrderNoticeDao = orderNoticeDao;
    }


    public Observable<List<OrderNotice>> queryNoticeDB(QueryParam param) {
        return Observable.create(new ObservableOnSubscribe<List<OrderNotice>>(){
            @Override
            public void subscribe(@NonNull ObservableEmitter<List<OrderNotice>> e) throws Exception {
                try {
                    List<OrderNotice> list = mOrderNoticeDao.queryBuilder().where(OrderNoticeDao.Properties.OrderTime.between(param.today,param.tomorrow)).list();
                    e.onNext(list);
                } catch (Exception e1) {
                    e.onError(e1);
                }
            }
        });
    }
}
