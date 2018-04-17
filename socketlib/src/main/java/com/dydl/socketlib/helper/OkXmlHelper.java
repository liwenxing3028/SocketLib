package com.dydl.socketlib.helper;

import com.dydl.socketlib.callback.OkCallBack;
import com.dydl.socketlib.common.Constants;
import com.dydl.socketlib.error.ApiException;
import com.dydl.socketlib.error.CodeException;
import com.dydl.socketlib.error.FactoryException;
import com.dydl.socketlib.error.RetryWithDelay;
import com.dydl.socketlib.error.SocketTimeException;
import com.dydl.socketlib.model.OkStrBean;
import com.dydl.socketlib.model.OkXmlBean;
import com.dydl.socketlib.utils.StringUtil;
import com.elvishew.xlog.XLog;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by lwx on 2018/3/14.
 */

public class OkXmlHelper {


    public static void sendCreateDataString(OkXmlBean bean, int port, OkCallBack listener) {

        createSocketXml(bean, port)
                .subscribe(okBean -> {
                    XLog.d(okBean.getValue());
                    listener.onSuccess(okBean.getValue());
                }, throwable -> {
                    XLog.d(throwable.getMessage());
                    if (throwable instanceof ApiException) {
                        listener.onError((ApiException) throwable);
                    } else if (throwable instanceof SocketTimeoutException) {
                        SocketTimeException exception = (SocketTimeException) throwable;
                        listener.onError(new ApiException(exception, CodeException.RUNTIME_ERROR, exception.getMessage()));
                    } else {
                        listener.onError(new ApiException(throwable, CodeException.UNKNOWN_ERROR, throwable.getMessage()));
                    }
                });
    }

    public static Observable<OkStrBean> createSocketXml(OkXmlBean bean, int port) {
        return Observable
                .just(GetDataUp(bean))
                .flatMap(s -> createIpObservable(s, port))
                .flatMap(okBean -> createResultXml(okBean))
                .delaySubscription(500, TimeUnit.MILLISECONDS)
                .retryWhen(new RetryWithDelay())
                .onErrorResumeNext(throwable -> {
                    return Observable.error(FactoryException.analysisExcetpion(throwable));
                })
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
                        subscriber.onError(e);//"未知错误"
                    }
                });
    }

    public static Observable<OkStrBean> createIpObservable(String sendStr, int port) {
        return Observable.create(subscriber -> {
            try {
                Socket socket = new Socket();
                socket.connect(new InetSocketAddress(Constants.getServerIp(), port), 10000);
                PrintWriter writer = new PrintWriter(new BufferedWriter(
                        new OutputStreamWriter(socket.getOutputStream(), "GBK")));
                writer.print(sendStr);
                writer.flush();
                subscriber.onNext(new OkStrBean().setSocket(socket)
                        .setValue(sendStr));
                subscriber.onComplete();
            } catch (IOException e) {
                subscriber.onError(e);//"上传失败"
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
