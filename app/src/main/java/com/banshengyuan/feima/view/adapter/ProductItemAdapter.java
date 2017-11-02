package com.banshengyuan.feima.view.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.entity.ProductResponse;
import com.example.mylibrary.adapter.BaseQuickAdapter;
import com.example.mylibrary.adapter.BaseViewHolder;

import java.util.List;


public class ProductItemAdapter extends BaseQuickAdapter<ProductResponse.ProductItemBean, BaseViewHolder> {
    private final Context mContext;
    private boolean mFlag;
    private boolean mFlag2;

    public ProductItemAdapter(List<ProductResponse.ProductItemBean> mList, Context context, boolean flag, boolean flag2) {
        super(R.layout.adapter_recommend_brand, mList);
        mContext = context;
        mFlag = flag;
        mFlag2 = flag2;
    }

    @Override
    protected void convert(BaseViewHolder helper, ProductResponse.ProductItemBean item) {
        if (item == null) return;
        ImageView imageView = helper.getView(R.id.adapter_common_icon);
        LinearLayout.LayoutParams params;
        if (mFlag) {
            if (mFlag2) {
                params = new LinearLayout.LayoutParams(360, 244);
            } else {
                params = new LinearLayout.LayoutParams(244, 244);
            }
        } else {
            params = new LinearLayout.LayoutParams(360, 244);
            helper.setVisible(R.id.adapter_common_price, true);
        }
        imageView.setLayoutParams(params);
        helper.setVisible(R.id.adapter_common_text, mFlag2);
        helper.setText(R.id.adapter_recommend_text, item.content);
        helper.setText(R.id.adapter_common_text, item.tip);
    }

}
