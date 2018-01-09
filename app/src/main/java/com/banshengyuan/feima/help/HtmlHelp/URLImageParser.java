package com.banshengyuan.feima.help.HtmlHelp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

/**
 * Created by lei.he on 2017/7/17.
 * URLImageParser
 */

public class URLImageParser implements Html.ImageGetter {
    private Context mContext;
    private TextView mTextView;

    public URLImageParser(TextView t, Context c) {
        mContext = c;
        mTextView =  t;
    }

    public Drawable getDrawable(String source) {
        final URLDrawable urlDrawable = new URLDrawable(mContext);
        Glide.with(mContext).load(source).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                urlDrawable.bitmap = resource;
                urlDrawable.setHeight(resource.getHeight());
                urlDrawable.setBounds(0,0,resource.getWidth() ,resource.getHeight());
                mTextView.invalidate();
                mTextView.setText(mTextView.getText());
            }
        });
        return urlDrawable;
    }
}
