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
import com.banshengyuan.feima.help.AniCreator;
import com.banshengyuan.feima.help.DialogFactory;
import com.banshengyuan.feima.help.GlideHelper.ImageLoaderHelper;
import com.banshengyuan.feima.utils.ValueUtil;
import com.banshengyuan.feima.view.adapter.SpecificationAdapter;

import java.util.HashMap;

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

    public static SpecificationDialog newInstance() {
        return new SpecificationDialog();
    }

    public void setListener(specificationDialogListener dialogListener) {
        this.dialogListener = dialogListener;
    }

    public void setGoodsBean(GoodsInfoResponse.InfoBean infoBean, Integer flag,ImageLoaderHelper imageLoaderHelper) {
        mInfoBean = infoBean;
        mAddOrBugFlag = flag;
        mImageLoaderHelper = imageLoaderHelper;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_specification_dialog, container, true);
        unbind = ButterKnife.bind(this, view);
        mDialogBuyGoods.setOnClickListener(this);
        mDialogAddGoods.setOnClickListener(this);
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
        if (mAddOrBugFlag == 1) {
            mDialogAddGoods.setVisibility(View.VISIBLE);
        } else {
            mDialogAddGoods.setVisibility(View.GONE);
            mDialogBuyGoods.setText("确定");
        }
        mDialogGoodsPrice.setText(ValueUtil.formatAmount2(mInfoBean.price));
        mImageLoaderHelper.displayRoundedCornerImage(getActivity(), mInfoBean.top_img.get(0), mDialogPersonIcon, 6);

        /*if (mInfoBean.other_spec.size() > 0) {
            if (mSelectProMap != null && mProduct.specificationList.size() == mSelectProMap.size()) {
                mDialogBuyGoods.setEnabled(true);
                mDialogAddGoods.setEnabled(true);
            } else {
                mDialogBuyGoods.setEnabled(false);
                mDialogAddGoods.setEnabled(false);
            }
        } else {
            mDialogBuyGoods.setEnabled(true);
            mDialogAddGoods.setEnabled(true);
        }*/



        SpecificationAdapter mAdapter = new SpecificationAdapter(mInfoBean.other_spec, getActivity());
        mSpecificationDiffRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mSpecificationDiffRecycleView.setAdapter(mAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_close:

                closeDialog();
                break;


            case R.id.dialog_buy_goods:
              /*  if (mAddOrBugFlag == 3) {
                    mView.addToShoppingCard(count);
                } else {
                    dialogListener.buyButtonListener(mSelectProMap, count);
                }*/

                break;

            case R.id.dialog_add_goods:
//                mView.addToShoppingCard(count);
                break;
        }
    }

    private void onDismiss() {
//        mView.closeSpecificationDialog(mSelectProMap, mDialogGoodsCount.getText().toString(), mColorList, mZipperList, mSizeList);
        closeDialog();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbind.unbind();
    }

    public interface specificationDialogListener {
        void buyButtonListener(HashMap<String, String> hashMap, Integer count);
    }


    public void closeDialog() {
        try {
            this.dismiss();
        } catch (Exception e) {
            DialogFactory.dismissDialogFragment(getActivity().getSupportFragmentManager(), TAG);
        }
    }
}
