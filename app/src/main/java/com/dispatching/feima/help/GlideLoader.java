package com.dispatching.feima.help;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.dispatching.feima.R;
import com.youth.banner.loader.ImageLoader;

/**
 * Created by lei.he on 2017/7/3.
 * GlideLoader
 */

public class GlideLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        Glide.with(context).load(path).error(R.mipmap.freemud_logo).into(imageView);
    }

}
