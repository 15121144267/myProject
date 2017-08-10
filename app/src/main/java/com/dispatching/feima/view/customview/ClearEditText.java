package com.dispatching.feima.view.customview;

import android.content.Context;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.dispatching.feima.R;


/**
 * EditText 后有清空按钮
 */
public class ClearEditText extends LinearLayout {

    private EditText editText;
    private LinearLayout linearFather;
    private ImageView mBtnClear;
    private boolean isInputBalance = false;
    private boolean isAlwaysShowDeleteBtn = false;
    private Context mContext;
    public setOnMyEditorActionListener mListenerInterface;
    public boolean mClearFocus;

    public void setOnMyEditorActionListener(setOnMyEditorActionListener listenerInterface, boolean clearFocus) {
        mListenerInterface = listenerInterface;
        mClearFocus = clearFocus;
    }

    public void setIsAlwaysShowDeleteBtn(boolean isAlwaysShowDeleteBtn) {
        this.isAlwaysShowDeleteBtn = isAlwaysShowDeleteBtn;
        if (mBtnClear == null) {
            return;
        }
        if (isAlwaysShowDeleteBtn) {
            mBtnClear.setVisibility(View.VISIBLE);
        } else {
            mBtnClear.setVisibility(View.GONE);
        }
    }

    public void setIsInputBalance(boolean isInputBalance) {
        this.isInputBalance = isInputBalance;
    }

    public ClearEditText(final Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_custom_edittext, this);
        editText = (EditText) findViewById(R.id.custom_edittext);
        mBtnClear = (ImageView) findViewById(R.id.custom_edittext_img);
        linearFather = (LinearLayout) findViewById(R.id.linear_father);
        if (isAlwaysShowDeleteBtn) {
            mBtnClear.setVisibility(View.VISIBLE);
        } else {
            mBtnClear.setVisibility(View.GONE);
        }
        mBtnClear.setOnClickListener(arg0 -> {
            if (!TextUtils.isEmpty(editText.getText())) {
                editText.setText("");
            }
            if (!isAlwaysShowDeleteBtn) {
                mBtnClear.setVisibility(View.GONE);
            }
            editText.requestFocus();
        });

        editText.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                if (mClearFocus) {
                    mListenerInterface.onMyTouchAction();
                    editText.clearFocus();
                }
            }
        });

        editText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                if (mListenerInterface != null) {
                    mListenerInterface.onMyEditorAction();
                }
                return true;
            }
            return false;
        });
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {

                if (isInputBalance) {
                    String temp = s.toString();
                    if (temp.length() > 1) {
                        char c1 = temp.charAt(0);
                        char c2 = temp.charAt(1);
                        if (c1 == '0' && c2 != '.') {
                            s.delete(0, 1);
                            return;
                        }
                    }
                    if (temp.contains(".")) {
                        int pos = temp.indexOf(".");
                        if (pos == 0) {
                            editText.setText("");
                            return;
                        }
                        if (temp.length() - pos - 1 > 2) {
                            s.delete(pos + 3, pos + 4);
                        }
                    }
                }
                if (isAlwaysShowDeleteBtn) {
                    mBtnClear.setVisibility(View.VISIBLE);
                } else {
                    if (!TextUtils.isEmpty(s.toString())) {
                        mBtnClear.setVisibility(View.VISIBLE);
                    } else {
                        mBtnClear.setVisibility(View.GONE);
                    }
                }

            }
        });
    }

    /**
     * 获取text
     */
    public void setEditInputPhone() {
        editText.setInputType(InputType.TYPE_CLASS_PHONE);
    }

    public void setLinearBackgroundResource(int background) {
        linearFather.setBackgroundResource(background);
    }

    public void setEditHint(String text) {
        editText.setHint(text);
    }

    public void setEditInputNum() {
        editText.setInputType(InputType.TYPE_CLASS_NUMBER);
    }

    public void setEditInputType(int type) {
        editText.setInputType(type);
    }

    public void setEditInputText() {
        editText.setInputType(InputType.TYPE_CLASS_TEXT);
//		 android:inputType="numberDecimal"
    }

    public void setEditInputSingle(boolean isSingle) {
        editText.setSingleLine(isSingle);
//		 android:inputType="numberDecimal"
    }

    /**
     * 获取text
     */
    public String getEditText() {
        return editText.getText().toString();
    }

    /**
     * 设置text Size
     */
    public void setEditTextSize(int textSize) {
        editText.setTextSize(TypedValue.COMPLEX_UNIT_DIP, textSize);
    }

    public void setEditFocus(boolean focus) {
        editText.setFocusable(focus);
    }

    /**
     * 设置text
     */
    public void setEditText(String str) {
        editText.setText(str);
        if (!TextUtils.isEmpty(str)) {
            editText.setSelection(str.length());
        }

    }

    /**
     * 设置Hinttext
     */
    public void setEditTextHint(String str) {
        editText.setHint(str);
    }

    public void setEditTextHint(int strid) {
        editText.setHint(strid);
    }

    /**
     * 设置editText的字体的颜色
     */
    public void setEditColor(int resId) {
        editText.setTextColor(resId);
    }

    /**
     * 设置editText的字体的颜色
     */
    public void setEditSize(int size) {
        editText.setTextSize(TypedValue.COMPLEX_UNIT_DIP, size);
    }

    /**
     * 设置editText Hint的字体的颜色
     */
    public void setEditHintColor(int resId) {
        editText.setHintTextColor(resId);
    }

    /**
     * 设置Edit的背景 默认为null
     */
    public void setEditBackground(int bgID) {
        editText.setBackgroundResource(bgID);
    }

    public void setEnable(boolean enable) {
        if (!enable) {
            mBtnClear.setVisibility(View.INVISIBLE);
        } else {
            mBtnClear.setVisibility(View.VISIBLE);
        }
        editText.setEnabled(enable);
        mBtnClear.setEnabled(enable);
    }

    public void setEditPadding() {
        editText.setPadding(0, 10, 10, 10);
    }

    public void setEditClearPadding() {
        editText.setPadding(0, 0, 0, 0);
    }

    public void requestFouce() {
        editText.requestFocus();
    }

    public void setEditGravity(int gravityId) {
        editText.setGravity(gravityId);
    }

    public interface setOnMyEditorActionListener {
        void onMyEditorAction();

        void onMyTouchAction();
    }
}