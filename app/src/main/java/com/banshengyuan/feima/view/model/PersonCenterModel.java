package com.banshengyuan.feima.view.model;

import com.banshengyuan.feima.entity.Constant;
import com.banshengyuan.feima.entity.PersonInfoResponse;
import com.banshengyuan.feima.entity.UpdatePersonInfoRequest;
import com.banshengyuan.feima.network.networkapi.PersonCenterApi;
import com.banshengyuan.feima.utils.LogUtils;
import com.google.gson.Gson;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by helei on 2017/4/28.
 * LoginModel
 */

public class PersonCenterModel {
    private final PersonCenterApi mApi;
    private final Gson mGson;
    private final ModelTransform mTransform;

    @Inject
    public PersonCenterModel(PersonCenterApi api, Gson gson, ModelTransform transform) {
        mApi = api;
        mGson = gson;
        mTransform = transform;
    }


    public Observable<ResponseData> updatePersonInfoRequest(PersonInfoResponse response) {
//        PersonInfoResponse.InfoBean infoBean = response.getInfo();
//        UpdatePersonInfoRequest request = new UpdatePersonInfoRequest();
////        request.memberId = response.getInfo().getId() + "";
////        request.id
//        request.id = infoBean.getId();
//        request.created_at = infoBean.getCreated_at();
//        request.password = infoBean.getPassword();
//        request.reg_source = infoBean.getReg_source();
//        request.status = infoBean.getStatus();
//        request.token = infoBean.getToken();
//        request.token_expire = infoBean.getToken_expire();
//
//        request.head_img = infoBean.getHead_img();
//        request.birthday = infoBean.getBirthday();
//        request.mobile = infoBean.getMobile();
//        request.sex = infoBean.getSex();
//        request.name = infoBean.getName();
//        request.salt = infoBean.getSalt();
//
//        LogUtils.i("---"+request.toString());
        return mApi.updatePersonInfoRequest(mGson.toJson(response.getInfo()), Constant.TOKEN).map(mTransform::transformCommon);
    }

}
