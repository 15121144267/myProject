package com.banshengyuan.feima.view.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.entity.FairContentDetailResponse;
import com.banshengyuan.feima.help.GlideHelper.ImageLoaderHelper;
import com.banshengyuan.feima.utils.ValueUtil;
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
        helper.addOnClickListener(R.id.product_collection).addOnClickListener(R.id.product_buy);
        TextView textView = helper.getView(R.id.product_collection);

        if (item.isCollection) {
            ValueUtil.setTextDrawable(mContext, textView, R.mipmap.shop_detail_collection, 0);
        } else {
            ValueUtil.setTextDrawable(mContext, textView, R.mipmap.shop_detail_uncollection, 0);
        }
        ImageView imageView = helper.getView(R.id.product_icon);
        mImageLoaderHelper.displayImage(mContext, item.image, imageView);
        helper.setText(R.id.product_name, item.name);
    }

}
