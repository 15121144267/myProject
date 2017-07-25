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
    private final List<String> mSizeList = new ArrayList<>();
    private final List<String> mColorList = new ArrayList<>();
    private final List<String> mZipperList = new ArrayList<>();
    private MyLinearLayout myLinearLayout;
    private HashMap<String, String> selectProMap;

    public SpecificationAdapter(SpecificationResponse.ProductsBean productsBean, List<SpecificationResponse.ProductsBean.SpecificationListBean> specificationList, Context context, SpecificationDialog dialog, HashMap<String, String> hashMap) {
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
    }

    @Override
    protected void convert(BaseViewHolder helper, SpecificationResponse.ProductsBean.SpecificationListBean item) {
        if (item == null || mSpecificationList == null) return;

        String type = item.partName;
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

        myLinearLayout = helper.getView(R.id.adapter_specification);
        if (myLinearLayout.getChildCount() == 0) {
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
            for (int j = 0; j < textViews.length; j++) {
                textViews[j].setTag(textViews);
                textViews[j].setOnClickListener(new SpecificationClickListener(type));
            }
        }
        /**判断之前是否已选中标签*/
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

    class SpecificationClickListener implements View.OnClickListener {
        private String type;

        public SpecificationClickListener(String type) {
            this.type = type;
        }

        @Override
        public void onClick(View v) {
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
            myLinearLayout.removeAllViews();
            TextView[] textViews = (TextView[]) v.getTag();
            TextView tv = (TextView) v;
            for (int i = 0; i < textViews.length; i++) {
                if (tv.equals(textViews[i])) {
                    String specificationContent = textViews[i].getText().toString();
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
                    for (SpecificationResponse.ProductsBean.SpecificationListBean specificationListBean : mSpecificationList) {
                        if (!specificationListBean.partName.equals(type)) {
                            switch (specificationListBean.partName) {
                                case "color":
                                    specificationListBean.value = mColorList;
                                    break;
                                case "size":
                                    specificationListBean.value = mSizeList;
                                    break;
                                case "zipper":
                                    specificationListBean.value = mZipperList;
                                    break;
                            }
                        }
                    }
                    textViews[i].setBackgroundResource(R.mipmap.specification_back);
                    selectProMap.put(type, specificationContent);
                } else {
                    textViews[i].setBackgroundResource(R.drawable.selector_enable);
                }
            }
            mDialog.setSpecificationContent(selectProMap);
            notifyDataSetChanged();
        }
    }

}
