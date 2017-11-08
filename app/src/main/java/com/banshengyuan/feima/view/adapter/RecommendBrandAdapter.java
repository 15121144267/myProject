package com.banshengyuan.feima.view.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.entity.ProductResponse;
import com.banshengyuan.feima.view.activity.FairDetailActivity;
import com.example.mylibrary.adapter.BaseQuickAdapter;
import com.example.mylibrary.adapter.BaseViewHolder;

import java.util.List;


public class RecommendBrandAdapter extends BaseQuickAdapter<ProductResponse, BaseViewHolder> {
    private final Context mContext;
    private boolean mChangeFlag;

    public RecommendBrandAdapter(List<ProductResponse> mList, Context context, boolean changeFlag) {
        super(R.layout.adapter_fair, mList);
        mContext = context;
        mChangeFlag = changeFlag;
    }

    @Override
    protected void convert(BaseViewHolder helper, ProductResponse item) {
        if (item == null) return;
        boolean flag;
        flag = helper.getAdapterPosition() != 0;
        helper.addOnClickListener(R.id.adapter_fair_more);
        RecyclerView recyclerView = helper.getView(R.id.adapter_fair_content);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        ProductItemAdapter itemAdapter = new ProductItemAdapter(item.mList, mContext, mChangeFlag, flag);
        recyclerView.setAdapter(itemAdapter);

        itemAdapter.setOnItemClickListener((adapter, view, position) ->
            mContext.startActivity(FairDetailActivity.getIntent(mContext))
        );
    }

}
