package com.banshengyuan.feima.view.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.entity.Constant;
import com.banshengyuan.feima.entity.MyCollectionBlockResponse;
import com.banshengyuan.feima.help.GlideHelper.ImageLoaderHelper;
import com.example.mylibrary.adapter.BaseQuickAdapter;
import com.example.mylibrary.adapter.BaseViewHolder;

import java.util.List;


public class CollectionBlockAdapter extends BaseQuickAdapter<MyCollectionBlockResponse.ListBean, BaseViewHolder> {
    private final Context mContext;
    private final ImageLoaderHelper mImageLoaderHelper;

    public CollectionBlockAdapter(List<MyCollectionBlockResponse.ListBean> mList, Context context, ImageLoaderHelper imageLoaderHelper) {
        super(R.layout.adapter_collection_block, mList);
        mContext = context;
        mImageLoaderHelper = imageLoaderHelper;
    }

    @Override
    protected void convert(BaseViewHolder helper, MyCollectionBlockResponse.ListBean item) {
        if (item == null) return;

        ImageView imageView = helper.getView(R.id.adapter_collection_block_pic);
        mImageLoaderHelper.displayRoundedCornerImage(mContext, Constant.URL + item.getCover_img(), imageView, 6);

        helper.setText(R.id.adapter_collection_block_name, item.getName());
        helper.setText(R.id.adapter_collection_block_desc, item.getSummary());
    }

}
