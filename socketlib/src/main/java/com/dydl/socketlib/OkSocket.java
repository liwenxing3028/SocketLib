package com.dydl.socketlib;


import android.content.Context;


import com.dydl.socketlib.callback.OkCallBack;
import com.dydl.socketlib.callback.OkResponse;
import com.dydl.socketlib.helper.OkParams;
import com.dydl.socketlib.helper.OkStrHelper;
import com.dydl.socketlib.helper.OkXmlHelper;
import com.dydl.socketlib.model.OkXmlBean;
import com.google.gson.internal.$Gson$Types;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.Socket;

/**
 * Created by lwx on 2018/1/25.
 */
public class OkSocket {

    private static OkSocket sInstance;
    private static Context mContext;  //不要使用static 可能会导致内存泄漏



    protected OkSocket(Context context) {
        mContext = context.getApplicationContext(); //获取Application的Context
    }

    //OkSocket单例对象
    public static synchronized OkSocket getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new OkSocket(context);
        }
        return sInstance;
    }

    // 发送请求字符码Bean
    public static  <T> void sendMsgBean(final OkParams bean,
                                        final int port,
                                        Class<? extends OkResponse> clazz,
                                    final OkCallBack listener) {
        OkStrHelper.<T>sendCreateData(bean, port, clazz, listener);

    }

    // 发送请求字符码String
    public static void sendMsgString(final OkParams bean, final int port,
                                     final OkCallBack<String> listener) {
        OkStrHelper.sendCreateDataString(bean, port, listener);

    }


    //发送请求Xml
    public static void sendMsgXml(final OkXmlBean bean, final int port, final OkCallBack listener) {
        listener.onStart();
        OkXmlHelper.createSocketXml(bean, port)
                .subscribe(okBean -> {
                    switch (okBean.getValue()) {

                    }
                }, throwable -> {//"上传失败"
                    //listener.onSendFailed(throwable.getMessage());
                });
    }

}
