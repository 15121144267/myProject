package com.dispatching.feima.view.adapter;

import android.content.Context;

import com.dispatching.feima.R;
import com.dispatching.feima.entity.ShoppingCardResponse;
import com.dispatching.feima.help.GlideHelper.ImageLoaderHelper;
import com.example.mylibrary.adapter.BaseQuickAdapter;
import com.example.mylibrary.adapter.BaseViewHolder;

import java.util.List;


public class ShoppingCardItemAdapter extends BaseQuickAdapter<ShoppingCardResponse.ShoppingCardListBean.ShoppingCardBean, BaseViewHolder> {
    private final Context mContext;
    private final ImageLoaderHelper mImageLoaderHelper;

    public ShoppingCardItemAdapter(List<ShoppingCardResponse.ShoppingCardListBean.ShoppingCardBean> notices, Context context, ImageLoaderHelper imageLoaderHelper) {
        super(R.layout.item_shopping_card_detal, notices);
        mContext = context;
        mImageLoaderHelper =imageLoaderHelper;
    }

    @Override
    protected void convert(BaseViewHolder helper, ShoppingCardResponse.ShoppingCardListBean.ShoppingCardBean item) {
        if (item == null) return;
        helper.setText(R.id.item_shopping_card_des,item.describe);
        helper.setText(R.id.item_shopping_card_specification,item.specification);
        helper.setText(R.id.item_shopping_card_price,"￥"+item.price);
    }

}
