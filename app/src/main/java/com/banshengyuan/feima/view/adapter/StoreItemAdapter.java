package com.banshengyuan.feima.view.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.entity.StoreListResponse;
import com.banshengyuan.feima.help.GlideHelper.ImageLoaderHelper;
import com.example.mylibrary.adapter.BaseQuickAdapter;
import com.example.mylibrary.adapter.BaseViewHolder;

import java.util.List;


public class StoreItemAdapter extends BaseQuickAdapter<StoreListResponse.CategoryBean.GoodsBean, BaseViewHolder> {
    private final Context mContext;
    private final ImageLoaderHelper mImageLoaderHelper;


    public StoreItemAdapter(List<StoreListResponse.CategoryBean.GoodsBean> mList, Context context, ImageLoaderHelper imageLoaderHelper) {
        super(R.layout.adapter_fragment_pending_common, mList);
        mContext = context;
        mImageLoaderHelper = imageLoaderHelper;
    }

    @Override
    protected void convert(BaseViewHolder helper, StoreListResponse.CategoryBean.GoodsBean item) {
        if (item == null) return;
        ImageView imageView = helper.getView(R.id.adapter_common_icon);
        mImageLoaderHelper.displayRoundedCornerImage(mContext,item.shop_logo,imageView,6);
        helper.setText(R.id.adapter_recommend_text, item.shop_name);
    }

}
