package com.banshengyuan.feima.view.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.entity.FairListResponse;
import com.banshengyuan.feima.help.GlideHelper.ImageLoaderHelper;
import com.example.mylibrary.adapter.BaseQuickAdapter;
import com.example.mylibrary.adapter.BaseViewHolder;

import java.util.List;


public class FairProductItemAdapter extends BaseQuickAdapter<FairListResponse.CategoryBean.FairBean, BaseViewHolder> {
    private final Context mContext;
    private final ImageLoaderHelper mImageLoaderHelper;

    public FairProductItemAdapter(List<FairListResponse.CategoryBean.FairBean> mList, Context context, ImageLoaderHelper imageLoaderHelper) {
        super(R.layout.adapter_recommend_brand, mList);
        mContext = context;
        mImageLoaderHelper = imageLoaderHelper;

    }

    @Override
    protected void convert(BaseViewHolder helper, FairListResponse.CategoryBean.FairBean item) {
        if (item == null) return;
        ImageView imageView = helper.getView(R.id.adapter_common_icon);
        mImageLoaderHelper.displayRoundedCornerImage(mContext,item.cover_img,imageView,6);
        helper.setVisible(R.id.adapter_common_text, true);
        helper.setText(R.id.adapter_recommend_text, item.name);
        helper.setText(R.id.adapter_common_text, item.summary);
    }

}
