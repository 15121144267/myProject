package com.dispatching.feima.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

import com.google.gson.Gson;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * SharePreference常量类
 */

@Singleton

public class SharePreferenceUtil {
    private static final String TAG = "SharePreferenceUtil";
    public static final String projectKey = "fm_base";
    private final Context context;
    private SharedPreferences settings;
    public static String ERROR_INFO = null;

    @Inject
    public SharePreferenceUtil(final Context ctx) {
        context = ctx;
        getSharedPreferences();
    }


    private SharedPreferences getSharedPreferences() {
        return context.getSharedPreferences(projectKey, Context.MODE_PRIVATE);
    }

    public String getStringValue(String key) {
        try{
            if (settings == null) {
                settings = getSharedPreferences();
            }
            if (key != null /*&& "uid".equals(key)*/) {
                return settings.getString(key, "");
            }
            return settings.getString(key, "");
        }catch(Exception e){
            return "";
        }

    }

    public int getIntValue(String key) {
        if (settings == null) {
            settings = getSharedPreferences();
        }
        return settings.getInt(key, 0);
    }

    public int getIntValue(String key, int defaultValue) {
        if (settings == null) {
            settings = getSharedPreferences();
        }
        return settings.getInt(key, defaultValue);
    }

    public void setStringValue(String key, String value) {
        if (settings == null) {
            settings = getSharedPreferences();
        }
        Editor editor = settings.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public void setIntValue(String key, int value) {
        if (settings == null) {
            settings = getSharedPreferences();
        }
        Editor editor = settings.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public boolean getBooleanValue(String key) {
        if (settings == null) {
            settings = getSharedPreferences();
        }
        return settings.getBoolean(key, true);
    }

    public boolean getBooleanValue(String key, boolean defaultvalue) {
        if (settings == null) {
            settings = getSharedPreferences();
        }
        return settings.getBoolean(key, defaultvalue);
    }

    public void setBooleanValue(String key, boolean isFirstLogin) {
        if (settings == null) {
            settings = getSharedPreferences();
        }
        Editor editor = settings.edit();
        editor.putBoolean(key, isFirstLogin);
        editor.apply();
    }


    /**
     * SharedPreferences保存对象
     *
     * @param key
     * @param object
     */
    public void setObjectValue(String key, Object object) {
        if (settings == null)
            settings = getSharedPreferences();
        String objectBase64 = "";
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            objectBase64 = Base64.encodeToString(baos.toByteArray(),
                    Base64.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Editor editor = settings.edit();
        editor.putString(key, objectBase64);
        editor.apply();
    }

    /**
     * SharedPreferences取得对象
     *
     * @param key
     * @return
     */
    public Object getObjectValue(String key) {
        if (settings == null)
            settings = getSharedPreferences();
        Object object = null;
        try {
            String objectBase64 = settings.getString(key, "");
            if (TextUtils.isEmpty(objectBase64)) {
                throw new NullPointerException("get an empty string accroding key" + key);
            }
            byte[] base64Bytes = Base64.decode(objectBase64.getBytes(),
                    Base64.DEFAULT);
            ByteArrayInputStream bais = new ByteArrayInputStream(base64Bytes);
            ObjectInputStream ois = new ObjectInputStream(bais);
            object = ois.readObject();
        } catch (Exception e) {// 发生异常情况下清空对应缓存
            removeKey(key);
            ERROR_INFO = e.toString();
            Log.e(TAG, e.toString());
        }
        return object;
    }

    public void removeKey(String key) {
        if (settings == null) {
            settings = getSharedPreferences();
        }
        if (settings.contains(key)) {
            settings.edit().remove(key).apply();
        }

    }

    public void clearSharePreference() {
        settings.edit().clear().apply();
    }

    public Object getObjectInfo(String key, java.lang.reflect.Type type) {
        String json = get(key);
        if(json != null ){
            return new Gson().fromJson(json, type);
        }else
            return null;
    }
    public Object getObjectInfo(String key , Class<?> clz){
        String json = get(key);
        if(json != null ){
            return new Gson().fromJson(json, clz);
        }else
            return null;
    }
    public void putObjectInfo(String key, Object obj) {
        if (settings == null) {
            settings = getSharedPreferences();
        }
        String json = new Gson().toJson(obj);
        settings.edit().putString(key, json).apply();
    }

    private String get(String key) {
        if (settings == null) {
            settings = getSharedPreferences();
        }
        String json = settings.getString(key, null);
        return json;
    }

}
