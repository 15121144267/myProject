package com.banshengyuan.feima.view.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by helei on 2017/4/28.
 * ModelTransform
 */

public class ModelTransform {

    public ResponseData transformCommon(String response) {
        ResponseData responseData;
        try {
                JSONObject jsonObject = new JSONObject(response);
             responseData = new ResponseData(jsonObject);
        } catch (JSONException e) {
            responseData = new ResponseData();
        }
        return responseData;
    }

    public ResponseData transformTypeTwo(String response) {
        ResponseData responseData;
        try {
            JSONObject jsonObject = new JSONObject(response);
            responseData = new ResponseData(jsonObject,1);
        } catch (JSONException e) {
            responseData = new ResponseData();
        }
        return responseData;
    }


    public ResponseData transformTypeThree(String response) {
        ResponseData responseData;
        try {
            JSONObject jsonObject = new JSONObject(response);
            responseData = new ResponseData(jsonObject,response);
        } catch (JSONException e) {
            responseData = new ResponseData();
        }
        return responseData;
    }

    public ResponseData transformTypeFour(String response) {
        ResponseData responseData;
        try {
            JSONObject jsonObject = new JSONObject(response);
            responseData = new ResponseData(jsonObject,response,1);
        } catch (JSONException e) {
            responseData = new ResponseData();
        }
        return responseData;
    }
}
