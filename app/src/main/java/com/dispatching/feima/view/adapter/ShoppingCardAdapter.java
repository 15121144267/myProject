package com.dispatching.feima.view.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.dispatching.feima.R;
import com.dispatching.feima.dagger.module.ShoppingCardListResponse;
import com.dispatching.feima.help.GlideHelper.ImageLoaderHelper;
import com.dispatching.feima.utils.ToastUtils;
import com.example.mylibrary.adapter.BaseQuickAdapter;
import com.example.mylibrary.adapter.BaseViewHolder;

import java.util.List;


public class ShoppingCardAdapter extends BaseQuickAdapter<ShoppingCardListResponse.DataBean, BaseViewHolder> {
    private final Context mContext;
    private final ImageLoaderHelper mImageLoaderHelper;

    public ShoppingCardAdapter(List<ShoppingCardListResponse.DataBean> notices, Context context, ImageLoaderHelper imageLoaderHelper) {
        super(R.layout.adapter_shopping_card_list, notices);
        mContext = context;
        mImageLoaderHelper = imageLoaderHelper;
    }

    @Override
    protected void convert(BaseViewHolder helper, ShoppingCardListResponse.DataBean item) {
        if (item == null) return;
        helper.addOnClickListener(R.id.adapter_shopping_card_check).addOnClickListener(R.id.adapter_shopping_card_edit);
        helper.setText(R.id.adapter_shopping_card_shop_name, "  店名");
        helper.setChecked(R.id.adapter_shopping_card_check,item.checkFlag);
        RecyclerView recyclerView = helper.getView(R.id.adapter_shopping_card_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        ShoppingCardItemAdapter itemAdapter = new ShoppingCardItemAdapter(item.products, mContext, mImageLoaderHelper);
        recyclerView.setAdapter(itemAdapter);

        itemAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            switch (view.getId()) {
                case R.id.item_shopping_card_check:
                    ToastUtils.showShortToast("check"+position);
                    break;
                case R.id.item_shopping_card_reduce:
                    ToastUtils.showShortToast("减少"+position);
                    break;
                case R.id.item_shopping_card_add:
                    ToastUtils.showShortToast("增加"+position);
                    break;
            }
        });
    }

}
