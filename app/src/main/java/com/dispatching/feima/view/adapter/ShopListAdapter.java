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
        mImageLoaderHelper = imageLoaderHelper;
    }

    @Override
    protected void convert(BaseViewHolder helper, ShopListResponse.ListBean item) {
        if (item == null) return;
        ImageView imageView = helper.getView(R.id.item_shop_pic);
        helper.setText(R.id.item_shop_name, item.fullName == null ? "未知" : item.fullName);
        List<ShopListResponse.ListBean.BusinessImagesBean> mList = item.businessImages;
        if (mList != null && mList.size() != 0) {
            mImageLoaderHelper.displayImage(mContext, mList.get(0).imageUrl, imageView);
        } else {
            mImageLoaderHelper.displayImage(mContext, R.mipmap.freemud_logo, imageView);
        }
        if (item.storeCode.equals("107")) {
            helper.setText(R.id.item_shop_location, "LMS 1-107");
            helper.setText(R.id.item_shop_content1, "LMS集合线下实体、O2O、品牌商业运营团队、潮流造势团队，将独立设计师品牌等潮流元素打造出潮流品牌。");
        } else {
            helper.setText(R.id.item_shop_location, "大创 A-101");
            helper.setText(R.id.item_shop_content1, "合肥大创生活馆是日本大创在安徽的首家门店，大创为您提供从厨房用品、洗漱用品等一系列生活用品。");
        }
    }

}
