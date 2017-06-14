package com.dispatching.feima.view.model;

import com.dispatching.feima.BuildConfig;
import com.dispatching.feima.database.OrderNotice;
import com.dispatching.feima.entity.DeliveryStatusRequest;
import com.dispatching.feima.entity.QueryParam;
import com.dispatching.feima.gen.OrderNoticeDao;
import com.dispatching.feima.network.networkapi.MainApi;
import com.google.gson.Gson;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

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
    @Inject
    public MainModel(MainApi api, Gson gson, ModelTransform transform,OrderNoticeDao orderNoticeDao) {
        mMainApi = api;
        mGson = gson;
        mTransform = transform;
        version = BuildConfig.VERSION_NAME;
        mOrderNoticeDao = orderNoticeDao;
    }

    public Observable<ResponseData> PendingOrderInfoRequest(String token,String uId) {
        return mMainApi.WaitOrderInfoRequest(token, uId, version).map(mTransform::transformCommon);
    }

    public Observable<ResponseData> SendingOrderInfoRequest(String token, String uId) {
        return mMainApi.SendingOrderInfoRequest(token, uId, version).map(mTransform::transformCommon);
    }

    public Observable<ResponseData> CompleteOrderInfoRequest(String token, String uId) {
        return mMainApi.CompletedOrderInfoRequest(token, uId, version).map(mTransform::transformCommon);
    }

    public Observable<ResponseData> TakeOrderRequest(Integer position, String token, String uId, String deliveryId) {
        DeliveryStatusRequest request = new DeliveryStatusRequest();
        request.token = token;
        request.version = version;
        request.uId = uId;
        request.deliveryId = deliveryId;
        switch (position) {
            case 0:
                return mMainApi.TakeDeliveryRequest(mGson.toJson(request)).map(mTransform::transformCommon);
            case 1:
                return mMainApi.ArrivedDeliveryRequest(mGson.toJson(request)).map(mTransform::transformCommon);
            default:
                ResponseData responseData = new ResponseData();
                return Observable.just(responseData);
        }

    }

    public Observable<Integer> updateDb(String businessId){
        return Observable.create(e->{
            try {
                List<OrderNotice> list = mOrderNoticeDao.queryBuilder().where(OrderNoticeDao.Properties.OrderId.eq(businessId)).build().list();
                if(list.size()>0){
                   OrderNotice orderNotice = list.get(0);
                    orderNotice.setOrderFlag(1);
                    mOrderNoticeDao.update(orderNotice);
                }
                e.onNext(list.size());
            } catch (Exception e1) {
               e.onError(e1);
            }

        });
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
