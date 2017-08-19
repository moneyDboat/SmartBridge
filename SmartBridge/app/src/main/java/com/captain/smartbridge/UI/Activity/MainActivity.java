package com.captain.smartbridge.UI.Activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.UiSettings;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.model.MyLocationStyle;
import com.captain.smartbridge.API.ApiManager;
import com.captain.smartbridge.Common.NetUtils;
import com.captain.smartbridge.Common.PreferenceUtils;
import com.captain.smartbridge.R;
import com.captain.smartbridge.UI.Activity.Detect.DetectActivity;
import com.captain.smartbridge.UI.Activity.Evalute.EvMessActivity;
import com.captain.smartbridge.UI.Activity.Monitor.MonMessActivity;
import com.captain.smartbridge.model.MapReq;
import com.captain.smartbridge.model.MapRes;
import com.google.gson.Gson;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements LocationSource, AMapLocationListener,
        AMap.InfoWindowAdapter, AMap.OnMapClickListener, AMap.OnMarkerClickListener {
    @BindView(R.id.main_map)
    MapView mapView;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }

    @BindView(R.id.main_navigation)
    NavigationView navigationView;
    @BindView(R.id.main_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.main_infor_layout)
    RelativeLayout relativeLayout;
    @BindView(R.id.main_nearby)
    Button button;

    //保存省份，城市，桥梁
    private String SF = null;
    private String CF = null;
    private boolean firstin = true;
    private List<MapRes> bridges = null;

    private AMap aMap;
    private OnLocationChangedListener mListener;
    private AMapLocationClient mLocationClient;
    private AMapLocationClientOption mLocationOption;
    private UiSettings uiSettings;
    private Marker oldMarker;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);

        //设置菜单
        initDrawer();

        mapView.onCreate(savedInstanceState);
        initMap();

        //注册广播接收
        //用于登出时关闭Activity
        regListener();


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //跳转到附近桥梁界面
                Intent intent = new Intent(MainActivity.this, NearbyActivity.class);
                intent.putExtra("bridges", new Gson().toJson(bridges));
                startActivityForResult(intent, 1);
            }
        });
    }

    //设置边栏菜单
    private void initDrawer() {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.main_menu_information:
                        startActivity(new Intent(MainActivity.this, UserActivity.class));
                        return true;
                    case R.id.main_menu_detect:
                        startActivity(new Intent(MainActivity.this, DetectActivity.class));
                        return true;
                    case R.id.main_menu_evalute:
                        startActivity(new Intent(MainActivity.this, EvMessActivity.class));
                        return true;
                    case R.id.main_menu_monitor:
                        startActivity(new Intent(MainActivity.this, MonMessActivity.class));
                        return true;
                    case R.id.main_menu_about:
                        startActivity(new Intent(MainActivity.this, AboutActivity.class));
                        return true;
                    default:
                        return true;
                }
            }
        });

        //根据用户类别设置菜单
        //这一部分还需要细化
        int role = PreferenceUtils.getInt(this, PreferenceUtils.Key.ROLE);
        Menu menu = navigationView.getMenu();
        //管理员和管理单位账户
        if (role == 1 || role == 2) {
            menu.getItem(1).setVisible(true);
            menu.getItem(2).setVisible(true);
            menu.getItem(3).setVisible(true);
        }
        //检测录入单位
        if (role == 3) {
            menu.getItem(1).setVisible(true);
        }

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, mToolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        mDrawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        View headerView = navigationView.getHeaderView(0);
        TextView headerName = (TextView) headerView.findViewById(R.id.main_header_name);
        ImageView headerImg = (ImageView) headerView.findViewById(R.id.main_header_pic);
        headerName.setText(PreferenceUtils.getString(this, PreferenceUtils.Key.NICK));
        //设置头像图片（待完成）
    }

    private void initMap() {
        if (aMap == null) {
            aMap = mapView.getMap();
            uiSettings = aMap.getUiSettings();
            aMap.setLocationSource(this);
            aMap.setOnMapClickListener(this);
        }
        uiSettings.setMyLocationButtonEnabled(true);
        aMap.setMyLocationEnabled(true);

        //定位图标
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        aMap.setMyLocationStyle(myLocationStyle);
        aMap.setOnMarkerClickListener(this);
        aMap.setInfoWindowAdapter(this);
    }

    //获取地图上所有桥梁
    private void setMarker() {
        if (NetUtils.isNetworkAvailable(this)) {
            MapReq mapReq = new MapReq(SF, CF);
            ApiManager.getmService().getMapInfo(mapReq).enqueue(new Callback<List<MapRes>>() {
                @Override
                public void onResponse(Call<List<MapRes>> call, Response<List<MapRes>> response) {
                    bridges = response.body();
                    if (bridges != null) {
                        for (MapRes bridge : bridges) {
                            addMarkerToMap(bridge);
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<MapRes>> call, Throwable t) {
                    t.printStackTrace();
                    showToast("网络错误");
                }
            });
        } else {
            showToast("请检查您的网络");
        }
    }

    //添加地图标记
    private void addMarkerToMap(MapRes bridge) {
        aMap.addMarker(new MarkerOptions().anchor(0.5f, 0.5f)
                .position(new LatLng(Double.parseDouble(bridge.getWd()),
                        Double.parseDouble(bridge.getJd())))
                .title(bridge.getQlmc())
                .snippet(bridge.getQldm())
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_place_red_36px)));
    }


    @Override
    public void activate(OnLocationChangedListener listener) {
        mListener = listener;
        if (mLocationClient == null) {
            mLocationClient = new AMapLocationClient(this);
            mLocationOption = new AMapLocationClientOption();
            mLocationClient.setLocationListener(this);
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            mLocationClient.setLocationOption(mLocationOption);
            mLocationClient.startLocation();//启动定位
        }
    }

    @Override
    public void deactivate() {
        mListener = null;
        if (mLocationClient != null) {
            mLocationClient.stopLocation();
            mLocationClient.onDestroy();
        }
        mLocationClient = null;
    }

    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        //目前是根据当前定位获取相应城市的桥梁
        SF = amapLocation.getProvince();
        CF = amapLocation.getCity();
        if (firstin && SF != null && CF != null) {
            //mark postion on the map
            setMarker();
            firstin = false;
        }
        //考虑一下定位更新时，桥梁标记的变化
        if (mListener != null && amapLocation != null) {
            if (amapLocation != null
                    && amapLocation.getErrorCode() == 0) {
                mListener.onLocationChanged(amapLocation);
            } else {
                String errText = "定位失败," + amapLocation.getErrorCode() + ": " + amapLocation.getErrorInfo();
                Log.e("AmapErr", errText);
            }
        }
    }

    @Override
    public void addContentView(View view, ViewGroup.LayoutParams params) {
        super.addContentView(view, params);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }


    @Override
    //自定义infowindow
    public View getInfoWindow(Marker marker) {
        LatLng latLng = marker.getPosition();
        String title = marker.getTitle();
        final String addr = marker.getSnippet();

        View view = LayoutInflater.from(this).inflate(R.layout.custom_info_window, null);

        TextView titleView = (TextView) view.findViewById(R.id.marker_title);
        TextView addrView = (TextView) view.findViewById(R.id.marker_addr);
        TextView moreView = (TextView) view.findViewById(R.id.marker_more);

        titleView.setText(title);
        addrView.setText(addr);
        moreView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //跳转到桥梁详情界面
                Intent intent = new Intent(MainActivity.this, BridgeActivity.class);
                BaseApplication.setID(addr);
                startActivity(intent);
            }
        });
        return view;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.main_search:
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                startActivityForResult(intent, 2);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        if (null != mLocationClient) {
            mLocationClient.onDestroy();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    private void showToast(String message) {
        Snackbar.make(getWindow().getDecorView(), message, Snackbar.LENGTH_SHORT).show();
    }

    //地图的点击事件
    @Override
    public void onMapClick(LatLng latLng) {
        //点击地图上没marker 的地方，隐藏inforwindow
        if (oldMarker != null) {
            oldMarker.hideInfoWindow();
            oldMarker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.ic_place_red_36px));
        }
    }

    //maker的点击事件
    @Override
    public boolean onMarkerClick(Marker marker) {
        Log.i("Marker", marker.getId());
        if (marker.getId().equals("Marker1")) {
            return false;
        }
        if (oldMarker != null) {
            oldMarker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.ic_place_red_36px));
        }
        oldMarker = marker;
        marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.ic_place_blue_36px));
        return false; //返回 “false”，除定义的操作之外，默认操作也将会被执行
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            //获取NearbyActivity的返回结果
            case 1:
                if (oldMarker != null) {
                    hideMarker(oldMarker);
                }
                oldMarker = aMap.getMapScreenMarkers().get(data.getIntExtra("ID", 0));
                showMarker(oldMarker);
                break;

            //（待完成），SearchActivity的返回
            //目前的做法是遍历，可能代价过大
            case 2:
                if (oldMarker != null) {
                    hideMarker(oldMarker);
                }
                String code = data.getStringExtra("code");
                for (Marker marker : aMap.getMapScreenMarkers()) {
                    if (marker.getSnippet() == code) {
                        showMarker(marker);
                        break;
                    }
                }
                break;
        }
    }

    private void hideMarker(Marker marker) {
        marker.hideInfoWindow();
        marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.ic_place_red_36px));
    }

    private void showMarker(Marker marker) {
        marker.showInfoWindow();
        marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.ic_place_blue_36px));
    }


    protected void regListener() {
        //注册广播接收者
        MyReceiver receiver = new MyReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("exit_app");
        registerReceiver(receiver, filter);
    }

    class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("exit_app")) {
                context.unregisterReceiver(this);
                finish();
            }
        }
    }
}
