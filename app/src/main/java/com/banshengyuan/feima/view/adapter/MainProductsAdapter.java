package com.banshengyuan.feima.view.adapter;

import android.content.Context;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.entity.MainProducts;
import com.example.mylibrary.adapter.BaseQuickAdapter;
import com.example.mylibrary.adapter.BaseViewHolder;

import java.util.List;


public class MainProductsAdapter extends BaseQuickAdapter<MainProducts, BaseViewHolder> {
    private final Context mContext;
    public MainProductsAdapter(List<MainProducts> notices, Context context) {
        super( R.layout.adapter_product, notices);
        mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, MainProducts item) {
            helper.setText(R.id.product_name,item.productName);
            helper.setImageDrawable(R.id.product_drawable,item.productDrawable);
    }

}
