package com.banshengyuan.feima.view.adapter;

import android.content.Context;

import com.banshengyuan.feima.R;
import com.example.mylibrary.adapter.BaseQuickAdapter;
import com.example.mylibrary.adapter.BaseViewHolder;

import java.util.List;


public class CouponListAdapter extends BaseQuickAdapter<Integer, BaseViewHolder> {
    private final Context mContext;

    public CouponListAdapter(List<Integer> mList, Context context) {
        super(R.layout.adapter_coupon_list, mList);
        mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, Integer item) {
        if (item == null) return;
        helper.setImageResource(R.id.adapter_coupon_icon,item);

    }

}
