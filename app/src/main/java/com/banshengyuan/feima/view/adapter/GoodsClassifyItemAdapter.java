package com.banshengyuan.feima.view.adapter;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.entity.AllFairListResponse;
import com.example.mylibrary.adapter.BaseQuickAdapter;
import com.example.mylibrary.adapter.BaseViewHolder;

import java.util.List;

/**
 * Created by lei.he on 2017/8/8.
 * GoodsClassifyItemAdapter
 */

public class GoodsClassifyItemAdapter extends BaseQuickAdapter<AllFairListResponse.ListBean.List1Bean, BaseViewHolder> {

    public GoodsClassifyItemAdapter(List<AllFairListResponse.ListBean.List1Bean> notices) {
        super(R.layout.adapter_goods_classify_item, notices);
    }

    @Override
    protected void convert(BaseViewHolder helper, AllFairListResponse.ListBean.List1Bean item) {
        if (item == null) return;
        helper.setText(R.id.adapter_item_goods_classify, item.name);
    }
}
