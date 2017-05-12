package com.dispatching.feima.view.adapter;

import com.dispatching.feima.R;
import com.dispatching.feima.database.OrderNotice;
import com.dispatching.feima.utils.TimeUtil;

import java.util.List;


public class NoticeAdapter extends BaseQuickAdapter<OrderNotice, BaseViewHolder> {
    public NoticeAdapter(List<OrderNotice> notices) {
        super( R.layout.adapter_notice, notices);
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderNotice item) {
        //helper.setText(R.id.adapter_content,item.getOrderId());
        helper.setText(R.id.adapter_time, TimeUtil.getTimeNow(item.getOrderTime()));
    }

}
