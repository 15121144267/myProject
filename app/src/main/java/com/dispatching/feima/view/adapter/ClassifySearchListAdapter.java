package com.dispatching.feima.view.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.dispatching.feima.R;
import com.dispatching.feima.entity.ClassifySearchListResponse;
import com.dispatching.feima.utils.ValueUtil;
import com.example.mylibrary.adapter.BaseQuickAdapter;
import com.example.mylibrary.adapter.BaseViewHolder;

import java.util.List;


public class ClassifySearchListAdapter extends BaseQuickAdapter<ClassifySearchListResponse.DataBean, BaseViewHolder> {
    private final Context mContext;

    public ClassifySearchListAdapter(List<ClassifySearchListResponse.DataBean> mList, Context context) {
        super(R.layout.adapter_shop_detail, mList);
        mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, ClassifySearchListResponse.DataBean item) {
        if (item == null) return;
        helper.setText(R.id.adapter_goods_name, item.name);
        helper.setText(R.id.adapter_goods_price, "￥"+ValueUtil.formatAmount( item.finalPrice));
        helper.setText(R.id.adapter_goods_count, "销量"+ item.saleCount);
        Glide.with(mContext).load( item.picture).error(R.mipmap.freemud_logo).into((ImageView) helper.getView(R.id.adapter_person_icon));
    }

}
