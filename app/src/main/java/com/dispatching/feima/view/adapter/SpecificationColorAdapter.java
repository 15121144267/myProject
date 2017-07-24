package com.dispatching.feima.view.adapter;

import android.content.Context;

import com.dispatching.feima.R;
import com.example.mylibrary.adapter.BaseQuickAdapter;
import com.example.mylibrary.adapter.BaseViewHolder;

import java.util.List;


public class SpecificationColorAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    private final Context mContext;
    private Integer mPosition =Integer.MAX_VALUE;

    public void setPosition(Integer position) {
        mPosition = position;
        notifyDataSetChanged();
    }

    public SpecificationColorAdapter(List<String> notices, Context context) {
        super(R.layout.adapter_specifiaction_color, notices);
        mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        if( helper.getAdapterPosition()==mPosition){
            helper.setBackgroundRes(R.id.specification_string,R.mipmap.specification_back);
        }else {
            helper.setBackgroundRes(R.id.specification_string,R.drawable.selector_enable);
        }

        helper.setText(R.id.specification_string, item);
    }

}
