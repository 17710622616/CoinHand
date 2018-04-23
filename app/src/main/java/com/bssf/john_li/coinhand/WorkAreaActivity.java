package com.bssf.john_li.coinhand;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.bssf.john_li.coinhand.CHModel.GetWorkAreaOutModel;
import com.bssf.john_li.coinhand.CHUtils.CHConfigtor;
import com.bssf.john_li.coinhand.CHUtils.SPUtils;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.Arrays;
import java.util.List;

/**
 * Created by John_Li on 18/4/2018.
 */

public class WorkAreaActivity extends BaseActivity implements View.OnClickListener {
    private ImageView backIv, refreshIv;
    private ListView workAreaLv;
    private LinearLayout noDataLL;

    private List<String> areaList;
    private ArrayAdapter<String> mAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_area);
        initView();
        setListener();
        initData();
    }

    @Override
    public void initView() {
        backIv = findViewById(R.id.work_area_back);
        refreshIv = findViewById(R.id.work_area_refresh);
        workAreaLv = findViewById(R.id.work_area_lv);
        noDataLL = findViewById(R.id.no_work_area_ll);
    }

    @Override
    public void setListener() {
        backIv.setOnClickListener(this);
        refreshIv.setOnClickListener(this);
    }

    @Override
    public void initData() {
        String areaStr = SPUtils.get(this, "qsWorkArea", "[ ]").toString();
        String[] areaArr = new Gson().fromJson(areaStr, String[].class);
        areaList = Arrays.asList(areaArr);
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, areaList);
        workAreaLv.setAdapter(mAdapter);
        if (areaArr.length > 0) {
            noDataLL.setVisibility(View.GONE);
        } else {
            noDataLL.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.work_area_back:
                finish();
                break;
            case R.id.work_area_refresh:
                callNetGetWorkAreaData();
                break;
        }
    }

    private void callNetGetWorkAreaData() {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setTitle("系統");
        dialog.setMessage("正在獲取投幣手工作區域中......");
        dialog.setCancelable(false);
        dialog.show();
        RequestParams params = new RequestParams(CHConfigtor.BASE_URL + CHConfigtor.WORK_AREA);
        params.setAsJsonContent(true);
        JSONObject jsonObj = new JSONObject();
        try {
            jsonObj.put("qstoken",SPUtils.get(this, "qsUserToken", ""));
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
                GetWorkAreaOutModel model = new Gson().fromJson(result.toString(), GetWorkAreaOutModel.class);
                if (model.getCode() == 200) {
                    areaList = model.getData();
                    SPUtils.put(WorkAreaActivity.this, "qsWorkArea", new Gson().toJson(areaList));
                    Toast.makeText(WorkAreaActivity.this, "獲取投幣手工作區域成功！", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(WorkAreaActivity.this, "獲取投幣手工作區域失敗！請重新提交" + String.valueOf(model.getMsg()), Toast.LENGTH_SHORT).show();
                }
            }
            //请求异常后的回调方法
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                if (ex instanceof java.net.SocketTimeoutException) {
                    Toast.makeText(WorkAreaActivity.this, "網絡連接超時，請重試", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(WorkAreaActivity.this, "獲取投幣手工作區域失敗！請重新提交", Toast.LENGTH_SHORT).show();
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
