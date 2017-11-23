package com.banshengyuan.feima.view.adapter;

import android.content.Context;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.entity.ShopDetailCouponListResponse;
import com.example.mylibrary.adapter.BaseQuickAdapter;
import com.example.mylibrary.adapter.BaseViewHolder;

import java.util.List;


public class CouponListAdapter extends BaseQuickAdapter<ShopDetailCouponListResponse.ListBean, BaseViewHolder> {
    private final Context mContext;
    public CouponListAdapter(List<ShopDetailCouponListResponse.ListBean> mList, Context context ) {
        super(R.layout.adapter_coupon_list, mList);
        mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, ShopDetailCouponListResponse.ListBean item) {
        if (item == null) return;

    }

}
