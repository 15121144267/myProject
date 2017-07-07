package com.dispatching.feima.view.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.dispatching.feima.R;
import com.dispatching.feima.entity.ShopDetailResponse;
import com.dispatching.feima.utils.ValueUtil;
import com.example.mylibrary.adapter.BaseQuickAdapter;
import com.example.mylibrary.adapter.BaseViewHolder;

import java.util.List;


public class ShopDetailAdapter extends BaseQuickAdapter<ShopDetailResponse.ProductsBean, BaseViewHolder> {
    private final Context mContext;

    public ShopDetailAdapter(List<ShopDetailResponse.ProductsBean> mList, Context context) {
        super(R.layout.adapter_shop_detail, mList);
        mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, ShopDetailResponse.ProductsBean item) {
        if (item == null) return;
        helper.setText(R.id.adapter_goods_name, item.name);
        helper.setText(R.id.adapter_goods_price, "￥"+ValueUtil.formatAmount(item.finalPrice));
        helper.setText(R.id.adapter_goods_count, item.saleCount+"件");
        Glide.with(mContext).load(item.picture).error(R.mipmap.freemud_logo).into((ImageView) helper.getView(R.id.adapter_person_icon));
    }

}
