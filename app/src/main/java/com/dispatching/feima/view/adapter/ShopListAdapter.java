package com.dispatching.feima.view.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.dispatching.feima.R;
import com.dispatching.feima.entity.ShopListResponse;
import com.dispatching.feima.help.GlideHelper.ImageLoaderHelper;
import com.example.mylibrary.adapter.BaseQuickAdapter;
import com.example.mylibrary.adapter.BaseViewHolder;

import java.util.List;


public class ShopListAdapter extends BaseQuickAdapter<ShopListResponse.ListBean, BaseViewHolder> {
    private final Context mContext;
    private final ImageLoaderHelper mImageLoaderHelper;

    public ShopListAdapter(List<ShopListResponse.ListBean> notices, Context context, ImageLoaderHelper imageLoaderHelper) {
        super(R.layout.adapter_shop_item, notices);
        mContext = context;
        mImageLoaderHelper =imageLoaderHelper;
    }

    @Override
    protected void convert(BaseViewHolder helper, ShopListResponse.ListBean item) {
        if (item == null) return;
        ImageView imageView =  helper.getView(R.id.item_shop_pic);
        helper.setText(R.id.item_shop_name,item.fullName==null?"未知":item.fullName);
        List<ShopListResponse.ListBean.BusinessImagesBean> mList = item.businessImages;
        if(mList.size()!=0){
            mImageLoaderHelper.displayImage(mContext,mList.get(0).imageUrl, imageView);
        }else {
            mImageLoaderHelper.displayImage(mContext,R.mipmap.freemud_logo, imageView);
        }
    }

}
