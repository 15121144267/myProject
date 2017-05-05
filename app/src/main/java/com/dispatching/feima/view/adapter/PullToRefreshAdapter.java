package com.dispatching.feima.view.adapter;

import com.dispatching.feima.R;
import com.dispatching.feima.entity.DataServer;
import com.dispatching.feima.entity.Status;


public class PullToRefreshAdapter extends BaseQuickAdapter<Status, BaseViewHolder> {
    public PullToRefreshAdapter() {
        super( R.layout.layout_animation, DataServer.getSampleData(10));
    }

    @Override
    protected void convert(BaseViewHolder helper, Status item) {
        helper.setText(R.id.tweetName,"订单详情:");
        helper.addOnClickListener(R.id.item_get_order);
    }

}
