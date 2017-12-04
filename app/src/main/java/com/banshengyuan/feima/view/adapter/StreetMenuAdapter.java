package com.banshengyuan.feima.view.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.entity.FairUnderLineResponse;
import com.example.mylibrary.adapter.BaseQuickAdapter;
import com.example.mylibrary.adapter.BaseViewHolder;

import java.util.List;


public class StreetMenuAdapter extends BaseQuickAdapter<FairUnderLineResponse.ListBean, BaseViewHolder> {
    private final Context mContext;

    public StreetMenuAdapter(List<FairUnderLineResponse.ListBean> mList, Context context) {
        super(R.layout.adapter_shop_menu, mList);
        mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, FairUnderLineResponse.ListBean item) {
        helper.setText(R.id.menu_text_name, item.name);
        if(item.select_position){
            helper.setTextColor(R.id.menu_text_name, ContextCompat.getColor(mContext,R.color.light_red));
        }else {
            helper.setTextColor(R.id.menu_text_name, ContextCompat.getColor(mContext,R.color.tab_text_normal));
        }

    }

}
