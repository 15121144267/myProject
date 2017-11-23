package com.banshengyuan.feima.view.adapter;

import android.content.Context;
import android.text.TextUtils;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.entity.ExChangeResponse;
import com.banshengyuan.feima.help.GlideHelper.ImageLoaderHelper;
import com.banshengyuan.feima.utils.TimeUtil;
import com.example.mylibrary.adapter.BaseQuickAdapter;
import com.example.mylibrary.adapter.BaseViewHolder;

import java.util.List;


public class ExChangeAdapter extends BaseQuickAdapter<ExChangeResponse.ListBean, BaseViewHolder> {
    private final Context mContext;
    private ImageLoaderHelper imageLoaderHelper;

    public ExChangeAdapter(List<ExChangeResponse.ListBean> mList, Context context, ImageLoaderHelper imageLoaderHelper) {
        super(R.layout.adapter_hot_fair, mList);
        mContext = context;
        this.imageLoaderHelper = imageLoaderHelper;
    }

    @Override
    protected void convert(BaseViewHolder helper, ExChangeResponse.ListBean item) {
        if (item == null) return;
        helper.setText(R.id.hot_fair_time_name, item.getName());
        helper.setText(R.id.adapter_fair_summary, item.getSummary());
        imageLoaderHelper.displayRoundedCornerImage(mContext, item.getCover_img(), helper.getView(R.id.hot_fair_pic), 4);
        if (!TextUtils.isEmpty(item.getEnd_time())) {
            helper.setVisible(R.id.hot_fair_time_first, true);
            helper.setText(R.id.hot_fair_time_first, TimeUtil.transferLongToDate("MM月dd日",Long.parseLong(item.getEnd_time())));
        }
    }

}
