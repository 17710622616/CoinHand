package com.bssf.john_li.coinhand;

import android.support.v4.app.FragmentActivity;

/**
 * Created by John_Li on 20/1/2018.
 */

public abstract class BaseActivity extends FragmentActivity {
    public abstract void initView();
    public abstract void setListener();
    public abstract void initData();
}
