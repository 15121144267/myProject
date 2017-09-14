package com.banshengyuan.feima.help.GlideHelper;

import android.content.Context;
import android.view.View;

/**
 * Created by lei.he on 2017/7/10.
 * GlideInterface
 */

public interface GlideInterface<T extends View> {
    void displayImage(Context context, Object path, T imageView);
    void displayCircularImage(Context context, Object path, T imageView);
    void displayRoundedCornerImage(Context context, Object path, T imageView,Integer size);
    T createImageView(Context context);
}
