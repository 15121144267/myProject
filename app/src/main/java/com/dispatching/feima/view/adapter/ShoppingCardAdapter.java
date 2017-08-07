package com.dispatching.feima.view.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.dispatching.feima.R;
import com.dispatching.feima.entity.ShoppingCardResponse;
import com.dispatching.feima.help.GlideHelper.ImageLoaderHelper;
import com.example.mylibrary.adapter.BaseQuickAdapter;
import com.example.mylibrary.adapter.BaseViewHolder;

import java.util.List;


public class ShoppingCardAdapter extends BaseQuickAdapter<ShoppingCardResponse.ShoppingCardListBean, BaseViewHolder> {
    private final Context mContext;
    private final ImageLoaderHelper mImageLoaderHelper;

    public ShoppingCardAdapter(List<ShoppingCardResponse.ShoppingCardListBean> notices, Context context, ImageLoaderHelper imageLoaderHelper) {
        super(R.layout.adapter_shopping_card_list, notices);
        mContext = context;
        mImageLoaderHelper =imageLoaderHelper;
    }

    @Override
    protected void convert(BaseViewHolder helper, ShoppingCardResponse.ShoppingCardListBean item) {
        if (item == null) return;
        helper.setText(R.id.adapter_shopping_card_shop_name,item.shopName);
        RecyclerView recyclerView = helper.getView(R.id.adapter_shopping_card_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        ShoppingCardItemAdapter itemAdapter = new ShoppingCardItemAdapter(item.shoppingCardBeen,mContext,mImageLoaderHelper);
        recyclerView.setAdapter(itemAdapter);

    }

}
