package com.dispatching.feima.view.adapter;

import com.dispatching.feima.R;
import com.dispatching.feima.entity.SortListResponse;
import com.example.mylibrary.adapter.BaseQuickAdapter;
import com.example.mylibrary.adapter.BaseViewHolder;

import java.util.List;

/**
 * Created by lei.he on 2017/8/8.
 */

public class GoodsClassifyItemAdapter extends BaseQuickAdapter<SortListResponse.DataBean.ChildrenBean, BaseViewHolder> {

    public GoodsClassifyItemAdapter(List<SortListResponse.DataBean.ChildrenBean> notices) {
        super(R.layout.adapter_goods_classify_item, notices);
    }

    @Override
    protected void convert(BaseViewHolder helper, SortListResponse.DataBean.ChildrenBean item) {
        if (item == null) return;
        helper.setText(R.id.adapter_item_goods_classify, item.resultModel.name);
    }
}
