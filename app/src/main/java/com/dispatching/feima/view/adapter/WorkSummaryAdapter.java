package com.dispatching.feima.view.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;

import com.dispatching.feima.R;
import com.dispatching.feima.entity.MyOrders;

import java.util.List;


public class WorkSummaryAdapter extends BaseQuickAdapter<MyOrders, BaseViewHolder> {
    private final Context mContext;
    public WorkSummaryAdapter(List<MyOrders> list, Context context) {
        super( R.layout.adapter_notice, list);
        mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, MyOrders item) {
        switch (item.channel) {
            case "bdwm":
                helper.setImageDrawable(R.id.order_channel,ContextCompat.getDrawable(mContext,R.mipmap.channl_baidu));
                break;
            case "eleme":
                helper.setImageDrawable(R.id.order_channel,ContextCompat.getDrawable(mContext,R.mipmap.channl_elme));
                break;
            case "fmwd":
                helper.setImageDrawable(R.id.order_channel,ContextCompat.getDrawable(mContext,R.mipmap.channl_laoxiangji));
                break;
            case "mtwm":
                helper.setImageDrawable(R.id.order_channel,ContextCompat.getDrawable(mContext,R.mipmap.channl_meituan));
                break;
            default:
                helper.setVisible(R.id.order_channel, false);
                break;
        }

        helper.setText(R.id.adapter_time, item.endTime);
        String orderId = "订单编号:"+item.businessId;
        helper.setText(R.id.adapter_content, orderId);
        helper.setVisible(R.id.order_new_message,false);
    }

}
