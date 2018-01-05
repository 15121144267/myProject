package com.banshengyuan.feima.view.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.entity.CollectionShopResponse;
import com.banshengyuan.feima.help.GlideHelper.ImageLoaderHelper;
import com.example.mylibrary.adapter.BaseQuickAdapter;
import com.example.mylibrary.adapter.BaseViewHolder;

import java.util.List;


public class CollectionShopAdapter extends BaseQuickAdapter<CollectionShopResponse.ListBean, BaseViewHolder> {
    private final Context mContext;
    private ImageLoaderHelper mImageLoaderHelper = null;

    public CollectionShopAdapter(List<CollectionShopResponse.ListBean> mList, Context context, ImageLoaderHelper imageLoaderHelper) {
        super(R.layout.adapter_shop_item, mList);
        mContext = context;
        mImageLoaderHelper = imageLoaderHelper;
    }

    @Override
    protected void convert(BaseViewHolder helper, CollectionShopResponse.ListBean item) {
        if (item == null) return;
        ImageView imageView = helper.getView(R.id.item_shop_pic);
        mImageLoaderHelper.displayImage(mContext, item.getCover_img(), imageView);

        helper.setText(R.id.item_shop_name, item.getName());
        if (!TextUtils.isEmpty(item.getCategory())) {
            helper.setText(R.id.item_shop_content1, item.getCategory());
        }
    }

}
