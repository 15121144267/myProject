package com.dispatching.feima.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.support.design.widget.TabLayout;
import android.text.TextUtils;
import android.util.Base64;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Double.parseDouble;


public class ValueUtil {

    public static final String TAG = ValueUtil.class.getSimpleName();


    //产生一个十位数的随机数
    public static long getRandom() {
        long l = 10000000000L;
        long nonce = (long) (Math.random() * l);
        if (nonce == 0) {
            getRandom();
        }
        return nonce;
    }

    //产生一个十位数的随机数
    public static int getRandom(int min, int max) {

        return min + (int) (Math.random() * (max - min + 1));
    }

    //是否是有效的手机号
    public static boolean isMobilePhone(String mobileNO) {
        if (TextUtils.isEmpty(mobileNO)) {
            return true;
        } else {
            Pattern p = Pattern.compile("^\\d{11}$");
            Matcher m = p.matcher(mobileNO);
            return !m.matches();
        }
    }

    //是否是有效的身份证
    public static boolean isValidityIdentityCard(String idCardStr) {
        if (TextUtils.isEmpty(idCardStr)) {
            return false;
        } else {
            String str = "^(\\d{6})(18|19|20)?(\\d{2})([01]\\d)([0123]\\d)(\\d{3})(\\d|X|x)?";
            Pattern p = Pattern.compile(str);
            Matcher m = p.matcher(idCardStr);
            return m.matches();
        }
    }

    //是否是有效的身份证
    public static boolean isValidityBalanceNum(String balance) {
        if (TextUtils.isEmpty(balance)) {
            return false;
        } else {
            String str = " ^(([1-9]\\d*)|0)(\\.\\d{2})?$";
            Pattern p = Pattern.compile(str);
            Matcher m = p.matcher(balance);
            return m.matches();
        }
    }

    //是否是有效的邮箱号
    public static boolean isValidityEmail(String email) {
        String

//			str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";

//	        str="^[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]@[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]\\.[a-zA-Z][a-zA-Z\\.]*[a-zA-Z]$";

                str = "^([a-zA-Z0-9]+[_|\\_|\\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\\_|\\.|-]?)*[a-zA-Z0-9]+\\.[a-zA-Z]{2,3}$";

        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);

        return m.matches();
    }

    /**
     * 包括中文数字和英文 不包括任何特殊字符
     */
    public static boolean checkSpecialString1(String str) {
        Pattern p = Pattern.compile("^[\u4e00-\u9fa5A-Za-z0-9]{4,20}+$");

        return p.matcher(str).matches();
    }

    public static boolean checkSpecialString(String str) {
        String regEx = "^[a-zA-Z][a-zA-Z0-9_]{3,19}$";
        Pattern pattern = Pattern.compile(regEx);
        return pattern.matcher(str).matches();
    }

    /**
     * 是否包含特殊字符  fasle包含   true不包含
     */
    public static boolean checkNotingSpecialString(String str) {
        if (!TextUtils.isEmpty(str)) {
            str = str.replace(" ", "");
        }
        String reg = "^[A-Za-z\\d\\u4E00-\\u9FA5\\p{P}‘’“”\\f\\n\\r]+$";
        Pattern p = Pattern.compile(reg);
        return p.matcher(str).matches();
    }

    public static String replaceBlank(String str) {
        String dest = "";
        if (str!=null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }

    /**
     * 是否包含特殊字符  fasle包含   true不包含
     */
    public static String removeSpace(String str) {
        if (TextUtils.isEmpty(str)) {
            str = "";
        }
        if (!TextUtils.isEmpty(str)) {
            str = str.replace(" ", "");
        }
        return str;
    }

    public static String getLoadingText(double nowSize, double allSize) {
        if (allSize == 0) {
            return "";
        }
        return String.format("%.2fM/%.2fM", nowSize / 1024 / 1024, allSize / 1024 / 1024);
    }

    public static String getLoadingPercent(double nowSize, double allSize) {
        if (allSize == 0) {
            return "";
        }
        return ((int) (((nowSize / 1024 / 1024) / (allSize / 1024 / 1024)) * 100)) + "%";
    }

    public static String getMoneyNum(double num) {
        return "￥" + num;
    }

    public static String getMoneyNum(String num) {
        if (TextUtils.isEmpty(num)) {
            return "￥" + 0.00;
        }
        return "￥" + num;
    }

    public static String getDoublle2(double s) {
        if (s == 0) {
            return "0.00";
        }
        return String.format("%.2f", s);
    }


    public static double getDoubleFromString(String s) {
        if (TextUtils.isEmpty(s)) {
            return 0.00;
        }
        double value = 0.00;
        try {
            value = parseDouble(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }

    public static int getIntFromString(String s) {
        if (TextUtils.isEmpty(s)) {
            return 0;
        }
        int value = 0;
        try {
            value = Integer.parseInt(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }

    public static String formatDistance(float distance) {
        double distance2 = distance / 1000;
        DecimalFormat format = new DecimalFormat("##0.0km");
        return format.format(distance2);
    }

    public static String formatAmount(double amount) {
        double newAmount = amount / 100;
        DecimalFormat format = new DecimalFormat("##0元");
        return format.format(newAmount);
    }

    public static String formatAmount2(double amount) {
        double newAmount = amount / 100;
        DecimalFormat format = new DecimalFormat("##0");
        return format.format(newAmount);
    }

    public static void setIndicator(TabLayout tabs, int leftDip, int rightDip) {
        Class<?> tabLayout = tabs.getClass();
        Field tabStrip = null;
        try {
            tabStrip = tabLayout.getDeclaredField("mTabStrip");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        assert tabStrip != null;
        tabStrip.setAccessible(true);
        LinearLayout llTab = null;
        try {
            llTab = (LinearLayout) tabStrip.get(tabs);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        int left = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, leftDip, Resources.getSystem().getDisplayMetrics());
        int right = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, rightDip, Resources.getSystem().getDisplayMetrics());

        for (int i = 0; i < (llTab != null ? llTab.getChildCount() : 0); i++) {
            View child = llTab.getChildAt(i);
            child.setPadding(0, 0, 0, 0);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
            params.leftMargin = left;
            params.rightMargin = right;
            child.setLayoutParams(params);
            child.invalidate();
        }
    }

    public static String convertIconToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();// outputstream
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, baos);
        byte[] appicon = baos.toByteArray();// 转为byte数组
        return Base64.encodeToString(appicon, Base64.DEFAULT);

    }

    public static String drawableToByte(Drawable drawable) {

        if (drawable != null) {
            Bitmap bitmap = Bitmap
                    .createBitmap(
                            drawable.getIntrinsicWidth(),
                            drawable.getIntrinsicHeight(),
                            drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                                    : Bitmap.Config.RGB_565);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
                    drawable.getIntrinsicHeight());
            drawable.draw(canvas);
            int size = bitmap.getWidth() * bitmap.getHeight() * 4;

            // 创建一个字节数组输出流,流的大小为size
            ByteArrayOutputStream baos = new ByteArrayOutputStream(size);
            // 设置位图的压缩格式，质量为100%，并放入字节数组输出流中
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            // 将字节数组输出流转化为字节数组byte[]
            byte[] imagedata = baos.toByteArray();

            String icon = Base64.encodeToString(imagedata, Base64.DEFAULT);
            return icon;
        }
        return null;
    }

    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}
