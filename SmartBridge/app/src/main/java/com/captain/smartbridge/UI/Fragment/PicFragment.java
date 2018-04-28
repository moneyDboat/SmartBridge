package com.captain.smartbridge.UI.Fragment;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.captain.smartbridge.API.ApiManager;
import com.captain.smartbridge.Common.NetUtils;
import com.captain.smartbridge.R;
import com.captain.smartbridge.model.other.MonDataReq;
import com.captain.smartbridge.model.other.MonPicData;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by captain on 18-4-12.
 */

public class PicFragment extends Fragment {
    public static final String ARG_PAGE = "ARG_PAGE";
    public static final String ID = "";
    private int mPage;
    private View view;

    static String id;
    static String sensor;
    static MonDataReq req = new MonDataReq();
    static String url1;
    static String url2;

    public static PicFragment newInstance(int page, String tid, String tsensor) {
        id = tid;
        sensor = tsensor;
        req.setId(tid);
        req.setCgqbh(tsensor);
        req.setNumber("-2");

        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        PicFragment fragment = new PicFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        switch (mPage) {
            case 0:
                return firstView(inflater, container);
            case 1:
                return secView(inflater, container);
            default:
                return null;
        }
    }

    private View firstView(LayoutInflater inflater, ViewGroup container) {
        view = inflater.inflate(R.layout.fragment_pic1, container, false);
        final ImageView img1 = (ImageView) view.findViewById(R.id.pic1_img);

        if (NetUtils.isNetworkAvailable(getActivity())){
            ApiManager.getmService().monPic(req).enqueue(new Callback<List<MonPicData>>() {
                @Override
                public void onResponse(Call<List<MonPicData>> call, Response<List<MonPicData>> response) {
                    if (response.body().size() == 2){
                        String url = response.body().get(1).getQiniuurl();
                        Glide.with(getActivity()).load(url).centerCrop().into(img1);
                    }else{
                        showDialog();
                    }
                }

                @Override
                public void onFailure(Call<List<MonPicData>> call, Throwable t) {

                }
            });
        }
//        String url = "http://p7l9j0wh9.bkt.clouddn.com/PIC00065.jpg";
//        Glide.with(getActivity()).load(url).centerCrop().into(img1);

        return view;
    }

    private View secView(LayoutInflater inflater, ViewGroup container) {
        view = inflater.inflate(R.layout.fragment_pic2, container, false);
        final ImageView img1 = (ImageView) view.findViewById(R.id.pic2_img1);
        ImageView img2 = (ImageView) view.findViewById(R.id.pic2_img2);
        final ImageView img3 = (ImageView) view.findViewById(R.id.pic2_img3);
        ImageView img4 = (ImageView) view.findViewById(R.id.pic2_img4);

        if (NetUtils.isNetworkAvailable(getActivity())){
            ApiManager.getmService().monPic(req).enqueue(new Callback<List<MonPicData>>() {
                @Override
                public void onResponse(Call<List<MonPicData>> call, Response<List<MonPicData>> response) {
                    if (response.body().size() == 2) {
                        String url1 = response.body().get(1).getQiniuurl();
                        String url3 = response.body().get(0).getQiniuurl();
                        Glide.with(getActivity()).load(url1).centerCrop().into(img1);
                        Glide.with(getActivity()).load(url3).centerCrop().into(img3);
                    }
                }

                @Override
                public void onFailure(Call<List<MonPicData>> call, Throwable t) {

                }
            });
        }

//        String url1 = "http://p7l9j0wh9.bkt.clouddn.com/PIC00065.jpg";
//        Glide.with(getActivity()).load(url1).centerCrop().into(img1);
//
//        String url2 = "http://p7l9j0wh9.bkt.clouddn.com/00101.jpg";
//        Glide.with(getActivity()).load(url2).centerCrop().into(img2);
        return view;
    }

    //显示对话框
    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("提示");
        builder.setMessage("当前传感器没有数据！");
        //builder.setNegativeButton("取消", null);
        //builder.setCancelable(true);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                getActivity().finish();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }


}
