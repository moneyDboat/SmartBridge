package com.captain.smartbridge.API;

import com.captain.smartbridge.model.AcceptMissReq;
import com.captain.smartbridge.model.AcceptMissRes;
import com.captain.smartbridge.model.CreateMissReq;
import com.captain.smartbridge.model.CreateMissRes;
import com.captain.smartbridge.model.Department;
import com.captain.smartbridge.model.DetectMission;
import com.captain.smartbridge.model.GouJian;
import com.captain.smartbridge.model.InfoRes;
import com.captain.smartbridge.model.LoginReq;
import com.captain.smartbridge.model.MapReq;
import com.captain.smartbridge.model.MapRes;
import com.captain.smartbridge.model.SearchCodeReq;
import com.captain.smartbridge.model.SearchCodeRes;

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

    @POST("app/map/search")
    Call<List<SearchCodeRes>> search(@Body SearchCodeReq searchCodeReq);

    @POST("app/bridge/goujian")
    Call<List<GouJian>> getGou(@Body SearchCodeReq searchCodeReq);

    @GET("/app/detect/detects")
    Call<List<DetectMission>> getDetect();

    //获取检测部门列表
    @GET("/app/detect/departments")
    Call<List<Department>> getDepart();

    //新建检测任务
    @POST("/app/detect/detects")
    Call<CreateMissRes> createDetect(@Body CreateMissReq createMissReq);

    //接受检测任务
    @POST("/app/detect/accept")
    Call<AcceptMissRes> acceptDetect(@Body AcceptMissReq acceptMissReq);
}
