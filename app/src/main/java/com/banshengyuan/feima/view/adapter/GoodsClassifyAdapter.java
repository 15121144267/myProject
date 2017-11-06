package com.banshengyuan.feima.view.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.entity.GoodsClassifyResponse;
import com.banshengyuan.feima.view.PresenterControl.GoodsClassifyControl;
import com.example.mylibrary.adapter.BaseQuickAdapter;
import com.example.mylibrary.adapter.BaseViewHolder;

import java.util.List;

/**
 * Created by lei.he on 2017/8/8.
 * GoodsClassifyAdapter
 */

public class GoodsClassifyAdapter extends BaseQuickAdapter<GoodsClassifyResponse, BaseViewHolder> {
    private final Context mContext;
    private final GoodsClassifyControl.GoodsClassifyView mView;

    public GoodsClassifyAdapter(List<GoodsClassifyResponse> notices, Context context, GoodsClassifyControl.GoodsClassifyView view) {
        super(R.layout.adapter_goods_classify, notices);
        mContext = context;
        mView = view;
    }

    @Override
    protected void convert(BaseViewHolder helper, GoodsClassifyResponse item) {
        if (item == null) return;
        helper.setText(R.id.adapter_goods_classify_name, item.name);
        RecyclerView recyclerView = helper.getView(R.id.adapter_goods_classify_list);
        recyclerView.setLayoutManager(new GridLayoutManager(mContext, 4));
        GoodsClassifyItemAdapter itemAdapter = new GoodsClassifyItemAdapter(item.mList);
        recyclerView.setAdapter(itemAdapter);
        itemAdapter.setOnItemClickListener(mView::onItemClick);
    }
}
