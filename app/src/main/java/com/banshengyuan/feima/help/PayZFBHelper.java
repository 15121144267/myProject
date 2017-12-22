package com.banshengyuan.feima.help;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.alipay.sdk.app.PayTask;
import com.banshengyuan.feima.R;
import com.banshengyuan.feima.entity.PayResult;
import com.banshengyuan.feima.view.PresenterControl.FinalPayControl;
import com.banshengyuan.feima.view.PresenterControl.LoadDataView;
import com.banshengyuan.feima.view.activity.BaseActivity;

public class PayZFBHelper {
    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_CHECK_FLAG = 2;

    private static PayZFBHelper instance = new PayZFBHelper();
    private int payFromActivity = 0;//支付页面类型 1 订单支付页面
    private LoadDataView mLoadDataView;

    public static PayZFBHelper getInstance() {
        return instance;
    }

    //
    private PayZFBHelper() {
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG:
                    PayResult payResult = new PayResult((String) msg.obj);

                    // 支付宝返回此次支付结果及加签，建议对支付宝签名信息拿签约时支付宝提供的公钥做验签
                    String resultInfo = payResult.getResult();

                    String resultStatus = payResult.getResultStatus();

                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {
                        mLoadDataView.showToast(mLoadDataView.getContext().getString(R.string.pay_success));
                        paySuccess();
                    } else {
                        // 判断resultStatus 为非“9000”则代表可能支付失败
                        // “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            mLoadDataView.showToast(mLoadDataView.getContext().getString(R.string.pay_verifying));

                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            mLoadDataView.showToast(mLoadDataView.getContext().getString(R.string.pay_fail));

                        }
                    }
                    break;
                default:
                    break;
            }
        }
    };

    public void pay(final String payUrl, LoadDataView loadDataView) {
        this.mLoadDataView = loadDataView;
        if (mLoadDataView == null){
            return;
        }
        if (TextUtils.isEmpty(payUrl)){
            mLoadDataView.showToast("支付失败，请重试");
            return;
        }
        Runnable payRunnable = () -> {
            // 构造PayTask 对象
            PayTask alipay = new PayTask((BaseActivity) mLoadDataView.getContext());
            // 调用支付接口，获取支付结果
            String payResult = alipay.pay(payUrl,true);
            //如果没有安装支付宝客户端，只有选择“完成”，才会回调支付结果
            Message msg = new Message();
            msg.what = SDK_PAY_FLAG;
            msg.obj = payResult;
            mHandler.sendMessage(msg);
        };
        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    private void paySuccess() {
        if (mLoadDataView == null) {
            return;
        }

        if(mLoadDataView instanceof FinalPayControl.FinalPayView){
            FinalPayControl.FinalPayView view = (FinalPayControl.FinalPayView)mLoadDataView;
            view.orderPaySuccess();
        }
    }
}
