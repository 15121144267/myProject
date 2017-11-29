package com.banshengyuan.feima.view.adapter;

import android.content.Context;
import android.view.View;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.entity.GoodsInfoResponse;
import com.example.mylibrary.adapter.BaseQuickAdapter;
import com.example.mylibrary.adapter.BaseViewHolder;

import java.util.HashMap;
import java.util.List;


public class SpecificationAdapter extends BaseQuickAdapter<GoodsInfoResponse.InfoBean.OtherSpecBean, BaseViewHolder> {
    private final Context mContext;
    private GoodsInfoResponse.InfoBean.OtherSpecBean mOtherSpecBean ;
    private HashMap<String, String> selectProMap;

    public SpecificationAdapter(List<GoodsInfoResponse.InfoBean.OtherSpecBean> otherSpecBean,
                                Context context) {
        super(R.layout.adapter_specifiaction, otherSpecBean);
        mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, GoodsInfoResponse.InfoBean.OtherSpecBean item) {


     /*  判断之前是否已选中标签
        if (selectProMap.get(type) != null) {
            for (int j = 0; j < myLinearLayout.getChildCount(); j++) {
                TextView v = (TextView) myLinearLayout.getChildAt(j);
                if (selectProMap.get(type).equals(v.getText().toString())) {
                    v.setBackgroundResource(R.mipmap.specification_back);
                    selectProMap.put(type, v.getText().toString());
                }
            }

        }*/
    }

    /*private void checkEnable(List<String> list) {
        for (int j = 0; j < myLinearLayout.getChildCount(); j++) {
            TextView v = (TextView) myLinearLayout.getChildAt(j);
            if (list.contains(v.getText().toString())) {
                v.setEnabled(true);
            } else {
                v.setEnabled(false);
            }
        }
    }

    private void checkEnable2(List<String> list) {
        for (int j = 0; j < myLinearLayout.getChildCount(); j++) {
            TextView v = (TextView) myLinearLayout.getChildAt(j);
            if (list == null || list.size() > 0 && list.contains(v.getText().toString())) {
                v.setEnabled(true);
            } else {
                v.setEnabled(false);
            }
        }
    }*/

    private class SpecificationClickListener implements View.OnClickListener {
        private String type;

        public SpecificationClickListener(String type) {
            this.type = type;
        }

        @Override
        public void onClick(View v) {
            clickEvent(type, v);
        }
    }

    private void clickEvent(String type, View v) {

    }
}
