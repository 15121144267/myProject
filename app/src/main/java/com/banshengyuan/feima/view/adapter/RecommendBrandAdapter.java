package com.banshengyuan.feima.view.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.entity.RecommendBrandResponse;
import com.banshengyuan.feima.help.GlideHelper.ImageLoaderHelper;
import com.banshengyuan.feima.view.activity.FairDetailActivity;
import com.example.mylibrary.adapter.BaseQuickAdapter;
import com.example.mylibrary.adapter.BaseViewHolder;

import java.util.List;


public class RecommendBrandAdapter extends BaseQuickAdapter<RecommendBrandResponse, BaseViewHolder> {
    private final Context mContext;
    private ImageLoaderHelper mImageLoaderHelper;

    public RecommendBrandAdapter(List<RecommendBrandResponse> mList, Context context, ImageLoaderHelper imageLoaderHelper) {
        super(R.layout.adapter_fair, mList);
        mContext = context;
        mImageLoaderHelper = imageLoaderHelper;
    }

    @Override
    protected void convert(BaseViewHolder helper, RecommendBrandResponse item) {
        if (item == null) return;
        helper.addOnClickListener(R.id.adapter_fair_more);
        RecyclerView recyclerView = helper.getView(R.id.adapter_fair_content);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        BrandItemAdapter itemAdapter = new BrandItemAdapter(item.list, mContext, mImageLoaderHelper);
        recyclerView.setAdapter(itemAdapter);

        itemAdapter.setOnItemClickListener((adapter, view, position) -> {
                    RecommendBrandResponse.ListBean bean = (RecommendBrandResponse.ListBean) adapter.getItem(position);
                    if (bean != null) {
                        mContext.startActivity(FairDetailActivity.getIntent(mContext, 1, bean.id));
                    }

                }

        );
    }

}
