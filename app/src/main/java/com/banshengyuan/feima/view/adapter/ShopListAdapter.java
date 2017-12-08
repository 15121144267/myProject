package com.banshengyuan.feima.view.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.entity.StoreCategoryListResponse;
import com.banshengyuan.feima.help.GlideHelper.ImageLoaderHelper;
import com.example.mylibrary.adapter.BaseQuickAdapter;
import com.example.mylibrary.adapter.BaseViewHolder;

import java.util.List;


public class ShopListAdapter extends BaseQuickAdapter<StoreCategoryListResponse.ListBean, BaseViewHolder> {
    private final Context mContext;
    private final ImageLoaderHelper mImageLoaderHelper;

    public ShopListAdapter(List<StoreCategoryListResponse.ListBean> notices, Context context, ImageLoaderHelper imageLoaderHelper) {
        super(R.layout.adapter_shop_list_item, notices);
        mContext = context;
        mImageLoaderHelper = imageLoaderHelper;
    }

    @Override
    protected void convert(BaseViewHolder helper, StoreCategoryListResponse.ListBean item) {
        if (item == null) return;
        ImageView imageView = helper.getView(R.id.adapter_shop_list_icon);
        mImageLoaderHelper.displayImage(mContext, item.cover_img, imageView);
        helper.setText(R.id.adapter_shop_list_name,item.name);
        helper.setText(R.id.adapter_shop_list_summary,item.category);

    }

}
