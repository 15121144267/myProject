package com.dispatching.feima.view.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.CheckBox;

import com.dispatching.feima.R;
import com.dispatching.feima.dagger.module.ShoppingCardListResponse;
import com.dispatching.feima.help.GlideHelper.ImageLoaderHelper;
import com.dispatching.feima.view.PresenterControl.ShoppingCardControl;
import com.example.mylibrary.adapter.BaseQuickAdapter;
import com.example.mylibrary.adapter.BaseViewHolder;

import java.util.List;


public class ShoppingCardAdapter extends BaseQuickAdapter<ShoppingCardListResponse.DataBean, BaseViewHolder> {
    private final Context mContext;
    private final ImageLoaderHelper mImageLoaderHelper;
    private ShoppingCardControl.ShoppingCardView mView;

    public ShoppingCardAdapter(List<ShoppingCardListResponse.DataBean> notices, ShoppingCardControl.ShoppingCardView view, Context context, ImageLoaderHelper imageLoaderHelper) {
        super(R.layout.adapter_shopping_card_list, notices);
        mContext = context;
        mView = view;
        mImageLoaderHelper = imageLoaderHelper;
    }

    @Override
    protected void convert(BaseViewHolder helper, ShoppingCardListResponse.DataBean item) {
        if (item == null) return;
        CheckBox checkBox = helper.getView(R.id.adapter_shopping_card_check);
        helper.addOnClickListener(R.id.adapter_shopping_card_check).addOnClickListener(R.id.adapter_shopping_card_edit);
        helper.setText(R.id.adapter_shopping_card_shop_name, TextUtils.isEmpty(item.linkName) ? "  未知店铺" : "  " + item.linkName);
        if (item.childEditFlag) {
            helper.setText(R.id.adapter_shopping_card_edit, "保存");
        } else {
            helper.setText(R.id.adapter_shopping_card_edit, "编辑");
        }
        helper.setChecked(R.id.adapter_shopping_card_check, item.checkFlag);

        RecyclerView recyclerView = helper.getView(R.id.adapter_shopping_card_list1);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        ShoppingCardItemAdapter itemAdapter = new ShoppingCardItemAdapter(item.products, mContext, mImageLoaderHelper);
        recyclerView.setAdapter(itemAdapter);
       /* recyclerView.setItemViewSwipeEnabled(false);
        recyclerView.setSwipeMenuCreator((swipeLeftMenu, swipeRightMenu, viewType) -> {
            int width = mContext.getResources().getDimensionPixelSize(R.dimen.dp_80);
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            SwipeMenuItem deleteItem = new SwipeMenuItem(mContext)
                    .setBackground(R.drawable.selector_red)
                    .setImage(R.mipmap.ic_action_delete)
                    .setText("删除")
                    .setTextColor(Color.WHITE)
                    .setWidth(width)
                    .setHeight(height);
            swipeRightMenu.addMenuItem(deleteItem);
        });

        recyclerView.setSwipeMenuItemClickListener(menuBridge ->
                mView.deleteProduct(item, item.products.get(menuBridge.getAdapterPosition()), menuBridge.getAdapterPosition()));*/


        mView.setChildAdapter(helper.getAdapterPosition(), itemAdapter, checkBox);
    }

}
