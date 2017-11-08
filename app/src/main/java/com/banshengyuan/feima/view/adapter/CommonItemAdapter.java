package com.banshengyuan.feima.view.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.entity.ProductResponse;
import com.banshengyuan.feima.utils.ToastUtils;
import com.example.mylibrary.adapter.BaseQuickAdapter;
import com.example.mylibrary.adapter.BaseViewHolder;

import java.util.List;


public class CommonItemAdapter extends BaseQuickAdapter<ProductResponse, BaseViewHolder> {
    private final Context mContext;

    public CommonItemAdapter(List<ProductResponse> mList, Context context) {
        super(R.layout.adapter_fair, mList);
        mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, ProductResponse item) {
        if (item == null) return;
        helper.addOnClickListener(R.id.adapter_fair_more);
        RecyclerView recyclerView = helper.getView(R.id.adapter_fair_content);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        BlockDetailItemHotAdapter itemAdapter = new BlockDetailItemHotAdapter(item.mList, mContext);
        recyclerView.setAdapter(itemAdapter);
        itemAdapter.setOnItemClickListener((adapter, view, position) -> ToastUtils.showLongToast("热闹详情"));
    }

}
