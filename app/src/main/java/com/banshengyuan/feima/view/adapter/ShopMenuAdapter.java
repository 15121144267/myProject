package com.banshengyuan.feima.view.adapter;

import android.content.Context;

import com.banshengyuan.feima.R;
import com.example.mylibrary.adapter.BaseQuickAdapter;
import com.example.mylibrary.adapter.BaseViewHolder;

import java.util.List;


public class ShopMenuAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    private final Context mContext;

    public ShopMenuAdapter(List<String> mList, Context context) {
        super(R.layout.adapter_shop_menu, mList);
        mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.menu_text_name, item);
    }

}
