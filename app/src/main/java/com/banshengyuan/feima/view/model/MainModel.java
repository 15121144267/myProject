package com.banshengyuan.feima.view.model;

import com.banshengyuan.feima.BuildConfig;
import com.banshengyuan.feima.gen.OrderNoticeDao;
import com.banshengyuan.feima.network.networkapi.MainApi;
import com.google.gson.Gson;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by helei on 2017/4/28.
 * MainModel
 */

public class MainModel {
    private final MainApi mMainApi;
    private final Gson mGson;
    private final ModelTransform mTransform;

    @Inject
    public MainModel(MainApi api, Gson gson, ModelTransform transform, OrderNoticeDao orderNoticeDao) {
        mMainApi = api;
        mGson = gson;
        mTransform = transform;
    }

    public Observable<ResponseData> personInfoRequest(String phone) {
        return mMainApi.personInfoRequest(BuildConfig.PARTNER_ID, phone).map(mTransform::transformCommon);
    }

    /*public Observable<Integer> queryNoticeDb(QueryParam param){
        return Observable.create(e->{
            try {
                QueryBuilder qb = mOrderNoticeDao.queryBuilder();
                qb.where(qb.and(OrderNoticeDao.Properties.OrderFlag.eq(0),OrderNoticeDao.Properties.OrderTime.between(param.today,param.tomorrow)));
                e.onNext(qb.build().list().size());
            } catch (Exception e1) {
                e.onError(e1);
            }

        });
    }*/
}