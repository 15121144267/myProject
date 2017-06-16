package com.dispatching.feima.view.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dispatching.feima.R;
import com.dispatching.feima.help.DialogFactory;
import com.dispatching.feima.utils.ToastUtils;
import com.dispatching.feima.view.customview.MyPasswordView;


/**
 * 充值dialog
 *
 */
public class PasswordDialog extends BaseDialogFragment {
    public static final String TAG = PasswordDialog.class.getSimpleName();
    private RelativeLayout mLayout;
    private Button mSureBtn;
    private Button mCancelBtn;
    private TextView mContentTextView;
    private MyPasswordView mEditTextLayout;
    private TextView mTitle;
    private String content;
    private String title;
    private passwordDialogListener dialogBtnListener;
    private boolean isCanClickOutSide = false;

    public static PasswordDialog newInstance() {
        return new PasswordDialog();
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setListener(passwordDialogListener dialogBtnListener) {
        this.dialogBtnListener = dialogBtnListener;
    }

    public void setCanClickOutSide(boolean isCanClickOutSide) {
        this.isCanClickOutSide = isCanClickOutSide;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_recharge_dialog, container, true);

        mLayout = (RelativeLayout) view.findViewById(R.id.recharge_dialog_layout);
        mSureBtn = (Button) view.findViewById(R.id.recharge_dialog_ok);
        mCancelBtn = (Button) view.findViewById(R.id.recharge_dialog_cancel);
        mTitle = (TextView) view.findViewById(R.id.recharge_dialog_title);
        mContentTextView = (TextView) view.findViewById(R.id.recharge_dialog_content);
        mEditTextLayout = (MyPasswordView) view.findViewById(R.id.password_view);
        mEditTextLayout.initStyle(R.color.white, 4, R.color.black, 30);
        mEditTextLayout.setShowPwd(false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mContentTextView.setGravity(Gravity.NO_GRAVITY);
        if (isCanClickOutSide) {
            mLayout.setOnClickListener(this);
        }

        mSureBtn.setOnClickListener(this);
        mCancelBtn.setOnClickListener(this);
        if (TextUtils.isEmpty(content)) {
            mContentTextView.setVisibility(View.GONE);
        } else {
            mContentTextView.setVisibility(View.VISIBLE);
            String orderId = "订单号：" + content;
            mContentTextView.setText(orderId);
        }
        if (!TextUtils.isEmpty(title)) {
            mTitle.setText(title);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.recharge_dialog_layout:
                closeRechargeDialog();
                break;
            case R.id.recharge_dialog_ok:
                String number = mEditTextLayout.getPwdText();
                if (number.length() < 4) {
                    ToastUtils.showShortToast("请填写完成");
                    return;
                }

                String checkNumber = content.substring(content.length() - 4, content.length());
                if (checkNumber.equals(number) && dialogBtnListener != null) {
                    dialogBtnListener.passwordDialogBtnOkListener();
                    closeRechargeDialog();
                } else {
                    ToastUtils.showShortToast("请核对单号是否正确");
                }

                break;
            case R.id.recharge_dialog_cancel:
                closeRechargeDialog();
                break;
        }
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
