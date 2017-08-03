package com.dispatching.feima.view.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.dispatching.feima.R;
import com.dispatching.feima.entity.SpecificationResponse;
import com.dispatching.feima.view.customview.MyLinearLayout;
import com.dispatching.feima.view.fragment.SpecificationDialog;
import com.example.mylibrary.adapter.BaseQuickAdapter;
import com.example.mylibrary.adapter.BaseViewHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class SpecificationAdapter extends BaseQuickAdapter<SpecificationResponse.ProductsBean.SpecificationListBean, BaseViewHolder> {
    private final Context mContext;
    private SpecificationDialog mDialog;
    private List<SpecificationResponse.ProductsBean.SpecificationListBean> mSpecificationList;
    private SpecificationResponse.ProductsBean mProduct;
    private List<String> mSizeList;
    private List<String> mColorList;
    private List<String> mZipperList;
    private MyLinearLayout myLinearLayout;
    private HashMap<String, String> selectProMap;

    public SpecificationAdapter(SpecificationResponse.ProductsBean productsBean,
                                List<SpecificationResponse.ProductsBean.SpecificationListBean> specificationList,
                                Context context, SpecificationDialog dialog, HashMap<String, String> hashMap, List<String> sizeList,
                                List<String> colorList, List<String> zipperList) {
        super(R.layout.adapter_specifiaction, specificationList);
        mProduct = productsBean;
        mContext = context;
        mDialog = dialog;
        mSpecificationList = specificationList;
        if (hashMap == null) {
            selectProMap = new HashMap<>();
        } else {
            selectProMap = hashMap;
        }
        if (sizeList != null && sizeList.size() > 0) {
            mSizeList = sizeList;
        }
        if (colorList != null && colorList.size() > 0) {
            mColorList = colorList;
        }
        if (zipperList != null && zipperList.size() > 0) {
            mZipperList = zipperList;
        }
    }

    @Override
    protected void convert(BaseViewHolder helper, SpecificationResponse.ProductsBean.SpecificationListBean item) {
        if (item == null || mSpecificationList == null) return;
        String type = item.partName;
        myLinearLayout = helper.getView(R.id.adapter_specification);
        if (myLinearLayout.getChildCount() == 0) {
            switch (type) {
                case "color":
                    helper.setText(R.id.adapter_specification_name, "颜色");

                    break;
                case "size":
                    helper.setText(R.id.adapter_specification_name, "尺寸");
                    break;
                case "zipper":
                    helper.setText(R.id.adapter_specification_name, "有无拉链");
                    break;
            }

            List<String> mList = item.value;
            TextView[] textViews = new TextView[mList.size()];
            for (int i = 0; i < mList.size(); i++) {
                TextView textView = new TextView(mContext);
                textView.setGravity(17);
                textView.setPadding(25, 15, 25, 15);
                textViews[i] = textView;
                textViews[i].setBackgroundResource(R.drawable.selector_enable);
                textViews[i].setText(mList.get(i));
                textViews[i].setTag(i);
                myLinearLayout.addView(textViews[i]);
            }
            for (TextView textView : textViews) {
                textView.setTag(textViews);
                textView.setOnClickListener(new SpecificationClickListener(type));
            }

            switch (type) {
                case "color":
                    checkEnable2(mColorList);
                    break;
                case "size":
                    checkEnable2(mSizeList);
                    break;
                case "zipper":
                    checkEnable2(mZipperList);
                    break;
            }


        } else {
            switch (type) {
                case "color":
                    if (mColorList.size() > 0) {
                        checkEnable(mColorList);
                    }
                    break;
                case "size":
                    if (mSizeList.size() > 0) {
                        checkEnable(mSizeList);
                    }
                    break;
                case "zipper":
                    if (mZipperList.size() > 0) {
                        checkEnable(mZipperList);
                    }
                    break;
            }
        }

//        判断之前是否已选中标签
        if (selectProMap.get(type) != null) {
            for (int j = 0; j < myLinearLayout.getChildCount(); j++) {
                TextView v = (TextView) myLinearLayout.getChildAt(j);
                if (selectProMap.get(type).equals(v.getText().toString())) {
                    v.setBackgroundResource(R.mipmap.specification_back);
                    selectProMap.put(type, v.getText().toString());
                }
            }

        }
    }

    private void checkEnable(List<String> list) {
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
    }

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
        if (mZipperList == null) {
            mZipperList = new ArrayList<>();
        }
        if (mColorList == null) {
            mColorList = new ArrayList<>();
        }
        if (mSizeList == null) {
            mSizeList = new ArrayList<>();
        }
        switch (type) {
            case "color":
                mSizeList.clear();
                mZipperList.clear();
                break;
            case "size":
                mZipperList.clear();
                mColorList.clear();
                break;
            case "zipper":
                mSizeList.clear();
                mColorList.clear();
                break;
        }
        TextView[] textViews = (TextView[]) v.getTag();
        TextView tv = (TextView) v;
        for (TextView textView : textViews) {
            if (tv.equals(textView)) {
                String specificationContent = textView.getText().toString();
                List<SpecificationResponse.ProductsBean.ProductSpecificationBean> productSpecificationList = mProduct.productSpecification;
                for (SpecificationResponse.ProductsBean.ProductSpecificationBean productSpecificationBean : productSpecificationList) {
                    if (productSpecificationBean.zipper != null && productSpecificationBean.zipper.equals(specificationContent)) {
                        mSizeList.add(productSpecificationBean.size);
                        mColorList.add(productSpecificationBean.color);
                    } else if (productSpecificationBean.size != null && productSpecificationBean.size.equals(specificationContent)) {
                        mZipperList.add(productSpecificationBean.zipper);
                        mColorList.add(productSpecificationBean.color);
                    } else if (productSpecificationBean.color != null && productSpecificationBean.color.equals(specificationContent)) {
                        mSizeList.add(productSpecificationBean.size);
                        mZipperList.add(productSpecificationBean.zipper);
                    }
                }
                selectProMap.put(type, specificationContent);
            } else {
                textView.setBackgroundResource(R.drawable.selector_enable);
            }
        }
        mDialog.setSpecificationContent(selectProMap, mColorList, mZipperList, mSizeList);
        notifyDataSetChanged();
    }
}
