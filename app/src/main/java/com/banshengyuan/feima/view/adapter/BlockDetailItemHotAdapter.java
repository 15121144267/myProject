package com.banshengyuan.feima.view.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.entity.BlockHotListResponse;
import com.banshengyuan.feima.help.GlideHelper.ImageLoaderHelper;
import com.banshengyuan.feima.utils.TimeUtil;
import com.example.mylibrary.adapter.BaseQuickAdapter;
import com.example.mylibrary.adapter.BaseViewHolder;

import java.util.List;


public class BlockDetailItemHotAdapter extends BaseQuickAdapter<BlockHotListResponse.ListBean, BaseViewHolder> {
    private final Context mContext;
    private final ImageLoaderHelper mImageLoaderHelper;

    public BlockDetailItemHotAdapter(List<BlockHotListResponse.ListBean> mList, Context context, ImageLoaderHelper imageLoaderHelper) {
        super(R.layout.adapter_item_hot, mList);
        mContext = context;
        mImageLoaderHelper = imageLoaderHelper;
    }

    @Override
    protected void convert(BaseViewHolder helper, BlockHotListResponse.ListBean item) {
        if (item == null) return;
        ImageView imageView = helper.getView(R.id.adapter_common_icon);
        mImageLoaderHelper.displayRoundedCornerImage(mContext,item.cover_img,imageView,4);
        helper.setText(R.id.adapter_recommend_text,item.name);
        helper.setText(R.id.adapter_common_des,item.summary);
        helper.setText(R.id.adapter_common_date, TimeUtil.transferLongToDate(TimeUtil.TIME_MMDD_CH,Long.parseLong(item.start_time)));
    }

}
