package com.banshengyuan.feima.view.adapter;

import android.content.Context;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.entity.BlockHotListResponse;
import com.banshengyuan.feima.utils.TimeUtil;
import com.example.mylibrary.adapter.BaseQuickAdapter;
import com.example.mylibrary.adapter.BaseViewHolder;

import java.util.List;


public class BlockDetailItemHotAdapter extends BaseQuickAdapter<BlockHotListResponse.ListBean, BaseViewHolder> {
    private final Context mContext;

    public BlockDetailItemHotAdapter(List<BlockHotListResponse.ListBean> mList, Context context) {
        super(R.layout.adapter_item_hot, mList);
        mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, BlockHotListResponse.ListBean item) {
        if (item == null) return;
        helper.setText(R.id.adapter_recommend_text,item.name);
        helper.setText(R.id.adapter_common_des,item.summary);
        helper.setText(R.id.adapter_common_date, TimeUtil.transferLongToDate(TimeUtil.TIME_MMDD_CH,Long.parseLong(item.end_time)));
    }

}
