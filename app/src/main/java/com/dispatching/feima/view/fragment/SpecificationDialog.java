package com.dispatching.feima.view.fragment;

import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dispatching.feima.R;
import com.dispatching.feima.entity.ShopDetailResponse;
import com.dispatching.feima.help.AniCreator;
import com.dispatching.feima.help.DialogFactory;
import com.dispatching.feima.help.GlideHelper.ImageLoaderHelper;
import com.dispatching.feima.utils.ValueUtil;
import com.dispatching.feima.view.adapter.SpecificationColorAdapter;
import com.dispatching.feima.view.adapter.SpecificationSizeAdapter;
import com.example.mylibrary.adapter.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class SpecificationDialog extends BaseDialogFragment {
    public static final String TAG = SpecificationDialog.class.getSimpleName();
    @BindView(R.id.dialog_goods_price)
    TextView mDialogGoodsPrice;
    @BindView(R.id.dialog_close)
    ImageView mDialogClose;
    @BindView(R.id.dialog_goods_all_count)
    TextView mDialogGoodsAllCount;
    @BindView(R.id.dialog_goods_color_checked)
    TextView mDialogGoodsColorChecked;
    @BindView(R.id.dialog_goods_reduce)
    TextView mDialogGoodsReduce;
    @BindView(R.id.dialog_goods_count)
    TextView mDialogGoodsCount;
    @BindView(R.id.dialog_goods_add)
    TextView mDialogGoodsAdd;
    @BindView(R.id.dialog_goods_color)
    RecyclerView mDialogGoodsColor;
    @BindView(R.id.dialog_goods_size)
    RecyclerView mDialogGoodsSize;
    @BindView(R.id.main_linear)
    LinearLayout mMainLinear;
    @BindView(R.id.dialog_buy_goods)
    Button mDialogBuyGoods;
    @BindView(R.id.dialog_person_icon)
    ImageView mDialogPersonIcon;
    @BindView(R.id.recharge_dialog_layout)
    RelativeLayout mRechargeDialogLayout;
    @BindView(R.id.specification_layout)
    NestedScrollView mSpecificationLayout;

    private specificationDialogListener dialogListener;
    private Unbinder unbind;
    private final List<String> sizeList = new ArrayList<>();
    private final List<String> colorList = new ArrayList<>();
    private SpecificationColorAdapter colorAdapter;
    private SpecificationSizeAdapter sizeAdapter;
    private ImageLoaderHelper mImageLoaderHelper;
    private ShopDetailResponse.ProductsBean mProduct;
    private boolean colorFirst = false;
    private boolean sizeFirst = false;
    private Integer count = 1;

    public static SpecificationDialog newInstance() {
        return new SpecificationDialog();
    }

    public void setImageLoadHelper(ImageLoaderHelper imageLoadHelper) {
        mImageLoaderHelper = imageLoadHelper;
    }

    public void productSpecifition(ShopDetailResponse.ProductsBean product) {
        mProduct = product;
    }


    public void setListener(specificationDialogListener dialogListener) {
        this.dialogListener = dialogListener;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_specification_dialog, container, true);
        unbind = ButterKnife.bind(this, view);
        mDialogGoodsColor.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        colorAdapter = new SpecificationColorAdapter(null, getActivity());
        mDialogGoodsColor.setAdapter(colorAdapter);
        mDialogGoodsSize.setLayoutManager(new GridLayoutManager(getActivity(), 5));
        sizeAdapter = new SpecificationSizeAdapter(null, getActivity());
        mDialogGoodsSize.setAdapter(sizeAdapter);
        mDialogGoodsReduce.setOnClickListener(this);
        mDialogGoodsAdd.setOnClickListener(this);
        mDialogBuyGoods.setOnClickListener(this);
        mDialogClose.setOnClickListener(this);
        AniCreator.getInstance().apply_animation_translate(mRechargeDialogLayout, AniCreator.ANIMATION_MODE_POPUP, View.VISIBLE, false, null);
        colorAdapter.setOnItemClickListener(this::changeColorUI);
        sizeAdapter.setOnItemClickListener(this::changeSizeUI);
        return view;
    }

    private void changeColorUI(BaseQuickAdapter adapter, View view, int position) {
        if (sizeFirst) {
            colorAdapter.setPosition(position);
        } else {
            colorFirst = true;
            sizeList.clear();
            colorAdapter.setPosition(position);
            String color = (String) adapter.getItem(position);
            sizeAdapter.setPosition(Integer.MAX_VALUE);
            List<ShopDetailResponse.ProductsBean.ProductSpecificationBean> productSpecification = mProduct.productSpecification;
            for (ShopDetailResponse.ProductsBean.ProductSpecificationBean productSpecificationBean : productSpecification) {
                if (productSpecificationBean.color.equals(color)) {
                    sizeList.add(productSpecificationBean.size);
                }
            }
            sizeAdapter.setNewData(sizeList);
        }

    }


    private void changeSizeUI(BaseQuickAdapter adapter, View view, int position) {
        if (colorFirst) {
            sizeAdapter.setPosition(position);
        } else {
            sizeFirst = true;
            colorList.clear();
            String size = (String) adapter.getItem(position);
            sizeAdapter.setPosition(position);
            colorAdapter.setPosition(Integer.MAX_VALUE);
            List<ShopDetailResponse.ProductsBean.ProductSpecificationBean> productSpecification = mProduct.productSpecification;
            for (ShopDetailResponse.ProductsBean.ProductSpecificationBean productSpecificationBean : productSpecification) {
                if (productSpecificationBean.size.equals(size)) {
                    colorList.add(productSpecificationBean.color);
                }
            }
            colorAdapter.setNewData(colorList);
        }

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mDialogGoodsPrice.setText(ValueUtil.formatAmount(mProduct.finalPrice));
        mImageLoaderHelper.displayRoundedCornerImage(getActivity(), mProduct.picture, mDialogPersonIcon, 6);
        if (mProduct.specificationList != null) {
            List<ShopDetailResponse.ProductsBean.SpecificationListBean> list1 = mProduct.specificationList;
            if (list1.size() > 0) {
                for (ShopDetailResponse.ProductsBean.SpecificationListBean specificationListBean : list1) {
                    if (specificationListBean.partName.equals("color")) {
                        List<String> colorList = specificationListBean.value;
                        colorList.addAll(sizeList);
                        colorAdapter.setNewData(colorList);
                    }
                    if (specificationListBean.partName.equals("size")) {
                        List<String> sizeList = specificationListBean.value;
                        sizeList.addAll(colorList);
                        sizeAdapter.setNewData(sizeList);

                    }
                }
            } else {
                mSpecificationLayout.setVisibility(View.INVISIBLE);
            }

        }else {
            mSpecificationLayout.setVisibility(View.INVISIBLE);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_close:
                closeRechargeDialog();
                break;
            case R.id.dialog_goods_reduce:
//                dialogListener.reduceCountListener();
                if (count == 0) return;
                mDialogGoodsCount.setText(--count + "");
                break;
            case R.id.dialog_goods_add:
//                dialogListener.addCountListener();
                mDialogGoodsCount.setText(++count + "");
                break;

            case R.id.dialog_buy_goods:
                dialogListener.buyButtonListener();
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbind.unbind();
    }

    public interface specificationDialogListener {
        void reduceCountListener();

        void addCountListener();

        void buyButtonListener();
    }


    private void closeRechargeDialog() {
        try {
            this.dismiss();
        } catch (Exception e) {
            DialogFactory.dismissDialogFragment(getActivity().getSupportFragmentManager(), TAG);
        }
    }
}
