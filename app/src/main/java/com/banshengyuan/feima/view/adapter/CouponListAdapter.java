package com.banshengyuan.feima.view.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.entity.ShopDetailCouponListResponse;
import com.example.mylibrary.adapter.BaseQuickAdapter;
import com.example.mylibrary.adapter.BaseViewHolder;

import java.util.List;


public class CouponListAdapter extends BaseQuickAdapter<ShopDetailCouponListResponse.ListBean, BaseViewHolder> {
    private final Context mContext;

    public CouponListAdapter(List<ShopDetailCouponListResponse.ListBean> mList, Context context) {
        super(R.layout.adapter_coupon_list, mList);
        mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, ShopDetailCouponListResponse.ListBean item) {

        helper.addOnClickListener(R.id.adapter_coupon_state);
        if (item.type == 1) {
            helper.setText(R.id.adapter_coupon_type, "元优惠券");
            helper.setText(R.id.adapter_coupon_price, "" + item.price);
            helper.setText(R.id.adapter_coupon_des, "满" + item.start_val + "元即可用" + item.end_val + "元优惠券");
        } else {
            helper.setText(R.id.adapter_coupon_type, "折优惠券");
            helper.setText(R.id.adapter_coupon_price, "" + item.price * 10 + "");
            helper.setText(R.id.adapter_coupon_des, "领取即可用" + item.end_val * 10 + "折优惠券");
        }
        if (item.is_received) {
            helper.setBackgroundColor(R.id.adapter_coupon_background, ContextCompat.getColor(mContext, R.color.gray));
            helper.setText(R.id.adapter_coupon_state, "已经领取");
            helper.setEnable(R.id.adapter_coupon_state, false);
        } else {
            helper.setBackgroundColor(R.id.adapter_coupon_background, ContextCompat.getColor(mContext, R.color.light_red));
            helper.setText(R.id.adapter_coupon_state, "立即领取");
            helper.setEnable(R.id.adapter_coupon_state, true);
        }

    }

}
