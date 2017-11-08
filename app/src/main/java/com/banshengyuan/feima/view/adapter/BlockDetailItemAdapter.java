package com.banshengyuan.feima.view.adapter;

import android.content.Context;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.entity.ProductResponse;
import com.example.mylibrary.adapter.BaseQuickAdapter;
import com.example.mylibrary.adapter.BaseViewHolder;

import java.util.List;


public class BlockDetailItemAdapter extends BaseQuickAdapter<ProductResponse.ProductItemBean, BaseViewHolder> {
    private final Context mContext;

    public BlockDetailItemAdapter(List<ProductResponse.ProductItemBean> mList, Context context) {
        super(R.layout.adapter_recommend_brand, mList);
        mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, ProductResponse.ProductItemBean item) {
        if (item == null) return;
        helper.setText(R.id.adapter_recommend_text, item.content);
        helper.setText(R.id.adapter_common_text, item.tip);
    }

}
