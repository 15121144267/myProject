package com.banshengyuan.feima.view.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.entity.OrderDetailResponse;
import com.banshengyuan.feima.help.GlideHelper.ImageLoaderHelper;
import com.banshengyuan.feima.utils.ValueUtil;
import com.example.mylibrary.adapter.BaseQuickAdapter;
import com.example.mylibrary.adapter.BaseViewHolder;

import java.util.List;


public class OrdersDetailAdapter extends BaseQuickAdapter<OrderDetailResponse.GoodsListBean.ProductBean, BaseViewHolder> {
    private final Context mContext;
    private final ImageLoaderHelper mImageLoaderHelper;

    public OrdersDetailAdapter(List<OrderDetailResponse.GoodsListBean.ProductBean> notices, Context context, ImageLoaderHelper imageLoaderHelper) {
        super(R.layout.adapter_order_detail, notices);
        mContext = context;
        mImageLoaderHelper = imageLoaderHelper;
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderDetailResponse.GoodsListBean.ProductBean item) {
        if (item == null) return;
        ImageView view = helper.getView(R.id.adapter_order_detail_product_pic);
        mImageLoaderHelper.displayCircularImage(mContext, item.getGoods_img(), view);
        helper.setText(R.id.adapter_order_detail_product_name, item.getGoods_name());
        if (item.getGoods_price()>=0) {
            helper.setText(R.id.adapter_order_detail_product_price, "￥" + ValueUtil.formatAmount2(item.getGoods_price()));
        }
        helper.setText(R.id.adapter_order_detail_product_count, "X" + item.getNumber());
    }

}
