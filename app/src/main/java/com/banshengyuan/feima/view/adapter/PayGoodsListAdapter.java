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
        RecyclerView recyclerView = helper.getView(R.id.adapter_shopping_card_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        PayGoodsItemListAdapter adapter = new PayGoodsItemListAdapter(item.list, mContext, mImageLoaderHelper);
        recyclerView.setAdapter(adapter);
        Integer productPrice = 0;
        for (ShoppingCardListResponse.ListBeanX.ListBean product : item.list) {
            productPrice += product.goods_price * product.number;
        }
        helper.setText(R.id.adapter_shopping_card_price_all, "￥" + ValueUtil.formatAmount2(productPrice));
       /* Integer dispatchingPrice = 0;
        for (OrderConfirmedRequest.AccountsBean account : item.accounts) {
            dispatchingPrice += Integer.valueOf(account.price);
        }*/
//        helper.setText(R.id.adapter_shopping_card_dispatching_price, "￥" + ValueUtil.formatAmount2(dispatchingPrice));
        helper.setText(R.id.adapter_shopping_card_product_count, "共计" + item.list.size() + "件商品");
        helper.setText(R.id.adapter_shopping_card_shop_name, TextUtils.isEmpty(item.stoer_name) ? "  未知店铺" : "  " + item.stoer_name);
//        helper.setText(R.id.adapter_shopping_card_price_all, "小计:" + ValueUtil.formatAmount2(dispatchingPrice + productPrice));

    }

}
