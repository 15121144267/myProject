package com.banshengyuan.feima.view.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.entity.CommentListResponse;
import com.banshengyuan.feima.help.GlideHelper.ImageLoaderHelper;
import com.example.mylibrary.adapter.BaseQuickAdapter;
import com.example.mylibrary.adapter.BaseViewHolder;

import java.util.List;


public class CommentAdapter extends BaseQuickAdapter<CommentListResponse.ListBean, BaseViewHolder> {
    private final Context mContext;
    private final ImageLoaderHelper mImageLoaderHelper;
    public CommentAdapter(List<CommentListResponse.ListBean> mList, Context context,ImageLoaderHelper imageLoaderHelper) {
        super(R.layout.adapter_comment, mList);
        mContext = context;
        mImageLoaderHelper = imageLoaderHelper;
    }

    @Override
    protected void convert(BaseViewHolder helper, CommentListResponse.ListBean item) {
        if (item == null) return;
        helper.addOnClickListener(R.id.adapter_comment_number);
        ImageView imageView = helper.getView(R.id.adapter_comment_icon);
        mImageLoaderHelper.displayCircularImage(mContext,item.head_img,imageView);
        helper.setText(R.id.adapter_comment_name,item.username);
        helper.setText(R.id.adapter_comment_time,item.create_time);
        helper.setText(R.id.adapter_comment_content,item.detail);

    }

}
