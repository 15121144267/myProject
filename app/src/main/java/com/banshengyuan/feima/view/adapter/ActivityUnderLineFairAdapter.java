package com.banshengyuan.feima.view.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.entity.FairUnderLineResponse;
import com.banshengyuan.feima.help.GlideHelper.ImageLoaderHelper;
import com.example.mylibrary.adapter.BaseQuickAdapter;
import com.example.mylibrary.adapter.BaseViewHolder;

import java.util.List;


public class ActivityUnderLineFairAdapter extends BaseQuickAdapter<FairUnderLineResponse.ListBean, BaseViewHolder> {
    private final Context mContext;
    private final ImageLoaderHelper mImageLoaderHelper;

    public ActivityUnderLineFairAdapter(List<FairUnderLineResponse.ListBean> mList, Context context, ImageLoaderHelper imageLoaderHelper) {
        super(R.layout.adapter_under_line_fair, mList);
        mContext = context;
        mImageLoaderHelper = imageLoaderHelper;
    }

    @Override
    protected void convert(BaseViewHolder helper, FairUnderLineResponse.ListBean item) {
        helper.setText(R.id.adapter_under_block_name, item.name);
        ImageView imageView = helper.getView(R.id.adapter_under_block_pic);
        mImageLoaderHelper.displayImage(mContext, item.cover_img, imageView);

    }

}
