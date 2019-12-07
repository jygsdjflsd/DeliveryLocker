package com.ysxsoft.deliverylocker.utils.cache;

import android.content.Context;
import android.os.Environment;

import java.io.Serializable;

public class ACacheHelper {

    private static ACache aCache;
    private ACacheHelper aCacheHelper;
    public static void init(Context context){
        aCache = ACache.get(context);
    }


    private static String keyPrefix(String key){
        return key ;
    }

    /**
     * 保存/获取 string信息
     * @param key
     * @param value
     */
    public static void putString(String key, String value){
        aCache.put(keyPrefix(key), value);
    }
    public static String getString(String key){
        return aCache.getAsString(keyPrefix(key));
    }
    public static String getString(String key, String def){
        String values = aCache.getAsString(keyPrefix(key));
        return values == null ? "" : values;
    }

    /**
     * 保存/获取 int信息
     * @param key
     * @param value
     */
    public static void putInt(String key, int value){
        aCache.put(keyPrefix(key), value);
    }
    public static Integer getInt(String key){
        Object object = aCache.getAsObject(keyPrefix(key));
        return object != null ? (Integer) object : 0;
    }

    /**
     * 保存/获取long信息
     * @param key
     * @param value
     */
    public static void putLong(String key, long value){
        aCache.put(keyPrefix(key), value);
    }
    public static Long getLong(String key){
        Object object = aCache.getAsObject(keyPrefix(key));
        return object != null ? (Long) object : 0;
    }

    /**
     * 保存/获取 boolean信息
     * @param key
     * @param value
     */
    public static void putBoolean(String key, boolean value){
        aCache.put(keyPrefix(key), value);
    }
    public static boolean getBoolean(String key, boolean def){
        boolean b = aCache.getAsObject(keyPrefix(key)) != null ? (boolean) aCache.getAsObject(keyPrefix(key)) : def;
        return b;
    }
    /**
     * 保存获取 objcet<T> 信息
     * @param key
     * @param s
     */
    public static void put(String key, Serializable s){
        aCache.put(keyPrefix(key), s);
    }
    public static <T> T get(String key){
        return (T) aCache.getAsObject(keyPrefix(key));
    }

    /**
     * 获取cache路径
     *
     * @param context
     * @return
     */
    public static String getDiskCachePath(Context context) {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            return context.getExternalCacheDir().getPath();
        } else {
            return context.getCacheDir().getPath();
        }
    }
}
