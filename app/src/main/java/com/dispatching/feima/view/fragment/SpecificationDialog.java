package com.dispatching.feima.view.fragment;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dispatching.feima.R;
import com.dispatching.feima.help.DialogFactory;
import com.dispatching.feima.help.GlideHelper.ImageLoaderHelper;
import com.dispatching.feima.view.adapter.SpecificationAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * 充值dialog
 */
public class SpecificationDialog extends BaseDialogFragment {
    public static final String TAG = SpecificationDialog.class.getSimpleName();
    @BindView(R.id.dialog_goods_price)
    TextView mDialogGoodsPrice;
    @BindView(R.id.dialog_close)
    ImageButton mDialogClose;
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

    private specificationDialogListener dialogListener;
    private Unbinder unbind;
    private final List<String> sizeList = new ArrayList<>();
    private final String[] mColorStrings = {"黑色", "花色", "花色", "图片色","黑色", "花色", "花色", "图片色"};
    private final List<String> colorList = new ArrayList<>();
    private final String[] mSizeStrings = {"S", "M", "L", "XL", "均码", "M", "L", "XL", "均码"};
    private SpecificationAdapter colorAdapter;
    private SpecificationAdapter sizeAdapter;
    private ImageLoaderHelper mImageLoaderHelper;
    public static SpecificationDialog newInstance() {
        return new SpecificationDialog();
    }

    public void setImageLoadHelper(ImageLoaderHelper imageLoadHelper) {
        mImageLoaderHelper = imageLoadHelper;
    }


    public void setListener(specificationDialogListener dialogListener) {
        this.dialogListener = dialogListener;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_specification_dialog, container, true);
        unbind = ButterKnife.bind(this, view);
        mImageLoaderHelper.displayRoundedCornerImage(getActivity(),R.mipmap.neo,mDialogPersonIcon,6);
        mDialogGoodsColor.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        colorAdapter = new SpecificationAdapter(null, getActivity());
        mDialogGoodsColor.setAdapter(colorAdapter);
        mDialogGoodsSize.setLayoutManager(new GridLayoutManager(getActivity(), 5));
        sizeAdapter = new SpecificationAdapter(null, getActivity());
        mDialogGoodsSize.setAdapter(sizeAdapter);
        mDialogGoodsReduce.setOnClickListener(this);
        mDialogGoodsAdd.setOnClickListener(this);
        mDialogBuyGoods.setOnClickListener(this);
        mDialogClose.setOnClickListener(this);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Collections.addAll(sizeList, mSizeStrings);
        sizeAdapter.setNewData(sizeList);
        Collections.addAll(colorList, mColorStrings);
        colorAdapter.setNewData(colorList);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_close:
                closeRechargeDialog();
                break;
            case R.id.dialog_goods_reduce:
                dialogListener.reduceCountListener();
                break;
            case R.id.dialog_goods_add:
                dialogListener.addCountListener();
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
