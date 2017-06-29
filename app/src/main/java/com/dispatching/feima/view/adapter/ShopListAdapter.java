package com.dispatching.feima.view.adapter;

import android.content.Context;

import com.dispatching.feima.R;
import com.dispatching.feima.entity.ShopListResponse;

import java.util.List;


public class ShopListAdapter extends BaseQuickAdapter<ShopListResponse, BaseViewHolder> {
    private final Context mContext;

    public ShopListAdapter(List<ShopListResponse> notices, Context context) {
        super(R.layout.adapter_shop_item, notices);
        mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, ShopListResponse item) {
        if (item == null) return;
        helper.setText(R.id.item_shop_content1,item.shopDesContent);
        helper.setText(R.id.item_shop_content2,item.shopDesContent);
        helper.setText(R.id.item_shop_content3,item.shopDesContent);
        helper.setText(R.id.item_shop_location,item.shopLocation);

    }

}
