package com.banshengyuan.feima.view.adapter;

import android.content.Context;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.entity.ProductResponse;
import com.example.mylibrary.adapter.BaseQuickAdapter;
import com.example.mylibrary.adapter.BaseViewHolder;

import java.util.List;


public class BlockDetailItemHotAdapter extends BaseQuickAdapter<ProductResponse.ProductItemBean, BaseViewHolder> {
    private final Context mContext;

    public BlockDetailItemHotAdapter(List<ProductResponse.ProductItemBean> mList, Context context) {
        super(R.layout.adapter_item_hot, mList);
        mContext = context;
//        adapter_item_hot
    }

    @Override
    protected void convert(BaseViewHolder helper, ProductResponse.ProductItemBean item) {
        if (item == null) return;

    }

}
