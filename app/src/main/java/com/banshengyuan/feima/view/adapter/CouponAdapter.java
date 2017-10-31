package com.banshengyuan.feima.view.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.banshengyuan.feima.R;
import com.example.mylibrary.adapter.BaseQuickAdapter;
import com.example.mylibrary.adapter.BaseViewHolder;

import java.util.List;


public class CouponAdapter extends BaseQuickAdapter<Integer, BaseViewHolder> {
    private final Context mContext;

    public CouponAdapter(List<Integer> mList, Context context) {
        super(R.layout.adapter_coupon_item, mList);
        mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, Integer item) {
        if (item == null) return;
        ImageView imageView = helper.getView(R.id.adapter_coupon_image);
        imageView.setImageResource(item);
    }

}
