package com.bssf.john_li.coinhand.CHFragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
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
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bssf.john_li.coinhand.CHAdapter.PopOrderListAdapter;
import com.bssf.john_li.coinhand.CHAdapter.PopUnKnowOrderListAdapter;
import com.bssf.john_li.coinhand.CHModel.CheckUnfinishModel;
import com.bssf.john_li.coinhand.CHModel.GetWorkAreaOutModel;
import com.bssf.john_li.coinhand.CHModel.OrderDetialOutModel;
import com.bssf.john_li.coinhand.CHModel.OrderListOutModel;
import com.bssf.john_li.coinhand.CHUtils.CHCommonUtils;
import com.bssf.john_li.coinhand.CHUtils.CHConfigtor;
import com.bssf.john_li.coinhand.CHUtils.DirectionsJSONParser;
import com.bssf.john_li.coinhand.CHUtils.SPUtils;
import com.bssf.john_li.coinhand.CHUtils.ValComparator;
import com.bssf.john_li.coinhand.OrderDetialActivity;
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
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
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
    private ImageView unknowMachaineIv;
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
    private List<OrderListOutModel.DataBean> orderList;
    // 已知咪錶的訂單
    private List<OrderListOutModel.DataBean> orderMaachineKnownList;
    // 未知機器的訂單
    private List<OrderListOutModel.DataBean> orderMachineUnknowList;
    private int totalCount = 0;

    private static final int REQUESTCODE = 6001;
    // 路线
    private String mapurl;

    private Polyline polylineFinal;
    PolylineOptions lineOptions = null;

    Handler mHandler2 = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String s1 = (String) msg.obj;
            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try {
                jObject = new JSONObject(s1);
                DirectionsJSONParser parser = new DirectionsJSONParser();

                // Starts parsing data
                routes = parser.parse(jObject);
            } catch (Exception e) {
                e.printStackTrace();
            }
            ArrayList<LatLng> points = null;
            MarkerOptions markerOptions = new MarkerOptions();

            // Traversing through all the routes
            for (int i = 0; i < routes.size(); i++) {
                points = new ArrayList<LatLng>();
                lineOptions = new PolylineOptions();

                // Fetching i-th route
                List<HashMap<String, String>> path = routes.get(i);

                // Fetching all the points in i-th route
                for (int j = 0; j < path.size(); j++) {
                    HashMap<String, String> point = path.get(j);

                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);

                    points.add(position);
                }

                // Adding all the points in the route to LineOptions
                lineOptions.addAll(points);
                lineOptions.width(10);

                // Changing the color polyline according to the mode
                lineOptions.color(getResources().getColor(R.color.base_color));
            }

            // Drawing polyline in the Google Map for the i-th route
            if (lineOptions != null) {
                if (polylineFinal != null) {
                    polylineFinal.remove();
                }
                polylineFinal = mGoogleMap.addPolyline(lineOptions);
            }
        }
    };

    public static InsertCoinsFragment newInstance(){
        return new InsertCoinsFragment();
    }
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        setContentView(R.layout.fragment_insert_coins);
        EventBus.getDefault().register(this);
        initView();
        setListener();
        initData();
    }

    private void initView() {
        loadLL = (LinearLayout) findViewById(R.id.insert_load_ll);
        loadIv = (ImageView) findViewById(R.id.insert_load_iv);
        unknowMachaineIv = (ImageView) findViewById(R.id.insert_unknown_machanie);
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
        loadLL.setOnClickListener(null);
        refreshIv.setOnClickListener(this);
        unknowMachaineIv.setOnClickListener(this);
    }

    private void initData() {
        loadIv.setBackgroundResource(R.drawable.load_anim);
        animationDrawable = (AnimationDrawable) loadIv.getBackground();
        animationDrawable.start();
        mLocationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        orderList = new ArrayList<>();
        orderMachineUnknowList = new ArrayList<>();
        orderMaachineKnownList = new ArrayList<>();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
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
            case R.id.insert_unknown_machanie:
                showUnkonwOrderListPop();
                break;
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d("MAPLOGS", "InsertonMapReady");
        mGoogleMap = googleMap;
        if (mGoogleMap != null) {
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

            mGoogleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    //getGoogleMapLine((String)marker.getTag());
                    showOrderListPop((String)marker.getTag());
                    return false;
                }
            });
        }
    }

    @Override
    protected void onPauseLazy() {
        Log.d("MAPLOGS", "InsertonPauseLazy");
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
        Log.d("MAPLOGS", "InsertbuildGoogleApiClient");
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.d("MAPLOGS", "InsertonConnected");
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
        loadLL.setOnClickListener(this);
        //loadIv.setImageResource(R.mipmap.head_boy);
        loadIv.setVisibility(View.GONE);
        animationDrawable.stop();
        loadFailIv.setVisibility(View.VISIBLE);
        loadTv.setText("加載失敗，點我重新加載\n如果未打開系統定位服務請開啟先!");
        addressTv.setText("定位失敗，請重試");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d("MAPLOGS", "onRequestPermissionsResult");
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
                    loadLL.setOnClickListener(null);
                    addressTv.setText(address);
                    // 刷新訂單列表
                    refreshOrderList();

                    mLocationRequest = LocationRequest.create();
                    mLocationRequest.setInterval(5000); //5 seconds
                    mLocationRequest.setFastestInterval(3000); //3 seconds
                    mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
                    //mLocationRequest.setSmallestDisplacement(0.1F); //1/10 meter

                    //LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
                    if (mGoogleApiClient.isConnected()) {
                        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, new LocationCallback() {
                            @Override
                            public void onLocationResult(LocationResult locationResult) {
                                super.onLocationResult(locationResult);
                            }
                        });
                    }
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
        Log.d("MAPLOGS", "InsertgetAddress");
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
            loadOrderList();
        } else {
            Toast.makeText(getActivity(), "定位信息有誤，請重新定位！", Toast.LENGTH_SHORT);
        }
    }

    /**
     * 請求訂單列表
     */
    private void loadOrderList() {
        final ProgressDialog dialog = new ProgressDialog(getActivity());
        dialog.setTitle("系統");
        dialog.setMessage("正在獲取最新訂單列表中......");
        dialog.setCancelable(false);
        dialog.show();
        orderMachineUnknowList.clear();
        orderMaachineKnownList.clear();
        RequestParams params = new RequestParams(CHConfigtor.BASE_URL + CHConfigtor.GET_ORDER_LIST);
        params.setAsJsonContent(true);
        JSONObject jsonObj = new JSONObject();
        try {
            jsonObj.put("qstoken", SPUtils.get(getActivity(), "qsUserToken", ""));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String urlJson = jsonObj.toString();
        params.setBodyContent(urlJson);
        String uri = params.getUri();
        params.setConnectTimeout(30 * 1000);
        x.http().request(HttpMethod.POST ,params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                OrderListOutModel model = new Gson().fromJson(result.toString(), OrderListOutModel.class);
                if (model.getCode() == 200) {
                    orderList = model.getData();
                    refreshNewMarkerList();
                    Toast.makeText(getActivity(), "獲取最新訂單列表成功！", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "獲取最新訂單列表失敗！請重新試"+ String.valueOf(model.getMsg()), Toast.LENGTH_SHORT).show();
                }
            }
            //请求异常后的回调方法
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                if (ex instanceof java.net.SocketTimeoutException) {
                    Toast.makeText(getActivity(), "網絡連接超時，請重試" + String.valueOf(ex.getStackTrace()), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "獲取最新訂單列表失敗！請重新試" + String.valueOf(ex.getStackTrace()), Toast.LENGTH_SHORT).show();
                }
            }
            //主动调用取消请求的回调方法
            @Override
            public void onCancelled(CancelledException cex) {
            }
            @Override
            public void onFinished() {
                dialog.dismiss();
            }
        });
    }

    /**
     * 刷新界面中的所以marker
     */
    private void refreshNewMarkerList() {
        // 清空之前的marker
        mGoogleMap.clear();
        List<OrderListOutModel.DataBean> orderUrgencyList = new ArrayList<>();
        // 添加新的marker集合到界面
        for (int i = 0; i < orderList.size(); i++) {
            if (CHCommonUtils.isToday(orderList.get(i).getOrder().getStartSlotTime())) {    // 判断时候是今天的订单，不是今天的订单不处理
                if (orderList.get(i).getOrder().getMachineNo() != null) {
                    MarkerOptions options = new MarkerOptions().position(new LatLng(orderList.get(i).getSoltMachine().getLatitude(), orderList.get(i).getSoltMachine().getLongitude()));
                    options.title("地址:" + String.valueOf(orderList.get(i).getSoltMachine().getAddress()));
                    String no = orderList.get(i).getSoltMachine().getMachineNo();
                    long timeDiff = CHCommonUtils.compareTimestamps(orderList.get(i).getOrder().getStartSlotTime());
                    Log.d("timeDiff", "timeDiff1" + String.valueOf(timeDiff));
                    Log.d("timeDiff", "orderMaachineKnownListSize=" + orderMaachineKnownList.size());
                    for (int j = 0; j < orderMaachineKnownList.size(); j++) {
                        if (orderMaachineKnownList.get(j).getOrder().getMachineNo().equals(orderList.get(i).getOrder().getMachineNo())) {
                            long cacheTimeDiff = CHCommonUtils.compareTimestamps(orderMaachineKnownList.get(j).getOrder().getStartSlotTime());
                            Log.d("timeDiff", "cacheTimeDiff" + String.valueOf(cacheTimeDiff));
                            if(timeDiff < cacheTimeDiff) {  // 當前訂單的時間比已知列表中同一個咪錶下的訂單小，則目前緊急度較小，將已知列表中同一個咪錶小的訂單作為緊急訂單，時間差改為cacheTimeDiff
                                timeDiff = cacheTimeDiff;
                            }
                        }
                    }
                    Log.d("timeDiff", "timeDiff2" + String.valueOf(timeDiff));

                    if (timeDiff > -30) {
                        options.icon(BitmapDescriptorFactory.fromResource(R.mipmap.drawing_pin));
                    } else if (timeDiff > -60){
                        options.icon(BitmapDescriptorFactory.fromResource(R.mipmap.drawing_pin_y));
                    } else {
                        options.icon(BitmapDescriptorFactory.fromResource(R.mipmap.drawing_pin_g));
                    }
                    Marker marker = mGoogleMap.addMarker(options);
                    marker.setTag(orderList.get(i).getSoltMachine().getMachineNo());
                    orderMaachineKnownList.add(orderList.get(i));
                } else {    // 当时未知订单加入未知订单列表
                    orderMachineUnknowList.add(orderList.get(i));
                }
            }
        }

        // 按时间的快慢排序list
        Collections.sort(orderMaachineKnownList, new ValComparator());
        latLng = new LatLng(mLastLocation.getLatitude(),mLastLocation.getLongitude());

        // 增加路线
        if (orderMaachineKnownList.size() > 0) {
            // 拼接googlemap路线请求地址
            mapurl = CHCommonUtils.getDirectionsUrl(getActivity(), latLng, new LatLng(orderMaachineKnownList.get(0).getSoltMachine().getLatitude(), orderMaachineKnownList.get(0).getSoltMachine().getLongitude()));
            // 請求獲取路線
            request();
        }

        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16F));
        // 加载完成地图及图钉检查是否有未完成订单
        checkHasUnfinishOrder();
    }

    private void request() {
        RequestParams params = new RequestParams(mapurl);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                String s1 = result.toString();
                Message message = new Message();
                message.obj = s1;
                mHandler2.sendMessage(message);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(getApplicationContext(), "路線獲取失敗！", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    /**
     * 获取路线
     * @param tag
     */
    private void getGoogleMapLine(String tag) {
        double latitude = 0.0;
        double longitude = 0.0;
        for (int i = 0; i < orderMaachineKnownList.size(); i++) {
            if (orderMaachineKnownList.get(i).getSoltMachine().getMachineNo().equals(tag)) {
                latitude = orderMaachineKnownList.get(i).getSoltMachine().getLatitude();
                longitude = orderMaachineKnownList.get(i).getSoltMachine().getLongitude();
                break;
            }
        }
        mapurl = CHCommonUtils.getDirectionsUrl(getActivity(), latLng, new LatLng(latitude, longitude));
        // 請求獲取路線
        request();
    }

    /**
     * 點擊地圖上的marker，打開對應咪錶的訂單列表的視窗
     * @param machineNo
     */
    private void showOrderListPop(String machineNo) {
        //设置contentView
        View contentView = LayoutInflater.from(getActivity()).inflate(R.layout.layout_order_list, null);
        final PopupWindow mPopWindow = new PopupWindow(contentView, LinearLayout.LayoutParams.MATCH_PARENT, 1000, true);
        mPopWindow.setContentView(contentView);
        mPopWindow.setFocusable(false);
        mPopWindow.setOutsideTouchable(false);
        //设置各个控件的点击响应
        TextView machineTv = contentView.findViewById(R.id.pop_macheine_address);
        ImageView cancleIv = contentView.findViewById(R.id.pop_cancle);
        ListView popOrderList = contentView.findViewById(R.id.pop_order_list_lv);
        final List<OrderListOutModel.DataBean> orderThatMacheineList = getOrderList(machineNo);
        machineTv.setText("編        號：" + machineNo + "\t\t\t\t区域：" + orderThatMacheineList.get(0).getSoltMachine().getAreaCode() + "\n咪錶位置：" + orderThatMacheineList.get(0).getSoltMachine().getAddress());
        cancleIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopWindow.dismiss();
            }
        });
        PopOrderListAdapter adapter = new PopOrderListAdapter(orderThatMacheineList, getActivity());
        popOrderList.setAdapter(adapter);
        popOrderList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), OrderDetialActivity.class);
                intent.putExtra("orderNo", orderThatMacheineList.get(position).getOrder().getOrderNo());
                intent.putExtra("isReciverOrder", "false");
                //intent.putExtra("address", orderThatMacheineList.get(position).getSoltMachine().getAddress());
                startActivity(intent);
                mPopWindow.dismiss();
            }
        });
        //显示PopupWindow
        View rootview = LayoutInflater.from(getActivity()).inflate(R.layout.activity_main, null);
        mPopWindow.showAtLocation(rootview, Gravity.BOTTOM, 0, 0);
    }

    /**
     * 點擊導航欄上的未知咪錶訂單按鈕，打開對應未知咪錶的訂單列表的視窗
     */
    private void showUnkonwOrderListPop() {
        //设置contentView
        View contentView = LayoutInflater.from(getActivity()).inflate(R.layout.layout_order_list, null);
        final PopupWindow mPopWindow = new PopupWindow(contentView, LinearLayout.LayoutParams.MATCH_PARENT, 1000, true);
        mPopWindow.setContentView(contentView);
        //设置各个控件的点击响应
        TextView machineTv = contentView.findViewById(R.id.pop_macheine_address);
        ImageView cancleIv = contentView.findViewById(R.id.pop_cancle);
        ListView popOrderList = contentView.findViewById(R.id.pop_order_list_lv);
        machineTv.setText("編        號：未知");
        cancleIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopWindow.dismiss();
            }
        });
        PopUnKnowOrderListAdapter adapter = new PopUnKnowOrderListAdapter(orderMachineUnknowList, getActivity());
        popOrderList.setAdapter(adapter);
        popOrderList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), OrderDetialActivity.class);
                intent.putExtra("orderNo", orderMachineUnknowList.get(position).getOrder().getOrderNo());
                intent.putExtra("isReciverOrder", "false");
                startActivity(intent);
                mPopWindow.dismiss();
            }
        });
        //显示PopupWindow
        View rootview = LayoutInflater.from(getActivity()).inflate(R.layout.activity_main, null);
        mPopWindow.showAtLocation(rootview, Gravity.BOTTOM, 0, 0);
    }

    /**
     * 查詢打開的咪錶訂單列表視窗內的訂單列表
     * @param machineNo
     */
    private List<OrderListOutModel.DataBean> getOrderList(String machineNo) {
        List<OrderListOutModel.DataBean> list = new ArrayList<>();
        for (OrderListOutModel.DataBean model : orderMaachineKnownList) {
            if (model.getOrder().getMachineNo() != null) {
                if (model.getSoltMachine().getMachineNo().equals(machineNo)) {
                    list.add(model);
                }
            }
        }
        return list;
    }

    /**
     * 檢查是否有未完成的訂單
     */
    private void checkHasUnfinishOrder() {
        final ProgressDialog dialog = new ProgressDialog(getActivity());
        dialog.setTitle("系統");
        dialog.setMessage("檢測是否有接嘅單......");
        dialog.setCancelable(false);
        dialog.show();
        RequestParams params = new RequestParams(CHConfigtor.BASE_URL + CHConfigtor.CHECK_UNFINISH);
        params.setAsJsonContent(true);
        JSONObject jsonObj = new JSONObject();
        try {
            jsonObj.put("qstoken",SPUtils.get(getActivity(), "qsUserToken", ""));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String urlJson = jsonObj.toString();
        params.setBodyContent(urlJson);
        String uri = params.getUri();
        params.setConnectTimeout(30 * 1000);
        x.http().request(HttpMethod.POST ,params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                CheckUnfinishModel model = new Gson().fromJson(result.toString(), CheckUnfinishModel.class);
                if (model.getCode() == 200) {
                    Toast.makeText(getActivity(), "您暫無未完成訂單！", Toast.LENGTH_SHORT).show();
                } else if (model.getCode() == 70008){
                    Intent intent = new Intent(getActivity(), OrderDetialActivity.class);
                    intent.putExtra("orderNo", model.getData().getOrderNo());
                    intent.putExtra("isReciverOrder", "true");
                    startActivity(intent);
                    Toast.makeText(getActivity(), "您有未完成訂單！請完成先", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "獲取未完成訂單失敗！請重新提交" + String.valueOf(model.getMsg()), Toast.LENGTH_SHORT).show();
                }
            }
            //请求异常后的回调方法
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                if (ex instanceof java.net.SocketTimeoutException) {
                    Toast.makeText(getActivity(), "網絡連接超時，請重試" + String.valueOf(ex.getStackTrace()), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "獲取投幣手工作區域失敗！請重新提交" + String.valueOf(ex.getStackTrace()), Toast.LENGTH_SHORT).show();
                }
            }
            //主动调用取消请求的回调方法
            @Override
            public void onCancelled(CancelledException cex) {
            }
            @Override
            public void onFinished() {
                dialog.dismiss();
            }
        });
    }

    @Subscribe
    public void onEvent(String msg){
        if (msg.equals("FINISH_ORDER_ONCE")) {
            refreshOrderList();
        } else {
        }
    }
}
