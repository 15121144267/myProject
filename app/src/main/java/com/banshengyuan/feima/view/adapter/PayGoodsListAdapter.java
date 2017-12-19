package com.banshengyuan.feima.view.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.dagger.module.ShoppingCardListResponse;
import com.banshengyuan.feima.help.GlideHelper.ImageLoaderHelper;
import com.banshengyuan.feima.utils.ValueUtil;
import com.example.mylibrary.adapter.BaseQuickAdapter;
import com.example.mylibrary.adapter.BaseViewHolder;

import java.util.List;


public class PayGoodsListAdapter extends BaseQuickAdapter<ShoppingCardListResponse.ListBeanX, BaseViewHolder> {
    private final Context mContext;
    private final ImageLoaderHelper mImageLoaderHelper;

    public PayGoodsListAdapter(List<ShoppingCardListResponse.ListBeanX> notices, Context context, ImageLoaderHelper imageLoaderHelper) {
        super(R.layout.adapter_pay_goods_list, notices);
        mContext = context;
        mImageLoaderHelper = imageLoaderHelper;
    }

    @Override
    protected void convert(BaseViewHolder helper, ShoppingCardListResponse.ListBeanX item) {
        helper.addOnClickListener(R.id.adapter_pay_coupon);
        Integer productPrice = 0;
        for (ShoppingCardListResponse.ListBeanX.ListBean product : item.list) {
            productPrice += product.goods_price * product.number;
        }
        if (item.freightWay == 0) {
            if (item.shop_freight_config != null) {
                if (item.shop_freight_config.freight == 1) {
                    if (productPrice >= item.shop_freight_config.free_shipping_price) {
                        helper.setText(R.id.adapter_pay_dispatching_way, "快递 免邮");
                        helper.setText(R.id.adapter_shopping_card_price_all, ValueUtil.formatAmount2(productPrice));
                    } else {
                        helper.setText(R.id.adapter_pay_dispatching_way, "快递" + ValueUtil.formatAmount2(item.shop_freight_config.shipping_price) + "");
                        helper.setText(R.id.adapter_shopping_card_price_all, ValueUtil.formatAmount2(productPrice + item.shop_freight_config.shipping_price) + "");
                    }
                } else {
                    helper.setText(R.id.adapter_pay_dispatching_way, "快递 免邮");
                    helper.setText(R.id.adapter_shopping_card_price_all, ValueUtil.formatAmount2(productPrice));
                }
            }
        } else {
            helper.setText(R.id.adapter_pay_dispatching_way, "门店自提");
            helper.setText(R.id.adapter_shopping_card_price_all, ValueUtil.formatAmount2(productPrice));
        }
        if (item.user_ticket != null && item.user_ticket.size() > 0) {
            helper.setText(R.id.adapter_pay_coupon, "可用优惠券" + item.user_ticket.size() + "");
            helper.setEnable(R.id.adapter_pay_coupon, true);
        } else {
            helper.setText(R.id.adapter_pay_coupon, "无可用");
            helper.setEnable(R.id.adapter_pay_coupon, false);
        }

        RecyclerView recyclerView = helper.getView(R.id.adapter_shopping_card_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        PayGoodsItemListAdapter adapter = new PayGoodsItemListAdapter(item.list, mContext, mImageLoaderHelper);
        recyclerView.setAdapter(adapter);


        helper.setText(R.id.adapter_shopping_card_product_count, "共计" + item.list.size() + "件商品");
        helper.setText(R.id.adapter_shopping_card_shop_name, TextUtils.isEmpty(item.stoer_name) ? "未知店铺" : item.stoer_name);
    }

}
