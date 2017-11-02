package com.banshengyuan.feima.view.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.help.GlideHelper.ImageLoaderHelper;
import com.example.mylibrary.adapter.BaseQuickAdapter;
import com.example.mylibrary.adapter.BaseViewHolder;

import java.util.List;


public class CollectionFairAdapter extends BaseQuickAdapter<Integer, BaseViewHolder> {
    private final Context mContext;
    private final ImageLoaderHelper mImageLoaderHelper;

    public CollectionFairAdapter(List<Integer> mList, Context context, ImageLoaderHelper imageLoaderHelper) {
        super(R.layout.adapter_collection_fair, mList);
        mContext = context;
        mImageLoaderHelper = imageLoaderHelper;
    }

    @Override
    protected void convert(BaseViewHolder helper, Integer item) {
        if (item == null) return;
        ImageView imageView = helper.getView(R.id.adapter_collection_fair);
        mImageLoaderHelper.displayRoundedCornerImage(mContext, item, imageView, 6);
    }

}
