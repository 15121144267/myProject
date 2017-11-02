package com.banshengyuan.feima.view.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.banshengyuan.feima.R;
import com.example.mylibrary.adapter.BaseQuickAdapter;
import com.example.mylibrary.adapter.BaseViewHolder;

import java.util.List;


public class CollectionShopAdapter extends BaseQuickAdapter<Integer, BaseViewHolder> {
    private final Context mContext;

    public CollectionShopAdapter(List<Integer> mList, Context context) {
        super(R.layout.adapter_collection_product, mList);
        mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, Integer item) {
        if (item == null) return;
        helper.setVisible(R.id.adapter_price_layout,false);
        helper.setMargin(R.id.adapter_content_text,10);
        ImageView imageView = helper.getView(R.id.adapter_collection_icon);
        imageView.setImageResource(item);
    }

}
