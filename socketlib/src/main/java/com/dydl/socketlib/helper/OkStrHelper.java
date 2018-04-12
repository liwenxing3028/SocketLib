package com.dydl.socketlib.helper;

import android.util.Log;
import com.dydl.socketlib.callback.OkCallBack;
import com.dydl.socketlib.callback.OkResponse;
import com.dydl.socketlib.common.Constants;
import com.dydl.socketlib.error.ApiException;
import com.dydl.socketlib.error.CodeException;
import com.dydl.socketlib.error.FactoryException;
import com.dydl.socketlib.error.RetryWithDelay;
import com.dydl.socketlib.error.SocketTimeException;
import com.dydl.socketlib.model.OkStrBean;
import com.dydl.socketlib.utils.SharePUtils;
import com.dydl.socketlib.utils.StringUtil;
import com.elvishew.xlog.XLog;
import com.google.gson.Gson;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeUnit;
import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by lwx on 2018/3/14.
 */

public class OkStrHelper {


    protected static CompositeDisposable mCompositeDisposable = new CompositeDisposable();


    // 发送请求字符码
    public static <T> void sendCreateData(final OkParams bean, final int port, final Class<? extends OkResponse> clazz,
                                          final OkCallBack listener) {
        listener.onStart();

        addDisposable(createSocket(bean, port)
                .subscribe(okBean -> {
                    XLog.d(okBean.getValue());
                    Gson gson = new Gson();
                    T result = gson.fromJson(changeStringToBean(okBean.getValue()), (Class<T>) clazz);
                    listener.onSuccess(result);
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
                }));
    }


    // 发送请求字符码
    public static <T> void sendCreateDataString(final OkParams bean, final int port,
                                                final OkCallBack listener) {
        listener.onStart();
        addDisposable(createSocket(bean, port)
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
                }));
    }


    public static Observable<OkStrBean> createSocket(OkParams bean, int port) {
        return Observable
                .just(getDataUp(bean))
                .flatMap(s -> createIpObservable(s, port))
                .flatMap(okBean -> createResultData(okBean))
                .flatMap(s -> createResultDataMsg(s))
                .delaySubscription(500, TimeUnit.MILLISECONDS)
                .retryWhen(new RetryWithDelay())
                .onErrorResumeNext(throwable -> {
                    return Observable.error(FactoryException.analysisExcetpion(throwable));
                })
                .compose(applySchedulers())
                ;
    }

    private static Observable<OkStrBean> createIpObservable(String sendStr, int port) {
        return Observable.create(subscriber -> {
            try {
                Socket socket = new Socket();
                socket.connect(new InetSocketAddress(Constants.getServerIp(), port), 10000);
                PrintWriter writer = new PrintWriter(new BufferedWriter(
                        new OutputStreamWriter(socket.getOutputStream(), "UTF-8")));
                writer.print(sendStr);
                writer.flush();
                subscriber.onNext(new OkStrBean().setSocket(socket)
                        .setValue(sendStr));
                subscriber.onComplete();
            } catch (IOException e) {
                subscriber.onError(e);
            }
        });
    }

    private static String changeStringToBean(String msg) {
        String[] data = msg.split("\\|");
        StringBuilder buffer = new StringBuilder();
        buffer.append("{");
        for (int j = 0; j < data.length; j++) {
            if (data[j].contains(";") && data[j].contains("&")) {
                buffer.append("\"" + j + "\"" + ": [ ");
                String[] datas = data[j].split("\\;");
                for (int i = 0; i < datas.length; i++) {

                    buffer.append("{");
                    String value = datas[i];
                    if (value.contains("&")) {
                        String[] d = value.split("\\&");
                        for (int k = 0; k < d.length; k++) {
                            if (k == d.length - 1) {
                                buffer.append("\"" + k + "\"" + ":" + "\"" + d[k] + "\"");
                            } else {
                                buffer.append("\"" + k + "\"" + ":" + "\"" + d[k] + "\"" + ",");
                            }
                        }
                    }
                    if (i == datas.length - 1) {
                        buffer.append("}");
                    } else {
                        buffer.append("},");
                    }
                }
                if (j == data.length - 1) {
                    buffer.append("]");
                } else {
                    buffer.append("],");
                }
            } else {

                if (j == data.length - 1) {
                    buffer.append("\"" + j + "\"" + ":" + "\"" + data[j] + "\"");
                } else {
                    buffer.append("\"" + j + "\"" + ":" + "\"" + data[j] + "\"" + ",");
                }
            }
        }
        buffer.append("}");
        Log.e("1212", buffer.toString());//获取数据处理
        return buffer.toString();
    }

    private static String getDataUp(OkParams bean) {
        XLog.d(bean.toString());
        StringBuilder builder = new StringBuilder();
        builder.append(bean.toGetParams());
        int strLength = StringUtil.getLengthContainsCn(builder.toString(), "UTF-8");
        String length = StringUtil.complementBit(Integer.toString(strLength),
                "0", 5, 1);
        builder.insert(0, length);
        String sendStr = builder.toString();
        return sendStr;
    }

    public static <T> ObservableTransformer<T, T> applySchedulers() {
        return observable -> observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private static Observable<OkStrBean> createResultData(OkStrBean okBean) {
        return Observable
                .create(subscriber -> {
                    try {
                        Socket socket = okBean.getSocket();
                        InputStream in = socket.getInputStream();
                        DataInputStream dis = new DataInputStream(in);
                        socket.setSoTimeout(10000);
                        byte[] valueByte = new byte[15360];
                        int size;
                        while (true) {
                            try {
                                if ((size = dis.read(valueByte)) > 0 || size == -1) {
                                    String hexValue = StringUtil.bytesToHexStr(valueByte, size == -1 ? 0 : size);
                                    String value = StringUtil.strHexToStr(hexValue, "UTF-8");
                                    //TODO****数据长度过长可能出现接收到的数据不全，没有报文结尾符"|"后期需注意规避
                                    subscriber.onNext(new OkStrBean()
                                            .setSocket(socket)
                                            .setValue(value));
                                    subscriber.onComplete();
                                    dis.close();
                                    in.close();
                                    return;
                                }
                            } catch (SocketTimeoutException e) {
                                subscriber.onError(e);//"等待响应超时!"
                                return;
                            }
                        }
                    } catch (IOException e) {
                        subscriber.onError(e);//"未知错误"
                    }
                });
    }

    private static Observable<OkStrBean> createResultDataMsg(OkStrBean okBean) {
        return Observable
                .create(subscriber -> {
                    if (!StringUtil.isEmpty(okBean.getValue())) {
                        String result = okBean.getValue().substring(5, 9);
                        if (result.equals("0000")) {
                            subscriber.onNext(new OkStrBean()
                                    .setSocket(okBean.getSocket())
                                    .setValue(okBean.getValue()));
                            subscriber.onComplete();
                        } else if (result.equals("1001")) {
                            subscriber.onNext(new OkStrBean()
                                    .setSocket(okBean.getSocket())
                                    .setValue("1001"));//
                        } else {
                            subscriber.onError(new ApiException(new Throwable("未知错误")));
                        }
                    } else {
                        subscriber.onError(new ApiException(new Throwable("数据为空")));

                    }
                });
    }

    /**
     * 添加RxJava订阅
     */
    protected static void addDisposable(Disposable disposable) {

        mCompositeDisposable.add(disposable);
    }

    /**
     * 取消RxJava订阅
     */
    public static void clearDisposable() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();
        }
    }
}
