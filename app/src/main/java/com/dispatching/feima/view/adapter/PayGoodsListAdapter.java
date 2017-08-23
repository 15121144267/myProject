package com.dispatching.feima.view.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import com.dispatching.feima.R;
import com.dispatching.feima.entity.OrderConfirmedRequest;
import com.dispatching.feima.help.GlideHelper.ImageLoaderHelper;
import com.dispatching.feima.utils.ValueUtil;
import com.example.mylibrary.adapter.BaseQuickAdapter;
import com.example.mylibrary.adapter.BaseViewHolder;

import java.util.List;


public class PayGoodsListAdapter extends BaseQuickAdapter<OrderConfirmedRequest, BaseViewHolder> {
    private final Context mContext;
    private final ImageLoaderHelper mImageLoaderHelper;

    public PayGoodsListAdapter(List<OrderConfirmedRequest> notices, Context context, ImageLoaderHelper imageLoaderHelper) {
        super(R.layout.adapter_pay_goods_list, notices);
        mContext = context;
        mImageLoaderHelper = imageLoaderHelper;
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderConfirmedRequest item) {
        if (item == null) return;
        RecyclerView recyclerView = helper.getView(R.id.adapter_shopping_card_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        PayGoodsItemListAdapter adapter = new PayGoodsItemListAdapter(item.products, mContext, mImageLoaderHelper);
        recyclerView.setAdapter(adapter);
        Integer productPrice = 0;
        for (OrderConfirmedRequest.ProductsBean product : item.products) {
            productPrice += product.price * Integer.valueOf(product.number);
        }
        helper.setText(R.id.adapter_shopping_card_product_price, "￥" + ValueUtil.formatAmount2(productPrice));
        Integer dispatchingPrice = 0;
        for (OrderConfirmedRequest.AccountsBean account : item.accounts) {
            dispatchingPrice += Integer.valueOf(account.price);
        }
        helper.setText(R.id.adapter_shopping_card_dispatching_price, "￥" + ValueUtil.formatAmount2(dispatchingPrice));
        helper.setText(R.id.adapter_shopping_card_product_count, "共计" + item.products.size() + "件商品");
        helper.setText(R.id.adapter_shopping_card_shop_name, TextUtils.isEmpty(item.shopName)?"  未知店铺":"  "+item.shopName);
        helper.setText(R.id.adapter_shopping_card_price_all, "小计:" + ValueUtil.formatAmount2(dispatchingPrice + productPrice));

    }

}
