package com.captain.smartbridge.API;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by fish on 17-6-2.
 */

public final class ApiManager {
    static String BASE_URL = "http://223.3.103.8:8888/";
    static BridgeService mService = null;


    public static BridgeService getmService() {
        if (mService == null) {
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(new ReadCookiesInterceptor())
                    .addInterceptor(new SaveCookiesInterceptor()).build();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            mService = retrofit.create(BridgeService.class);
        }
        return mService;
    }

    public static void clear() {
        mService = null;
    }
}
