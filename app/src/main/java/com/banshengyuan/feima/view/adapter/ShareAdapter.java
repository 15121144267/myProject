package com.banshengyuan.feima.view.adapter;

import android.content.Context;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.entity.ShareStatus;
import com.example.mylibrary.adapter.BaseQuickAdapter;
import com.example.mylibrary.adapter.BaseViewHolder;

import java.util.List;


public class ShareAdapter extends BaseQuickAdapter<ShareStatus, BaseViewHolder> {
    private final Context mContext;

    public ShareAdapter(List<ShareStatus> mList, Context context) {
        super(R.layout.adapter_share, mList);
        mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, ShareStatus item) {
        helper.setImageResource(R.id.adapter_share_icon, item.res);
        helper.setText(R.id.adapter_share_text, item.name);
    }

}
