package com.dispatching.feima.view.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;

import com.dispatching.feima.R;
import com.dispatching.feima.entity.MyOrdersResponse;
import com.dispatching.feima.help.GlideHelper.ImageLoaderHelper;
import com.dispatching.feima.utils.SpannableStringUtils;
import com.dispatching.feima.utils.ValueUtil;
import com.example.mylibrary.adapter.BaseQuickAdapter;
import com.example.mylibrary.adapter.BaseViewHolder;

import java.util.List;


public class MyOrdersAdapter extends BaseQuickAdapter<MyOrdersResponse.OrdersBean, BaseViewHolder> {
    private final Context mContext;
    private ImageLoaderHelper mImageLoaderHelper;

    public MyOrdersAdapter(List<MyOrdersResponse.OrdersBean> notices, Context context, ImageLoaderHelper imageLoaderHelper) {
        super(R.layout.adapter_my_orders, notices);
        mContext = context;
        mImageLoaderHelper = imageLoaderHelper;
    }

    @Override
    protected void convert(BaseViewHolder helper, MyOrdersResponse.OrdersBean item) {
        if (item == null) return;
        helper.addOnClickListener(R.id.order_pull_off).addOnClickListener(R.id.order_pull_sure).addOnClickListener(R.id.order_for_pay);
        List<MyOrdersResponse.OrdersBean.ProductsBean> products = item.products;
        RecyclerView recyclerView = helper.getView(R.id.adapter_product_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        OrdersItemAdapter itemAdapter = new OrdersItemAdapter(products, mContext, mImageLoaderHelper);
        recyclerView.setAdapter(itemAdapter);

        helper.setText(R.id.shop_name, "  " + item.shopName);
        switch (item.status) {
            case 2:
                helper.setText(R.id.adapter_order_status, "  待支付");
                helper.setVisible(R.id.order_for_pay, true);
                helper.setVisible(R.id.order_pull_sure, false);
                helper.setVisible(R.id.order_pull_off, false);
                break;
            case 3:
                helper.setText(R.id.adapter_order_status, "  代发货");
                break;
            case 4:
                helper.setText(R.id.adapter_order_status, "  配送中");
                break;
            default:
                helper.setText(R.id.adapter_order_status, "  系统处理中");
        }
        Integer orderCount = 0;
        Integer orderPrice = 0;
        String orderPricePartOne = "合计：";
        for (MyOrdersResponse.OrdersBean.ProductsBean product : products) {
            orderPrice += product.finalPrice * product.productNumber;
            orderCount = +product.productNumber;
        }

        String orderPricePartTwo = "￥" + ValueUtil.formatAmount(orderPrice);
        SpannableStringBuilder stringBuilder = SpannableStringUtils.getBuilder(orderPricePartTwo)
                .setForegroundColor(ContextCompat.getColor(mContext, R.color.order_price_color))
                .setSize(18, true)
                .create();
        SpannableStringBuilder stringBuilder2 = SpannableStringUtils.getBuilder(orderPricePartOne)
                .setForegroundColor(ContextCompat.getColor(mContext, R.color.light_grey_dark))
                .append(stringBuilder).create();
        helper.setText(R.id.order_price, stringBuilder2);
        helper.setText(R.id.order_count, "共" + orderCount + "件商品");

    }

}
