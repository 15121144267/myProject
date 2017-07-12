package com.dispatching.feima.view.model;

import com.dispatching.feima.BuildConfig;
import com.dispatching.feima.entity.QueryParam;
import com.dispatching.feima.gen.OrderNoticeDao;
import com.dispatching.feima.network.networkapi.MainApi;
import com.google.gson.Gson;

import org.greenrobot.greendao.query.QueryBuilder;

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
    private final String version;
    private final OrderNoticeDao mOrderNoticeDao;
    private final String partnerId = BuildConfig.PARTNER_ID;
    @Inject
    public MainModel(MainApi api, Gson gson, ModelTransform transform,OrderNoticeDao orderNoticeDao) {
        mMainApi = api;
        mGson = gson;
        mTransform = transform;
        version = BuildConfig.VERSION_NAME;
        mOrderNoticeDao = orderNoticeDao;
    }

    public Observable<ResponseData> personInfoRequest(String phone) {
        return mMainApi.personInfoRequest(partnerId,phone).map(mTransform::transformCommon);
    }

    public Observable<Integer> queryNoticeDb(QueryParam param){
        return Observable.create(e->{
            try {
                QueryBuilder qb = mOrderNoticeDao.queryBuilder();
                qb.where(qb.and(OrderNoticeDao.Properties.OrderFlag.eq(0),OrderNoticeDao.Properties.OrderTime.between(param.today,param.tomorrow)));
                e.onNext(qb.build().list().size());
            } catch (Exception e1) {
                e.onError(e1);
            }

        });
    }
}
