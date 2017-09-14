package com.banshengyuan.feima.wxapi;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.banshengyuan.feima.BuildConfig;
import com.banshengyuan.feima.R;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelpay.PayResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {
	

    private IWXAPI api;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_result);

    	api = WXAPIFactory.createWXAPI(this, BuildConfig.WX_APP_ID);

		try {
			api.handleIntent(getIntent(), this);
		} catch (Exception e) {
			e.printStackTrace();
		}

    }

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
        api.handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq req) {

	}

	@Override
	public void onResp(BaseResp resp) {
		if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
			switch (resp.errCode){
				case BaseResp.ErrCode.ERR_OK:
					PayResp payResp = null;
					if (resp instanceof PayResp){
						payResp = (PayResp) resp;
					}
					/*if (payResp != null && !TextUtils.isEmpty(payResp.extData)){
						Intent i = new Intent(Constant.LOCAL_BROADCAST_WXPAY_SUCCESS);
						i.putExtra(Constant.LOCAL_BROADCAST_WXPAY_SUCCESS, payResp.extData);
						LocalBroadcastManager.getInstance(this).sendBroadcast(i);
					}*/
					Toast.makeText(this,"支付成功",Toast.LENGTH_SHORT).show();
					break;
				case BaseResp.ErrCode.ERR_USER_CANCEL:
					Toast.makeText(this,"取消支付",Toast.LENGTH_SHORT).show();
					break;
				default:
					Toast.makeText(this,"支付失败",Toast.LENGTH_SHORT).show();
					break;
			}
			finish();
		}
	}
}