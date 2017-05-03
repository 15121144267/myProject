package com.dispatching.feima.listener;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * Created by helei on 2017/5/1.
 */

public abstract class MyTextWatchListener implements TextWatcher {
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        onMyTextChanged(s,start,before,count);
    }


    @Override
    public void afterTextChanged(Editable s) {

    }

   public abstract void onMyTextChanged(CharSequence s, int start, int before, int count) ;
}
