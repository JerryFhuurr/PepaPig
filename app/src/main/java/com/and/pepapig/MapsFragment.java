package com.and.pepapig;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.TextureMapView;
import com.baidu.mapapi.model.LatLng;

public class MapsFragment extends Fragment {

    private TextureMapView map;
    private View v;
    private BaiduMap mBaiduMap;
    private LocationClient client;
    private LatLng latLng;
    private boolean isFirstLoc = true; // 是否首次定位
    private BDLocationListener listener = new MyLocationListener();

    private Button normal;
    private Button satellite;

    public MapsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.setAgreePrivacy(getActivity().getApplicationContext(), true);
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        SDKInitializer.initialize(getActivity().getApplicationContext());
        //自4.3.0起，百度地图SDK所有接口均支持百度坐标和国测局坐标，用此方法设置您使用的坐标类型.
        //包括BD09LL和GCJ02两种坐标，默认是BD09LL坐标。
        SDKInitializer.setCoordType(CoordType.BD09LL);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_maps, container, false);
        initView();
        init();
        //设置放大倍数
        initMap();
        configure();
        return v;
    }

    private void initView(){
        map = v.findViewById(R.id.mapView);
        normal = v.findViewById(R.id.map_normal);
        satellite = v.findViewById(R.id.map_satellite);
        normal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
            }
        });

        satellite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBaiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
            }
        });
    }

    private void init() {

        mBaiduMap = map.getMap();
        mBaiduMap.setMyLocationEnabled(true);
    }

    private void initMap() {
        //设置地图放大的倍数
        MapStatus.Builder builder = new MapStatus.Builder();
        builder.zoom(18f);
        mBaiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));

        try {
            client = new LocationClient(getActivity().getApplicationContext());
            init_location();
            client.registerLocationListener(listener);  //注册监听函数
            client.start();
            client.requestLocation();
        } catch (Exception e){
            Log.e("map_error_client", e.getMessage());
        }
    }

    /**
     * 自定义内容:
     * 参数说明
     * (1)   定位模式 地图SDK支持三种定位模式：NORMAL（普通态）, FOLLOWING（跟随态）, COMPASS（罗盘态）
     * （2）是否开启方向
     * （3）自定义定位图标 支持自定义定位图标样式，
     * （4）自定义精度圈填充颜色
     * （5）自定义精度圈边框颜色
     */
    private void configure() {
        mBaiduMap.setMyLocationConfiguration(new MyLocationConfiguration(MyLocationConfiguration.LocationMode.NORMAL, true, null));
    }

    /**
     * 定位的初始化
     */
    public void init_location() {

            //通过LocationClientOption设置LocationClient相关参数
            LocationClientOption mOption = new LocationClientOption();
/**
 * 默认高精度，设置定位模式
 * LocationMode.Hight_Accuracy 高精度定位模式：这种定位模式下，会同时使用
 * LocationMode.Battery_Saving 低功耗定位模式：这种定位模式下，不会使用GPS，只会使用网络定位。
 * LocationMode.Device_Sensors 仅用设备定位模式：这种定位模式下，
 */
            mOption.setLocationMode(LocationClientOption.LocationMode.Battery_Saving);

/**
 * 默认是true，设置是否使用gps定位
 * 如果设置为false，即使mOption.setLocationMode(LocationMode.Hight_Accuracy)也不会gps定位
 */
            mOption.setOpenGps(true);

/**
 * 默认gcj02，设置返回的定位结果坐标系，如果配合百度地图使用，建议设置为bd09ll;
 * 目前国内主要有以下三种坐标系：
 1. wgs84：目前广泛使用的GPS全球卫星定位系统使用的标准坐标系；
 2. gcj02：经过国测局加密的坐标；
 3. bd09：为百度坐标系，其中bd09ll表示百度经纬度坐标，bd09mc表示百度墨卡托米制坐标；
 * 在国内获得的坐标系类型可以是：国测局坐标、百度墨卡托坐标 和 百度经纬度坐标。
 在海外地区，只能获得WGS84坐标。请在使用过程中注意选择坐标。
 */
            mOption.setCoorType("bd09ll");

/**
 * 默认0，即仅定位一次；设置间隔需大于等于1000ms，表示周期性定位
 * 如果不在AndroidManifest.xml声明百度指定的Service，周期性请求无法正常工作
 * 这里需要注意的是：如果是室外gps定位，不用访问服务器，设置的间隔是3秒，那么就是3秒返回一次位置
 如果是WiFi基站定位，需要访问服务器，这个时候每次网络请求时间差异很大，设置的间隔是3秒，
 只能大概保证3秒左右会返回就一次位置，有时某次定位可能会5秒才返回
 */
            mOption.setScanSpan(3000);

/**
 * 默认false，设置是否需要地址信息
 * 返回省、市、区、街道等地址信息，这个api用处很大，
 很多新闻类app会根据定位返回的市区信息推送用户所在市的新闻
 */
            mOption.setIsNeedAddress(true);

/**
 * 默认false，设置是否需要位置语义化结果
 * 可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
 */
            mOption.setIsNeedLocationDescribe(true);

/**
 * 默认false,设置是否需要设备方向传感器的方向结果
 * 一般在室外gps定位时，返回的位置信息是带有方向的，但是有时候gps返回的位置也不带方向，
 这个时候可以获取设备方向传感器的方向
 * wifi基站定位的位置信息是不带方向的，如果需要可以获取设备方向传感器的方向
 */
            mOption.setNeedDeviceDirect(false);

/**
 * 默认false，设置是否当gps有效时按照设定的周期频率输出GPS结果
 * 室外gps有效时，周期性1秒返回一次位置信息，其实就是设置了
 locationManager.requestLocationUpdates中的minTime参数为1000ms，1秒回调一个gps位置
 * 如果设置了mOption.setScanSpan(3000)，那minTime就是3000ms了，3秒回调一个gps位置
 */
            mOption.setLocationNotify(false);

/**
 * 默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
 * 如果你已经拿到了你要的位置信息，不需要再定位了，不杀死留着干嘛
 */
            mOption.setIgnoreKillProcess(true);

/**
 * 默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
 * POI就是获取到的位置附近的一些商场、饭店、银行等信息
 */
            mOption.setIsNeedLocationPoiList(true);

/**
 * 默认false，设置是否收集CRASH信息，默认收集
 */
            mOption.SetIgnoreCacheException(false);

/**
 * 默认false，设置定位时是否需要海拔高度信息，默认不需要，除基础定位版本都可用
 */
            mOption.setIsNeedAltitude(false);

            client.setLocOption(mOption);//设置定位参数

    }

    /**
     * 继承抽象类BDAbstractListener并重写其onReceieveLocation方法来获取定位数据，并将其传给MapView。
     */
    public class MyLocationListener implements BDLocationListener{
        @Override
        public void onReceiveLocation(BDLocation location){
            latLng = new LatLng(location.getLatitude(), location.getLongitude());
            // 构造定位数据
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(100).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            // 设置定位数据
            mBaiduMap.setMyLocationData(locData);
            // 当不需要定位图层时关闭定位图层
            //mBaiduMap.setMyLocationEnabled(false);
            if (isFirstLoc) {
                isFirstLoc = false;
                LatLng ll = new LatLng(location.getLatitude(),
                        location.getLongitude());
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(ll).zoom(18.0f);
                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));

                if (location.getLocType() == BDLocation.TypeGpsLocation) {
                    // GPS定位结果
                    Toast.makeText(getActivity(), location.getAddrStr(), Toast.LENGTH_SHORT).show();
                } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {
                    // 网络定位结果
                    Toast.makeText(getActivity(), location.getAddrStr(), Toast.LENGTH_SHORT).show();

                } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {
                    // 离线定位结果
                    Toast.makeText(getActivity(), location.getAddrStr(), Toast.LENGTH_SHORT).show();

                } else if (location.getLocType() == BDLocation.TypeServerError) {
                    //TODO 替换此处的text
                    Toast.makeText(getActivity(), "服务器错误，请检查", Toast.LENGTH_SHORT).show();
                } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
                    Toast.makeText(getActivity(), "网络错误，请检查", Toast.LENGTH_SHORT).show();
                } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
                    Toast.makeText(getActivity(), "手机模式错误，请检查是否飞行", Toast.LENGTH_SHORT).show();
                }
            }

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        map.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        map.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        map.onDestroy();
    }
}