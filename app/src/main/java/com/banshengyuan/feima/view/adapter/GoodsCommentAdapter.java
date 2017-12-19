package com.banshengyuan.feima.view.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.entity.GoodsCommentResponse;
import com.banshengyuan.feima.help.GlideHelper.ImageLoaderHelper;
import com.example.mylibrary.adapter.BaseQuickAdapter;
import com.example.mylibrary.adapter.BaseViewHolder;

import java.util.List;


public class GoodsCommentAdapter extends BaseQuickAdapter<GoodsCommentResponse.ListBean, BaseViewHolder> {
    private final Context mContext;
    private final ImageLoaderHelper mImageLoaderHelper;

    public GoodsCommentAdapter(List<GoodsCommentResponse.ListBean> mList, Context context, ImageLoaderHelper imageLoaderHelper) {
        super(R.layout.adapter_goods_comment, mList);
        mContext = context;
        mImageLoaderHelper = imageLoaderHelper;
    }

    @Override
    protected void convert(BaseViewHolder helper, GoodsCommentResponse.ListBean item) {
        helper.setText(R.id.adapter_comment_name, item.user_name);
//        helper.setText(R.id.adapter_comment_time, TimeUtil.transferLongToDate(TimeUtil.TIME_YYMMDD, (long) item.));
        helper.setText(R.id.adapter_comment_content, item.content);
        ImageView imageView = helper.getView(R.id.adapter_comment_icon);
        mImageLoaderHelper.displayCircularImage(mContext, item.user_head_img, imageView);
    }

}
