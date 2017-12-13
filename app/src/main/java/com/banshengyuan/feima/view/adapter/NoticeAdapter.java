package com.banshengyuan.feima.view.adapter;

import android.content.Context;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.entity.NoticeResponse;
import com.example.mylibrary.adapter.BaseQuickAdapter;
import com.example.mylibrary.adapter.BaseViewHolder;

import java.util.List;


public class NoticeAdapter extends BaseQuickAdapter<NoticeResponse.ListBean, BaseViewHolder> {
    private final Context mContext;

    public NoticeAdapter(List<NoticeResponse.ListBean> notices, Context context) {
        super(R.layout.adapter_notice, notices);
        mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, NoticeResponse.ListBean item) {
//        switch (item.getOrderChannel()) {
//            case "bdwm":
//                helper.setImageDrawable(R.id.order_channel,ContextCompat.getDrawable(mContext,R.mipmap.channl_baidu));
//                break;
//            case "eleme":
//                helper.setImageDrawable(R.id.order_channel,ContextCompat.getDrawable(mContext,R.mipmap.channl_elme));
//                break;
//            case "fmwd":
//                helper.setImageDrawable(R.id.order_channel,ContextCompat.getDrawable(mContext,R.mipmap.channl_laoxiangji));
//                break;
//            case "mtwm":
//                helper.setImageDrawable(R.id.order_channel,ContextCompat.getDrawable(mContext,R.mipmap.channl_meituan));
//                break;
//            default:
//                helper.setVisible(R.id.order_channel, false);
//                break;
//        }

        /*helper.setText(R.id.adapter_content, item.getTitle());
        helper.setText(R.id.adapter_notice_detail, item.getDesc());
        if (item.getCreate_time() > 0){
            if(TimeUtil.getTimeIsToday(String.valueOf(item.getCreate_time()))){
                helper.setText(R.id.adapter_notice_date, "今天"+TimeUtil.transferLongToDate(TimeUtil.TIME_HHMMSS, (long)item.getCreate_time()));
            }else {
                helper.setText(R.id.adapter_notice_date, TimeUtil.transferLongToDate(TimeUtil.TIME_YMMDD, (long)item.getCreate_time()));
            }
        }*/
//        if (item.getOrderFlag() == 0) {
//            helper.setVisible(R.id.order_new_message, true);
//        } else {
//            helper.setVisible(R.id.order_new_message, false);
//        }

    }

}
