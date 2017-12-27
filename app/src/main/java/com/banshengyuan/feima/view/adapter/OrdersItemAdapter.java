package com.banshengyuan.feima.view.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.entity.MyOrdersResponse;
import com.banshengyuan.feima.help.GlideHelper.ImageLoaderHelper;
import com.banshengyuan.feima.utils.ValueUtil;
import com.example.mylibrary.adapter.BaseQuickAdapter;
import com.example.mylibrary.adapter.BaseViewHolder;

import java.util.List;


public class OrdersItemAdapter extends BaseQuickAdapter<MyOrdersResponse.ListBean.ProductBean, BaseViewHolder> {
    private final Context mContext;
    private final ImageLoaderHelper mImageLoaderHelper;

    public OrdersItemAdapter(List<MyOrdersResponse.ListBean.ProductBean> notices, Context context, ImageLoaderHelper imageLoaderHelper) {
        super(R.layout.adapter_order_item, notices);
        mContext = context;
        mImageLoaderHelper = imageLoaderHelper;
    }

    @Override
    protected void convert(BaseViewHolder helper, MyOrdersResponse.ListBean.ProductBean product) {
        if (product == null) return;
        ImageView iconView = helper.getView(R.id.adapter_item_person_icon);
        mImageLoaderHelper.displayRoundedCornerImage(mContext, product.getCover_img(), iconView, 6);

        helper.setText(R.id.adapter_item_product_name, product.getName());
        helper.setText(R.id.adapter_item_product_price, "￥" + ValueUtil.formatAmount(product.getPrice()));
//        helper.setText(R.id.adapter_item_product_info2, "规格:" + product.specification);
        helper.setText(R.id.adapter_item_product_count, "x" + product.getNumber());

    }

}
