package com.banshengyuan.feima.view.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.entity.MyCollectionProductsResponse;
import com.banshengyuan.feima.help.GlideHelper.ImageLoaderHelper;
import com.example.mylibrary.adapter.BaseQuickAdapter;
import com.example.mylibrary.adapter.BaseViewHolder;

import java.util.List;


public class CollectionProductAdapter extends BaseQuickAdapter<MyCollectionProductsResponse.ListBean, BaseViewHolder> {
    private final Context mContext;
    private final ImageLoaderHelper mImageLoaderHelper;

    public CollectionProductAdapter(List<MyCollectionProductsResponse.ListBean> mList, Context context, ImageLoaderHelper imageLoaderHelper) {
        super(R.layout.adapter_collection_product, mList);
        mContext = context;
        mImageLoaderHelper = imageLoaderHelper;
    }

    @Override
    protected void convert(BaseViewHolder helper, MyCollectionProductsResponse.ListBean item) {
        if (item == null) return;

        ImageView imageView = helper.getView(R.id.adapter_collection_icon);
        mImageLoaderHelper.displayRoundedCornerImage(mContext, item.getCover_img(), imageView, 6);
        helper.setText(R.id.adapter_product_name, item.getName());
        helper.setText(R.id.adapter_product_summary, "暂无描述");
        helper.setText(R.id.adapter_product_price, "￥" + item.getPrice());
    }

}
