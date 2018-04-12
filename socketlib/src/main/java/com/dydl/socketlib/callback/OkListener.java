package com.dydl.socketlib.callback;

import com.dydl.socketlib.error.ApiException;

/**
 * Created by lwx on 2018/1/26.
 */

interface OkListener<T> {

    void onStart();//开始执行

    void onError(ApiException e);//失败

    void onSuccess(T result);//成功

}
