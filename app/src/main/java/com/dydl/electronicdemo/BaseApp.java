package com.dydl.electronicdemo;

import android.app.Application;
import android.content.Context;

import com.dydl.socketlib.OkSocket;
import com.dydl.socketlib.helper.OkParams;
import com.elvishew.xlog.LogConfiguration;
import com.elvishew.xlog.LogLevel;
import com.elvishew.xlog.XLog;

/**
 * Created by lwx on 2018/3/12.
 */

public class BaseApp extends Application {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this.getApplicationContext();
        // TODO 检查更新
        //添加secket方法
        OkSocket.getInstance(mContext);
        XLog.init(/*BuildConfig.DEBUG ? */LogLevel.ALL /*: LogLevel.NONE*/
                , new LogConfiguration.Builder().b().build());



    }

    public static Context getContext() {
        return mContext;
    }

}
