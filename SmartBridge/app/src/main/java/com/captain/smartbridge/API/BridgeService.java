package com.captain.smartbridge.API;

import com.captain.smartbridge.model.LoginReq;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;

/**
 * Created by fish on 17-6-2.
 */

public interface BridgeService {

    @GET("users/{user}/repos")
    Call<ResponseBody> login(@Body LoginReq loginReq);
}
