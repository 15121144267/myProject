package com.banshengyuan.feima.view.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.entity.Constant;
import com.banshengyuan.feima.entity.MyCollectionFairResponse;
import com.banshengyuan.feima.help.GlideHelper.ImageLoaderHelper;
import com.banshengyuan.feima.utils.TimeUtil;
import com.example.mylibrary.adapter.BaseQuickAdapter;
import com.example.mylibrary.adapter.BaseViewHolder;

import java.util.List;


public class CollectionFairAdapter extends BaseQuickAdapter<MyCollectionFairResponse.ListBean, BaseViewHolder> {
    private final Context mContext;
    private final ImageLoaderHelper mImageLoaderHelper;
    private List<MyCollectionFairResponse.ListBean> mList;

    public CollectionFairAdapter(List<MyCollectionFairResponse.ListBean> myCollectionResponseList, Context context, ImageLoaderHelper imageLoaderHelper) {
        super(R.layout.adapter_collection_fair, null);
        mContext = context;
        mImageLoaderHelper = imageLoaderHelper;
        mList = myCollectionResponseList;
    }

    @Override
    protected void convert(BaseViewHolder helper, MyCollectionFairResponse.ListBean item) {
        if (item == null) return;
        ImageView imageView = helper.getView(R.id.adapter_collection_fair);
        mImageLoaderHelper.displayRoundedCornerImage(mContext, Constant.URL + item.getCover_img(), imageView, 6);
        helper.setText(R.id.adapter_collection_fair_name, item.getName());
        if (item.getCreated_at() > 0) {
            helper.setText(R.id.adapter_collection_fair_time, TimeUtil.transferLongToDate(TimeUtil.TIME_YYMMDD_CH, (long) item.getCreated_at()));
        }
    }

}
