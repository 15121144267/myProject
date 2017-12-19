package com.banshengyuan.feima.view.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.entity.ClassifySearchListResponse;
import com.banshengyuan.feima.help.GlideHelper.ImageLoaderHelper;
import com.banshengyuan.feima.utils.TimeUtil;
import com.example.mylibrary.adapter.BaseQuickAdapter;
import com.example.mylibrary.adapter.BaseViewHolder;

import java.util.List;


public class ClassifySearchListAdapter extends BaseQuickAdapter<ClassifySearchListResponse.ListBean, BaseViewHolder> {
    private final Context mContext;
    private final ImageLoaderHelper mImageLoaderHelper;

    public ClassifySearchListAdapter(List<ClassifySearchListResponse.ListBean> mList, Context context, ImageLoaderHelper imageLoaderHelper) {
        super(R.layout.adapter_comment_list, mList);
        mContext = context;
        mImageLoaderHelper = imageLoaderHelper;
    }

    @Override
    protected void convert(BaseViewHolder helper, ClassifySearchListResponse.ListBean item) {
        helper.setText(R.id.adapter_comment_name, item.username);
        helper.setText(R.id.adapter_comment_date, TimeUtil.transferLongToDate(TimeUtil.TIME_MMDD_CH, (long) item.create_time));
        helper.setText(R.id.adapter_comment_name, item.detail);
        ImageView imageView = helper.getView(R.id.adapter_comment_icon);
        mImageLoaderHelper.displayCircularImage(mContext, item.head_img, imageView);
    }

}
