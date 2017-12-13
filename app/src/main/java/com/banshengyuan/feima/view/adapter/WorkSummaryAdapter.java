package com.banshengyuan.feima.view.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.entity.FairContentDetailResponse;
import com.banshengyuan.feima.help.GlideHelper.ImageLoaderHelper;
import com.banshengyuan.feima.utils.ValueUtil;
import com.example.mylibrary.adapter.BaseQuickAdapter;
import com.example.mylibrary.adapter.BaseViewHolder;

import java.util.List;


public class WorkSummaryAdapter extends BaseQuickAdapter<FairContentDetailResponse.DetailBean, BaseViewHolder> {
    private final Context mContext;
    private final ImageLoaderHelper mImageLoaderHelper;

    public WorkSummaryAdapter(List<FairContentDetailResponse.DetailBean> list, Context context, ImageLoaderHelper imageLoaderHelper) {
        super(R.layout.adapter_notice, list);
        mContext = context;
        mImageLoaderHelper = imageLoaderHelper;
    }

    @Override
    protected void convert(BaseViewHolder helper, FairContentDetailResponse.DetailBean item) {
        ValueUtil.setHtmlContent(mContext, item.content, helper.getView(R.id.adapter_fair_product_html));
        if (item.product != null) {
            RecyclerView recyclerView = helper.getView(R.id.adapter_fair_product_list);
            recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
            FairContentDetailProductItemAdapter itemAdapter = new FairContentDetailProductItemAdapter(item.product, mContext, mImageLoaderHelper);
            recyclerView.setAdapter(itemAdapter);
        }

    }

}
