package com.banshengyuan.feima.view.adapter;

import android.content.Context;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.entity.NoticeResponse;
import com.banshengyuan.feima.utils.TimeUtil;
import com.example.mylibrary.adapter.BaseQuickAdapter;
import com.example.mylibrary.adapter.BaseViewHolder;

import java.util.List;


public class NoticeAdapter extends BaseQuickAdapter<NoticeResponse.ListBean, BaseViewHolder> {
    private final Context mContext;

    public NoticeAdapter(List<NoticeResponse.ListBean> notices, Context context) {
        super(R.layout.adapter_notice_item, notices);
        mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, NoticeResponse.ListBean item) {

        if (item == null) return;

        /**
         * 1.等待买家付款（待付款）
         2.等待买家收货（已发货或待收货、待自提）
         3.等待卖家发货（待发货或已付款）自提订单无此状态
         4.交易成功（待评价或已完成、线下收款）线下收款订单没有商品，故无评价
         5.交易关闭（已取消）
         */
        switch (item.getType()) {
            case "1":
                helper.setImageResource(R.id.notice_item_image, R.mipmap.notice_order_nofinish);
                break;
            case "2":
                helper.setImageResource(R.id.notice_item_image, R.mipmap.notice_deliery);
                break;
            case "3":
                helper.setImageResource(R.id.notice_item_image, R.mipmap.notice_deliery);
                break;
            case "4":
                helper.setImageResource(R.id.notice_item_image, R.mipmap.notice_sign);
                break;
            default:
                helper.setImageResource(R.id.notice_item_image, R.mipmap.notice_logo);
                break;
        }
        helper.setText(R.id.notice_item_state, item.getTitle());
        helper.setText(R.id.notice_item_desc, item.getDesc());
        String time ;
        if (item.getCreate_time() != 0) {
            time = TimeUtil.getNoticeTime(String.valueOf(item.getCreate_time()), TimeUtil.TIME_YYMMDD);
        } else {
            time = "未知";
        }
        helper.setText(R.id.notice_item_time, time);

    }

}
