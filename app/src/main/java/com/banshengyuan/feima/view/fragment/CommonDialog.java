package com.banshengyuan.feima.view.fragment;

import android.os.Bundle;
import android.text.SpannableString;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.banshengyuan.feima.R;
import com.banshengyuan.feima.help.DialogFactory;

/**
 * 公用提示的dialog
 *
 * @author helei
 */
public class CommonDialog extends BaseDialogFragment {
    public static final String TAG = CommonDialog.class.getSimpleName();
    private RelativeLayout mLayout;
    private LinearLayout mButtonLayout;
    private Button mSureBtn;
    private Button mCancelBrn;
    private View mBtnLine;
    private View mBtnLayoutLine;
    private TextView mContentTextView;
    private TextView mTitle;
    private String content;
    private String leftBtn;
    private String rightBtn;
    private String title;
    private int position;//ListView点击事件使用
    private CommonDialogListener dialogBtnListener;
    private boolean isCanClickOutSide = false;
    private boolean isShowOkBtn = true;
    private boolean isShowCancelBtn = true;
    private boolean isCanClickBack = false;
    private int type = 0;//当一个activity 需要多次调用此dialog 需要type判断
    private boolean isShowButtonLayout = true;
    private SpannableString mContentSpannableString;

    public static CommonDialog newInstance() {
        return new CommonDialog();
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setSpannableStringContent(SpannableString contentSpannableString) {
        this.mContentSpannableString = contentSpannableString;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content, int position) {
        this.content = content;
        this.position = position;
    }

    public void setIsShowButtonLayout(boolean isShowButtonLayout) {
        this.isShowButtonLayout = isShowButtonLayout;
    }

    public void setLeftBtnText(String leftBtn) {
        this.leftBtn = leftBtn;
    }

    public void setRightBtnText(String rightBtn) {
        this.rightBtn = rightBtn;
    }

    public void setListener(CommonDialogListener dialogBtnListener, int type) {
        this.dialogBtnListener = dialogBtnListener;
        this.type = type;
    }

    public void setListener(CommonDialogListener dialogBtnListener) {
        this.dialogBtnListener = dialogBtnListener;
    }

    public void setCanClickOutSide(boolean isCanClickOutSide) {
        this.isCanClickOutSide = isCanClickOutSide;
    }

    public void setDialogCancleBtnDismiss() {
        this.isShowCancelBtn = false;
    }

    public void setDialogOkBtnDismiss() {
        this.isShowOkBtn = false;
    }

    public void isCanClickBack(boolean isCanClickBack) {
        this.isCanClickBack = isCanClickBack;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_common_dialog, container, true);

        mLayout = (RelativeLayout) view.findViewById(R.id.common_dialog_layout);
        mSureBtn = (Button) view.findViewById(R.id.common_dialog_ok);
        mCancelBrn = (Button) view.findViewById(R.id.common_dialog_cancel);
        mTitle = (TextView) view.findViewById(R.id.common_dialog_title);
        mContentTextView = (TextView) view.findViewById(R.id.common_dialog_content);
        mBtnLine = view.findViewById(R.id.common_dialog_btnLine);
        mButtonLayout = (LinearLayout) view.findViewById(R.id.common_dialog_btnLayout);
        mBtnLayoutLine = view.findViewById(R.id.common_dialog_btnLayoutLine);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.setCancelable(isCanClickBack);
        if (isShowButtonLayout) {
            mButtonLayout.setVisibility(View.VISIBLE);
            mBtnLayoutLine.setVisibility(View.VISIBLE);
        } else {
            mButtonLayout.setVisibility(View.GONE);
            mBtnLayoutLine.setVisibility(View.GONE);
        }
        if (isCanClickOutSide) {
            mLayout.setOnClickListener(this);
        }
        if (isShowCancelBtn) {
            mCancelBrn.setOnClickListener(this);
            if (!isShowOkBtn) {
                mBtnLine.setVisibility(View.GONE);
            } else {
                mBtnLine.setVisibility(View.VISIBLE);
            }
        } else {
            mSureBtn.setBackgroundResource(R.drawable.button_common_single);
            mBtnLine.setVisibility(View.GONE);
            mCancelBrn.setVisibility(View.GONE);
        }
        if (isShowOkBtn) {
            if (!isShowCancelBtn) {
                mBtnLine.setVisibility(View.GONE);
            } else {
                mBtnLine.setVisibility(View.VISIBLE);
            }
            mSureBtn.setOnClickListener(this);
        } else {
            mBtnLine.setVisibility(View.GONE);
            mSureBtn.setVisibility(View.GONE);
            mCancelBrn.setBackgroundResource(R.drawable.button_common_single);
        }


        if (!TextUtils.isEmpty(content)) {
            mContentTextView.setText(content);
        } else if (!TextUtils.isEmpty(mContentSpannableString.toString())) {
            mContentTextView.setText(mContentSpannableString);
        }
        if (!TextUtils.isEmpty(leftBtn)) {
            mCancelBrn.setText(leftBtn);
        }
        if (!TextUtils.isEmpty(rightBtn)) {
            mSureBtn.setText(rightBtn);
        }
        if (!TextUtils.isEmpty(title)) {
            mTitle.setText(title);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.common_dialog_layout:
                closeCommonDialog();
                break;
            case R.id.common_dialog_ok:
                if (dialogBtnListener != null) {
                    dialogBtnListener.commonDialogBtnOkListener(type, position);
                }
                closeCommonDialog();
                break;
            case R.id.common_dialog_cancel:
                closeCommonDialog();
                break;
        }
    }

    public interface CommonDialogListener {
        void commonDialogBtnOkListener(int type, int position);
    }

    public int getType() {
        return type;
    }

    public void closeCommonDialog() {
        try {
            this.dismiss();
        } catch (Exception e) {
            DialogFactory.dismissDialogFragment(getActivity().getSupportFragmentManager(), TAG);
        }
    }
}
