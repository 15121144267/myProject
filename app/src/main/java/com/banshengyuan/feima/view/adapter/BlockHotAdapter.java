package com.banshengyuan.feima.view.adapter;

import android.content.Context;

import com.banshengyuan.feima.R;
import com.example.mylibrary.adapter.BaseQuickAdapter;
import com.example.mylibrary.adapter.BaseViewHolder;

import java.util.List;


public class BlockHotAdapter extends BaseQuickAdapter<Integer, BaseViewHolder> {
    private final Context mContext;

    public BlockHotAdapter(List<Integer> mList, Context context) {
        super(R.layout.adapter_hot_fair, mList);
        mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, Integer item) {
        if (item == null) return;
        helper.setVisible(R.id.hot_fair_time_first,true);
    }

}
