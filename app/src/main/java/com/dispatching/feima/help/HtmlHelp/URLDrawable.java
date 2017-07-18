package com.dispatching.feima.help.HtmlHelp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;

/**
 * Created by lei.he on 2017/7/17.
 */

public class URLDrawable  extends BitmapDrawable {
    protected Bitmap bitmap;
    private Context mContext;
    private float height;
    public URLDrawable(Context context){
        mContext= context;
    }
    public void setHeight(float height){
        this.height = height;
    }
    @Override
    public void draw(Canvas canvas) {
        if (bitmap != null) {
            RectF rectF = new RectF(0,0,mContext.getResources().getDisplayMetrics().widthPixels,height);
            canvas.drawBitmap(bitmap, null, rectF, getPaint());
        }
    }
}
