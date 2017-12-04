package com.banshengyuan.feima.view.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.entity.AllProductSortResponse;
import com.example.mylibrary.adapter.BaseQuickAdapter;
import com.example.mylibrary.adapter.BaseViewHolder;

import java.util.List;


public class ProductListSortAdapter extends BaseQuickAdapter<AllProductSortResponse.ListBean, BaseViewHolder> {
    private final Context mContext;

    public ProductListSortAdapter(List<AllProductSortResponse.ListBean> mList, Context context) {
        super(R.layout.adapter_product_list_sort, mList);
        mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, AllProductSortResponse.ListBean item) {
        if (item == null) return;
        helper.setText(R.id.adapter_product_sort_name,item.name);
        helper.setTextColor(R.id.adapter_product_sort_name,item.isRed? ContextCompat.getColor(mContext,R.color.red):ContextCompat.getColor(mContext,R.color.tab_text_normal));
    }

}
