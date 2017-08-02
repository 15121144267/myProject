package com.dispatching.feima.help.WXPayHelp;

import android.text.TextUtils;

import com.dispatching.feima.BuildConfig;
import com.dispatching.feima.view.PresenterControl.LoadDataView;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.json.JSONException;
import org.json.JSONObject;

public class PayWXHelper {


    public static PayWXHelper getInstance() {
        return new PayWXHelper();
    }

    public void pay(String payInfo, LoadDataView loadDataView) {

        if (loadDataView == null || loadDataView.getContext() == null){
            return;
        }
        if (TextUtils.isEmpty(payInfo)){
            loadDataView.showToast("支付数据出错");
            return;
        }

        IWXAPI msgApi = WXAPIFactory.createWXAPI(loadDataView.getContext(), null);
        msgApi.registerApp(BuildConfig.WX_APP_ID);
        if (!msgApi.isWXAppInstalled()) {
            loadDataView.showToast("当前没有安装微信");
            return;
        }
        PayReq payReq = new PayReq();
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(payInfo);
            payReq.appId = jsonObject.optString("appid");
            payReq.partnerId =jsonObject.optString("partnerid");
            payReq.prepayId = jsonObject.optString("prepayid");
            payReq.packageValue = jsonObject.optString("package");
            payReq.nonceStr = jsonObject.optString("noncestr");
            payReq.timeStamp = jsonObject.optString("timestamp");
            payReq.sign = jsonObject.optString("sign");
            msgApi.sendReq(payReq);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
