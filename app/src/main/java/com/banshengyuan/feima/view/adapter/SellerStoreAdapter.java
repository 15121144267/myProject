package com.banshengyuan.feima.view.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.entity.StoreListResponse;
import com.banshengyuan.feima.help.GlideHelper.ImageLoaderHelper;
import com.banshengyuan.feima.view.activity.ShopProductDetailActivity;
import com.example.mylibrary.adapter.BaseQuickAdapter;
import com.example.mylibrary.adapter.BaseViewHolder;

import java.util.List;


public class SellerStoreAdapter extends BaseQuickAdapter<StoreListResponse.CategoryBean, BaseViewHolder> {
    private final Context mContext;
    private final ImageLoaderHelper mImageLoaderHelper;

    public SellerStoreAdapter(List<StoreListResponse.CategoryBean> mList, Context context, ImageLoaderHelper imageLoaderHelper) {
        super(R.layout.adapter_fair, mList);
        mContext = context;
        mImageLoaderHelper = imageLoaderHelper;
    }

    @Override
    protected void convert(BaseViewHolder helper, StoreListResponse.CategoryBean item) {
        if (item == null) return;
        helper.addOnClickListener(R.id.adapter_fair_more);
        helper.setText(R.id.adapter_fair_sign, item.name);
        RecyclerView recyclerView = helper.getView(R.id.adapter_fair_content);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        StoreItemAdapter itemAdapter = new StoreItemAdapter(item.goods, mContext, mImageLoaderHelper);
        recyclerView.setAdapter(itemAdapter);
        itemAdapter.setOnItemClickListener((adapter, view, position) -> {
                    StoreListResponse.CategoryBean.GoodsBean bean = (StoreListResponse.CategoryBean.GoodsBean) adapter.getItem(position);
                    if (bean != null) {
                        mContext.startActivity(ShopProductDetailActivity.getActivityDetailIntent(mContext, bean.id));
                    }});
    }

}
