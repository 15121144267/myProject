package com.dispatching.feima.view.model;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

public class ResponseData {

    private static final Gson sGson = new GsonBuilder().create();
    public Integer resultCode;
    public String errorDesc;
    public String result;
    public Object parsedData;

    public ResponseData() {
        resultCode = 110;
        errorDesc = "信息获取失败";
    }

    public ResponseData(JSONObject jsonObject) throws JSONException {
        resultCode = jsonObject.optInt("statusCode");
        errorDesc = jsonObject.optString("msg");
        result = jsonObject.optString("data");
    }

    public ResponseData(JSONObject jsonObject, Integer flag) throws JSONException {
        resultCode = jsonObject.optInt("errcode");
        errorDesc = jsonObject.optString("errmsg");
        result = jsonObject.optString("data");

    }

    public ResponseData(JSONObject jsonObject, String response) throws JSONException {
        resultCode = jsonObject.optInt("statusCode");
        errorDesc = jsonObject.optString("msg");
        result = response;
    }

    public ResponseData(JSONObject jsonObject, String response,Integer flag) throws JSONException {
        resultCode = jsonObject.optInt("errcode");
        errorDesc = jsonObject.optString("errmsg");
        result = response;
    }

    public <T> T parseData(Class<T> objectClass) {
        T t = null;
        try {
            t = sGson.fromJson(result, objectClass);
            parsedData = t;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return t;
    }

}
