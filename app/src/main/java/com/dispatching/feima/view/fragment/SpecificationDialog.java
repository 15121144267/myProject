package com.dispatching.feima.view.fragment;

import android.os.Bundle;
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
import com.dispatching.feima.help.DialogFactory;
import com.github.siyamed.shapeimageview.mask.PorterShapeImageView;

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
    PorterShapeImageView mDialogPersonIcon;
    @BindView(R.id.recharge_dialog_layout)
    RelativeLayout mRechargeDialogLayout;

    private passwordDialogListener dialogBtnListener;
    private Unbinder unbind;

    public static SpecificationDialog newInstance() {
        return new SpecificationDialog();
    }


    public void setListener(passwordDialogListener dialogBtnListener) {
        this.dialogBtnListener = dialogBtnListener;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_specification_dialog, container, true);
        unbind = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_close:
                closeRechargeDialog();
                break;
            case R.id.recharge_dialog_ok:

                break;
            case R.id.recharge_dialog_cancel:
                closeRechargeDialog();
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbind.unbind();
    }

    public interface passwordDialogListener {
        void passwordDialogBtnOkListener();
    }


    private void closeRechargeDialog() {
        try {
            this.dismiss();
        } catch (Exception e) {
            DialogFactory.dismissDialogFragment(getActivity().getSupportFragmentManager(), TAG);
        }
    }
}
