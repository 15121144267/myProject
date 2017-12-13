package com.banshengyuan.feima.view.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.entity.FairContentDetailResponse;
import com.banshengyuan.feima.help.GlideHelper.ImageLoaderHelper;
import com.example.mylibrary.adapter.BaseQuickAdapter;
import com.example.mylibrary.adapter.BaseViewHolder;

import java.util.List;


public class FairContentDetailProductItemAdapter extends BaseQuickAdapter<FairContentDetailResponse.DetailBean.ProductBean, BaseViewHolder> {
    private final Context mContext;
    private final ImageLoaderHelper mImageLoaderHelper;

    public FairContentDetailProductItemAdapter(List<FairContentDetailResponse.DetailBean.ProductBean> list, Context context, ImageLoaderHelper imageLoaderHelper) {
        super(R.layout.adapter_fair_content_product_item, list);
        mContext = context;
        mImageLoaderHelper = imageLoaderHelper;
    }

    @Override
    protected void convert(BaseViewHolder helper, FairContentDetailResponse.DetailBean.ProductBean item) {
        ImageView imageView = helper.getView(R.id.product_icon);
        mImageLoaderHelper.displayImage(mContext, item.cover_img, imageView);
        helper.setText(R.id.product_name,item.name);
    }

}
