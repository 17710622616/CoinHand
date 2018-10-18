package com.bssf.john_li.coinhand;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bssf.john_li.coinhand.CHAdapter.OrderOperationRecordAdapter;
import com.bssf.john_li.coinhand.CHAdapter.PhotoAdapter;
import com.bssf.john_li.coinhand.CHAdapter.PopOrderListAdapter;
import com.bssf.john_li.coinhand.CHFragment.InsertCoinsFragment;
import com.bssf.john_li.coinhand.CHModel.OrderDetialOutModel;
import com.bssf.john_li.coinhand.CHModel.OrderListOutModel;
import com.bssf.john_li.coinhand.CHUtils.CHCommonUtils;
import com.bssf.john_li.coinhand.CHUtils.CHConfigtor;
import com.bssf.john_li.coinhand.CHUtils.SPUtils;
import com.bssf.john_li.coinhand.CHView.NoScrollGridView;
import com.bssf.john_li.coinhand.CHView.NoScrollLinearLayout;
import com.bssf.john_li.coinhand.CHView.NoScrollListView;
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
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by John_Li on 8/5/2018.
 */

public class OrderDetialActivity extends BaseActivity implements View.OnClickListener, OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private LinearLayout loadingLL;
    private ScrollView mScrollView;

    private LocationRequest mLocationRequest;
    private GoogleApiClient mGoogleApiClient;
    private LatLng latLng;
    private GoogleMap mGoogleMap;
    private SupportMapFragment mMapFragment;
    private Marker mCurrLocation;
    private LocationManager mLocationManager;
    private Location mLastLocation = null;
    private static final int REQUESTCODE = 6001;
    private NoScrollLinearLayout mNoScrollLinearLayout;
    // 投手记录列表
    //private NoScrollListView oparetionRecordLv;
    private NoScrollGridView order_img_gv;
    private ImageView backIv, submitIv, loadingIv;
    private TextView orderNoTv, addressTv, carNoTv, carTypeTv, startSlotTimeTv, moneyEverytimeTv, nextSlottimeTv, receiverOrderTv, machineNoTv, areaTv, isRecieverTv, loadingTv;

    private String orderNo;
    //private String mAddress;
    // 是否接單成功
    private boolean isReciverOrder = false;
    private OrderDetialOutModel.DataBean.OrderBean mOrderDetialModel;
    private OrderDetialOutModel.DataBean.SoltMachineBean mSoltMachineBean;
    private OrderDetialOutModel.DataBean.CarBean mCarBean;
    private int currentToubiAmount;
    private List<OrderDetialOutModel.DataBean.ToushouRecordListBean> mToushouRecordList;
    private OrderOperationRecordAdapter mOrderOperationRecordAdapter;
    private List<String> mPhotoList;
    private PhotoAdapter mPhotoAdapter;
    private ImageOptions options = new ImageOptions.Builder().setSize(0, 0).setFailureDrawableId(R.mipmap.load_img_fail).build();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detial);
        initView();
        setListener();
        initData();
    }

    @Override
    public void initView() {
        mScrollView = findViewById(R.id.order_detial_sv);
        backIv = findViewById(R.id.order_detial_back);
        submitIv = findViewById(R.id.order_detial_submit);
        orderNoTv = findViewById(R.id.order_detial_orderno);
        addressTv = findViewById(R.id.order_detial_address);
        carNoTv = findViewById(R.id.order_detial_carno);
        carTypeTv = findViewById(R.id.order_detial_cartype);
        startSlotTimeTv = findViewById(R.id.order_detial_start_slottime);
        moneyEverytimeTv = findViewById(R.id.order_detial_money_ervery);
        nextSlottimeTv = findViewById(R.id.order_detial_next_slottime);
        receiverOrderTv = findViewById(R.id.order_detial_receiver_tv);
        machineNoTv = findViewById(R.id.order_detial_machine_no);
        areaTv = findViewById(R.id.order_detial_area);
        isRecieverTv = findViewById(R.id.order_detial_isreciever_order);
        loadingIv = findViewById(R.id.order_detial_load_iv);
        loadingTv = findViewById(R.id.order_detial_load_tv);
        loadingLL = findViewById(R.id.order_detial_loading);
        //oparetionRecordLv = findViewById(R.id.order_detial_lv);
        order_img_gv = findViewById(R.id.order_img_gv);
        mNoScrollLinearLayout = findViewById(R.id.order_detial_map_view_ll);
        mNoScrollLinearLayout.setScrollView(mScrollView);

        mMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.order_detial_map_view);
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
        backIv.setOnClickListener(this);
        submitIv.setOnClickListener(this);
        receiverOrderTv.setOnClickListener(this);
    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        orderNo = intent.getStringExtra("orderNo");
        orderNoTv.setText("訂單編號：" + String.valueOf(orderNo));
        if (intent.getStringExtra("isReciverOrder").equals("true")) {
            backIv.setVisibility(View.GONE);
            isReciverOrder = true;
            isRecieverTv.setVisibility(View.VISIBLE);
        }
        /*if (intent.getStringExtra("address") != null) {
            mAddress = intent.getStringExtra("address");
        }*/
        mToushouRecordList = new ArrayList<>();
        mOrderOperationRecordAdapter = new OrderOperationRecordAdapter(mToushouRecordList, this);
        //oparetionRecordLv.setAdapter(mOrderOperationRecordAdapter);
        mPhotoList = new ArrayList<>();
        mPhotoAdapter = new PhotoAdapter(this, mPhotoList);
        order_img_gv.setAdapter(mPhotoAdapter);
        callNetGetOredrDetial();
        mLocationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
    }

    private void callNetGetOredrDetial() {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setTitle("系統");
        dialog.setMessage("正在獲取訂單詳情中......");
        dialog.setCancelable(false);
        dialog.show();
        RequestParams params = new RequestParams(CHConfigtor.BASE_URL + CHConfigtor.GET_ORDER_DETAIL);
        params.setAsJsonContent(true);
        JSONObject jsonObj = new JSONObject();
        try {
            jsonObj.put("qstoken", SPUtils.get(this, "qsUserToken", ""));
            jsonObj.put("orderNo", orderNo);
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
                OrderDetialOutModel model = new Gson().fromJson(result.toString(), OrderDetialOutModel.class);
                if (model.getCode() == 200) {
                    Toast.makeText(OrderDetialActivity.this, "獲取訂單詳情成功！", Toast.LENGTH_SHORT).show();
                    mOrderDetialModel = model.getData().getOrder();
                    mSoltMachineBean = model.getData().getSoltMachine();
                    mCarBean = model.getData().getCar();
                    currentToubiAmount = model.getData().getCurrentToubiAmount();
                    mToushouRecordList = model.getData().getToushouRecordList();
                    refreshUI();
                    loadingLL.setVisibility(View.GONE);
                } else {
                    Toast.makeText(OrderDetialActivity.this, "獲取訂單詳情失敗！" + String.valueOf(model.getMsg()), Toast.LENGTH_SHORT).show();
                    orderDetialLoadFail();
                }
            }
            //请求异常后的回调方法
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                if (ex instanceof java.net.SocketTimeoutException) {
                    Toast.makeText(OrderDetialActivity.this, "網絡連接超時，請重試", Toast.LENGTH_SHORT).show();
                    orderDetialLoadFail();
                } else {
                    Toast.makeText(OrderDetialActivity.this, "獲取訂單詳情失敗！請重新試", Toast.LENGTH_SHORT).show();
                    orderDetialLoadFail();
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
     * 獲取訂單詳情成功刷新界面
     */
    private void refreshUI() {
        if (mCarBean != null) {
            carNoTv.setText("車牌號碼：" + mCarBean.getCarNo());
        }
        if (mSoltMachineBean != null) {
            addressTv.setText("地        址：" + mSoltMachineBean.getAddress());
        }
        moneyEverytimeTv.setText("訂單未投金額：" + String.valueOf(currentToubiAmount));
        startSlotTimeTv.setText("開始投幣時間：" + CHCommonUtils.stampToDate(String.valueOf(mOrderDetialModel.getStartSlotTime())));
        nextSlottimeTv.setText("下次投幣時間：" + CHCommonUtils.stampToDate(String.valueOf(mOrderDetialModel.getStartSlotTime())));
        machineNoTv.setText("咪錶編號：" + mOrderDetialModel.getMachineNo() + "：車位" + mOrderDetialModel.getParkingSpace());
        areaTv.setText("區域編號：" + mOrderDetialModel.getAreaCode());
        switch (mOrderDetialModel.getCarType()) {
            case 1:
                carTypeTv.setText("車輛類型：輕重型摩托車");
                break;
            case 2:
                carTypeTv.setText("車輛類型：輕型汽車");
                break;
            case 3:
                carTypeTv.setText("車輛類型：重型汽車");
                break;
        }
        if (mOrderDetialModel.getImg1() != null) {
            mPhotoList.add(mOrderDetialModel.getImg1());
        }
        if (mOrderDetialModel.getImg2() != null) {
            mPhotoList.add(mOrderDetialModel.getImg2());
        }
        if (mOrderDetialModel.getImg3() != null) {
            mPhotoList.add(mOrderDetialModel.getImg3());
        }
        if (mOrderDetialModel.getImg4() != null) {
            mPhotoList.add(mOrderDetialModel.getImg4());
        }
        if (mOrderDetialModel.getImg5() != null) {
            mPhotoList.add(mOrderDetialModel.getImg5());
        }

        mPhotoAdapter.notifyDataSetChanged();
        mOrderOperationRecordAdapter.notifyDataSetChanged();
    }

    private void orderDetialLoadFail() {
        loadingIv.setImageResource(R.mipmap.loaction_fail);
        loadingTv.setText("獲取訂單詳情失敗，請重試！");
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (!isReciverOrder) {
                Toast.makeText(this, "您尚未未完成此訂單，請先完成后退出！", Toast.LENGTH_LONG).show();
                return false;
            } else {
                return true;
            }
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.order_detial_back:    // 返回
                finish();
                break;
            case R.id.order_detial_submit:  // 提交單次投幣完成
                if (true) { //isReciverOrder
                    showOrderAmountDialog();
                } else {
                    Toast.makeText(this, "您尚未提交接單操作，請先接單！", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.order_detial_receiver_tv:  // 提交單次投幣接單操作
                if (isReciverOrder == false) {
                    callNetReciverOrderOnce();
                } else {
                    Toast.makeText(this, "您已接過此單，請唔重複接單！", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    /**
     *  接單操作
     */
    private void callNetReciverOrderOnce() {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setTitle("系統");
        dialog.setMessage("正在接單中......");
        dialog.setCancelable(false);
        dialog.show();
        RequestParams params = new RequestParams(CHConfigtor.BASE_URL + CHConfigtor.TOUSHOU_RECIEVER_ORDER_ONCE);
        params.setAsJsonContent(true);
        JSONObject jsonObj = new JSONObject();
        try {
            jsonObj.put("qstoken", SPUtils.get(this, "qsUserToken", ""));
            jsonObj.put("orderNo", orderNo);
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
                OrderDetialOutModel model = new Gson().fromJson(result.toString(), OrderDetialOutModel.class);
                if (model.getCode() == 200) {
                    isReciverOrder = true;
                    isRecieverTv.setVisibility(View.VISIBLE);
                    backIv.setVisibility(View.GONE);
                    Toast.makeText(OrderDetialActivity.this, "接單成功！", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(OrderDetialActivity.this, "接單失敗！" + String.valueOf(model.getMsg()), Toast.LENGTH_SHORT).show();
                }
            }
            //请求异常后的回调方法
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                if (ex instanceof java.net.SocketTimeoutException) {
                    Toast.makeText(OrderDetialActivity.this, "網絡連接超時，請重試", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(OrderDetialActivity.this, "接單失敗！請重新試", Toast.LENGTH_SHORT).show();
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
     * 顯示填寫金額的dialog
     */
    private void showOrderAmountDialog() {
        View view = null;
        AlertDialog.Builder changeUserDialog = new AlertDialog.Builder(this);
        changeUserDialog.setTitle("請填寫投幣金額，不超過MOP" + String.valueOf(currentToubiAmount));
        view = LayoutInflater.from(this).inflate(R.layout.dialog_order_amount_insert, null);
        changeUserDialog.setView(view);
        Dialog d = changeUserDialog.create();
        // 初始化dialog中的控件
        final EditText amountEt = view.findViewById(R.id.dialog_order_amount_et);
        Button amountSubmit = view.findViewById(R.id.dialog_order_amount_submit);
        amountSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    double amountSubmit = Double.parseDouble(amountEt.getText().toString());
                    if (0 < amountSubmit && amountSubmit <= (double)currentToubiAmount) {
                        callNetSubmitInsertCoinOnce(amountSubmit);
                    } else {
                        Toast.makeText(OrderDetialActivity.this, "請填寫正確金額！", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(OrderDetialActivity.this, "請填寫正確金額！", Toast.LENGTH_SHORT).show();
                }
            }
        });
        d.show();
    }

    /**
     * 完成當次投幣
     * @param amountSubmit
     */
    private void callNetSubmitInsertCoinOnce(double amountSubmit) {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setTitle("系統");
        dialog.setMessage("正在完成訂單中......");
        dialog.setCancelable(false);
        dialog.show();
        RequestParams params = new RequestParams(CHConfigtor.BASE_URL + CHConfigtor.FINISH_INSERT_COINS_ORDER_ONCE);
        params.setAsJsonContent(true);
        JSONObject jsonObj = new JSONObject();
        try {
            jsonObj.put("qstoken", SPUtils.get(this, "qsUserToken", ""));
            jsonObj.put("orderNo", orderNo);
            jsonObj.put("amount", String.valueOf(amountSubmit));
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
                OrderDetialOutModel model = new Gson().fromJson(result.toString(), OrderDetialOutModel.class);
                if (model.getCode() == 200) {
                    Toast.makeText(OrderDetialActivity.this, "本階段投幣成功！", Toast.LENGTH_SHORT).show();
                    EventBus.getDefault().post("FINISH_ORDER_ONCE");
                    finish();
                } else {
                    Toast.makeText(OrderDetialActivity.this, "本階段投幣失敗！" + String.valueOf(model.getMsg()), Toast.LENGTH_SHORT).show();
                }
            }
            //请求异常后的回调方法
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                if (ex instanceof java.net.SocketTimeoutException) {
                    Toast.makeText(OrderDetialActivity.this, "網絡連接超時，請重試", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(OrderDetialActivity.this, "本階段投幣失敗！請重新試", Toast.LENGTH_SHORT).show();
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

    @Override
    public void onMapReady(GoogleMap googleMap) {
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
                        Toast.makeText(OrderDetialActivity.this, "定位之前請打開GPS及網絡！", Toast.LENGTH_SHORT).show();
                    }
                    return false;
                }
            });
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
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
        mGoogleApiClient = new GoogleApiClient.Builder(this)
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
                Toast.makeText(OrderDetialActivity.this, "定位之前請打開GPS！", Toast.LENGTH_SHORT).show();
                loadMapFail();
            }

            if (!isNetworkEnabled) {
                Toast.makeText(OrderDetialActivity.this, "定位之前請打開網絡！", Toast.LENGTH_SHORT).show();
                loadMapFail();
            }
        } else {
            Toast.makeText(OrderDetialActivity.this, "定位之前請打開GPS及網絡！", Toast.LENGTH_SHORT).show();
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
                    mGoogleMap.clear();
                    latLng = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
                    mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude()), 12));
                    mLocationRequest = LocationRequest.create();
                    mLocationRequest.setInterval(5000); //5 seconds
                    mLocationRequest.setFastestInterval(3000); //3 seconds
                    mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
                    //mLocationRequest.setSmallestDisplacement(0.1F); //1/10 meter
                    // 刷新訂單列表
                    refreshMap();

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

    private void refreshMap() {
        if (mSoltMachineBean != null) {
            MarkerOptions options = new MarkerOptions().position(new LatLng(mSoltMachineBean.getLatitude(), mSoltMachineBean.getLongitude()));
            options.title("地址:" + String.valueOf(mSoltMachineBean.getAddress()));
            long timeDiff = CHCommonUtils.compareTimestamps(mOrderDetialModel.getStartSlotTime());
            if (timeDiff > -30) {
                options.icon(BitmapDescriptorFactory.fromResource(R.mipmap.drawing_pin));
            } else if (timeDiff > -60){
                options.icon(BitmapDescriptorFactory.fromResource(R.mipmap.drawing_pin_y));
            } else {
                options.icon(BitmapDescriptorFactory.fromResource(R.mipmap.drawing_pin_g));
            }
            Marker marker = mGoogleMap.addMarker(options);
        }
    }
}
