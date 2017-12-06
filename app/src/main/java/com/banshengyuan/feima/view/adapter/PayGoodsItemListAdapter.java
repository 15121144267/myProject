package com.banshengyuan.feima.view.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.dagger.module.ShoppingCardListResponse;
import com.banshengyuan.feima.help.GlideHelper.ImageLoaderHelper;
import com.banshengyuan.feima.utils.ValueUtil;
import com.example.mylibrary.adapter.BaseQuickAdapter;
import com.example.mylibrary.adapter.BaseViewHolder;

import java.util.List;


public class PayGoodsItemListAdapter extends BaseQuickAdapter<ShoppingCardListResponse.ListBeanX.ListBean, BaseViewHolder> {
    private final Context mContext;
    private final ImageLoaderHelper mImageLoaderHelper;

    public PayGoodsItemListAdapter(List<ShoppingCardListResponse.ListBeanX.ListBean> notices, Context context, ImageLoaderHelper imageLoaderHelper) {
        super(R.layout.adapter_pay_goods_list_item, notices);
        mContext = context;
        mImageLoaderHelper = imageLoaderHelper;
    }

    @Override
    protected void convert(BaseViewHolder helper, ShoppingCardListResponse.ListBeanX.ListBean item) {
        if (item == null) return;
        ImageView iconView = helper.getView(R.id.adapter_shopping_card_item_icon);
        mImageLoaderHelper.displayImage(mContext, item.goods_img, iconView);
        helper.setText(R.id.adapter_shopping_card_item_name, item.goods_name);
        helper.setText(R.id.adapter_shopping_card_item_price, "￥"+ValueUtil.formatAmount2(item.goods_price));
        helper.setText(R.id.adapter_shopping_card_item_count, "x" + item.number);

    }

}
