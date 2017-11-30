package com.banshengyuan.feima.view.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.entity.GoodsInfoResponse;
import com.banshengyuan.feima.view.customview.MyLinearLayout;
import com.banshengyuan.feima.view.fragment.SpecificationDialog;
import com.example.mylibrary.adapter.BaseQuickAdapter;
import com.example.mylibrary.adapter.BaseViewHolder;

import java.util.HashMap;
import java.util.List;


public class SpecificationAdapter extends BaseQuickAdapter<GoodsInfoResponse.InfoBean.OtherSpecBean, BaseViewHolder> {
    private final Context mContext;
    private GoodsInfoResponse.InfoBean.OtherSpecBean mOtherSpecBean;
    private HashMap<Integer, Integer> mSkuProMap;
    private HashMap<Integer, String> mSelectProMap;
    private SpecificationDialog mDialog;

    public SpecificationAdapter(List<GoodsInfoResponse.InfoBean.OtherSpecBean> otherSpecBean,
                                Context context, SpecificationDialog dialog, HashMap<Integer, String> selectProMap, HashMap<Integer, Integer> skuProMap) {
        super(R.layout.adapter_specifiaction, otherSpecBean);
        mContext = context;
        mDialog = dialog;
        if (selectProMap == null) {
            mSelectProMap = new HashMap<>();
        } else {
            mSelectProMap = selectProMap;
        }
        if (mSkuProMap == null) {
            mSkuProMap = new HashMap<>();
        } else {
            mSkuProMap = skuProMap;
        }
    }

    @Override
    protected void convert(BaseViewHolder helper, GoodsInfoResponse.InfoBean.OtherSpecBean item) {
        Integer type = item.id;
        MyLinearLayout  myLinearLayout = helper.getView(R.id.adapter_specification);
        if (myLinearLayout.getChildCount() == 0) {
            List<GoodsInfoResponse.InfoBean.OtherSpecBean.ValueBean> mList = item.value;
            TextView[] textViews = new TextView[mList.size()];
            for (int i = 0; i < mList.size(); i++) {
                TextView textView = new TextView(mContext);
                textView.setGravity(17);
                textView.setPadding(25, 15, 25, 15);
                textViews[i] = textView;
                textViews[i].setBackgroundResource(R.drawable.shape_corners_white);
                textViews[i].setText(mList.get(i).name);
                textViews[i].setTag(i);
                myLinearLayout.addView(textViews[i]);
            }
            for (TextView textView : textViews) {
                textView.setTag(textViews);
                textView.setOnClickListener(new SpecificationClickListener(type, mList));
            }
        }

//       判断之前是否已选中标签
        if (mSelectProMap.get(type) != null) {
            for (int j = 0; j < myLinearLayout.getChildCount(); j++) {
                TextView v = (TextView) myLinearLayout.getChildAt(j);
                if (mSelectProMap.get(type).equals(v.getText())) {
                    v.setBackgroundResource(R.drawable.shape_button_blue);
                }
            }

        }
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
        private Integer type;
        private List<GoodsInfoResponse.InfoBean.OtherSpecBean.ValueBean> value;

        public SpecificationClickListener(Integer type, List<GoodsInfoResponse.InfoBean.OtherSpecBean.ValueBean> value) {
            this.type = type;
            this.value = value;
        }

        @Override
        public void onClick(View v) {
            clickEvent(type, value, v);
        }
    }

    private void clickEvent(Integer type, List<GoodsInfoResponse.InfoBean.OtherSpecBean.ValueBean> value, View v) {
        TextView[] textViews = (TextView[]) v.getTag();
        TextView tv = (TextView) v;
        for (int i = 0; i < textViews.length; i++) {
            if (tv.equals(textViews[i])) {
                mSkuProMap.put(type, value.get(i).id);
                mSelectProMap.put(type, value.get(i).name);
            } else {
                textViews[i].setBackgroundResource(R.drawable.shape_corners_white);
            }
        }
        mDialog.setSpecificationContent(mSkuProMap,mSelectProMap);
        notifyDataSetChanged();
    }
}
