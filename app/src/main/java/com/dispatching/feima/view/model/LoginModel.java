package com.dispatching.feima.view.model;

import com.dispatching.feima.entity.LoginRequest;
import com.dispatching.feima.network.networkapi.LoginApi;
import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by helei on 2017/4/28.
 * LoginModel
 */

public class LoginModel {
    private final LoginApi mLoginApi;
    private final Gson mGson;
    private final ModelTransform mTransform;
    private ConnectionFactory mFactory;
    private Channel mChannel;
    private Connection mConnection;
    private String mPhone;

    @Inject
    public LoginModel(LoginApi api, Gson gson, ModelTransform transform, ConnectionFactory factory) {
        mLoginApi = api;
        mGson = gson;
        mTransform = transform;
        mFactory = factory;
    }

    public Observable<Integer> VerifyCodeRequest(String request) {
        mPhone = request;
        return Observable.create(e -> {
            try {
                new Thread(mSendRunnable).start();
                e.onNext(1);
            } catch (Exception e1) {
                e.onError(e1);
            }
        });
    }

    public Observable<ResponseData> LoginRequest(String phone, String password) {
        LoginRequest request = new LoginRequest();
        request.phone = phone;
        request.verifyCode = password;
        return mLoginApi.loginRequest(mGson.toJson(request)).map(mTransform::transformCommon);
    }

    private final Runnable mSendRunnable = new Runnable() {
        @Override
        public void run() {
            if (mFactory != null) {
                try {
                    if (mConnection == null) {
                        mConnection = mFactory.newConnection();
                    }
                    if (mChannel == null) {
                        mChannel = mConnection.createChannel();
                    }

                    mChannel.queueDeclare("delivery.sms.verify", false, false, false, null);
                    mChannel.basicPublish("", "delivery.sms.verify", null, mPhone.getBytes());
                    mChannel.close();
                    mConnection.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    };
}
