package com.dispatching.feima.view.model;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

public class ResponseData {

    private static final Gson sGson = new GsonBuilder().create();
    public Integer resultCode;
    public String errorDesc;
    public String businessId;
    public String time;
    private String result;
    public Object parsedData;


    public ResponseData() {
        resultCode = 110;
        errorDesc = "信息获取失败";
    }

    public ResponseData(JSONObject jsonObject) throws JSONException {
        resultCode = jsonObject.getInt("status_code");
        errorDesc = jsonObject.getString("msg");
        result = jsonObject.optString("result") == null ? "" : jsonObject.optString("result");
    }

    public ResponseData(JSONObject jsonObject,Integer type) throws JSONException {
        businessId = jsonObject.getString("business_id");
        time = jsonObject.getString("distribute_time");
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
