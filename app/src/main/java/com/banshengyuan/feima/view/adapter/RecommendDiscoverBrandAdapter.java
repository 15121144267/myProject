package com.banshengyuan.feima.view.adapter;

import android.content.Context;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.entity.RecommendDiscoverResponse;
import com.example.mylibrary.adapter.BaseQuickAdapter;
import com.example.mylibrary.adapter.BaseViewHolder;

import java.util.List;


public class RecommendDiscoverBrandAdapter extends BaseQuickAdapter<RecommendDiscoverResponse, BaseViewHolder> {
    private final Context mContext;

    public RecommendDiscoverBrandAdapter(List<RecommendDiscoverResponse> mList, Context context) {
        super(R.layout.adapter_recommend_discover, mList);
        mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, RecommendDiscoverResponse item) {
        if (item == null) return;
        helper.setText(R.id.adapter_recommend_discover_text, item.name);
    }

}
