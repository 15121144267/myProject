package com.dispatching.feima.view.adapter;

import android.support.v7.widget.LinearLayoutCompat;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

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
        LinearLayout linearLayout = helper.getView(R.id.adapter_item_goods_classify_layout);
        if(helper.getAdapterPosition()%2==0){
            LinearLayoutCompat.LayoutParams layoutParams = new LinearLayoutCompat.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(0,0,8,8);
            linearLayout.setLayoutParams(layoutParams);
        }
        helper.setText(R.id.adapter_item_goods_classify, item.resultModel.name);
    }
}
