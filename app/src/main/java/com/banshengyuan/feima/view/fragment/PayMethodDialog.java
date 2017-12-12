package com.banshengyuan.feima.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.help.AniCreator;
import com.banshengyuan.feima.help.DialogFactory;


public class PayMethodDialog extends BaseDialogFragment {
    public static final String TAG = PayMethodDialog.class.getSimpleName();
    private RelativeLayout mLayout;
    private LinearLayout mBtnZFBPay;
    private LinearLayout mBtnWXPay;
    private TextView mCancelBrn;
    private PayMethodClickListener mListener;
//    private String payFlag = "0";//支付开关 0 支付关闭 1 支付宝支付开启 2 支付宝和微信支付开启

    public static PayMethodDialog newInstance() {
        return new PayMethodDialog();
    }

    /*public void setPayFlag(String payFlag) {
        this.payFlag = payFlag;
    }*/

    public void setListener(PayMethodClickListener listener) {
        this.mListener = listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_pay_method_dialog, container, true);

        mLayout = (RelativeLayout) view.findViewById(R.id.payMethod_dialog_layout);
        mCancelBrn = (TextView) view.findViewById(R.id.payMethod_dialog_cancel);
        LinearLayout mAnimLayout = (LinearLayout) view.findViewById(R.id.payMethod_dialog_animLayout);
        mBtnZFBPay = (LinearLayout) view.findViewById(R.id.payMethod_ZFBLayout);
        mBtnWXPay = (LinearLayout) view.findViewById(R.id.payMethod_WXLayout);
        AniCreator.getInstance().apply_animation_translate(mAnimLayout, AniCreator.ANIMATION_MODE_POPUP, View.VISIBLE, false, null);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBtnZFBPay.setOnClickListener(this);
        mBtnWXPay.setOnClickListener(this);
        mLayout.setOnClickListener(this);
        mCancelBrn.setOnClickListener(this);

       /* if ("1".equals(payFlag)) {
            mWxPayLine.setVisibility(View.GONE);
            mBtnWXPay.setVisibility(View.GONE);
            mBtnZFBPay.setVisibility(View.VISIBLE);
        } else {
            mWxPayLine.setVisibility(View.VISIBLE);
            mBtnWXPay.setVisibility(View.VISIBLE);
            mBtnZFBPay.setVisibility(View.VISIBLE);
        }*/
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.payMethod_dialog_layout:
                closeThisDialog();
                break;
            case R.id.payMethod_dialog_cancel:
                closeThisDialog();
                break;
            case R.id.payMethod_WXLayout:
                if (mListener != null) {
                    mListener.clickRechargeBtn(2);
                }
                closeThisDialog();
                break;
            case R.id.payMethod_ZFBLayout:
                if (mListener != null) {
                    mListener.clickRechargeBtn(1);
                }
                closeThisDialog();
                break;
        }
    }

    public interface PayMethodClickListener {
        void clickRechargeBtn(Integer payType);
    }


    private void closeThisDialog() {
        try {
            this.dismiss();
        } catch (Exception e) {
            DialogFactory.dismissDialogFragment(getActivity().getSupportFragmentManager(), TAG);
        }
    }
}
