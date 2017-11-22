package com.banshengyuan.feima.view.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.entity.ProductListResponse;
import com.banshengyuan.feima.help.GlideHelper.ImageLoaderHelper;
import com.banshengyuan.feima.utils.ToastUtils;
import com.example.mylibrary.adapter.BaseQuickAdapter;
import com.example.mylibrary.adapter.BaseViewHolder;

import java.util.List;


public class ProductAdapter extends BaseQuickAdapter<ProductListResponse.CategoryBean, BaseViewHolder> {
    private final Context mContext;
    private final ImageLoaderHelper mImageLoaderHelper;

    public ProductAdapter(List<ProductListResponse.CategoryBean> mList, Context context , ImageLoaderHelper imageLoaderHelper) {
        super(R.layout.adapter_fair, mList);
        mContext = context;
        mImageLoaderHelper = imageLoaderHelper;
    }

    @Override
    protected void convert(BaseViewHolder helper, ProductListResponse.CategoryBean item) {
        if (item == null) return;
        helper.addOnClickListener(R.id.adapter_fair_more);
        helper.setText(R.id.adapter_fair_sign,item.name);
        RecyclerView recyclerView = helper.getView(R.id.adapter_fair_content);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        ProductItemAdapter itemAdapter = new ProductItemAdapter(item.goods, mContext,mImageLoaderHelper);
        recyclerView.setAdapter(itemAdapter);
        itemAdapter.setOnItemClickListener((adapter, view, position) ->
                ToastUtils.showLongToast("产品详情页面")
        );
    }

}
