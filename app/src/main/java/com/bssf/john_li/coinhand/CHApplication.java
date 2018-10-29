package com.bssf.john_li.coinhand;

import android.app.Application;
import android.support.multidex.MultiDexApplication;

import com.bssf.john_li.coinhand.CHUtils.CrashHandler;

import org.greenrobot.eventbus.EventBus;
import org.xutils.x;

import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

import static android.content.Intent.ACTION_DELETE;

/**
 * Created by John_Li on 17/4/2018.
 */

public class CHApplication extends MultiDexApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        CrashHandler.getInstance().init(this);
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
    }
}
