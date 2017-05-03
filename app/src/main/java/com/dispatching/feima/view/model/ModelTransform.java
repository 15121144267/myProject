package com.dispatching.feima.view.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by helei on 2017/4/28.
 */

public class ModelTransform {
    private ResponseData responseData;

    public ResponseData transformCommon(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            responseData = new ResponseData(jsonObject);
            responseData.result = jsonObject.optString("Result") == null ? "" : jsonObject.optString("Result");
        } catch (JSONException e) {
            responseData = new ResponseData();
        }
        return responseData;
    }
}
