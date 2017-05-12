package com.dispatching.feima.view.model;

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

    public ResponseData transformNotice(String response) {
        ResponseData responseData;
        try {
            JSONObject jsonObject = new JSONObject(response);
            responseData = new ResponseData(jsonObject,1);
        } catch (JSONException e) {
            responseData = new ResponseData();
        }
        return responseData;
    }
}
