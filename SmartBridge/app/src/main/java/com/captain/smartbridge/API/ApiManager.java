package com.captain.smartbridge.API;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by fish on 17-6-2.
 */

public final class ApiManager {
    static String BASE_URL = "223.3.106.136:8000";
    static BridgeService mService = null;

    public static BridgeService getmService(){
        if (mService == null){
            synchronized (ApiManager.class) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                mService = retrofit.create(BridgeService.class);
            }
        }
        return mService;
    }
}
