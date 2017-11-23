package com.banshengyuan.feima.view.adapter;

import android.content.Context;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.entity.ShopDetailCouponListResponse;
import com.banshengyuan.feima.help.GlideHelper.ImageLoaderHelper;
import com.example.mylibrary.adapter.BaseQuickAdapter;
import com.example.mylibrary.adapter.BaseViewHolder;

import java.util.List;


public class CouponListAdapter extends BaseQuickAdapter<ShopDetailCouponListResponse.ListBean, BaseViewHolder> {
    private final Context mContext;
    private ImageLoaderHelper mImageLoaderHelper;
    public CouponListAdapter(List<ShopDetailCouponListResponse.ListBean> mList, Context context,ImageLoaderHelper imageLoaderHelper) {
        super(R.layout.adapter_coupon_list, mList);
        mContext = context;
        mImageLoaderHelper = imageLoaderHelper;
    }

    @Override
    protected void convert(BaseViewHolder helper, ShopDetailCouponListResponse.ListBean item) {
        if (item == null) return;
       /* ImageView imageView = helper.getView(R.id.adapter_coupon_icon);
        mImageLoaderHelper.displayImage(mContext,null,imageView);*/
    }

}
