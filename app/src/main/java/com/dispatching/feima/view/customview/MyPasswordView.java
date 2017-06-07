package com.dispatching.feima.view.customview;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Selection;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dispatching.feima.R;

/**
 * Created by lei.he on 2017/5/23.
 * MyPasswordView
 */

public class MyPasswordView extends RelativeLayout {

    private EditText editText; //文本编辑框
    private Context context;

    private LinearLayout linearLayout; //文本密码的文本
    private TextView[] textViews; //文本数组

    private int pwdlength = 4; //密码长度

    private OnTextFinishListener onTextFinishListener;


    public MyPasswordView(Context context) {
        this(context, null);
    }

    public MyPasswordView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyPasswordView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    /**
     * @param bgdrawable    背景drawable
     * @param pwdlength     密码长度
     * @param pwdcolor      密码字体颜色
     * @param pwdsize       密码字体大小
     */
    public void initStyle(int bgdrawable, int pwdlength, int pwdcolor, int pwdsize) {
        this.pwdlength = pwdlength;
        initEdit(bgdrawable);
        initShowInput(bgdrawable, pwdlength, pwdcolor, pwdsize);
    }

    /**
     * 初始化编辑框
     *
     * @param bgcolor
     */
    private void initEdit(int bgcolor) {
        editText = new EditText(context);
        editText.setBackgroundResource(bgcolor);
        editText.setCursorVisible(false);
        editText.setTextSize(0);
        editText.setInputType(InputType.TYPE_CLASS_NUMBER);
        editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(pwdlength)});
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Editable etext = editText.getText();
                Selection.setSelection(etext, etext.length());
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                initDatas(s);
                if (s.length() == pwdlength) {
                    if (onTextFinishListener != null) {
                        onTextFinishListener.onFinish(s.toString().trim());
                    }
                }
            }
        });
        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        addView(editText, lp);

    }

    /**
     * @param bgcolor   背景drawable
     * @param pwdlength 密码长度
     * @param pwdcolor  密码字体颜色
     * @param pwdsize   密码字体大小
     */
    public void initShowInput(int bgcolor, int pwdlength, int pwdcolor, int pwdsize) {
        //添加密码框父布局
        linearLayout = new LinearLayout(context);
        linearLayout.setBackgroundResource(bgcolor);
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        linearLayout.setLayoutParams(layoutParams);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        addView(linearLayout);

        //添加密码框
        textViews = new TextView[pwdlength];
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT);
        params.weight = 1;
        params.gravity = Gravity.CENTER;
        params.setMargins(8, 8, 8, 8);

        for (int i = 0; i < textViews.length; i++) {
            TextView textView = new TextView(context);
            textView.setGravity(Gravity.CENTER);
            textViews[i] = textView;
            textViews[i].setTextSize(pwdsize);
            textViews[i].setTextColor(ContextCompat.getColor(context,pwdcolor));
            textViews[i].setInputType(InputType.TYPE_CLASS_NUMBER);
            textViews[i].setBackground(ContextCompat.getDrawable(context, R.drawable.shape_rectangle));
            linearLayout.addView(textView, params);

/*
            if(i < textViews.length - 1)
            {
                View view = new View(context);
                view.setBackgroundColor(context.getResources().getColor(splilinecolor));
                linearLayout.addView(view);
            }*/
        }
    }

    /**
     * 是否显示明文
     *
     * @param showPwd
     */
    public void setShowPwd(boolean showPwd) {
        for (TextView textView : textViews) {
            if (showPwd) {
                textView.setTransformationMethod(PasswordTransformationMethod.getInstance());
            } else {
                textView.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            }
        }
    }

    /**
     * 设置显示类型
     *
     * @param type
     */
    public void setInputType(int type) {
        for (TextView textView : textViews) {
            textView.setInputType(type);
        }
    }

    /**
     * 清除文本框
     */
    public void clearText() {
        editText.setText("");
        for (int i = 0; i < pwdlength; i++) {
            textViews[i].setText("");
        }
    }

    public void setOnTextFinishListener(OnTextFinishListener onTextFinishListener) {
        this.onTextFinishListener = onTextFinishListener;
    }

    /**
     * 根据输入字符，显示密码个数
     *
     * @param s
     */
    public void initDatas(Editable s) {
        if (s.length() > 0) {
            int length = s.length();
            for (int i = 0; i < pwdlength; i++) {
                if (i < length) {
                    for (int j = 0; j < length; j++) {
                        char ch = s.charAt(j);
                        textViews[j].setText(String.valueOf(ch));
                    }
                } else {
                    textViews[i].setText("");
                }
            }
        } else {
            for (int i = 0; i < pwdlength; i++) {
                textViews[i].setText("");
            }
        }
    }

    public String getPwdText() {
        if (editText != null)
            return editText.getText().toString().trim();
        return "";
    }

    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public interface OnTextFinishListener {
        void onFinish(String str);
    }

    public void setFocus() {
        editText.requestFocus();
        editText.setFocusable(true);
        showKeyBord(editText);
    }

    /**
     * 显示键盘
     *
     * @param view
     */
    public void showKeyBord(View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);

    }

}
