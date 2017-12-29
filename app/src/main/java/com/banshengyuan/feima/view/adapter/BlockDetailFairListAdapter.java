package com.banshengyuan.feima.view.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.entity.BlockFairListResponse;
import com.banshengyuan.feima.help.GlideHelper.ImageLoaderHelper;
import com.banshengyuan.feima.utils.TimeUtil;
import com.example.mylibrary.adapter.BaseQuickAdapter;
import com.example.mylibrary.adapter.BaseViewHolder;

import java.util.List;


public class BlockDetailFairListAdapter extends BaseQuickAdapter<BlockFairListResponse.ListBean, BaseViewHolder> {
    private final Context mContext;
    private final ImageLoaderHelper mImageLoaderHelper;

    public BlockDetailFairListAdapter(List<BlockFairListResponse.ListBean> mList, Context context, ImageLoaderHelper imageLoaderHelper) {
        super(R.layout.adapter_collection_fair, mList);
        mContext = context;
        mImageLoaderHelper = imageLoaderHelper;
    }

    @Override
    protected void convert(BaseViewHolder helper, BlockFairListResponse.ListBean item) {
        if (item == null) return;
        ImageView imageView = helper.getView(R.id.adapter_collection_fair);
        mImageLoaderHelper.displayRoundedCornerImage(mContext, item.cover_img, imageView, 4);
        helper.setText(R.id.adapter_collection_fair_name, item.name);
        helper.setText(R.id.adapter_collection_fair_time, TimeUtil.transferLongToDate(TimeUtil.TIMEYYMMDD, (long) item.create_time));
    }

}
