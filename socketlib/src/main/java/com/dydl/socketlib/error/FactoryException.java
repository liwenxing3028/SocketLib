package com.dydl.socketlib.error;

import android.util.Log;

import org.json.JSONException;

import java.io.IOException;
import java.net.ConnectException;
import java.net.JarURLConnection;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import io.reactivex.exceptions.CompositeException;

/**
 * Created by lwx on 2018/4/10.
 */

public class FactoryException {

    private static final String HttpException_MSG = "网络断开，请检查网络...";
    private static final String ConnectException_MSG = "网络断开，请检查网络...";
    private static final String SocketOut_MSG = "Socket连接有误，请检查Ip";

    private static final String JSONException_MSG = "Gson解析失败";
    private static final String UnknownHostException_MSG = "无法解析该域名";

    /**
     * 解析异常
     *
     * @param e
     * @return
     */
    public static ApiException analysisExcetpion(Throwable e) {
        ApiException apiException = new ApiException(e);
        Log.e("121212",e.getMessage() );
        if (e instanceof IOException) {
             /*网络异常*/
            apiException.setCode(CodeException.HTTP_ERROR);
            apiException.setDisplayMessage(HttpException_MSG);
        } else if (e instanceof SocketTimeException) {
             /*自定义运行时异常*/
            SocketTimeException exception = (SocketTimeException) e;
            apiException.setCode(CodeException.RUNTIME_ERROR);
            apiException.setDisplayMessage(exception.getMessage());
        } else if (e instanceof ConnectException) {
             /*连接断开*/
            apiException.setCode(CodeException.HTTP_ERROR);
            apiException.setDisplayMessage(ConnectException_MSG);
        } else if (e instanceof SocketTimeoutException) {
             /*连接异常*/
            apiException.setCode(CodeException.HTTP_ERROR);
            apiException.setDisplayMessage(SocketOut_MSG);
        }
        else if (e instanceof JSONException) {
             /*fastjson解析异常*/
            apiException.setCode(CodeException.JSON_ERROR);
            apiException.setDisplayMessage(JSONException_MSG);
        }else if (e instanceof UnknownHostException){
            /*无法解析该域名异常*/
            apiException.setCode(CodeException.UNKOWNHOST_ERROR);
            apiException.setDisplayMessage(UnknownHostException_MSG);
        }else if (e instanceof CompositeException){
            apiException.setCode(CodeException.UPDATE_ERROR);
            apiException.setDisplayMessage("发送错误，请检查上传数据");
        }else if(e instanceof Throwable){
            apiException.setCode(CodeException.EMPTY_ERROR);
            apiException.setDisplayMessage("返回数据为空");
        } else {/*未知异常*/
            apiException.setCode(CodeException.UNKNOWN_ERROR);
            apiException.setDisplayMessage("未知错误"+"\n"+e.getMessage());
        }
        return apiException;
    }
}
