package com.banshengyuan.feima.view.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.entity.ProductCategoryResponse;
import com.banshengyuan.feima.help.GlideHelper.ImageLoaderHelper;
import com.banshengyuan.feima.utils.ValueUtil;
import com.example.mylibrary.adapter.BaseQuickAdapter;
import com.example.mylibrary.adapter.BaseViewHolder;

import java.util.List;


public class ProductCategoryListAdapter extends BaseQuickAdapter<ProductCategoryResponse.ListBean, BaseViewHolder> {
    private final Context mContext;
    private final ImageLoaderHelper mImageLoaderHelper;
    public ProductCategoryListAdapter(List<ProductCategoryResponse.ListBean> mList, Context context,ImageLoaderHelper imageLoaderHelper) {
        super(R.layout.adapter_collection_product, mList);
        mContext = context;
        mImageLoaderHelper = imageLoaderHelper;
    }

    @Override
    protected void convert(BaseViewHolder helper, ProductCategoryResponse.ListBean item) {
        ImageView imageView = helper.getView(R.id.adapter_collection_icon);
        mImageLoaderHelper.displayImage(mContext,item.image,imageView);
        helper.setVisible(R.id.adapter_price_layout,true);
        helper.setText(R.id.adapter_product_price, ValueUtil.formatAmount2(item.price));
        helper.setText(R.id.adapter_product_name, item.name);
    }

}
