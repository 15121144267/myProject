package com.dispatching.feima.view.model;

import com.dispatching.feima.entity.LoginRequest;
import com.dispatching.feima.network.networkapi.LoginApi;
import com.dispatching.feima.superscoket.ISendResult;
import com.dispatching.feima.superscoket.SocketClient;
import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

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
    private SocketClient mSocketClient;
    private Channel mChannel;
    private Connection mConnection;
    private String mPhone;

    @Inject
    public LoginModel(LoginApi api, Gson gson, ModelTransform transform, SocketClient socketClient) {
        mLoginApi = api;
        mGson = gson;
        mTransform = transform;
        mSocketClient = socketClient;
    }

    public Observable<Integer> VerifyCodeRequest(String request) {
       /* VerifyCode verifyCode = new VerifyCode();
        verifyCode.SmsSend = request;
        Log.d("connection",mGson.toJson(verifyCode));*/
       String verifyInfo = "SmsSend:"+request+"\r\n";
        return Observable.create(e -> {
                if(mSocketClient.judgeClient()){
                    mSocketClient.SenddData(verifyInfo, new ISendResult() {
                        @Override
                        public void OnSendSuccess() {
                            e.onNext(1);
                        }

                        @Override
                        public void OnSendFailure(Exception e1) {
                            e.onError(e1);
                        }
                    });
                }
        });
    }

    public Observable<ResponseData> LoginRequest(String phone, String password) {
        LoginRequest request = new LoginRequest();
        request.phone = phone;
        request.verifyCode = password;
        return mLoginApi.loginRequest(mGson.toJson(request)).map(mTransform::transformCommon);
    }

}
