package com.bssf.john_li.coinhand.CHFragment;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.AnimationDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bssf.john_li.coinhand.CHModel.OrderListOutModel;
import com.bssf.john_li.coinhand.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by John_Li on 20/1/2018.
 */

public class InsertCoinsFragment extends LazyLoadFragment implements View.OnClickListener, OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    public static String TAG = InsertCoinsFragment.class.getName();
    private View view;
    private LinearLayout loadLL;
    private ImageView loadIv;
    private ImageView loadFailIv;
    private ImageView refreshIv;
    private TextView loadTv, addressTv;

    private AnimationDrawable animationDrawable = null;
    private LocationRequest mLocationRequest;
    private GoogleApiClient mGoogleApiClient;

    private LatLng latLng;
    private GoogleMap mGoogleMap;
    private SupportMapFragment mMapFragment;
    private Marker mCurrLocation;
    private LocationManager mLocationManager;
    private Location mLastLocation = null;
    private String mAddress;
    private List<OrderListOutModel> orderList;

    private static final int REQUESTCODE = 6001;

    public static InsertCoinsFragment newInstance(){
        return new InsertCoinsFragment();
    }
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        setContentView(R.layout.fragment_insert_coins);
        initView();
        setListener();
        initData();
    }

    private void initView() {
        loadLL = (LinearLayout) findViewById(R.id.insert_load_ll);
        loadIv = (ImageView) findViewById(R.id.insert_load_iv);
        loadFailIv = (ImageView) findViewById(R.id.insert_load_fail);
        loadTv = (TextView) findViewById(R.id.insert_load_tv);
        refreshIv = (ImageView) findViewById(R.id.insert_refresh);
        addressTv = (TextView) findViewById(R.id.inset_address);
        mMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map_view);
        mMapFragment.getMapAsync(this);
        View mapView = mMapFragment.getView();
        // 調整按鈕位置
        if (mapView != null && mapView.findViewById(1) != null) {
            // Get the button view
            View locationButton = ((View) mapView.findViewById(1).getParent()).findViewById(2);
            // and next place it, on bottom right (as Google Maps app)
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) locationButton.getLayoutParams();
            // position on right bottom
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
            layoutParams.setMargins(0, 120, 30, 0);
        }
    }

    private void setListener() {
        loadLL.setOnClickListener(this);
        refreshIv.setOnClickListener(this);
    }

    private void initData() {
        loadIv.setBackgroundResource(R.drawable.load_anim);
        animationDrawable = (AnimationDrawable) loadIv.getBackground();
        animationDrawable.start();
        mLocationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.insert_load_ll:
                animationDrawable.start();
                loadIv.setVisibility(View.VISIBLE);
                loadFailIv.setVisibility(View.GONE);
                loadTv.setText("加載中......");
                if (mGoogleMap != null) {
                    mGoogleMap.clear();
                }
                onMapReady(mGoogleMap);
                break;
            case R.id.insert_refresh:
                refreshOrderList();
                break;
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        // 允许获取我的位置
        try {
            mGoogleMap.getUiSettings().setMapToolbarEnabled(false);
            mGoogleMap.setMyLocationEnabled(true);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        buildGoogleApiClient();
        mGoogleApiClient.connect();

        // 定位按鈕觸發事件：重新定位
        mGoogleMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
            @Override
            public boolean onMyLocationButtonClick() {
                //onConnected(null);
                if (mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                    if (mGoogleMap != null) {
                        mGoogleMap.clear();
                    }
                    onMapReady(mGoogleMap);
                } else {
                    Toast.makeText(getActivity(), "定位之前請打開GPS及網絡！", Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });
    }

    @Override
    protected void onPauseLazy() {
        super.onPauseLazy();
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, new LocationCallback() {
                @Override
                public void onLocationResult(LocationResult locationResult) {
                    super.onLocationResult(locationResult);
                }
            });
        }
    }

    protected synchronized void buildGoogleApiClient() {//4
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        boolean isGPSEnabled = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean isNetworkEnabled = mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (isGPSEnabled || isNetworkEnabled) {
            if (!isGPSEnabled) {
                Toast.makeText(getActivity(), "定位之前請打開GPS！", Toast.LENGTH_SHORT).show();
                loadMapFail();
            }

            if (!isNetworkEnabled) {
                Toast.makeText(getActivity(), "定位之前請打開網絡！", Toast.LENGTH_SHORT).show();
                loadMapFail();
            }
        } else {
            Toast.makeText(getActivity(), "定位之前請打開GPS及網絡！", Toast.LENGTH_SHORT).show();
            loadMapFail();
        }

        if (isGPSEnabled && isNetworkEnabled){
            mTimer = new Timer();
            mTimer.schedule(new WaitTask(), 1000, 3000);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        loadMapFail();
    }

    private void loadMapFail() {
        loadLL.setVisibility(View.VISIBLE);
        //loadIv.setImageResource(R.mipmap.head_boy);
        loadIv.setVisibility(View.GONE);
        animationDrawable.stop();
        loadFailIv.setVisibility(View.VISIBLE);
        loadTv.setText("加載失敗，點我重新加載\n如果未打開系統定位服務請開啟先!");
        addressTv.setText("定位失敗，請重試");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUESTCODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    buildGoogleApiClient();
                    mGoogleApiClient.connect();
                } else {
                }
                return;
            }
        }
    }

    /**
     * 等待線程，讓主線程等待子線程，每隔一秒拿一次，直至拿到，最多拿五次
     */
    public class WaitTask extends TimerTask {
        int times = 5;
        @Override
        public void run() {
            Message msg = new Message();
            if (times > 0 && mLastLocation == null) {   // 请求次数在五次内，且没获取到定位坐标
                try {
                    mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
                } catch (SecurityException e) {
                    e.printStackTrace();
                }
                times--;
            } else {
                if (mLastLocation != null){     // 获取到定位坐标
                    msg.what = 1;
                    mHandler.sendMessage(msg);
                } else {    // 获取次数已经超过五次，且没获取到，判定获取失败
                    msg.what = 2;
                    mHandler.sendMessage(msg);
                }
                mTimer.cancel();
            }
        }
    }

    private Timer mTimer;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    mAddress = getAddress(getActivity(), mLastLocation.getLatitude(), mLastLocation.getLongitude());
                    String address = "當前位置：" + mAddress;
                    mGoogleMap.clear();
                    latLng = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
                    mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude()), 18));
                    loadLL.setVisibility(View.GONE);
                    addressTv.setText(address);

                    mLocationRequest = LocationRequest.create();
                    mLocationRequest.setInterval(5000); //5 seconds
                    mLocationRequest.setFastestInterval(3000); //3 seconds
                    mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
                    //mLocationRequest.setSmallestDisplacement(0.1F); //1/10 meter

                    //LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
                    LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, new LocationCallback() {
                        @Override
                        public void onLocationResult(LocationResult locationResult) {
                            super.onLocationResult(locationResult);
                        }
                    });
                    break;
                case 2:
                    loadMapFail();
                    break;
            }
        }
    };

    /**
     * 逆地理编码 得到地址
     * @param context
     * @param latitude
     * @param longitude
     * @return
     */
    public static String getAddress(Context context, double latitude, double longitude) {//6
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        try {
            List<Address> address = geocoder.getFromLocation(latitude, longitude, 1);
            Log.i("位置", "得到位置当前" + address + "'\n"
                    + "经度：" + String.valueOf(address.get(0).getLongitude()) + "\n"
                    + "纬度：" + String.valueOf(address.get(0).getLatitude()) + "\n"
                    + "纬度：" + "国家：" + address.get(0).getCountryName() + "\n"
                    + "城市：" + address.get(0).getLocality() + "\n"
                    + "名称：" + address.get(0).getAddressLine(1) + "\n"
                    + "街道：" + address.get(0).getAddressLine(0)
            );
            //return address.get(0).getCountryName() + " " + address.get(0).getAddressLine(0);
            return address.get(0).getAddressLine(0);
        } catch (Exception e) {
            e.printStackTrace();
            return "未知";
        }
    }

    /**
     * 刷新訂單
     */
    private void refreshOrderList() {
        if (mGoogleMap != null) {
            // 模擬加數據
            loadOrderList();
            /*mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(22.198372,113.549666)).title("澳门特别行政区澳门半岛連勝馬路").icon(BitmapDescriptorFactory.fromResource(R.mipmap.location_g)));
            mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(22.199962,113.544517)).title("澳门特别行政区澳门半岛土地廟前地").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
            mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(22.201054,113.545858)).title("澳门特别行政区澳门半岛沙梨頭海邊街").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
            mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(22.200166,113.544715)).title("澳门特别行政区澳门半岛沙梨頭海邊街").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
            mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(22.200130,113.544753)).title("澳门特别行政区澳门半岛麻子街").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
            mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(22.198322,113.543218)).title("澳门特别行政区澳门半岛土地廟前地").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
            mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(22.199326,113.545128)).title("澳门特别行政区澳门半岛沙梨頭海邊街").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
            mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(22.199147,113.545053)).title("澳门特别行政区澳门半岛沙梨頭海邊街").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
            mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(22.199524,113.545707)).title("澳门特别行政区澳门半岛麻子街").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
            mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(22.201223,113.543701)).title("澳门特别行政区澳门半岛土地廟前地").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
            mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(22.199366,113.542510)).title("澳门特别行政区澳门半岛沙梨頭海邊街").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
            mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(22.200548,113.547113)).title("澳门特别行政区澳门半岛沙梨頭海邊街").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
            mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(22.199664,113.546866)).title("澳门特别行政区澳门半岛麻子街").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
            mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(22.199256,113.546920)).title("澳门特别行政区澳门半岛土地廟前地").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
            mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(22.198630,113.546909)).title("澳门特别行政区澳门半岛沙梨頭海邊街").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
            mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(22.201342,113.546212)).title("澳门特别行政区澳门半岛沙梨頭海邊街").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
            mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(22.201591,113.544720)).title("澳门特别行政区澳门半岛麻子街").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
            mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(22.198660,113.543401)).title("澳门特别行政区澳门半岛土地廟前地").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
            mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(22.198114,113.545375)).title("澳门特别行政区澳门半岛沙梨頭海邊街").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
            mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(22.198432,113.546008)).title("澳门特别行政区澳门半岛沙梨頭海邊街").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
            mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(22.199485,113.542800)).title("澳门特别行政区澳门半岛麻子街").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
            mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(22.197597,113.543283)).title("澳门特别行政区澳门半岛土地廟前地").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
            mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(22.198044,113.542693)).title("澳门特别行政区澳门半岛沙梨頭海邊街").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
            mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(22.198273,113.543272)).title("澳门特别行政区澳门半岛沙梨頭海邊街").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
            mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(22.198332,113.542178)).title("澳门特别行政区澳门半岛麻子街").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
            mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(22.197121,113.542746)).title("澳门特别行政区澳门半岛土地廟前地").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
            mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(22.196574,113.542886)).title("澳门特别行政区澳门半岛沙梨頭海邊街").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
            mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(22.198183,113.542306)).title("澳门特别行政区澳门半岛沙梨頭海邊街").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
            mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(22.197935,113.543669)).title("澳门特别行政区澳门半岛麻子街").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
            mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(22.197021,113.543572)).title("澳门特别行政区澳门半岛土地廟前地").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
            mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(22.197468,113.542897)).title("澳门特别行政区澳门半岛沙梨頭海邊街").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
            mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(22.196415,113.542639)).title("澳门特别行政区澳门半岛沙梨頭海邊街").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
            mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(22.196157,113.543015)).title("澳门特别行政区澳门半岛麻子街").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
            mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(22.197915,113.542553)).title("澳门特别行政区澳门半岛土地廟前地").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
            mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(22.196256,113.542156)).title("澳门特别行政区澳门半岛沙梨頭海邊街").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
            mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(22.197260,113.542264)).title("澳门特别行政区澳门半岛沙梨頭海邊街").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
            mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(22.197915,113.542618)).title("澳门特别行政区澳门半岛麻子街").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
            mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(22.196107,113.542317)).title("澳门特别行政区澳门半岛土地廟前地").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
            mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(22.198789,113.542371)).title("澳门特别行政区澳门半岛沙梨頭海邊街").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
            mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(22.197945,113.542575)).title("澳门特别行政区澳门半岛沙梨頭海邊街").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
            mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(22.197587,113.543261)).title("澳门特别行政区澳门半岛麻子街").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
            mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(22.197011,113.542532)).title("澳门特别行政区澳门半岛土地廟前地").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
            mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(22.197299,113.542017)).title("澳门特别行政区澳门半岛沙梨頭海邊街").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
            mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(22.197160,113.542961)).title("澳门特别行政区澳门半岛沙梨頭海邊街").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
            mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(22.198134,113.543626)).title("澳门特别行政区澳门半岛麻子街").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
            mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(22.197796,113.546448)).title("澳门特别行政区澳门半岛土地廟前地").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
            mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(22.198938,113.544903)).title("澳门特别行政区澳门半岛沙梨頭海邊街").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
            mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(22.198859,113.544388)).title("澳门特别行政区澳门半岛沙梨頭海邊街").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));*/
            for (int i = 0; i < orderList.size(); i++) {
                MarkerOptions options = new MarkerOptions().position(new LatLng(orderList.get(i).getLatitude(),orderList.get(i).getLongitude()));
                options.title(orderList.get(i).getAddress());
                switch (orderList.get(i).getColor()) {
                    case 0:
                        options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));
                        break;
                    case 1:
                        options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                        break;
                    case 2:
                        options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                        break;
                }
                Marker marker = mGoogleMap.addMarker(options);
                marker.setTag(i);
            }
            latLng = new LatLng(mLastLocation.getLatitude(),mLastLocation.getLongitude());
            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15.5F));
        } else {
            Toast.makeText(getActivity(), "定位信息有誤，請重新定位！", Toast.LENGTH_SHORT);
        }
    }

    /**
     * 模擬假數據請求訂單
     */
    private void loadOrderList() {
        orderList = new ArrayList<>();
        for (int i = 1; i < 5000; i += 500) {
            OrderListOutModel model = new OrderListOutModel();
            model.setLatitude(mLastLocation.getLatitude() + i * 0.000001);
            model.setLongitude(mLastLocation.getLongitude() + i * 0.000001);
            model.setAddress("澳门特别行政区澳门半岛沙梨頭海邊街");
            if (i % 3 == 0){
                model.setColor(0);
            } else if(i % 7 ==0) {
                model.setColor(1);
            } else {
                model.setColor(2);
            }
            orderList.add(model);
        }
        for (int i = 1; i < 5000; i += 500) {
            OrderListOutModel model = new OrderListOutModel();
            model.setLatitude((mLastLocation.getLatitude() - 0.001) + i * 0.000001);
            model.setLongitude((mLastLocation.getLongitude() - 0.002) + i * 0.000001);
            model.setAddress("澳门特别行政区澳门半岛麻子街");
            if (i % 3 == 0){
                model.setColor(0);
            } else if(i % 7 ==0) {
                model.setColor(1);
            } else {
                model.setColor(2);
            }
            orderList.add(model);
        }
        for (int i = 1; i < 5000; i += 500) {
            OrderListOutModel model = new OrderListOutModel();
            model.setLatitude((mLastLocation.getLatitude() - 0.002) + i * 0.000001);
            model.setLongitude((mLastLocation.getLongitude() + 0.002) + i * 0.000001);
            model.setAddress("澳门特别行政区澳门半岛土地廟前地");
            if (i % 3 == 0){
                model.setColor(0);
            } else if(i % 7 ==0) {
                model.setColor(1);
            } else {
                model.setColor(2);
            }
            orderList.add(model);
        }

        for (int i = 1; i < 5000; i += 500) {
            OrderListOutModel model = new OrderListOutModel();
            model.setLatitude((mLastLocation.getLatitude() - 0.003) + i * 0.000001);
            model.setLongitude((mLastLocation.getLongitude() - 0.002) + i * 0.000001);
            model.setAddress("澳门特别行政区澳门半岛僑樂新街");
            if (i % 3 == 0){
                model.setColor(0);
            } else if(i % 7 ==0) {
                model.setColor(1);
            } else {
                model.setColor(2);
            }
            orderList.add(model);
        }

        for (int i = 1; i < 5000; i += 500) {
            OrderListOutModel model = new OrderListOutModel();
            model.setLatitude((mLastLocation.getLatitude() - 0.004) + i * 0.000001);
            model.setLongitude((mLastLocation.getLongitude() + 0.002) + i * 0.000001);
            model.setAddress("澳门特别行政区澳门半岛連勝馬路");
            if (i % 3 == 0){
                model.setColor(0);
            } else if(i % 7 ==0) {
                model.setColor(1);
            } else {
                model.setColor(2);
            }
            orderList.add(model);
        }
        for (int i = 1; i < 5000; i += 500) {
            OrderListOutModel model = new OrderListOutModel();
            model.setLatitude((mLastLocation.getLatitude() + 0.001) + i * 0.000001);
            model.setLongitude((mLastLocation.getLongitude() - 0.002) + i * 0.000001);
            model.setAddress("澳门特别行政区澳门半岛沙梨頭海邊街");
            if (i % 3 == 0){
                model.setColor(0);
            } else if(i % 7 ==0) {
                model.setColor(1);
            } else {
                model.setColor(2);
            }
            orderList.add(model);
        }
        for (int i = 1; i < 5000; i += 500) {
            OrderListOutModel model = new OrderListOutModel();
            model.setLatitude((mLastLocation.getLatitude() + 0.002) + i * 0.000001);
            model.setLongitude((mLastLocation.getLongitude() + 0.002) + i * 0.000001);
            model.setAddress("澳门特别行政区澳门半岛麻子街");
            if (i % 3 == 0){
                model.setColor(0);
            } else if(i % 7 ==0) {
                model.setColor(1);
            } else {
                model.setColor(2);
            }
            orderList.add(model);
        }
        for (int i = 1; i < 5000; i += 500) {
            OrderListOutModel model = new OrderListOutModel();
            model.setLatitude((mLastLocation.getLatitude() + 0.003) + i * 0.000001);
            model.setLongitude((mLastLocation.getLongitude() - 0.002) + i * 0.000001);
            model.setAddress("澳门特别行政区澳门半岛土地廟前地");
            if (i % 3 == 0){
                model.setColor(0);
            } else if(i % 7 ==0) {
                model.setColor(1);
            } else {
                model.setColor(2);
            }
            orderList.add(model);
        }

        for (int i = 1; i < 5000; i += 500) {
            OrderListOutModel model = new OrderListOutModel();
            model.setLatitude((mLastLocation.getLatitude() + 0.004) + i * 0.000001);
            model.setLongitude((mLastLocation.getLongitude() + 0.002) + i * 0.000001);
            model.setAddress("澳门特别行政区澳门半岛僑樂新街");
            if (i % 3 == 0){
                model.setColor(0);
            } else if(i % 7 ==0) {
                model.setColor(1);
            } else {
                model.setColor(2);
            }
            orderList.add(model);
        }

        for (int i = 1; i < 5000; i += 500) {
            OrderListOutModel model = new OrderListOutModel();
            model.setLatitude((mLastLocation.getLatitude() + 0.005) + i * 0.000001);
            model.setLongitude((mLastLocation.getLongitude() - 0.002) + i * 0.000001);
            model.setAddress("澳门特别行政区澳门半岛連勝馬路");
            if (i % 3 == 0){
                model.setColor(0);
            } else if(i % 7 ==0) {
                model.setColor(1);
            } else {
                model.setColor(2);
            }
            orderList.add(model);
        }
    }
}
