package com.dispatching.feima.view.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dispatching.feima.R;
import com.dispatching.feima.entity.SpecificationResponse;
import com.dispatching.feima.help.AniCreator;
import com.dispatching.feima.help.DialogFactory;
import com.dispatching.feima.help.GlideHelper.ImageLoaderHelper;
import com.dispatching.feima.utils.ToastUtils;
import com.dispatching.feima.utils.ValueUtil;
import com.dispatching.feima.view.PresenterControl.GoodsDetailControl;
import com.dispatching.feima.view.adapter.SpecificationAdapter;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.dispatching.feima.R.id.dialog_buy_goods;


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
    @BindView(dialog_buy_goods)
    Button mDialogBuyGoods;
    @BindView(R.id.dialog_person_icon)
    ImageView mDialogPersonIcon;
    @BindView(R.id.recharge_dialog_layout)
    RelativeLayout mRechargeDialogLayout;
    @BindView(R.id.specification_diff_recycle_view)
    RecyclerView mSpecificationDiffRecycleView;

    private specificationDialogListener dialogListener;
    private Unbinder unbind;
    private ImageLoaderHelper mImageLoaderHelper;
    private SpecificationResponse.ProductsBean mProduct;
    private Integer count = 1;
    private String mStoreCode;
    private String mTextContent;
    private SpecificationAdapter mAdapter;
    private SpecificationDialog mDialog;
    private HashMap<String, String> mSelectProMap;
    private StringBuilder mButter = new StringBuilder();
    private GoodsDetailControl.GoodsDetailView mView;

    public void setInstance(SpecificationDialog dialog) {
        mDialog = dialog;

    }

    public void setGoodsView(GoodsDetailControl.GoodsDetailView view) {
        mView = view;
    }

    public void setSpecificationHashMap(HashMap<String, String> hashMap) {
        mSelectProMap = hashMap;
    }

    public void setTextContent(String textContent) {
        mTextContent = textContent;
    }

    public void setImageLoadHelper(ImageLoaderHelper imageLoadHelper) {
        mImageLoaderHelper = imageLoadHelper;
    }

    public void productSpecification(SpecificationResponse.ProductsBean product) {
        mProduct = product;
    }

    public void setStoreCode(String storeCode) {
        mStoreCode = storeCode;
    }


    public void setListener(specificationDialogListener dialogListener) {
        this.dialogListener = dialogListener;
    }

    public void setSpecificationContent(HashMap<String, String> selectProMap) {
        mSelectProMap = selectProMap;
        for (Map.Entry<String, String> stringStringEntry : selectProMap.entrySet()) {
            switch (stringStringEntry.getKey()) {
                case "color":
                    mButter.append("颜色:" + stringStringEntry.getValue() + " ");
                    break;
                case "size":
                    mButter.append("尺寸:" + stringStringEntry.getValue() + " ");
                    break;
                case "zipper":
                    mButter.append("有无拉链:" + stringStringEntry.getValue() + " ");
                    break;
            }
        }
        if (mProduct.specificationList.size() == selectProMap.size()) {
            mDialogBuyGoods.setEnabled(true);
        } else {
            mDialogBuyGoods.setEnabled(false);
        }
        mDialogGoodsColorChecked.setText(mButter.toString());
        mButter.delete(0, mButter.toString().length());
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
        this.getDialog().setOnKeyListener((DialogInterface arg0, int keyCode, KeyEvent arg2) -> {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                onDismiss();
                return true;
            }
            return false;
        });
        AniCreator.getInstance().apply_animation_translate(mRechargeDialogLayout, AniCreator.ANIMATION_MODE_POPUP, View.VISIBLE, false, null);
        return view;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (mStoreCode.equals("107")) {
            mDialogGoodsAllCount.setText("库存1件");
        } else {
            mDialogGoodsAllCount.setText("库存2件");
        }
        if (!TextUtils.isEmpty(mTextContent)) {
            mDialogGoodsColorChecked.setText(mTextContent);
        }
        if (mSelectProMap != null) {
            if (mProduct.specificationList.size() == mSelectProMap.size()) {
                mDialogBuyGoods.setEnabled(true);
            } else {
                mDialogBuyGoods.setEnabled(false);
            }
        }

        mDialogGoodsPrice.setText(ValueUtil.formatAmount(mProduct.finalPrice));
        mImageLoaderHelper.displayRoundedCornerImage(getActivity(), mProduct.picture, mDialogPersonIcon, 6);
        mAdapter = new SpecificationAdapter(mProduct, mProduct.specificationList, getActivity(), mDialog, mSelectProMap);
        mSpecificationDiffRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mSpecificationDiffRecycleView.setAdapter(mAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_close:
                if (mSelectProMap != null) {
                    mView.closeSpecificationDialog(mSelectProMap);
                }
                closeRechargeDialog();
                break;
            case R.id.dialog_goods_reduce:
//                dialogListener.reduceCountListener();
                if (count == 1) return;
                mDialogGoodsCount.setText(--count + "");
                break;
            case R.id.dialog_goods_add:
//                dialogListener.addCountListener();
                if(count.equals(Integer.valueOf(mDialogGoodsCount.getText().toString()))){
                    ToastUtils.showShortToast("数量超出范围");
                    return;
                }
                mDialogGoodsCount.setText(++count + "");
                break;

            case dialog_buy_goods:

                dialogListener.buyButtonListener(mSelectProMap,count);
                break;
        }
    }

    private void onDismiss() {
        if (mSelectProMap != null) {
            mView.closeSpecificationDialog(mSelectProMap);
        }
        closeRechargeDialog();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbind.unbind();
    }

    public interface specificationDialogListener {
        void reduceCountListener();

        void addCountListener();

        void buyButtonListener(HashMap<String,String> hashMap,Integer count);

    }


    private void closeRechargeDialog() {
        try {
            this.dismiss();
        } catch (Exception e) {
            DialogFactory.dismissDialogFragment(getActivity().getSupportFragmentManager(), TAG);
        }
    }
}
