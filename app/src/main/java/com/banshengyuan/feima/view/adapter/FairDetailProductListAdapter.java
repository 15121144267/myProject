package com.banshengyuan.feima.view.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.entity.FairDetailProductListResponse;
import com.banshengyuan.feima.help.GlideHelper.ImageLoaderHelper;
import com.banshengyuan.feima.utils.ValueUtil;
import com.example.mylibrary.adapter.BaseQuickAdapter;
import com.example.mylibrary.adapter.BaseViewHolder;

import java.util.List;


public class FairDetailProductListAdapter extends BaseQuickAdapter<FairDetailProductListResponse.ListBean, BaseViewHolder> {
    private final Context mContext;
    private final ImageLoaderHelper mImageLoaderHelper;

    public FairDetailProductListAdapter(List<FairDetailProductListResponse.ListBean> mList, Context context, ImageLoaderHelper imageLoaderHelper) {
        super(R.layout.adapter_fair_detail_new, mList);
        mContext = context;
        mImageLoaderHelper = imageLoaderHelper;
    }

    @Override
    protected void convert(BaseViewHolder helper, FairDetailProductListResponse.ListBean item) {
        if (item == null) return;
        ImageView imageView = helper.getView(R.id.adapter_product_icon);
        mImageLoaderHelper.displayImage(mContext, item.cover_img, imageView);
        helper.setText(R.id.adapter_product_name, item.name);
        helper.setText(R.id.adapter_product_price, "￥" + ValueUtil.formatAmount2(item.price));
    }

}
