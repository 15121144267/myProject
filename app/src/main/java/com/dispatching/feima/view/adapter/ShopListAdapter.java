package com.dispatching.feima.view.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.dispatching.feima.R;
import com.dispatching.feima.entity.ShopListResponse;
import com.example.mylibrary.adapter.BaseQuickAdapter;
import com.example.mylibrary.adapter.BaseViewHolder;

import java.util.List;


public class ShopListAdapter extends BaseQuickAdapter<ShopListResponse.ListBean, BaseViewHolder> {
    private final Context mContext;

    public ShopListAdapter(List<ShopListResponse.ListBean> notices, Context context) {
        super(R.layout.adapter_shop_item, notices);
        mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, ShopListResponse.ListBean item) {
        if (item == null) return;
        List<ShopListResponse.ListBean.BusinessImagesBean> mList = item.businessImages;

        if (mList.size() != 0) {
            Glide.with(mContext).load(mList.get(0).imageUrl)
                    .error(R.mipmap.freemud_logo).into((ImageView) helper.getView(R.id.item_shop_pic));
            helper.setText(R.id.item_shop_name,item.fullName);
        }

    }

}
