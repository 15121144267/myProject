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


public class BlockHotAdapter extends BaseQuickAdapter<BlockHotListResponse.ListBean, BaseViewHolder> {
    private final Context mContext;
    private final ImageLoaderHelper mImageLoaderHelper;

    public BlockHotAdapter(List<BlockHotListResponse.ListBean> mList, Context context,ImageLoaderHelper imageLoaderHelper) {
        super(R.layout.adapter_hot_fair, mList);
        mContext = context;
        mImageLoaderHelper = imageLoaderHelper;
    }

    @Override
    protected void convert(BaseViewHolder helper, BlockHotListResponse.ListBean item) {
        if (item == null) return;
        helper.setVisible(R.id.hot_fair_time_first,true);
        ImageView imageView = helper.getView(R.id.hot_fair_pic);
        mImageLoaderHelper.displayRoundedCornerImage(mContext,item.cover_img,imageView,4);
        helper.setText(R.id.hot_fair_time_name,item.name);
        helper.setText(R.id.adapter_fair_summary,item.summary);
        helper.setText(R.id.hot_fair_time_first, TimeUtil.transferLongToDate(TimeUtil.TIME_MMDD_CH,Long.parseLong(item.start_time)));

    }

}
