package com.dispatching.feima.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.dispatching.feima.R;
import com.dispatching.feima.help.DialogFactory;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class SpecificationEmptyDialog extends BaseDialogFragment {
    public static final String TAG = SpecificationEmptyDialog.class.getSimpleName();
    @BindView(R.id.specification_empty_close)
    ImageView mSpecificationEmptyClose;
    @BindView(R.id.specification_empty_dialog_layout)
    LinearLayout mRechargeDialogLayout;


    public static SpecificationEmptyDialog newInstance() {
        return new SpecificationEmptyDialog();
    }


    private Unbinder unbind;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.specification_empty_dialog, container, true);
        unbind = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mSpecificationEmptyClose.setOnClickListener(this);
        mRechargeDialogLayout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.specification_empty_close:
                closeRechargeDialog();
                break;
            default:
                closeRechargeDialog();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbind.unbind();
    }

    private void closeRechargeDialog() {
        try {
            this.dismiss();
        } catch (Exception e) {
            DialogFactory.dismissDialogFragment(getActivity().getSupportFragmentManager(), TAG);
        }
    }
}
