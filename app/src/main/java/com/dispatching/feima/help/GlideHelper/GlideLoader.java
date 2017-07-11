package com.dispatching.feima.help.GlideHelper;

import android.content.Context;
import android.widget.ImageView;

/**
 * Created by lei.he on 2017/7/10.
 */

public abstract class GlideLoader implements GlideInterface<ImageView> {
    @Override
    public ImageView createImageView(Context context) {
        ImageView imageView = new ImageView(context);
        return imageView;
    }
}
