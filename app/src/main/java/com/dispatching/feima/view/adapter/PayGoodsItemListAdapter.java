package com.dispatching.feima.view.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.dispatching.feima.R;
import com.dispatching.feima.entity.OrderConfirmedRequest;
import com.dispatching.feima.help.GlideHelper.ImageLoaderHelper;
import com.dispatching.feima.utils.ValueUtil;
import com.example.mylibrary.adapter.BaseQuickAdapter;
import com.example.mylibrary.adapter.BaseViewHolder;

import java.util.List;


public class PayGoodsItemListAdapter extends BaseQuickAdapter<OrderConfirmedRequest.ProductsBean, BaseViewHolder> {
    private final Context mContext;
    private final ImageLoaderHelper mImageLoaderHelper;

    public PayGoodsItemListAdapter(List<OrderConfirmedRequest.ProductsBean> notices, Context context, ImageLoaderHelper imageLoaderHelper) {
        super(R.layout.adapter_pay_goods_list_item, notices);
        mContext = context;
        mImageLoaderHelper = imageLoaderHelper;
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderConfirmedRequest.ProductsBean item) {
        if (item == null) return;
        ImageView iconView = helper.getView(R.id.adapter_shopping_card_item_icon);
        mImageLoaderHelper.displayRoundedCornerImage(mContext, item.picture, iconView, 6);
        helper.setText(R.id.adapter_shopping_card_item_name, item.productName);
        helper.setText(R.id.adapter_shopping_card_item_des, "暂无描述");
        helper.setText(R.id.adapter_shopping_card_item_price, ValueUtil.formatAmount(item.price));
        helper.setText(R.id.adapter_shopping_card_item_spe, "规格:" + item.specification);
        helper.setText(R.id.adapter_shopping_card_item_count, "x" + item.number);

    }

}
