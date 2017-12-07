package com.banshengyuan.feima.view.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.entity.MyOrdersResponse;
import com.banshengyuan.feima.help.GlideHelper.ImageLoaderHelper;
import com.banshengyuan.feima.utils.ValueUtil;
import com.example.mylibrary.adapter.BaseQuickAdapter;
import com.example.mylibrary.adapter.BaseViewHolder;

import java.util.List;


public class OrdersDetailAdapter extends BaseQuickAdapter<MyOrdersResponse.ListBean.ProductBean, BaseViewHolder> {
    private final Context mContext;
    private ImageLoaderHelper mImageLoaderHelper;

    public OrdersDetailAdapter(List<MyOrdersResponse.ListBean.ProductBean> notices, Context context, ImageLoaderHelper imageLoaderHelper) {
        super(R.layout.adapter_order_detail, notices);
        mContext = context;
        mImageLoaderHelper = imageLoaderHelper;
    }

    @Override
    protected void convert(BaseViewHolder helper, MyOrdersResponse.ListBean.ProductBean item) {
        if (item == null) return;
        ImageView view = helper.getView(R.id.adapter_order_detail_product_pic);
        mImageLoaderHelper.displayCircularImage(mContext, item.getCover_img(), view);
        helper.setText(R.id.adapter_order_detail_product_name, item.getName());
        helper.setText(R.id.adapter_order_detail_product_price, "￥" + ValueUtil.formatAmount2(item.getPrice()));
        helper.setText(R.id.adapter_order_detail_product_count, "X" + item.getNumber());

    }

}
