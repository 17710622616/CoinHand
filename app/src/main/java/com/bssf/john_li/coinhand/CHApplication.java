package com.bssf.john_li.coinhand;

import android.app.Application;
import android.support.multidex.MultiDexApplication;

import com.bssf.john_li.coinhand.CHUtils.CrashHandler;

import org.greenrobot.eventbus.EventBus;
import org.xutils.x;

/**
 * Created by John_Li on 17/4/2018.
 */

public class CHApplication extends MultiDexApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        CrashHandler.getInstance().init(this);
    }
}
