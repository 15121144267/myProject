package com.banshengyuan.feima.view.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.entity.AddressResponse;
import com.example.mylibrary.adapter.BaseQuickAdapter;
import com.example.mylibrary.adapter.BaseViewHolder;

import java.util.List;


public class ProductAdapter extends BaseQuickAdapter<AddressResponse.DataBean, BaseViewHolder> {
    private final Context mContext;

    public ProductAdapter(List<AddressResponse.DataBean> mList, Context context) {
        super(R.layout.adapter_fair, mList);
        mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, AddressResponse.DataBean item) {
        if (item == null) return;
        helper.addOnClickListener(R.id.adapter_fair_more);
        RecyclerView recyclerView = helper.getView(R.id.adapter_fair_content);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.HORIZONTAL,false));
    }

}
