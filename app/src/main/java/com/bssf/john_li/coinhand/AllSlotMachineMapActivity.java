package com.bssf.john_li.coinhand;

import android.app.AlertDialog;
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
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bssf.john_li.coinhand.CHModel.AllSMModel;
import com.bssf.john_li.coinhand.CHUtils.CHCommonUtils;
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
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by John_Li on 9/8/2018.
 */

public class AllSlotMachineMapActivity extends BaseActivity implements View.OnClickListener, OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    public static String TAG = AllSlotMachineMapActivity.class.getName();
    private View view;
    private LinearLayout loadLL;
    private ImageView loadIv;
    private ImageView unknowMachaineIv;
    private ImageView loadFailIv;
    private ImageView searchIv;
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
    private static final int REQUESTCODE = 6001;

    private AllSMModel smModel;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_sm_map);
        initView();
        setListener();
        initData();
    }

    @Override
    public void initView() {
        loadLL = (LinearLayout) findViewById(R.id.all_sm_load_ll);
        loadIv = (ImageView) findViewById(R.id.all_sm_load_iv);
        unknowMachaineIv = (ImageView) findViewById(R.id.all_sm_back);
        loadFailIv = (ImageView) findViewById(R.id.all_sm_load_fail);
        loadTv = (TextView) findViewById(R.id.all_sm_load_tv);
        searchIv = (ImageView) findViewById(R.id.all_sm_search);
        addressTv = (TextView) findViewById(R.id.all_sm_address);
        FragmentManager manager = getSupportFragmentManager();
        mMapFragment = (SupportMapFragment) manager.findFragmentById(R.id.all_sm_map_view);
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

    @Override
    public void setListener() {
        loadLL.setOnClickListener(this);
        searchIv.setOnClickListener(this);
        unknowMachaineIv.setOnClickListener(this);
    }

    @Override
    public void initData() {
        loadIv.setBackgroundResource(R.drawable.load_anim);
        animationDrawable = (AnimationDrawable) loadIv.getBackground();
        animationDrawable.start();
        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.all_sm_load_ll:
                animationDrawable.start();
                loadIv.setVisibility(View.VISIBLE);
                loadFailIv.setVisibility(View.GONE);
                loadTv.setText("加載中......");
                if (mGoogleMap != null) {
                    mGoogleMap.clear();
                }
                onMapReady(mGoogleMap);
                break;
            case R.id.all_sm_search:
                searchMachina();
                break;
            case R.id.all_sm_back:
                finish();
                break;
        }
    }

    private void searchMachina() {
        // 创建对话框构建器
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // 获取布局
        View view2 = View.inflate(AllSlotMachineMapActivity.this, R.layout.dialog_search_machine, null);
        // 获取布局中的控件
        final EditText machine_no_et = (EditText) view2.findViewById(R.id.machine_no_et);
        final Button button = (Button) view2.findViewById(R.id.search_machine_no_btn);
        // 设置参数
        builder.setTitle("Login").setView(view2);
        // 创建对话框
        final AlertDialog alertDialog = builder.create();
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                String machineNo = String.valueOf(machine_no_et.getText());
                if (!machineNo.equals("")) {
                    AllSMModel.DataBeanX.DataBean dataBean = null;
                    for (int i = 0; i < smModel.getData().getData().size(); i++) {
                        if (machineNo.equals(smModel.getData().getData().get(i).getMachineNo())) {
                            dataBean = smModel.getData().getData().get(i);
                        }
                    }

                    if (dataBean == null) {
                        Toast.makeText(AllSlotMachineMapActivity.this, "未找到此咪錶", Toast.LENGTH_LONG).show();
                    } else {
                        latLng = new LatLng(dataBean.getLatitude(), dataBean.getLongitude());
                        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 18F));
                    }
                } else {
                    Toast.makeText(AllSlotMachineMapActivity.this, "請輸入正確的咪錶編號", Toast.LENGTH_LONG).show();
                }
                alertDialog.dismiss();// 对话框消失
            }

        });
        alertDialog.show();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d("MAPLOGS", "ALLonMapReady");
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
                    Toast.makeText(AllSlotMachineMapActivity.this, "定位之前請打開GPS及網絡！", Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });

        mGoogleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                LatLng cacheLatLng =  marker.getPosition();
                addressTv.setText(getAddress(AllSlotMachineMapActivity.this, cacheLatLng.latitude, cacheLatLng.longitude));
                return false;
            }
        });
    }

    @Override
    protected void onDestroy() {
        Log.d("MAPLOGS", "ALLonDestroy");
        super.onDestroy();
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
        Log.d("MAPLOGS", "ALLbuildGoogleApiClient");
        mGoogleApiClient = new GoogleApiClient.Builder(AllSlotMachineMapActivity.this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.d("MAPLOGS", "ALLonConnected");
        boolean isGPSEnabled = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean isNetworkEnabled = mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (isGPSEnabled || isNetworkEnabled) {
            if (!isGPSEnabled) {
                Toast.makeText(AllSlotMachineMapActivity.this, "定位之前請打開GPS！", Toast.LENGTH_SHORT).show();
                loadMapFail();
            }

            if (!isNetworkEnabled) {
                Toast.makeText(AllSlotMachineMapActivity.this, "定位之前請打開網絡！", Toast.LENGTH_SHORT).show();
                loadMapFail();
            }
        } else {
            Toast.makeText(AllSlotMachineMapActivity.this, "定位之前請打開GPS及網絡！", Toast.LENGTH_SHORT).show();
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
        Log.d("MAPLOGS", "ALLonRequestPermissionsResult");
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
                    mAddress = getAddress(AllSlotMachineMapActivity.this, mLastLocation.getLatitude(), mLastLocation.getLongitude());
                    String address = "當前位置：" + mAddress;
                    mGoogleMap.clear();
                    latLng = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
                    mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude()), 18));
                    loadLL.setVisibility(View.GONE);
                    addressTv.setText(address);
                    refreshAllSM();

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
        Log.d("MAPLOGS", "ALLgetAddress");
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
     * 刷新所有描点
     */
    private void refreshAllSM() {
        InputStream is = null;
        String result = null;
        try {
            is = getAssets().open("all_sm_list.txt");
            int lenght = is.available();
            byte[]  buffer = new byte[lenght];
            is.read(buffer);
            result = new String(buffer, "utf8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (result != null) {
            smModel = new Gson().fromJson(result, AllSMModel.class);
            // 清空之前的marker
            mGoogleMap.clear();
            // 添加新的marker集合到界面
            for (int i = 0; i < smModel.getData().getData().size(); i++) {
                MarkerOptions options = new MarkerOptions().position(new LatLng(smModel.getData().getData().get(i).getLatitude(), smModel.getData().getData().get(i).getLongitude()));
                options.title("地址:" + smModel.getData().getData().get(i).getAddress() + "咪錶編號:" + String.valueOf(smModel.getData().getData().get(i).getMachineNo()));
                String no = smModel.getData().getData().get(i).getMachineNo();
                //options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                options.icon(BitmapDescriptorFactory.fromResource(R.mipmap.drawing_pin));
                Marker marker = mGoogleMap.addMarker(options);
                marker.setTag(smModel.getData().getData().get(i).getMachineNo());
            }
            latLng = new LatLng(mLastLocation.getLatitude(),mLastLocation.getLongitude());
            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16F));
        }
    }
}
