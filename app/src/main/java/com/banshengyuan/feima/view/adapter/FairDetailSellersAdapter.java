package com.banshengyuan.feima.view.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.entity.SearchResultResponse;
import com.banshengyuan.feima.help.GlideHelper.ImageLoaderHelper;
import com.example.mylibrary.adapter.BaseQuickAdapter;
import com.example.mylibrary.adapter.BaseViewHolder;

import java.util.List;


public class FairDetailSellersAdapter extends BaseQuickAdapter<SearchResultResponse.ListBean, BaseViewHolder> {
    private final Context mContext;
    private final ImageLoaderHelper mImageLoaderHelper;

    public FairDetailSellersAdapter(List<SearchResultResponse.ListBean> mList, Context context,ImageLoaderHelper imageLoaderHelper) {
        super(R.layout.adapter_fair_detail_shop, mList);
        mContext = context;
        mImageLoaderHelper = imageLoaderHelper;
    }

    @Override
    protected void convert(BaseViewHolder helper, SearchResultResponse.ListBean item) {
        ImageView imageView = helper.getView(R.id.adapter_store_icon);
        mImageLoaderHelper.displayImage(mContext,item.cover_img,imageView);
        helper.setText(R.id.adapter_store_name,item.name);
        helper.setText(R.id.adapter_store_category,item.category);
    }

}
