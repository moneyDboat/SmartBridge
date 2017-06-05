package com.captain.smartbridge.API;

import android.util.Log;

import com.captain.smartbridge.Common.PreferenceUtils;
import com.captain.smartbridge.UI.Activity.BaseApplication;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by fish on 17-6-5.
 */

public class ReadCookiesInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();
//        HashSet<String> preferences = (HashSet<String>) PreferenceUtils.getStringSet(BaseApplication.getIntance(), PreferenceUtils.Key.ACCESS);
//        for (String cookie : preferences) {
//            builder.addHeader("Cookie", cookie);
//            Log.v("OkHttp", "Adding Header: " + cookie); // This is done so I know which headers are being added; this interceptor is used after the normal logging of OkHttp
//        }
        String cookie = PreferenceUtils.getString(BaseApplication.getIntance(), PreferenceUtils.Key.ACCESS);
        builder.addHeader("Cookie", cookie);
        Log.i("header", cookie);

        return chain.proceed(builder.build());
    }
}