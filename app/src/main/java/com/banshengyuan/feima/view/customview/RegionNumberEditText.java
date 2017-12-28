package com.banshengyuan.feima.view.customview;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;

import com.banshengyuan.feima.utils.ToastUtils;

/**
 * Created by lei.he on 2017/12/28.
 */

public class RegionNumberEditText extends android.support.v7.widget.AppCompatEditText {
    private Context context;
    private double max;
    private double min;

    public RegionNumberEditText(Context context) {
        super(context);
        this.context = context;
    }

    public RegionNumberEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public RegionNumberEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    /**
     * 设置输入数字的范围
     *
     * @param maxNum 最大数
     * @param minNum 最小数
     */
    public void setRegion(double maxNum, double minNum) {
        this.max = maxNum;
        this.min = minNum;
    }

    public void setTextWatcher() {
        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (start >= 0) {//从一输入就开始判断，
                    if (min != -1 && max != -1) {
                        try {
                            int num = Integer.parseInt(s.toString());
                            //判断当前edittext中的数字(可能一开始Edittext中有数字)是否大于max
                            if (num > max) {
                                s = String.valueOf(max);//如果大于max，则内容为max
                                setText(s);
                                ToastUtils.showLongToast("金额不能超过消费总额");

                            } else if (num < min) {
                                s = String.valueOf(min);//如果小于min,则内容为min
                            }
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }
                        //edittext中的数字在max和min之间，则不做处理，正常显示即可。
                        return;
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
