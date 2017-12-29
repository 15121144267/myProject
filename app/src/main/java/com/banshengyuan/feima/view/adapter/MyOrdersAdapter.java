package com.banshengyuan.feima.view.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.entity.MyOrdersResponse;
import com.banshengyuan.feima.help.GlideHelper.ImageLoaderHelper;
import com.banshengyuan.feima.utils.SpannableStringUtils;
import com.banshengyuan.feima.utils.ValueUtil;
import com.banshengyuan.feima.view.activity.OrderDetailActivity;
import com.example.mylibrary.adapter.BaseMultiItemQuickAdapter;
import com.example.mylibrary.adapter.BaseViewHolder;

import java.util.List;


public class MyOrdersAdapter extends BaseMultiItemQuickAdapter<MyOrdersResponse.ListBean, BaseViewHolder> {
    private final Context mContext;
    private ImageLoaderHelper mImageLoaderHelper;

    public MyOrdersAdapter(List<MyOrdersResponse.ListBean> data, Context context, ImageLoaderHelper imageLoaderHelper) {
        super(data);
        mContext = context;
        mImageLoaderHelper = imageLoaderHelper;
        addItemType(MyOrdersResponse.ListBean.LAYOUT_ONE, R.layout.adapter_my_orders);
        addItemType(MyOrdersResponse.ListBean.LAYOUT_TWO, R.layout.adapter_my_orders_unline);
    }

    @Override
    protected void convert(BaseViewHolder helper, MyOrdersResponse.ListBean item) {
        switch (helper.getItemViewType()) {
            case MyOrdersResponse.ListBean.LAYOUT_ONE:
                showOneLayout(helper, item);
                break;
            case MyOrdersResponse.ListBean.LAYOUT_TWO:
                showTwoLayout(helper, item);
                break;
        }
    }

    private void showOneLayout(BaseViewHolder helper, MyOrdersResponse.ListBean item) {
        if (item == null) return;
        helper.addOnClickListener(R.id.order_left_btn).addOnClickListener(R.id.order_right_btn).addOnClickListener(R.id.mime_order_lv);

        List<MyOrdersResponse.ListBean.ProductBean> products = item.getProduct();
        RecyclerView recyclerView = helper.getView(R.id.adapter_product_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        OrdersItemAdapter itemAdapter = new OrdersItemAdapter(products, mContext, mImageLoaderHelper);
        recyclerView.setAdapter(itemAdapter);

        itemAdapter.setOnItemClickListener((adapter, view, position) -> mContext.startActivity(OrderDetailActivity.getOrderDetailIntent(mContext, item.getOrder_sn())));

        helper.setText(R.id.shop_name, "  " + item.getStore_name());
        //pay_status :1 待付款 2已付款
        //deliver_status : 1 待发货 2已发货
        helper.setText(R.id.order_state, item.getPay_status_name());
        helper.setVisible(R.id.order_left_btn, true);
        /**
         *
         * 订单状态
         * order_type  1线上订单  2自提订单  3线下收款订单
         * 1.等待买家付款（待付款）
         2.等待买家收货（已发货或待收货、待自提）
         3.等待卖家发货（待发货或已付款）自提订单无此状态
         4.交易成功（待评价或已完成、线下收款）线下收款订单没有商品，故无评价
         5.交易关闭（已取消）
         * pay_status        1 待付款 2已付款
         * 	deliver_status   1 待发货 2已发货
         */
        if (item.getOrder_type() == 1) {
            helper.setVisible(R.id.order_left_btn, true);
            helper.setVisible(R.id.order_right_btn, true);
            if (item.getPay_status() == 1) {
                helper.setText(R.id.order_left_btn, "取消订单");
                helper.setText(R.id.order_right_btn, "立即付款");
            } else if (item.getPay_status() == 2) {
                helper.setVisible(R.id.order_left_btn, false);
                helper.setText(R.id.order_right_btn, "确认收货");
            } else if (item.getPay_status() == 3) {
                helper.setVisible(R.id.order_left_btn, false);
                helper.setText(R.id.order_right_btn, "提醒发货");
            } else if (item.getPay_status() == 4) {
                helper.setText(R.id.order_left_btn, "删除订单");
                helper.setText(R.id.order_right_btn, "去评价");
            } else if (item.getPay_status() == 5) {
                helper.setVisible(R.id.order_left_btn, false);
                helper.setText(R.id.order_right_btn, "删除订单");
            }
        } else if (item.getOrder_type() == 2) {//2自提订单
            helper.setVisible(R.id.order_left_btn, true);
            helper.setVisible(R.id.order_right_btn, true);
            if (item.getPay_status() == 1) {
                helper.setText(R.id.order_left_btn, "取消订单");
                helper.setText(R.id.order_right_btn, "立即付款");
            } else if (item.getPay_status() == 2) {
                helper.setVisible(R.id.order_left_btn, false);
                helper.setText(R.id.order_right_btn, "确认收货");
            } else if (item.getPay_status() == 3) {
                helper.setVisible(R.id.order_left_btn, false);
                helper.setText(R.id.order_right_btn, "确认收货");
            } else if (item.getPay_status() == 4) {
                helper.setText(R.id.order_left_btn, "删除订单");
                helper.setText(R.id.order_right_btn, "去评价");
            } else if (item.getPay_status() == 5) {
                helper.setVisible(R.id.order_left_btn, false);
                helper.setText(R.id.order_right_btn, "删除订单");
            }
        } else if (item.getOrder_type() == 3) {//3线下收款订单
            helper.setVisible(R.id.order_left_btn, false);
            helper.setText(R.id.order_right_btn, "删除订单");
        }

        Integer orderCount = 0;
        String orderPricePartOne = "合计：";
        if (products != null) {
            for (MyOrdersResponse.ListBean.ProductBean product : products) {
                orderCount += product.getNumber();
            }
        }

        String orderPricePartTwo = "￥" + ValueUtil.formatAmount2(item.getTotal_fee());
        SpannableStringBuilder stringBuilder = SpannableStringUtils.getBuilder(orderPricePartTwo)
                .setForegroundColor(ContextCompat.getColor(mContext, R.color.order_price_color))
                .setSize(18, true)
                .create();
        SpannableStringBuilder stringBuilder2 = SpannableStringUtils.getBuilder(orderPricePartOne)
                .setForegroundColor(ContextCompat.getColor(mContext, R.color.light_grey_dark))
                .append(stringBuilder).create();

        helper.setText(R.id.order_price, stringBuilder2 + "(含运费￥" + ValueUtil.formatAmount2(item.getFreight()) + ")");
        helper.setText(R.id.order_count, "共" + orderCount + "件商品");
    }

    private void showTwoLayout(BaseViewHolder helper, MyOrdersResponse.ListBean item) {
        if (item == null) return;
        helper.addOnClickListener(R.id.order_left_btn).addOnClickListener(R.id.order_right_btn).addOnClickListener(R.id.mime_order_lv);

        helper.setText(R.id.shop_name, "  " + item.getStore_name());
        //pay_status :1 待付款 2已付款
        //deliver_status : 1 待发货 2已发货
        helper.setText(R.id.order_state, item.getPay_status_name());


        helper.setText(R.id.order_price, "实付金额￥" + ValueUtil.formatAmount2(item.getTotal_fee()));
    }
}
