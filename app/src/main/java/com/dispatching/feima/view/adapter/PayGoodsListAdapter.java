package com.dispatching.feima.view.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.dispatching.feima.R;
import com.dispatching.feima.entity.SpecificationResponse;
import com.dispatching.feima.help.GlideHelper.ImageLoaderHelper;
import com.dispatching.feima.utils.ValueUtil;
import com.example.mylibrary.adapter.BaseQuickAdapter;
import com.example.mylibrary.adapter.BaseViewHolder;

import java.util.List;


public class PayGoodsListAdapter extends BaseQuickAdapter<SpecificationResponse.ProductsBean, BaseViewHolder> {
    private final Context mContext;
    private final ImageLoaderHelper mImageLoaderHelper;

    public PayGoodsListAdapter(List<SpecificationResponse.ProductsBean> notices, Context context, ImageLoaderHelper imageLoaderHelper) {
        super(R.layout.adapter_pay_goods_list, notices);
        mContext = context;
        mImageLoaderHelper = imageLoaderHelper;
    }

    @Override
    protected void convert(BaseViewHolder helper, SpecificationResponse.ProductsBean item) {
        if (item == null) return;
        ImageView iconView = helper.getView(R.id.adapter_pay_goods_icon);
        mImageLoaderHelper.displayRoundedCornerImage(mContext, item.picture, iconView, 6);
        helper.setText(R.id.adapter_pay_goods_name, item.name);
        helper.setText(R.id.adapter_pay_goods_des, "暂无描述");
        helper.setText(R.id.adapter_pay_goods_price, ValueUtil.formatAmount(item.finalPrice));
        helper.setText(R.id.adapter_pay_goods_spe, "规格:" + item.specification);
        helper.setText(R.id.adapter_pay_goods_count, "x" + item.saleCount);

    }

}
