package com.banshengyuan.feima.view.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.entity.BlockFairListResponse;
import com.banshengyuan.feima.help.GlideHelper.ImageLoaderHelper;
import com.banshengyuan.feima.utils.ToastUtils;
import com.example.mylibrary.adapter.BaseQuickAdapter;
import com.example.mylibrary.adapter.BaseViewHolder;

import java.util.List;


public class BlockDetailFairAdapter extends BaseQuickAdapter<BlockFairListResponse, BaseViewHolder> {
    private final Context mContext;
    private final ImageLoaderHelper mImageLoaderHelper;
    public BlockDetailFairAdapter(List<BlockFairListResponse> mList, Context context,ImageLoaderHelper imageLoaderHelper) {
        super(R.layout.adapter_block_detail, mList);
        mContext = context;
        mImageLoaderHelper = imageLoaderHelper;
    }

    @Override
    protected void convert(BaseViewHolder helper, BlockFairListResponse item) {
        if (item == null) return;
        helper.addOnClickListener(R.id.adapter_fair_more);
        helper.setText(R.id.adapter_fair_sign," 市集");
        RecyclerView recyclerView = helper.getView(R.id.adapter_fair_content);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        BlockDetailFairItemAdapter itemAdapter = new BlockDetailFairItemAdapter(item.list, mContext,mImageLoaderHelper);
        recyclerView.setAdapter(itemAdapter);
        itemAdapter.setOnItemClickListener((adapter, view, position) -> ToastUtils.showLongToast("市集详情"));
    }

}
