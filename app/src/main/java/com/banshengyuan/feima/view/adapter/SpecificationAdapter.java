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
    private List<GoodsInfoResponse.InfoBean.OtherSpecBean> mOtherSpecBean;
    private HashMap<Integer, Integer> mSkuProMap;
    private HashMap<Integer, String> mSelectProMap;
    private GoodsInfoResponse.InfoBean mInfoBean;
    private SpecificationDialog mDialog;

    public SpecificationAdapter(List<GoodsInfoResponse.InfoBean.OtherSpecBean> otherSpecBean, GoodsInfoResponse.InfoBean infoBean,
                                Context context, SpecificationDialog dialog, HashMap<Integer, String> selectProMap, HashMap<Integer, Integer> skuProMap) {
        super(R.layout.adapter_specifiaction, otherSpecBean);
        mOtherSpecBean = otherSpecBean;
        mContext = context;
        mInfoBean = infoBean;
        mDialog = dialog;
        if (selectProMap == null) {
            mSelectProMap = new HashMap<>();
        } else {
            mSelectProMap = selectProMap;
        }
        if (skuProMap == null) {
            mSkuProMap = new HashMap<>();
        } else {
            mSkuProMap = skuProMap;
        }
    }

    @Override
    protected void convert(BaseViewHolder helper, GoodsInfoResponse.InfoBean.OtherSpecBean item) {
        Integer type = item.id;
        MyLinearLayout myLinearLayout = helper.getView(R.id.adapter_specification);
        List<GoodsInfoResponse.InfoBean.OtherSpecBean.ValueBean> mList = item.value;
        if (myLinearLayout.getChildCount() == 0) {
            TextView[] textViews = new TextView[mList.size()];
            for (int i = 0; i < mList.size(); i++) {
                TextView textView = new TextView(mContext);
                textView.setGravity(17);
                textView.setPadding(25, 15, 25, 15);
                textViews[i] = textView;
                textViews[i].setBackgroundResource(R.drawable.selector_enable);
                textViews[i].setText(mList.get(i).name);
                textViews[i].setTag(i);
                textViews[i].setEnabled(mList.get(i).enableFlag);
                myLinearLayout.addView(textViews[i]);
            }
            for (TextView textView : textViews) {
                textView.setTag(textViews);
                textView.setOnClickListener(new SpecificationClickListener(type, mList, helper.getAdapterPosition()));
            }
        } else {
            for (int j = 0; j < myLinearLayout.getChildCount(); j++) {
                TextView v = (TextView) myLinearLayout.getChildAt(j);
                v.setEnabled(mList.get(j).enableFlag);
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
        private Integer mPosition;
        private List<GoodsInfoResponse.InfoBean.OtherSpecBean.ValueBean> value;

        public SpecificationClickListener(Integer type, List<GoodsInfoResponse.InfoBean.OtherSpecBean.ValueBean> value, Integer position) {
            this.type = type;
            this.value = value;
            mPosition = position;
        }

        @Override
        public void onClick(View v) {
            clickEvent(type, value, mPosition, v);
        }
    }

    private void clickEvent(Integer type, List<GoodsInfoResponse.InfoBean.OtherSpecBean.ValueBean> value, Integer position, View v) {
        TextView[] textViews = (TextView[]) v.getTag();
        TextView tv = (TextView) v;

        for (int i = 0; i < textViews.length; i++) {
            if (tv.equals(textViews[i])) {
                mSkuProMap.put(type, value.get(i).id);
                mSelectProMap.put(type, value.get(i).name);
                for (int i1 = 0; i1 < mInfoBean.other_spec.size(); i1++) {
                    if (i1 != position) {
                        for (GoodsInfoResponse.InfoBean.OtherSpecBean.ValueBean valueBean : mInfoBean.other_spec.get(i1).value) {
                            for (GoodsInfoResponse.InfoBean.BindSpecBean bindSpecBean : mInfoBean.bind_spec) {
                                if (bindSpecBean.spec_id.contains(valueBean.id + "") && bindSpecBean.spec_id.contains(value.get(i).id + "")) {
                                    valueBean.enableFlag = true;
                                    break;
                                } else {
                                    valueBean.enableFlag = false;
                                }
                            }
                        }

                    }
                }
                mOtherSpecBean = mInfoBean.other_spec;
            } else {
                textViews[i].setBackgroundResource(R.drawable.selector_enable2);
            }
        }
        mDialog.setSpecificationContent(mSkuProMap, mSelectProMap, mInfoBean);
        notifyDataSetChanged();
    }
}
