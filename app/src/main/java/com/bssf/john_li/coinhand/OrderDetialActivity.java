package com.bssf.john_li.coinhand;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bssf.john_li.coinhand.CHAdapter.OrderOperationRecordAdapter;
import com.bssf.john_li.coinhand.CHAdapter.PhotoAdapter;
import com.bssf.john_li.coinhand.CHModel.OrderDetialOutModel;
import com.bssf.john_li.coinhand.CHUtils.CHCommonUtils;
import com.bssf.john_li.coinhand.CHUtils.CHConfigtor;
import com.bssf.john_li.coinhand.CHUtils.SPUtils;
import com.bssf.john_li.coinhand.CHView.NoScrollGridView;
import com.bssf.john_li.coinhand.CHView.NoScrollListView;
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

/**
 * Created by John_Li on 8/5/2018.
 */

public class OrderDetialActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout loadingLL;
    private NoScrollListView oparetionRecordLv;
    private NoScrollGridView order_img_gv;
    private ImageView carIv, backIv, submitIv, loadingIv;
    private TextView orderNoTv, carNoTv, carTypeTv, startSlotTimeTv, moneyEverytimeTv, nextSlottimeTv, receiverOrderTv, machineNoTv, areaTv, isRecieverTv, loadingTv;

    private String orderNo;
    // 是否接單成功
    private boolean isReciverOrder = false;
    private OrderDetialOutModel.DataBean.OrderBean mOrderDetialModel;
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
        carIv = findViewById(R.id.order_detial_car_iv);
        backIv = findViewById(R.id.order_detial_back);
        submitIv = findViewById(R.id.order_detial_submit);
        orderNoTv = findViewById(R.id.order_detial_orderno);
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
        oparetionRecordLv = findViewById(R.id.order_detial_lv);
        order_img_gv = findViewById(R.id.order_img_gv);
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
        orderNoTv.setText("訂單編號" + String.valueOf(orderNo));
        mToushouRecordList = new ArrayList<>();
        mOrderOperationRecordAdapter = new OrderOperationRecordAdapter(mToushouRecordList, this);
        oparetionRecordLv.setAdapter(mOrderOperationRecordAdapter);
        mPhotoList = new ArrayList<>();
        mPhotoAdapter = new PhotoAdapter(this, mPhotoList);
        order_img_gv.setAdapter(mPhotoAdapter);
        callNetGetOredrDetial();
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
        //x.image().bind(carIv, mOrderDetialModel.getImg1(), options);
        Picasso.with(this).load(mOrderDetialModel.getImg1()).placeholder(R.mipmap.load_img_fail).into(carIv);
        moneyEverytimeTv.setText("每次投幣金額：" + String.valueOf(currentToubiAmount));
        startSlotTimeTv.setText("開始投幣時間：" + CHCommonUtils.stampToDate(String.valueOf(mOrderDetialModel.getStartSlotTime())));
        nextSlottimeTv.setText("下次投幣時間：" + CHCommonUtils.stampToDate(String.valueOf(mOrderDetialModel.getStartSlotTime())));
        machineNoTv.setText("咪錶編號：" + mOrderDetialModel.getMachineNo());
        areaTv.setText("區域編號：" + mOrderDetialModel.getAreaCode());
        carNoTv.setText("車牌號碼：" + mOrderDetialModel.getCarId());
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.order_detial_back:    // 返回
                finish();
                break;
            case R.id.order_detial_submit:  // 提交單次投幣完成
                if (true) { //isReciverOrder
                    callNetSubmitInsertCoinOnce();
                } else {
                    Toast.makeText(this, "您尚未提交接單操作，請先接單！", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.order_detial_receiver_tv:  // 提交單次投幣接單操作
                callNetReciverOrderOnce();
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
     * 完成當次投幣
     */
    private void callNetSubmitInsertCoinOnce() {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setTitle("系統");
        dialog.setMessage("正在接單中......");
        dialog.setCancelable(false);
        dialog.show();
        RequestParams params = new RequestParams(CHConfigtor.BASE_URL + CHConfigtor.FINISH_INSERT_COINS_ORDER_ONCE);
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
}
