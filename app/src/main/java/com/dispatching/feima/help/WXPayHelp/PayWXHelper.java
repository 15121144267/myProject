package com.dispatching.feima.help.WXPayHelp;

import com.dispatching.feima.BuildConfig;
import com.dispatching.feima.entity.PayResponse;
import com.dispatching.feima.view.PresenterControl.LoadDataView;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

public class PayWXHelper {


    public static PayWXHelper getInstance() {
        return new PayWXHelper();
    }

    public void pay(PayResponse.PayOrderBean payInfo, LoadDataView loadDataView) {

        if (loadDataView == null || loadDataView.getContext() == null) {
            return;
        }
        if (payInfo == null) {
            loadDataView.showToast("支付数据出错");
            return;
        }

        IWXAPI msgApi = WXAPIFactory.createWXAPI(loadDataView.getContext(), BuildConfig.WX_APP_ID);
        msgApi.registerApp(BuildConfig.WX_APP_ID);
        if (!msgApi.isWXAppInstalled()) {
            loadDataView.showToast("当前没有安装微信");
            return;
        }
        PayReq payReq = new PayReq();

        payReq.appId = payInfo.appid;
        payReq.partnerId =payInfo.mch_id;
        payReq.prepayId = payInfo.prepay_id;
        payReq.packageValue = payInfo.packageX;
        payReq.nonceStr = payInfo.nonce_str;
        payReq.timeStamp = payInfo.timestamp;
        payReq.sign = payInfo.sign;
        msgApi.sendReq(payReq);

    }
}
