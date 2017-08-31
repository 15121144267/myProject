package com.dispatching.feima.view.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.dispatching.feima.R;
import com.dispatching.feima.entity.MyOrdersResponse;
import com.dispatching.feima.help.GlideHelper.ImageLoaderHelper;
import com.dispatching.feima.utils.ValueUtil;
import com.example.mylibrary.adapter.BaseQuickAdapter;
import com.example.mylibrary.adapter.BaseViewHolder;

import java.util.List;


public class OrdersDetailAdapter extends BaseQuickAdapter<MyOrdersResponse.OrdersBean.ProductsBean, BaseViewHolder> {
    private final Context mContext;
    private ImageLoaderHelper mImageLoaderHelper;

    public OrdersDetailAdapter(List<MyOrdersResponse.OrdersBean.ProductsBean> notices, Context context, ImageLoaderHelper imageLoaderHelper) {
        super(R.layout.adapter_order_detail, notices);
        mContext = context;
        mImageLoaderHelper = imageLoaderHelper;
    }

    @Override
    protected void convert(BaseViewHolder helper, MyOrdersResponse.OrdersBean.ProductsBean item) {
        if (item == null) return;
        ImageView view = helper.getView(R.id.adapter_order_detail_product_pic);
        mImageLoaderHelper.displayCircularImage(mContext, item.picture, view);
        helper.setText(R.id.adapter_order_detail_product_name, item.name);
        helper.setText(R.id.adapter_order_detail_product_price, "￥" + ValueUtil.formatAmount2(item.finalPrice));
        helper.setText(R.id.adapter_order_detail_product_count, "X" + item.productNumber);
        helper.setText(R.id.adapter_order_detail_product_total_amount, "￥" + ValueUtil.formatAmount2(item.productNumber * item.finalPrice));

    }

}
