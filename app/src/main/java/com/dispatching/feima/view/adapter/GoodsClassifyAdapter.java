package com.dispatching.feima.view.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.dispatching.feima.R;
import com.dispatching.feima.entity.SpecificationResponse;
import com.dispatching.feima.view.PresenterControl.GoodsClassifyControl;
import com.example.mylibrary.adapter.BaseQuickAdapter;
import com.example.mylibrary.adapter.BaseViewHolder;

import java.util.List;

/**
 * Created by lei.he on 2017/8/8.
 */

public class GoodsClassifyAdapter extends BaseQuickAdapter<SpecificationResponse.ProductsBean.SpecificationListBean, BaseViewHolder> {
    private final Context mContext;
    private final GoodsClassifyControl.GoodsClassifyView mView;

    public GoodsClassifyAdapter(List<SpecificationResponse.ProductsBean.SpecificationListBean> notices, Context context, GoodsClassifyControl.GoodsClassifyView view) {
        super(R.layout.adapter_goods_classify, notices);
        mContext = context;
        mView = view;
    }

    @Override
    protected void convert(BaseViewHolder helper, SpecificationResponse.ProductsBean.SpecificationListBean item) {
        if (item == null) return;
        helper.addOnClickListener(R.id.adapter_goods_classify_all);
        helper.setText(R.id.adapter_goods_classify_name, item.partName);
        RecyclerView recyclerView = helper.getView(R.id.adapter_goods_classify_list);
        recyclerView.setLayoutManager(new GridLayoutManager(mContext, 2));
        GoodsClassifyItemAdapter itemAdapter = new GoodsClassifyItemAdapter(item.value);
        recyclerView.setAdapter(itemAdapter);
        itemAdapter.setOnItemClickListener((adapter, view, position) -> {
                    mView.showToast("" + position);
                }
        );
    }
}
