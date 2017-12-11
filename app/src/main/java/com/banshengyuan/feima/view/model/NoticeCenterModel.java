package com.banshengyuan.feima.view.model;

import com.banshengyuan.feima.database.OrderNotice;
import com.banshengyuan.feima.entity.BuProcessor;
import com.banshengyuan.feima.entity.QueryParam;
import com.banshengyuan.feima.gen.OrderNoticeDao;
import com.banshengyuan.feima.network.networkapi.MyOrderApi;
import com.banshengyuan.feima.network.networkapi.NoticeApi;
import com.google.gson.Gson;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by helei on 2017/4/28.
 * LoginModel
 */

public class NoticeCenterModel {
//    private final OrderNoticeDao mOrderNoticeDao;
    private NoticeApi mApi;
    private final Gson mGson;
    private final ModelTransform mTransform;

//    @Inject
//    public NoticeCenterModel(OrderNoticeDao orderNoticeDao) {
//        mOrderNoticeDao = orderNoticeDao;
//    }

    @Inject
    public NoticeCenterModel(NoticeApi api, Gson gson, ModelTransform transform) {
        mApi = api;
        mGson = gson;
        mTransform = transform;
    }


    public Observable<List<OrderNotice>> queryNoticeDB(QueryParam param) {
        return Observable.create(e->{
            try {
//                List<OrderNotice> list = mOrderNoticeDao.queryBuilder().where(OrderNoticeDao.Properties.OrderTime.between(param.today,param.tomorrow)).list();
//                e.onNext(list);
            } catch (Exception e1) {
                e.onError(e1);
            }
        });
    }

    public Observable<Integer> updateNoticeDB(OrderNotice orderNotice) {
        return Observable.create(e->{
            try {
//                orderNotice.setOrderFlag(1);
//                mOrderNoticeDao.update(orderNotice);
//                e.onNext(1);
            } catch (Exception e1) {
                e.onError(e1);
            }
        });
    }

    public Observable<ResponseData> noticeListRequest(int pageNo, int pageSize,String token) {
        return mApi.noticeListRequest(pageNo, pageSize,token).map(mTransform::transformTypeTwo);
    }
}
