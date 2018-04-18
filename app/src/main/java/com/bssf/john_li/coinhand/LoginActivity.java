package com.bssf.john_li.coinhand;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bssf.john_li.coinhand.CHModel.CommonModel;
import com.bssf.john_li.coinhand.CHUtils.CHConfigtor;
import com.bssf.john_li.coinhand.CHUtils.DigestUtils;
import com.bssf.john_li.coinhand.CHUtils.SPUtils;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * Created by John_Li on 23/1/2018.
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private EditText nameEt, pwEt;
    private TextView loginTv;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        setListener();
        initData();
    }

    @Override
    public void initView() {
        nameEt = findViewById(R.id.login_username);
        pwEt = findViewById(R.id.login_pw);
        loginTv = findViewById(R.id.login_tv);
    }

    @Override
    public void setListener() {
        loginTv.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_tv:
                checkInfo();
                break;
        }
    }

    /**
     * 檢查用戶名密碼是否正確
     */
    private void checkInfo() {
        ProgressDialog dialog = new ProgressDialog(this);
        dialog.setTitle("提示");
        dialog.setMessage("正在登錄中......");
        dialog.setCancelable(false);
        dialog.show();
        String userName = nameEt.getText().toString();
        String passWord = pwEt.getText().toString();
        if (!userName.equals("") && !passWord.equals("")) {
            callNetLogin(userName, passWord, dialog);
        } else {
            dialog.dismiss();
            Toast.makeText(this, "登錄賬號密碼不能為空！", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * 登錄
     * @param userName
     * @param passWord
     */
    private void callNetLogin(String userName, String passWord, final ProgressDialog dialog) {
        RequestParams params = new RequestParams(CHConfigtor.BASE_URL + CHConfigtor.USER_LOGIN);
        params.setAsJsonContent(true);
        JSONObject jsonObj = new JSONObject();
        try {
            jsonObj.put("account",userName);
            jsonObj.put("pwd", DigestUtils.encryptPw(passWord));
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
                CommonModel model = new Gson().fromJson(result.toString(), CommonModel.class);
                if (model.getCode().equals("200")) {
                    SPUtils.put(LoginActivity.this, "qsUserToken", model.getData().toString());
                    Toast.makeText(LoginActivity.this, "登錄成功！", Toast.LENGTH_SHORT).show();
                    setResult(RESULT_OK);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "登錄失敗！", Toast.LENGTH_SHORT).show();
                }
            }
            //请求异常后的回调方法
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                if (ex instanceof java.net.SocketTimeoutException) {
                    Toast.makeText(LoginActivity.this, "網絡連接超時，請重試", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LoginActivity.this, "登錄失敗，請重新提交", Toast.LENGTH_SHORT).show();
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
     * 不可返回
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return false;
    }
}
