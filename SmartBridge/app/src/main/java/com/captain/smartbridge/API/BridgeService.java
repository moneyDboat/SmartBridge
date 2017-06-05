package com.captain.smartbridge.API;

import com.captain.smartbridge.model.Info;
import com.captain.smartbridge.model.LoginReq;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by fish on 17-6-2.
 */

public interface BridgeService {

    @POST("app/auth/login")
    Call<ResponseBody> login(@Body LoginReq loginReq);

    @Headers("Cookie: 2|1:0|10:1496645947|8:username|48:NzdmZGQ0MzAtNDliYy0xMWU3LWFiNjItNzg0ZjQzODY0NjJl|13bb81922ab8ad40313091228e41f574e13dbb50846a7979addfbe493a3cea6f")
    @GET("app/auth/info")
    Call<Info> getInfo();

}
