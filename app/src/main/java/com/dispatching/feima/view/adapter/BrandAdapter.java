package com.dispatching.feima.view.adapter;

import android.content.Context;

import com.dispatching.feima.R;
import com.dispatching.feima.entity.MainProducts;
import com.example.mylibrary.adapter.BaseQuickAdapter;
import com.example.mylibrary.adapter.BaseViewHolder;

import java.util.List;


public class BrandAdapter extends BaseQuickAdapter<MainProducts, BaseViewHolder> {
    private final Context mContext;


    public BrandAdapter(List<MainProducts> mList, Context context) {
        super(R.layout.adapter_brand, mList);
        mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, MainProducts item) {
        helper.setText(R.id.adapter_brand_name,item.productName);
        helper.setImageDrawable(R.id.adapter_brand_image,item.productDrawable);
    }

}
