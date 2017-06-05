package com.captain.smartbridge.Common;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * SharedPreferences工具类
 * Created by fish on 17-6-2.
 */

public class PreferenceUtils {

//    {
//        "content": {
//        "username": "lei123",
//                "roleType": 3,
//                "registerDate": "2017-01-20",
//                "headPortrait": null,
//                "sf": null,
//                "userId": 7,
//                "inspectionDepartmentDM": "NJ2013",
//                "phoneNumber": "12345678",
//                "cs": null,
//                "lastLoginTime": "2017-06-02 01:10:29",
//                "password": "lei123",
//                "nickname": "樊森",
//                "email": "2331598677@qq.com"
//    },
//        "code": 200
//    }

    public static class Key {
        public static final String ACCESS = "access_token";
        public static final String USER = "user_name";
        public static final String ROLE = "role_type";
        public static final String NICK = "nick_name";
        public static final String DEPART = "department";
        public static final String PHONE = "phone_number";
    }

    public static final String DEFAULT_STRING = "";
    public static final int DEFAULT_INT = 0;
    public static final boolean DEFAULT_BOOLEAN = false;
    public static final long DEFAULT_LONG = 0;
    public static final float DEFAULT_FLOAT = 0.0f;

//    public static Set<String> getStringSet(Context context, String key){
//        final SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
//        return settings.getStringSet(key, new HashSet<String>());
//    }
//
//    public static void putStringSet(Context context, final String key, final HashSet<String> value){
//        final SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
//        settings.edit().putStringSet(key, value).commit();
//    }

    public static String getString(Context context, String key) {
        final SharedPreferences settings = PreferenceManager
                .getDefaultSharedPreferences(context);
        return settings.getString(key, DEFAULT_STRING);
    }

    public static String getString(Context context, String key, String defaultvalue) {
        final SharedPreferences settings = PreferenceManager
                .getDefaultSharedPreferences(context);
        return settings.getString(key, defaultvalue);
    }

    public static void putString(Context context, final String key,
                                 final String value) {
        final SharedPreferences settings = PreferenceManager.
                getDefaultSharedPreferences(context);
        settings.edit().putString(key, value).commit();
    }

    public static boolean getBoolean(Context context, final String key) {
        final SharedPreferences settings = PreferenceManager
                .getDefaultSharedPreferences(context);
        return settings.getBoolean(key, DEFAULT_BOOLEAN);
    }

    public static boolean hasKey(Context context, final String key) {
        return PreferenceManager.getDefaultSharedPreferences(context).contains(
                key);
    }

    public static void putBoolean(Context context, final String key,
                                  final boolean value) {
        final SharedPreferences settings = PreferenceManager
                .getDefaultSharedPreferences(context);
        settings.edit().putBoolean(key, value).commit();
    }

    public static void putInt(Context context, final String key,
                              final int value) {
        final SharedPreferences settings = PreferenceManager
                .getDefaultSharedPreferences(context);
        settings.edit().putInt(key, value).commit();
    }

    public static int getInt(Context context, final String key) {
        final SharedPreferences settings = PreferenceManager
                .getDefaultSharedPreferences(context);
        return settings.getInt(key, DEFAULT_INT);
    }

    public static void putFloat(Context context, final String key,
                                final float value) {
        final SharedPreferences settings = PreferenceManager
                .getDefaultSharedPreferences(context);
        settings.edit().putFloat(key, value).commit();
    }

    public static float getFloat(Context context, final String key) {
        final SharedPreferences settings = PreferenceManager
                .getDefaultSharedPreferences(context);
        return settings.getFloat(key, DEFAULT_FLOAT);
    }

    public static void putLong(Context context, final String key,
                               final long value) {
        final SharedPreferences settings = PreferenceManager
                .getDefaultSharedPreferences(context);
        settings.edit().putLong(key, value).commit();
    }

    public static long getLong(Context context, final String key) {
        final SharedPreferences settings = PreferenceManager
                .getDefaultSharedPreferences(context);
        return settings.getLong(key, DEFAULT_LONG);
    }

    public static void clearPreference(Context context,
                                       final SharedPreferences p) {
        final SharedPreferences.Editor editor = p.edit();
        editor.clear();
        editor.commit();
    }

    public static SharedPreferences getDefaultSp(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }


}
