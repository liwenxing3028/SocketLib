package com.dydl.socketlib.helper;

import com.dydl.socketlib.common.Constants;
import com.dydl.socketlib.model.OkStrBean;
import com.dydl.socketlib.model.OkXmlBean;
import com.dydl.socketlib.utils.SharePUtils;
import com.dydl.socketlib.utils.StringUtil;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by lwx on 2018/3/14.
 */

public class OkXmlHelper {


    public static Observable<OkStrBean> createSocketXml(OkXmlBean bean, int port) {
        return Observable
                .just(GetDataUp(bean))
                .flatMap(s -> createIpObservable(s, port))
                .flatMap(okBean -> createResultXml(okBean))
                .compose(applySchedulers());
    }

    public static Observable<OkStrBean> createResultXml(OkStrBean okBean) {
        return Observable
                .create(subscriber -> {
                    try {
                        Socket socket = okBean.getSocket();
                        InputStream inputStream = socket.getInputStream();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "GBK"));
                        String line = null;
                        StringBuffer buffer = new StringBuffer();
                        while ((line = reader.readLine()) != null) {
                            buffer.append(line);
                        }
                        String value = buffer.toString();
                        subscriber.onNext(new OkStrBean()
                                .setSocket(socket)
                                .setValue(value));
                        subscriber.onComplete();
                    } catch (IOException e) {
                        subscriber.onNext(new OkStrBean().setValue("001"));//"未知错误"
                        subscriber.onComplete();
                    }
                });
    }

    public static Observable<OkStrBean> createIpObservable(String sendStr, int port) {
        return Observable.create(subscriber -> {
            String url = null;
            if (SharePUtils.isContains(Constants.SERVER_IP)) {
                url = SharePUtils.getString(Constants.SERVER_IP);
            }
            try {
                Socket socket = new Socket();
                socket.connect(new InetSocketAddress(url, port), 10000);
                PrintWriter writer = new PrintWriter(new BufferedWriter(
                        new OutputStreamWriter(socket.getOutputStream(), "UTF-8")));
                writer.print(sendStr);
                writer.flush();
                subscriber.onNext(new OkStrBean().setSocket(socket)
                        .setValue(sendStr));
                subscriber.onComplete();
            } catch (IOException e) {
                e.printStackTrace();
                subscriber.onNext(new OkStrBean()
                        .setValue("003"));//"上传失败"
                subscriber.onComplete();
            }
        });
    }

    public static String GetDataUp(OkXmlBean bean) {

        String send = bean.toString();
        int strLength = StringUtil.getLengthContainsCn(send);
        String length = StringUtil.complementBit(Integer.toString(strLength), "0", 5, 1);
        send = length + send;
        return send;
    }


    public static <T> ObservableTransformer<T, T> applySchedulers() {
        return observable -> observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
