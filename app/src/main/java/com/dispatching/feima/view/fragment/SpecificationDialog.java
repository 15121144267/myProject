package com.dispatching.feima.view.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
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
import com.dispatching.feima.view.adapter.SpecificationAdapter;
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

    @BindView(R.id.main_linear)
    LinearLayout mMainLinear;
    @BindView(R.id.dialog_buy_goods)
    Button mDialogBuyGoods;
    @BindView(R.id.dialog_person_icon)
    ImageView mDialogPersonIcon;
    @BindView(R.id.recharge_dialog_layout)
    RelativeLayout mRechargeDialogLayout;
    @BindView(R.id.specification_diff_recycle_view)
    RecyclerView mSpecificationDiffRecycleView;

    private specificationDialogListener dialogListener;
    private Unbinder unbind;
    private final List<String> mSizeList = new ArrayList<>();
    private final List<String> mColorList = new ArrayList<>();
    private final List<String> mZipperList = new ArrayList<>();
    private SpecificationColorAdapter colorAdapter;
    private SpecificationSizeAdapter sizeAdapter;
    private ImageLoaderHelper mImageLoaderHelper;
    private ShopDetailResponse.ProductsBean mProduct;
    private boolean colorFirst = false;
    private boolean sizeFirst = false;
    private Integer count = 1;
    private String mStoreCode;
    private SpecificationAdapter mAdapter;
    private SpecificationDialog mDialog;

    public void setInstance(SpecificationDialog dialog) {
        mDialog = dialog;
    }

    public void setImageLoadHelper(ImageLoaderHelper imageLoadHelper) {
        mImageLoaderHelper = imageLoadHelper;
    }

    public void productSpecification(ShopDetailResponse.ProductsBean product) {
        mProduct = product;
    }

    public void setStoreCode(String storeCode) {
        mStoreCode = storeCode;
    }

    public void setSelectPosition(String name, Integer position, String flagName) {
        mSizeList.clear();
        mColorList.clear();
        mZipperList.clear();
        List<ShopDetailResponse.ProductsBean.ProductSpecificationBean> productSpecification = mProduct.productSpecification;
        List<ShopDetailResponse.ProductsBean.SpecificationListBean> list = mProduct.specificationList;
//        ShopDetailResponse.ProductsBean.SpecificationListBean bean  =list.get(position);
        for (int i = 0; i < list.size(); i++) {
            if (position != i) {
                ShopDetailResponse.ProductsBean.SpecificationListBean bean = list.get(i);
                if (!bean.partName.equals(flagName)) {
                    for (ShopDetailResponse.ProductsBean.ProductSpecificationBean productSpecificationBean : productSpecification) {
                        if (productSpecificationBean.zipper!=null&&productSpecificationBean.zipper.equals(name)) {
                            mSizeList.add(productSpecificationBean.size);
                            mColorList.add(productSpecificationBean.color);
                        } else if (productSpecificationBean.size!=null&&productSpecificationBean.size.equals(name)) {
                            mSizeList.add(productSpecificationBean.size);
                            mZipperList.add(productSpecificationBean.zipper);
                        } else if(productSpecificationBean.color!=null&&productSpecificationBean.color.equals(name)){
                            mSizeList.add(productSpecificationBean.size);
                            mZipperList.add(productSpecificationBean.zipper);
                        }
                    }
                    if (bean.partName.equals("color")) {
                        bean.value = mColorList;
                    } else if (bean.partName.equals("size")) {
                        bean.value = mSizeList;
                    } else if (bean.partName.equals("zipper")) {
                        bean.value = mZipperList;
                    }
                }

            }
        }
        mAdapter.setPosition(position,list);

    }


    public void setListener(specificationDialogListener dialogListener) {
        this.dialogListener = dialogListener;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_specification_dialog, container, true);
        unbind = ButterKnife.bind(this, view);
        mDialogGoodsReduce.setOnClickListener(this);
        mDialogGoodsAdd.setOnClickListener(this);
        mDialogBuyGoods.setOnClickListener(this);
        mDialogClose.setOnClickListener(this);
        AniCreator.getInstance().apply_animation_translate(mRechargeDialogLayout, AniCreator.ANIMATION_MODE_POPUP, View.VISIBLE, false, null);
        return view;
    }

    private void changeColorUI(BaseQuickAdapter adapter, View view, int position) {
        if (sizeFirst) {
            colorAdapter.setPosition(position);
        } else {
            colorFirst = true;
            mSizeList.clear();
            colorAdapter.setPosition(position);
            if (sizeAdapter != null) {
                String color = (String) adapter.getItem(position);
                sizeAdapter.setPosition(Integer.MAX_VALUE);
                List<ShopDetailResponse.ProductsBean.ProductSpecificationBean> productSpecification = mProduct.productSpecification;
                for (ShopDetailResponse.ProductsBean.ProductSpecificationBean productSpecificationBean : productSpecification) {
                    if (productSpecificationBean.color != null) {
                        if (productSpecificationBean.color.equals(color)) {
                            mSizeList.add(productSpecificationBean.size);
                        }
                    } else {
                        if (productSpecificationBean.zipper.equals(color)) {
                            mSizeList.add(productSpecificationBean.size);
                        }
                    }
                }
                sizeAdapter.setNewData(mSizeList);
            }

        }

    }

    private void changeSizeUI(BaseQuickAdapter adapter, View view, int position) {
        if (colorFirst) {
            sizeAdapter.setPosition(position);
        } else {
            sizeFirst = true;
            mColorList.clear();
            sizeAdapter.setPosition(position);
            if (colorAdapter != null) {
                String size = (String) adapter.getItem(position);
                colorAdapter.setPosition(Integer.MAX_VALUE);
                List<ShopDetailResponse.ProductsBean.ProductSpecificationBean> productSpecification = mProduct.productSpecification;
                for (ShopDetailResponse.ProductsBean.ProductSpecificationBean productSpecificationBean : productSpecification) {
                    if (productSpecificationBean.size.equals(size)) {
                        mColorList.add(productSpecificationBean.color);
                    }
                }

                colorAdapter.setNewData(mColorList);
            }

        }

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (mStoreCode.equals("107")) {
            mDialogGoodsAllCount.setText("库存1件");
        } else {
            mDialogGoodsAllCount.setText("库存2件");
        }

        mDialogGoodsPrice.setText(ValueUtil.formatAmount(mProduct.finalPrice));
        mImageLoaderHelper.displayRoundedCornerImage(getActivity(), mProduct.picture, mDialogPersonIcon, 6);
        mAdapter = new SpecificationAdapter(mProduct.specificationList, getActivity(), mDialog);
        mSpecificationDiffRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mSpecificationDiffRecycleView.setAdapter(mAdapter);
       /* if (mProduct.specificationList != null) {
            List<ShopDetailResponse.ProductsBean.SpecificationListBean> list1 = mProduct.specificationList;
            if (list1.size() > 0) {
                for (ShopDetailResponse.ProductsBean.SpecificationListBean specificationListBean : list1) {
                    if (specificationListBean.partName.equals("color")) {
                        mColorText.setText("颜色");
                        decideSpecification(specificationListBean);
                    } else if (specificationListBean.partName.equals("size")) {
                        mSizeText.setVisibility(View.VISIBLE);
                        List<String> sizeList = specificationListBean.value;
                        mDialogGoodsSize.setLayoutManager(new GridLayoutManager(getActivity(), 5));
                        sizeAdapter = new SpecificationSizeAdapter(sizeList, getActivity());
                        mDialogGoodsSize.setAdapter(sizeAdapter);
                        sizeAdapter.setOnItemClickListener(this::changeSizeUI);
                    }else {
                        mColorText.setText("规格");
                        decideSpecification(specificationListBean);
                    }
                }
            } else {
                mSpecificationLayout.setVisibility(View.INVISIBLE);
            }

        } else {
            mSpecificationLayout.setVisibility(View.INVISIBLE);
        }*/

    }
   /* private void decideSpecification(ShopDetailResponse.ProductsBean.SpecificationListBean bean){
        mColorText.setVisibility(View.VISIBLE);
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
                number = 6;
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
        mDialogGoodsColor.setLayoutManager(new GridLayoutManager(getActivity(), number));
        colorAdapter = new SpecificationColorAdapter(bean.value, getActivity());
        mDialogGoodsColor.setAdapter(colorAdapter);
        colorAdapter.setOnItemClickListener(this::changeColorUI);
    }*/

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
