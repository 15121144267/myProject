package com.banshengyuan.feima.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.help.AniCreator;
import com.banshengyuan.feima.help.DialogFactory;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * 充值dialog
 */
public class PhotoChoiceDialog extends BaseDialogFragment {
    public static final String TAG = PhotoChoiceDialog.class.getSimpleName();
    @BindView(R.id.dialog_photo_take)
    Button mDialogPhotoTake;
    @BindView(R.id.dialog_photo_direct)
    Button mDialogPhotoDirect;
    @BindView(R.id.dialog_photo_close)
    Button mDialogPhotoClose;
    @BindView(R.id.recharge_dialog_layout)
    RelativeLayout mRechargeDialogLayout;

    public static PhotoChoiceDialog newInstance() {
        return new PhotoChoiceDialog();
    }

    private Unbinder unbind;
    private photoChoiceDialogListener dialogListener;


    public void setListener(photoChoiceDialogListener dialogListener) {
        this.dialogListener = dialogListener;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_photo_choice_dialog, container, true);
        unbind = ButterKnife.bind(this, view);
        AniCreator.getInstance().apply_animation_translate(mRechargeDialogLayout, AniCreator.ANIMATION_MODE_POPUP, View.VISIBLE, false, null);
        mDialogPhotoClose.setOnClickListener(this);
        mRechargeDialogLayout.setOnClickListener(this);
        mDialogPhotoDirect.setOnClickListener(this);
        mDialogPhotoTake.setOnClickListener(this);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_photo_direct:
                dialogListener.PhotoDirectListener();
                closeRechargeDialog();
                break;
            case R.id.dialog_photo_take:
                dialogListener.photoTakeListener();
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

    public interface photoChoiceDialogListener {

        void photoTakeListener();

        void PhotoDirectListener();
    }


    private void closeRechargeDialog() {
        try {
            this.dismiss();
        } catch (Exception e) {
            DialogFactory.dismissDialogFragment(getActivity().getSupportFragmentManager(), TAG);
        }
    }
}
