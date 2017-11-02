package com.banshengyuan.feima.view.adapter;

import android.content.Context;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.entity.MainProducts;
import com.example.mylibrary.adapter.BaseQuickAdapter;
import com.example.mylibrary.adapter.BaseViewHolder;

import java.util.List;


public class MainProductsSecondAdapter extends BaseQuickAdapter<MainProducts, BaseViewHolder> {
    private final Context mContext;
    public MainProductsSecondAdapter(List<MainProducts> notices, Context context) {
        super( R.layout.adapter_product_sencond, notices);
        mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, MainProducts item) {
            helper.setText(R.id.product_name,item.productName);
            helper.setImageDrawable(R.id.product_drawable,item.productDrawable);
    }

}
