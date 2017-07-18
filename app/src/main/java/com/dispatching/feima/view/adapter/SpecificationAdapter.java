package com.dispatching.feima.view.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.dispatching.feima.R;
import com.dispatching.feima.entity.ShopDetailResponse;
import com.dispatching.feima.view.fragment.SpecificationDialog;
import com.example.mylibrary.adapter.BaseQuickAdapter;
import com.example.mylibrary.adapter.BaseViewHolder;

import java.util.List;


public class SpecificationAdapter extends BaseQuickAdapter<ShopDetailResponse.ProductsBean.SpecificationListBean, BaseViewHolder> {
    private final Context mContext;
    private Integer mPosition = Integer.MAX_VALUE;
    private SpecificationDialog mDialog;
    private List<ShopDetailResponse.ProductsBean.SpecificationListBean> mSpecificationList;

    public void setPosition(Integer position) {
        mPosition = position;
        notifyDataSetChanged();
    }

    public SpecificationAdapter(List<ShopDetailResponse.ProductsBean.SpecificationListBean> specificationList, Context context, SpecificationDialog dialog) {
        super(R.layout.adapter_specifiaction, specificationList);
        mContext = context;
        mDialog = dialog;
        mSpecificationList = specificationList;
    }

    @Override
    protected void convert(BaseViewHolder helper, ShopDetailResponse.ProductsBean.SpecificationListBean item) {
        if (item == null || mSpecificationList == null) return;
        if (helper.getAdapterPosition() == mSpecificationList.size() - 1) {
            helper.setVisible(R.id.specification_line, false);
        }
        helper.setText(R.id.adapter_specification_name, item.partName);
        RecyclerView recyclerView = helper.getView(R.id.adapter_specification);
        decideSpecification(helper, item, recyclerView);
    }

    private void decideSpecification(BaseViewHolder helper, ShopDetailResponse.ProductsBean.SpecificationListBean bean, RecyclerView recyclerView) {
        String maxText = "";
        for (String s : bean.value) {
            if (s.length() > maxText.length()) {
                maxText = s;
            }
        }
        int number;
        switch (maxText.length()) {
            case 1:
            case 2:
                number = 7;
                break;
            case 3:
                number = 5;
                break;
            case 4:
                number = 4;
                break;
            case 5:
            case 6:
                number = 3;
                break;
            case 7:
            case 8:
                number = 2;
                break;
            default:
                number = 1;
        }
        recyclerView.setLayoutManager(new GridLayoutManager(mContext, number));
        SpecificationColorAdapter specificationColorAdapter = new SpecificationColorAdapter(bean.value, mContext);
        recyclerView.setAdapter(specificationColorAdapter);
        specificationColorAdapter.setOnItemClickListener((adapter, view, position2) -> {
            specificationColorAdapter.setPosition(position2);
            String name = (String) adapter.getItem(position2);
            mDialog.setSelectPosition(name, helper.getAdapterPosition(),bean.partName);
        });
    }
}
