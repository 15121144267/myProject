package com.banshengyuan.feima.view.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.entity.FairBottomResponse;
import com.banshengyuan.feima.help.GlideHelper.ImageLoaderHelper;
import com.example.mylibrary.adapter.BaseQuickAdapter;
import com.example.mylibrary.adapter.BaseViewHolder;

import java.util.List;


public class MainHotFairAdapter extends BaseQuickAdapter<FairBottomResponse.ListBean, BaseViewHolder> {
    private final Context mContext;
    private final ImageLoaderHelper mImageLoaderHelper;
    public MainHotFairAdapter(List<FairBottomResponse.ListBean> mList, Context context,ImageLoaderHelper imageLoaderHelper) {
        super(R.layout.adapter_hot_fair, mList);
        mContext = context;
        mImageLoaderHelper = imageLoaderHelper;
    }

    @Override
    protected void convert(BaseViewHolder helper, FairBottomResponse.ListBean item) {
        if (item == null) return;
        ImageView imageView = helper.getView(R.id.hot_fair_pic);
        mImageLoaderHelper.displayRoundedCornerImage(mContext,item.cover_img,imageView,6);
        helper.setText(R.id.hot_fair_time_name, item.name);
        helper.setText(R.id.adapter_fair_summary, item.name);

    }

}
