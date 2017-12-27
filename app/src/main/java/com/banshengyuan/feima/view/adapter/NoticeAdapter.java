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
            3 订单发货、 4 订单签收、 2 提交订单、 1 系统消
         */
        switch (item.getType()) {
            case "1":
                helper.setImageResource(R.id.notice_item_image, R.mipmap.notice_logo);
                break;
            case "2":
                helper.setImageResource(R.id.notice_item_image, R.mipmap.notice_action_start);
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
