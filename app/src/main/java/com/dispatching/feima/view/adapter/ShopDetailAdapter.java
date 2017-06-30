package com.dispatching.feima.view.adapter;

import android.content.Context;

import com.dispatching.feima.R;
import com.dispatching.feima.entity.ShopDetailResponse;

import java.util.List;


public class ShopDetailAdapter extends BaseQuickAdapter<ShopDetailResponse, BaseViewHolder> {
    private final Context mContext;

    public ShopDetailAdapter(List<ShopDetailResponse> mList, Context context) {
        super(R.layout.adapter_shop_detail, mList);
        mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, ShopDetailResponse item) {
        if(item == null) return;
        helper.setText(R.id.adapter_goods_name,item.productName);
        helper.setText(R.id.adapter_goods_price,item.productPrice);
        helper.setText(R.id.adapter_goods_count,item.productCount);
    }

}
