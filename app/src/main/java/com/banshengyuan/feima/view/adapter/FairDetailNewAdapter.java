package com.banshengyuan.feima.view.adapter;

import android.content.Context;

import com.banshengyuan.feima.R;
import com.example.mylibrary.adapter.BaseQuickAdapter;
import com.example.mylibrary.adapter.BaseViewHolder;

import java.util.List;


public class FairDetailNewAdapter extends BaseQuickAdapter<Integer, BaseViewHolder> {
    private final Context mContext;

    public FairDetailNewAdapter(List<Integer> mList, Context context) {
        super(R.layout.adapter_fair_detail_new, mList);
        mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, Integer item) {
        if (item == null) return;


    }

}
