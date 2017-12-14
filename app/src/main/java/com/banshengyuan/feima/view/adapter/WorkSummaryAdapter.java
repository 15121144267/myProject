package com.banshengyuan.feima.view.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.entity.FairContentDetailResponse;
import com.banshengyuan.feima.help.GlideHelper.ImageLoaderHelper;
import com.banshengyuan.feima.utils.ValueUtil;
import com.banshengyuan.feima.view.PresenterControl.WorkSummaryControl;
import com.banshengyuan.feima.view.activity.GoodDetailActivity;
import com.example.mylibrary.adapter.BaseQuickAdapter;
import com.example.mylibrary.adapter.BaseViewHolder;

import java.util.List;


public class WorkSummaryAdapter extends BaseQuickAdapter<FairContentDetailResponse.DetailBean, BaseViewHolder> {
    private final Context mContext;
    private final ImageLoaderHelper mImageLoaderHelper;
    private WorkSummaryControl.WorkSummaryView mView;

    public WorkSummaryAdapter(List<FairContentDetailResponse.DetailBean> list, Context context, ImageLoaderHelper imageLoaderHelper, WorkSummaryControl.WorkSummaryView view) {
        super(R.layout.adapter_notice, list);
        mContext = context;
        mImageLoaderHelper = imageLoaderHelper;
        mView = view;
    }

    @Override
    protected void convert(BaseViewHolder helper, FairContentDetailResponse.DetailBean item) {
        ValueUtil.setHtmlContent(mContext, item.content, helper.getView(R.id.adapter_fair_product_html));
        if (item.product != null) {
            RecyclerView recyclerView = helper.getView(R.id.adapter_fair_product_list);
            recyclerView.setNestedScrollingEnabled(false);
            recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
            FairContentDetailProductItemAdapter itemAdapter = new FairContentDetailProductItemAdapter(item.product, mContext, mImageLoaderHelper);
            recyclerView.setAdapter(itemAdapter);
            itemAdapter.setOnItemClickListener((adapter, view, position) -> {
                FairContentDetailResponse.DetailBean.ProductBean bean = (FairContentDetailResponse.DetailBean.ProductBean) adapter.getItem(position);
                if (bean != null) {
                    mContext.startActivity(GoodDetailActivity.getIntent(mContext, bean.id));
                }
            });
            itemAdapter.setOnItemChildClickListener((adapter, view, position) -> {
                FairContentDetailResponse.DetailBean.ProductBean bean = (FairContentDetailResponse.DetailBean.ProductBean) adapter.getItem(position);
                if (bean != null) {
                    switch (view.getId()) {
                        case R.id.product_collection:
                            mView.requestCollection(adapter,position,bean);
                            break;
                        case R.id.product_buy:
                            mContext.startActivity(GoodDetailActivity.getIntent(mContext, bean.id));
                            break;
                    }
                }

            });
        }

    }

}
