package com.dispatching.feima.view.customview.timepickview.util;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * 获取屏幕宽高等信息、全屏切换、保持屏幕常亮、截屏等
 *
 * @author liyujiang[QQ:1032694760]
 * @since 2015/11/26
 */
public final class ScreenUtils {
    private static DisplayMetrics dm = null;

    public static DisplayMetrics displayMetrics(Context context) {
        if (null != dm) {
            return dm;
        }
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(dm);
        return dm;
    }

}
