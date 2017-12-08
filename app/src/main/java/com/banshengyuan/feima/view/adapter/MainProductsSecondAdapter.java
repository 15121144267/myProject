package com.banshengyuan.feima.view.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.entity.AllProductSortResponse;
import com.banshengyuan.feima.help.GlideHelper.ImageLoaderHelper;
import com.example.mylibrary.adapter.BaseQuickAdapter;
import com.example.mylibrary.adapter.BaseViewHolder;

import java.util.List;


public class MainProductsSecondAdapter extends BaseQuickAdapter<AllProductSortResponse.ListBean, BaseViewHolder> {
    private final Context mContext;
    private ImageLoaderHelper mImageLoaderHelper;

    public MainProductsSecondAdapter(List<AllProductSortResponse.ListBean> notices, Context context, ImageLoaderHelper imageLoaderHelper) {
        super(R.layout.adapter_product_sencond, notices);
        mContext = context;
        mImageLoaderHelper = imageLoaderHelper;
    }

    @Override
    protected void convert(BaseViewHolder helper, AllProductSortResponse.ListBean item) {
        helper.setText(R.id.product_name, item.name);
        ImageView imageView = helper.getView(R.id.product_drawable);
        mImageLoaderHelper.displayImage(mContext, item.cover_img, imageView);
    }

}
