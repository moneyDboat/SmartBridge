package com.captain.smartbridge.API;

import com.captain.smartbridge.model.LoginReq;

import retrofit2.http.Body;
import retrofit2.http.GET;

/**
 * Created by fish on 17-6-2.
 */

public interface BridgeService {

    @GET("users/{user}/repos")
    void listRepos(@Body LoginReq loginReq);
}
