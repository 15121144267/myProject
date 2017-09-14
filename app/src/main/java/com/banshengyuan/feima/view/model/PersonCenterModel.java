package com.banshengyuan.feima.view.model;

import com.banshengyuan.feima.entity.PersonInfoResponse;
import com.banshengyuan.feima.entity.UpdatePersonInfoRequest;
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


    public Observable<ResponseData> updatePersonInfoRequest(PersonInfoResponse response) {
        UpdatePersonInfoRequest request = new UpdatePersonInfoRequest();
        request.partnerId = response.partnerId;
        request.memberId = response.memberId;
        request.avatarUrl = response.avatarUrl;
        request.birthday = response.birthday;
        request.phone = response.phone;
        request.sex = response.sex;
        request.nickName = response.nickName;
        return mApi.updatePersonInfoRequest(mGson.toJson(request)).map(mTransform::transformCommon);
    }

}
