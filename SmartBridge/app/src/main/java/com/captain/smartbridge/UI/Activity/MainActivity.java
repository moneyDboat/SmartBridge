package com.captain.smartbridge.UI.Activity;

import android.content.Intent;
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
import com.captain.smartbridge.model.MapReq;
import com.captain.smartbridge.model.MapRes;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements LocationSource, AMapLocationListener,
        AMap.InfoWindowAdapter,AMap.OnMapClickListener, AMap.OnMarkerClickListener {
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

    private String SF = null;
    private String CF = null;
    private boolean firstin = true;

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

        initDrawer();

        mapView.onCreate(savedInstanceState);
        initMap();


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NearbyActivity.class);
                intent.putExtra("SF", SF);
                intent.putExtra("CF", CF);
                startActivityForResult(intent, 1);
            }
        });
    }

    private void initDrawer() {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.main_menu_about:
                        startActivity(new Intent(MainActivity.this, AboutActivity.class));
                        return true;
                    case R.id.main_menu_detect:
                        startActivity(new Intent(MainActivity.this, DetectActivity.class));
                        return true;
                    case R.id.main_menu_information:
                        startActivity(new Intent(MainActivity.this, UserActivity.class));
                        return true;
                    case R.id.main_menu_evalute:
                        return true;
                    default:
                        return true;
                }
            }
        });

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
        headerName.setText(PreferenceUtils.getString(this, PreferenceUtils.Key.NICK));
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
    }

    //初始化地图标记
    private void initMarker(final AMap aMap) {
        aMap.setOnMarkerClickListener(this);
        aMap.setInfoWindowAdapter(this);

        if (NetUtils.isNetworkAvailable(this)) {
            MapReq mapReq = new MapReq(SF, CF);
            ApiManager.getmService().getMapInfo(mapReq).enqueue(new Callback<List<MapRes>>() {
                @Override
                public void onResponse(Call<List<MapRes>> call, Response<List<MapRes>> response) {
                    List<MapRes> bridges = response.body();
                    for (MapRes bridge:bridges){
                        addMarkerToMap(bridge);
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
        SF = amapLocation.getProvince();
        CF = amapLocation.getCity();
        if (firstin && SF != null && CF != null) {
            //mark postion on the map
            initMarker(aMap);
            firstin = false;
        }
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
    public View getInfoWindow(Marker marker) {
        //自定义infowindow
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
                startActivity(intent);
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
        Log.i("Marker", marker.toString());
        if (oldMarker != null) {
            oldMarker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.ic_place_red_36px));
        }
        oldMarker = marker;
        marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.ic_place_blue_36px));
        return false; //返回 “false”，除定义的操作之外，默认操作也将会被执行
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode){
            case 1:
                if(oldMarker!=null){
                    oldMarker.hideInfoWindow();
                    oldMarker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.ic_place_red_36px));
                }
                oldMarker = aMap.getMapScreenMarkers().get(data.getIntExtra("ID", 0));
                oldMarker.showInfoWindow();
                oldMarker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.ic_place_blue_36px));
        }
    }
}
