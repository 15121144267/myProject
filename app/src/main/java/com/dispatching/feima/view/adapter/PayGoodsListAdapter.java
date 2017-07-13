package com.dispatching.feima.view.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.dispatching.feima.R;
import com.dispatching.feima.entity.MyPayOrderRequest;
import com.dispatching.feima.help.GlideHelper.ImageLoaderHelper;
import com.example.mylibrary.adapter.BaseQuickAdapter;
import com.example.mylibrary.adapter.BaseViewHolder;

import java.util.List;


public class PayGoodsListAdapter extends BaseQuickAdapter<MyPayOrderRequest, BaseViewHolder> {
    private final Context mContext;
    private final ImageLoaderHelper mImageLoaderHelper;

    public PayGoodsListAdapter(List<MyPayOrderRequest> notices, Context context, ImageLoaderHelper imageLoaderHelper) {
        super(R.layout.adapter_pay_goods_list, notices);
        mContext = context;
        mImageLoaderHelper =imageLoaderHelper;
    }

    @Override
    protected void convert(BaseViewHolder helper, MyPayOrderRequest item) {
        if (item == null) return;
        ImageView iconView = helper.getView(R.id.adapter_pay_goods_icon);
        mImageLoaderHelper.displayRoundedCornerImage(mContext,R.mipmap.neo,iconView,6);
        helper.setText(R.id.adapter_pay_goods_name, item.productName);
        helper.setText(R.id.adapter_pay_goods_des, item.productDes1);
        helper.setText(R.id.adapter_pay_goods_price, "￥" + item.productPrice);
        helper.setText(R.id.adapter_pay_goods_spe, item.productDes2);
        helper.setText(R.id.adapter_pay_goods_count, "x" + item.productcount);

    }

}
