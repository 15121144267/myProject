package com.banshengyuan.feima.view.adapter;

import android.content.Context;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.entity.RecommendBrandResponse;
import com.example.mylibrary.adapter.BaseQuickAdapter;
import com.example.mylibrary.adapter.BaseViewHolder;

import java.util.List;


public class RecommendBrandAdapter extends BaseQuickAdapter<RecommendBrandResponse, BaseViewHolder> {
    private final Context mContext;

    public RecommendBrandAdapter(List<RecommendBrandResponse> mList, Context context) {
        super(R.layout.adapter_recommend_brand, mList);
        mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, RecommendBrandResponse item) {
        if (item == null) return;
        helper.setText(R.id.adapter_recommend_text, item.name);
    }

}
