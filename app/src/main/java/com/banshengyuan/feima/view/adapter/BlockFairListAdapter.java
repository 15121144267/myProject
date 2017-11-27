package com.banshengyuan.feima.view.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.entity.BlockDetailFairListResponse;
import com.banshengyuan.feima.help.GlideHelper.ImageLoaderHelper;
import com.banshengyuan.feima.utils.TimeUtil;
import com.example.mylibrary.adapter.BaseQuickAdapter;
import com.example.mylibrary.adapter.BaseViewHolder;

import java.util.List;


public class BlockFairListAdapter extends BaseQuickAdapter<BlockDetailFairListResponse.ListBean, BaseViewHolder> {
    private final Context mContext;
    private final ImageLoaderHelper mImageLoaderHelper;

    public BlockFairListAdapter(List<BlockDetailFairListResponse.ListBean> mList, Context context, ImageLoaderHelper imageLoaderHelper) {
        super(R.layout.adapter_collection_fair, mList);
        mContext = context;
        mImageLoaderHelper = imageLoaderHelper;
    }

    @Override
    protected void convert(BaseViewHolder helper, BlockDetailFairListResponse.ListBean item) {
        if (item == null) return;
        ImageView imageView = helper.getView(R.id.adapter_collection_fair);
        mImageLoaderHelper.displayRoundedCornerImage(mContext, item.cover_img, imageView, 6);
        helper.setText(R.id.adapter_collection_fair_name,item.name);
        helper.setText(R.id.adapter_collection_fair_time, TimeUtil.transferLongToDate(TimeUtil.TIMEYYMMDD,Long.parseLong(item.create_time)));
    }

}
