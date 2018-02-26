package com.banshengyuan.feima.view.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.help.GlideHelper.ImageLoaderHelper;
import com.example.mylibrary.adapter.BaseQuickAdapter;
import com.example.mylibrary.adapter.BaseViewHolder;

import java.util.List;


public class ProductAnotherAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    private final Context mContext;
    private final ImageLoaderHelper mImageLoaderHelper;

    public ProductAnotherAdapter(List<String> mList, Context context, ImageLoaderHelper imageLoaderHelper) {
        super(R.layout.adapter_proudct_another, mList);
        mContext = context;
        mImageLoaderHelper = imageLoaderHelper;
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        ImageView imageView = helper.getView(R.id.adapter_another_image_view);
        mImageLoaderHelper.displayMatchImage(mContext, item, imageView);
    }

}
