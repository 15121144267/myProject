package com.banshengyuan.feima.view.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.entity.GoodsInfoResponse;
import com.banshengyuan.feima.entity.SkuProductResponse;
import com.banshengyuan.feima.help.AniCreator;
import com.banshengyuan.feima.help.GlideHelper.ImageLoaderHelper;
import com.banshengyuan.feima.utils.ToastUtils;
import com.banshengyuan.feima.utils.ValueUtil;
import com.banshengyuan.feima.view.PresenterControl.GoodsDetailControl;
import com.banshengyuan.feima.view.adapter.SpecificationAdapter;

import java.util.Map;
import java.util.TreeMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class SpecificationDialog extends BaseDialogFragment {
    public static final String TAG = SpecificationDialog.class.getSimpleName();
    @BindView(R.id.dialog_person_icon)
    ImageView mDialogPersonIcon;
    @BindView(R.id.dialog_goods_price)
    TextView mDialogGoodsPrice;
    @BindView(R.id.dialog_goods_choice_specification)
    TextView mDialogGoodsChoiceSpecification;
    @BindView(R.id.dialog_goods_choice_count)
    TextView mDialogGoodsChoiceCount;
    @BindView(R.id.specification_diff_recycle_view)
    RecyclerView mSpecificationDiffRecycleView;
    @BindView(R.id.dialog_add_goods)
    Button mDialogAddGoods;
    @BindView(R.id.dialog_buy_goods)
    Button mDialogBuyGoods;
    @BindView(R.id.main_linear)
    LinearLayout mMainLinear;
    @BindView(R.id.recharge_dialog_layout)
    RelativeLayout mRechargeDialogLayout;
    @BindView(R.id.dialog_close)
    ImageView mDialogClose;


    private specificationDialogListener dialogListener;
    private Unbinder unbind;
    private ImageLoaderHelper mImageLoaderHelper;
    private GoodsInfoResponse.InfoBean mInfoBean;
    private Integer mAddOrBugFlag;
    private TreeMap<Integer, Integer> mSkuProMap;
    private TreeMap<Integer, String> mSelectProMap;
    private StringBuilder mButter = new StringBuilder();
    private GoodsDetailControl.GoodsDetailView mView;
    private SpecificationDialog mDialog;
    private SkuProductResponse.InfoBean mSkuInfoBean;
    private boolean mDoFlag = true;
    private TextView mDialogGoodsReduce;
    private TextView mDialogGoodsAdd;
    private TextView mDialogGoodsCount;
    private Integer count = 1;
    private Integer mStock;
    private String mSku;

    public static SpecificationDialog newInstance() {
        return new SpecificationDialog();
    }

    public void setListener(specificationDialogListener dialogListener) {
        this.dialogListener = dialogListener;
    }

    //初始化数据
    public void setContent(GoodsInfoResponse.InfoBean infoBean, Integer flag, ImageLoaderHelper imageLoaderHelper,
                           GoodsDetailControl.GoodsDetailView view, SpecificationDialog dialog,
                           TreeMap<Integer, Integer> skuProMap, TreeMap<Integer, String> selectProMap,
                           SkuProductResponse.InfoBean skuInfoBean, boolean doFlag) {
        mSkuProMap = skuProMap;
        mSelectProMap = selectProMap;
        mSkuInfoBean = skuInfoBean;
        mInfoBean = infoBean;
        mStock = infoBean.stock;
        mAddOrBugFlag = flag;
        mImageLoaderHelper = imageLoaderHelper;
        mView = view;
        mDialog = dialog;
        mDoFlag = doFlag;
    }

    //adapter回调数据
    public void setSpecificationContent(TreeMap<Integer, Integer> skuProMap, TreeMap<Integer, String> selectProMap, GoodsInfoResponse.InfoBean infoBean) {
        mSkuProMap = skuProMap;
        mInfoBean = infoBean;
        mSelectProMap = selectProMap;
        mDialogBuyGoods.setEnabled(false);
        mDialogAddGoods.setEnabled(false);
        setTextContent();
        if (mInfoBean.other_spec.size() == selectProMap.size()) {
            count = 1;
            mDialogGoodsCount.setText(String.valueOf(count));
            //查询PID
            mView.checkProductId(skuProMap);
        }
    }

    //sku接口回调
    public void setSkuDetail(SkuProductResponse.InfoBean infoBean) {
        mSkuInfoBean = infoBean;
        mDialogBuyGoods.setEnabled(true);
        mDialogAddGoods.setEnabled(true);
        mStock = infoBean.stock;
        mImageLoaderHelper.displayRoundedCornerImage(getActivity(), infoBean.img, mDialogPersonIcon, 4);
        mDialogGoodsPrice.setText("￥" + ValueUtil.formatAmount2(infoBean.price) + "");
        mDialogGoodsChoiceCount.setText("库存:" + infoBean.stock + "");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_specification_dialog, container, true);
        unbind = ButterKnife.bind(this, view);
        mDialogBuyGoods.setOnClickListener(this);
        mDialogAddGoods.setOnClickListener(this);
        mDialogClose.setOnClickListener(this);
        mRechargeDialogLayout.setOnClickListener(this);

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
        if (mAddOrBugFlag == 1) {
            mDialogAddGoods.setVisibility(View.VISIBLE);
        } else {
            mDialogAddGoods.setVisibility(View.GONE);
            mDialogBuyGoods.setText("确定");
        }

        if (mSkuInfoBean == null) {
            mDialogGoodsPrice.setText("￥" + ValueUtil.formatAmount2(mInfoBean.price) + "");
            mImageLoaderHelper.displayRoundedCornerImage(getActivity(), mInfoBean.top_img.get(0), mDialogPersonIcon, 6);
            mDialogGoodsChoiceCount.setText("库存:" + mInfoBean.stock + "");
        } else {
            mImageLoaderHelper.displayRoundedCornerImage(getActivity(), mSkuInfoBean.img, mDialogPersonIcon, 4);
            mDialogGoodsPrice.setText("￥" + ValueUtil.formatAmount2(mSkuInfoBean.price) + "");
            mDialogGoodsChoiceCount.setText("库存:" + mSkuInfoBean.stock + "");
        }

        if (mSelectProMap != null) {
            setTextContent();
        } else {
            mDialogGoodsChoiceSpecification.setText("请选择");
        }

        if (mInfoBean.other_spec.size() > 0) {
            if (mSelectProMap != null && mInfoBean.other_spec.size() == mSelectProMap.size() && mSkuInfoBean != null) {
                mDialogBuyGoods.setEnabled(true);
                mDialogAddGoods.setEnabled(true);
            } else {
                mDialogBuyGoods.setEnabled(false);
                mDialogAddGoods.setEnabled(false);
            }
        } else {
            mDialogBuyGoods.setEnabled(true);
            mDialogAddGoods.setEnabled(true);
        }

        if (mDoFlag) {
            for (GoodsInfoResponse.InfoBean.OtherSpecBean specBean : mInfoBean.other_spec) {
                for (GoodsInfoResponse.InfoBean.OtherSpecBean.ValueBean valueBean : specBean.value) {
                    for (GoodsInfoResponse.InfoBean.BindSpecBean bindSpecBean : mInfoBean.bind_spec) {
                        if (bindSpecBean.spec_id.contains(valueBean.id + "")) {
                            valueBean.enableFlag = true;
                            break;
                        } else {
                            valueBean.enableFlag = false;
                        }
                    }
                }
            }
            mDoFlag = false;
        }

        SpecificationAdapter mAdapter = new SpecificationAdapter(mInfoBean.other_spec, mInfoBean, getActivity(), mDialog, mSelectProMap, mSkuProMap);
        mAdapter.addFooterView(getFootView());
        mSpecificationDiffRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mSpecificationDiffRecycleView.setAdapter(mAdapter);
    }

    private View getFootView() {
        View view = getActivity().getLayoutInflater().inflate(R.layout.recycleview_foot_specification, null);
        mDialogGoodsReduce = view.findViewById(R.id.dialog_goods_reduce);
        mDialogGoodsAdd = view.findViewById(R.id.dialog_goods_add);
        mDialogGoodsCount = view.findViewById(R.id.dialog_goods_count);
        mDialogGoodsReduce.setOnClickListener(this);
        mDialogGoodsAdd.setOnClickListener(this);
        return view;
    }

    private void setTextContent() {
        mButter.append("已选:");
        for (Map.Entry<Integer, String> stringStringEntry : mSelectProMap.entrySet()) {
            mButter.append(stringStringEntry.getValue()).append(" ");
        }
        mDialogGoodsChoiceSpecification.setText(mButter.toString());
        mButter.delete(0, mButter.toString().length());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_close:
                onDismiss();
                break;
            case R.id.dialog_goods_reduce:
                if (count == 1) return;
                mDialogGoodsCount.setText(String.valueOf(--count));
                break;
            case R.id.dialog_goods_add:
                if (count.equals(mStock) || mStock <= 0) {
                    ToastUtils.showShortToast("库存不足");
                    return;
                }
                mDialogGoodsCount.setText(String.valueOf(++count));
                break;

            case R.id.dialog_buy_goods:
                if (mStock <= 0) {
                    ToastUtils.showShortToast("库存不足");
                    return;
                }
                if (mSkuInfoBean == null) {
                    mSku = mInfoBean.main_sku;
                } else {
                    mSku = mSkuInfoBean.sku;
                }
                if (mAddOrBugFlag == 3) {
                    mView.addToShoppingCard(mSku, count);
                } else {
                    dialogListener.buyButtonListener(mSkuInfoBean, count);
                }
                break;

            case R.id.dialog_add_goods:
                if (mSkuInfoBean == null) {
                    mSku = mInfoBean.main_sku;
                } else {
                    mSku = mSkuInfoBean.sku;
                }
                mView.addToShoppingCard(mSku, count);
                break;
            default:
                onDismiss();
                break;
        }
    }

    private void onDismiss() {
        if (mSkuInfoBean == null && mSelectProMap != null) {
            mView.closeSpecificationDialog(mSelectProMap, mSkuProMap, mDialogGoodsChoiceSpecification.getText().toString(), mInfoBean, mDoFlag);
        } else if (mSkuInfoBean != null) {
            mView.closeSpecificationDialog2(mSkuInfoBean, mSelectProMap, mSkuProMap, mDialogGoodsChoiceSpecification.getText().toString(), mInfoBean, mDoFlag);
        }
        closeDialog(TAG);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbind.unbind();
    }

    public interface specificationDialogListener {
        void buyButtonListener(SkuProductResponse.InfoBean sku, Integer count);
    }
}
