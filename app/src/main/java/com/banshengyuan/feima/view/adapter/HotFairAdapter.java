package com.banshengyuan.feima.view.adapter;

import android.content.Context;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.entity.HotFairResponse;
import com.example.mylibrary.adapter.BaseQuickAdapter;
import com.example.mylibrary.adapter.BaseViewHolder;

import java.util.List;


public class HotFairAdapter extends BaseQuickAdapter<HotFairResponse, BaseViewHolder> {
    private final Context mContext;

    public HotFairAdapter(List<HotFairResponse> mList, Context context) {
        super(R.layout.adapter_hot_fair, mList);
        mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, HotFairResponse item) {
        if (item == null) return;
        helper.setText(R.id.adapter_fair_text, item.name);
    }

}
