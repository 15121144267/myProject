package com.banshengyuan.feima.view.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.entity.SortListResponse;
import com.banshengyuan.feima.view.PresenterControl.GoodsClassifyControl;
import com.example.mylibrary.adapter.BaseQuickAdapter;
import com.example.mylibrary.adapter.BaseViewHolder;

import java.util.List;

/**
 * Created by lei.he on 2017/8/8.
 * GoodsClassifyAdapter
 */

public class GoodsClassifyAdapter extends BaseQuickAdapter<SortListResponse.DataBean, BaseViewHolder> {
    private final Context mContext;
    private final GoodsClassifyControl.GoodsClassifyView mView;

    public GoodsClassifyAdapter(List<SortListResponse.DataBean> notices, Context context, GoodsClassifyControl.GoodsClassifyView view) {
        super(R.layout.adapter_goods_classify, notices);
        mContext = context;
        mView = view;
    }

    @Override
    protected void convert(BaseViewHolder helper, SortListResponse.DataBean item) {
        if (item == null) return;
        helper.addOnClickListener(R.id.adapter_goods_classify_all);
        helper.setText(R.id.adapter_goods_classify_name, item.resultModel.name);
        RecyclerView recyclerView = helper.getView(R.id.adapter_goods_classify_list);
        recyclerView.setLayoutManager(new GridLayoutManager(mContext, 2));
        GoodsClassifyItemAdapter itemAdapter = new GoodsClassifyItemAdapter(item.children);
        recyclerView.setAdapter(itemAdapter);
        itemAdapter.setOnItemClickListener(mView::onItemClick);
    }
}
