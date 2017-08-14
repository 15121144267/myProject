package com.dispatching.feima.view.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.dispatching.feima.R;
import com.dispatching.feima.dagger.module.ShoppingCardListResponse;
import com.dispatching.feima.help.GlideHelper.ImageLoaderHelper;
import com.dispatching.feima.utils.ValueUtil;
import com.example.mylibrary.adapter.BaseQuickAdapter;
import com.example.mylibrary.adapter.BaseViewHolder;

import java.util.List;


public class ShoppingCardItemAdapter extends BaseQuickAdapter<ShoppingCardListResponse.DataBean.ProductsBean, BaseViewHolder> {
    private final Context mContext;
    private final ImageLoaderHelper mImageLoaderHelper;

    public ShoppingCardItemAdapter(List<ShoppingCardListResponse.DataBean.ProductsBean> notices, Context context, ImageLoaderHelper imageLoaderHelper) {
        super(R.layout.item_shopping_card_detal, notices);
        mContext = context;
        mImageLoaderHelper =imageLoaderHelper;
    }

    @Override
    protected void convert(BaseViewHolder helper, ShoppingCardListResponse.DataBean.ProductsBean item) {
        if (item == null) return;
        helper.addOnClickListener(R.id.item_shopping_card_check).addOnClickListener(R.id.item_shopping_card_reduce)
                .addOnClickListener(R.id.item_shopping_card_add).addOnClickListener(R.id.item_shopping_card_delete);
        helper.setText(R.id.item_shopping_card_des,item.name);
        if(item.childEditFlag){
            helper.setVisible(R.id.item_shopping_card_count_layout,true);
            helper.setVisible(R.id.item_shopping_card_delete,true);
            helper.setVisible(R.id.item_shopping_card_count_text,false);
            helper.setText(R.id.item_shopping_card_count,String.valueOf(item.productNumber));
        }else {
            helper.setVisible(R.id.item_shopping_card_count_layout,false);
            helper.setVisible(R.id.item_shopping_card_delete,false);
            helper.setVisible(R.id.item_shopping_card_count_text,true);
            helper.setText(R.id.item_shopping_card_count_text,"x "+item.productNumber);
        }

        helper.setChecked(R.id.item_shopping_card_check,item.childCheckFlag);
        helper.setText(R.id.item_shopping_card_specification,item.specification);
        helper.setText(R.id.item_shopping_card_price,"￥"+ ValueUtil.formatAmount(item.finalPrice));
        ImageView imageView = helper.getView(R.id.item_shopping_card_icon);
        mImageLoaderHelper.displayRoundedCornerImage(mContext,item.picture,imageView,6);
    }

}
