package com.captain.smartbridge.API;

import android.util.Log;

import com.captain.smartbridge.Common.PreferenceUtils;
import com.captain.smartbridge.UI.Activity.BaseApplication;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * Created by fish on 17-6-5.
 */

public class SaveCookiesInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());

        if (!originalResponse.headers("Set-Cookie").isEmpty()) {
            String raw = originalResponse.header("Set-Cookie").toString();
            String cookie = raw.split("\"")[0] + "\"" + raw.split("\"")[1] + "\"";
            Log.i("cookie", cookie);

            PreferenceUtils.putString(BaseApplication.getIntance(), PreferenceUtils.Key.ACCESS,
                    cookie);
        }

        return originalResponse;
    }
}
