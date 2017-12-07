package com.banshengyuan.feima.view.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.widget.Toast;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.entity.MyOrdersResponse;
import com.banshengyuan.feima.help.GlideHelper.ImageLoaderHelper;
import com.banshengyuan.feima.utils.SpannableStringUtils;
import com.banshengyuan.feima.utils.ValueUtil;
import com.banshengyuan.feima.view.activity.OrderDetailActivity;
import com.example.mylibrary.adapter.BaseQuickAdapter;
import com.example.mylibrary.adapter.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;


public class MyOrdersAdapter extends BaseQuickAdapter<MyOrdersResponse.ListBean, BaseViewHolder> {
    private final Context mContext;
    private ImageLoaderHelper mImageLoaderHelper;
    private List<MyOrdersResponse.ListBean.OrderItemBean> mOrderItemBeen = new ArrayList<>();

    public MyOrdersAdapter(List<MyOrdersResponse.ListBean> listBean, Context context, ImageLoaderHelper imageLoaderHelper) {
        super(R.layout.adapter_my_orders, listBean);
        mContext = context;
        mImageLoaderHelper = imageLoaderHelper;
    }


    @Override
    protected void convert(BaseViewHolder helper, MyOrdersResponse.ListBean item) {
        if (item == null) return;
        helper.addOnClickListener(R.id.order_left_btn).addOnClickListener(R.id.order_right_btn).addOnClickListener(R.id.mime_order_lv);

        MyOrdersResponse.ListBean.OrderItemBean bean = item.getOrder_item().get(0);
        List<MyOrdersResponse.ListBean.OrderItemBean.ProductBean> products = bean.getProduct();
        RecyclerView recyclerView = helper.getView(R.id.adapter_product_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        OrdersItemAdapter itemAdapter = new OrdersItemAdapter(products, mContext, mImageLoaderHelper);
        recyclerView.setAdapter(itemAdapter);

        itemAdapter.setOnItemClickListener((adapter, view, position) -> {
            mContext.startActivity(OrderDetailActivity.getOrderDetailIntent(mContext, item));
        });

        helper.setText(R.id.shop_name, "  " + bean.getStore_name());
        //pay_status :1 待付款 2已付款
        //deliver_status : 1 待发货 2已发货


        switch (item.getPay_status()) {
            case 1:
                helper.setText(R.id.adapter_order_status, "待支付");
                break;
            case 2:
                helper.setText(R.id.adapter_order_status, "已付款");
                break;
            default:
                helper.setText(R.id.adapter_order_status, "系统处理中");
        }
        Integer orderCount = 0;
        Double orderPrice = 0.00;
        String orderPricePartOne = "合计：";
        if (products != null) {
            for (MyOrdersResponse.ListBean.OrderItemBean.ProductBean product : products) {
                orderPrice += product.getPrice() * product.getNumber();
                orderCount += product.getNumber();
            }
        }

        String orderPricePartTwo = "￥" + ValueUtil.formatAmount2(orderPrice);
        SpannableStringBuilder stringBuilder = SpannableStringUtils.getBuilder(orderPricePartTwo)
                .setForegroundColor(ContextCompat.getColor(mContext, R.color.order_price_color))
                .setSize(18, true)
                .create();
        SpannableStringBuilder stringBuilder2 = SpannableStringUtils.getBuilder(orderPricePartOne)
                .setForegroundColor(ContextCompat.getColor(mContext, R.color.light_grey_dark))
                .append(stringBuilder).create();
        helper.setText(R.id.order_price, stringBuilder2 + ("(含运费￥0.00)"));
        helper.setText(R.id.order_count, "共" + orderCount + "件商品");

    }

}
