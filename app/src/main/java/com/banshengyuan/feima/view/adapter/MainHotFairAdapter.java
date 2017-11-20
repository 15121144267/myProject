package com.banshengyuan.feima.view.adapter;

import android.content.Context;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.entity.FairBottomResponse;
import com.example.mylibrary.adapter.BaseQuickAdapter;
import com.example.mylibrary.adapter.BaseViewHolder;

import java.util.List;


public class MainHotFairAdapter extends BaseQuickAdapter<FairBottomResponse.ListBean, BaseViewHolder> {
    private final Context mContext;

    public MainHotFairAdapter(List<FairBottomResponse.ListBean> mList, Context context) {
        super(R.layout.adapter_hot_fair, mList);
        mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, FairBottomResponse.ListBean item) {
        if (item == null) return;
        helper.setText(R.id.hot_fair_time_name, item.name);
        helper.setText(R.id.adapter_fair_summary, item.name);

    }

}
