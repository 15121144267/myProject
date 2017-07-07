package com.dispatching.feima.view.adapter;

import android.content.Context;

import com.dispatching.feima.R;
import com.example.mylibrary.adapter.BaseQuickAdapter;
import com.example.mylibrary.adapter.BaseViewHolder;

import java.util.List;


public class SpecificationAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    private final Context mContext;

    public SpecificationAdapter(List<String> notices, Context context) {
        super(R.layout.adapter_specifiaction, notices);
        mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.specification_string, item);

    }

}
