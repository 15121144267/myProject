package com.banshengyuan.feima.view.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.entity.VistaListResponse;
import com.banshengyuan.feima.help.GlideHelper.ImageLoaderHelper;
import com.banshengyuan.feima.utils.ValueUtil;
import com.example.mylibrary.adapter.BaseQuickAdapter;
import com.example.mylibrary.adapter.BaseViewHolder;

import java.util.List;


public class VistaFairAdapter extends BaseQuickAdapter<VistaListResponse.ListBean, BaseViewHolder> {
    private final Context mContext;
    private ImageLoaderHelper mImageLoaderHelper;

    public VistaFairAdapter(List<VistaListResponse.ListBean> mList, Context context, ImageLoaderHelper imageLoaderHelper) {
        super(R.layout.adapter_hot_fair, mList);
        mContext = context;
        mImageLoaderHelper = imageLoaderHelper;
    }

    @Override
    protected void convert(BaseViewHolder helper, VistaListResponse.ListBean item) {
        if (item == null) return;
        ImageView imageView = helper.getView(R.id.hot_fair_pic);
        mImageLoaderHelper.displayRoundedCornerImage(mContext, item.cover_img, imageView, 6);
        helper.setText(R.id.hot_fair_time_name, item.name);
        helper.setText(R.id.adapter_fair_summary, item.summary);
        helper.setVisible(R.id.hot_fair_time_first, true);
        TextView textView = helper.getView(R.id.hot_fair_time_first);
        if (item.distance == 0) {
            textView.setText("  距离未知");
        } else {
            textView.setText("  " + ValueUtil.formatDistance((float) item.distance));
        }
        ValueUtil.setTextDrawable(mContext,textView,R.mipmap.pay_address_location,0);
    }

}
