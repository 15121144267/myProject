package com.banshengyuan.feima.view.model;

import com.banshengyuan.feima.entity.PersonInfoRequest;
import com.banshengyuan.feima.entity.PersonInfoResponse;
import com.banshengyuan.feima.network.networkapi.PersonCenterApi;
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


    public Observable<ResponseData> updatePersonInfoRequest(PersonInfoResponse response, String token, boolean avatalFlag) {
        if (avatalFlag) {
            return mApi.updatePersonInfoRequest(mGson.toJson(response.getInfo()), token).map(mTransform::transformCommon);
        } else {//不更新头像
            PersonInfoRequest request = new PersonInfoRequest();
            request.setBirthday(response.getInfo().getBirthday());
            request.setMobile(response.getInfo().getMobile());
            request.setCreated_at(response.getInfo().getCreated_at());
            request.setId(response.getInfo().getId());
            request.setName(response.getInfo().getName());
            request.setPassword(response.getInfo().getPassword());
            request.setReg_source(response.getInfo().getReg_source());
            request.setSalt(response.getInfo().getSalt());
            request.setSex(response.getInfo().getSex());
            request.setStatus(response.getInfo().getStatus());
            request.setToken(response.getInfo().getToken());
            request.setToken_expire(response.getInfo().getToken_expire());

            return mApi.updatePersonInfoRequest(mGson.toJson(request), token).map(mTransform::transformCommon);
        }
    }

}
