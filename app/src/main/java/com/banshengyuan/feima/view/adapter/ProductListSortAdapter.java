package com.banshengyuan.feima.view.adapter;

import android.content.Context;

import com.banshengyuan.feima.R;
import com.example.mylibrary.adapter.BaseQuickAdapter;
import com.example.mylibrary.adapter.BaseViewHolder;

import java.util.List;


public class ProductListSortAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    private final Context mContext;

    public ProductListSortAdapter(List<String> mList, Context context) {
        super(R.layout.adapter_product_list_sort, mList);
        mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        if (item == null) return;
        helper.setText(R.id.adapter_product_sort_name,item);
    }

}
