package com.banshengyuan.feima.view.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.CheckBox;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.entity.ShoppingCardListResponse;
import com.banshengyuan.feima.help.GlideHelper.ImageLoaderHelper;
import com.banshengyuan.feima.view.PresenterControl.ShoppingCardControl;
import com.example.mylibrary.adapter.BaseQuickAdapter;
import com.example.mylibrary.adapter.BaseViewHolder;

import java.util.List;


public class ShoppingCardAdapter extends BaseQuickAdapter<ShoppingCardListResponse.ListBeanX, BaseViewHolder> {
    private final Context mContext;
    private final ImageLoaderHelper mImageLoaderHelper;
    private ShoppingCardControl.ShoppingCardView mView;

    public ShoppingCardAdapter(List<ShoppingCardListResponse.ListBeanX> notices, ShoppingCardControl.ShoppingCardView view, Context context, ImageLoaderHelper imageLoaderHelper) {
        super(R.layout.adapter_shopping_card_list, notices);
        mContext = context;
        mView = view;
        mImageLoaderHelper = imageLoaderHelper;
    }

    @Override
    protected void convert(BaseViewHolder helper, ShoppingCardListResponse.ListBeanX item) {
        helper.addOnClickListener(R.id.adapter_shopping_card_list1);
        CheckBox checkBox = helper.getView(R.id.adapter_shopping_card_check);
        helper.addOnClickListener(R.id.adapter_shopping_card_check);
        helper.setText(R.id.adapter_shopping_card_shop_name, TextUtils.isEmpty(item.stoer_name) ? "  未知店铺" : item.stoer_name);
        helper.setChecked(R.id.adapter_shopping_card_check, item.checkFlag);
        RecyclerView recyclerView = helper.getView(R.id.adapter_shopping_card_list1);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        ShoppingCardItemAdapter itemAdapter = new ShoppingCardItemAdapter(item.list, mContext, mImageLoaderHelper);
        recyclerView.setAdapter(itemAdapter);
        mView.setChildAdapter(helper.getAdapterPosition(), itemAdapter, checkBox);

    }

}
