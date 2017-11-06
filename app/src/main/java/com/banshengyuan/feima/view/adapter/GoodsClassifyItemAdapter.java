package com.banshengyuan.feima.view.adapter;

import com.banshengyuan.feima.R;
import com.example.mylibrary.adapter.BaseQuickAdapter;
import com.example.mylibrary.adapter.BaseViewHolder;

import java.util.List;

/**
 * Created by lei.he on 2017/8/8.
 * GoodsClassifyItemAdapter
 */

public class GoodsClassifyItemAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public GoodsClassifyItemAdapter(List<String> notices) {
        super(R.layout.adapter_goods_classify_item, notices);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        if (item == null) return;
        helper.setText(R.id.adapter_item_goods_classify, item);
    }
}
