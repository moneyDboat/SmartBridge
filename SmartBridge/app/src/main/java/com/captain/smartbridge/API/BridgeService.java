package com.captain.smartbridge.API;

import com.captain.smartbridge.model.InfoRes;
import com.captain.smartbridge.model.LoginReq;
import com.captain.smartbridge.model.MapReq;
import com.captain.smartbridge.model.MapRes;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by fish on 17-6-2.
 */

public interface BridgeService {

    @POST("app/auth/login")
    Call<ResponseBody> login(@Body LoginReq loginReq);

    @GET("app/auth/info")
    Call<InfoRes> getInfo();

    @POST("app/map/getmap")
    Call<List<MapRes>> getMapInfo(@Body MapReq mapReq);


}
