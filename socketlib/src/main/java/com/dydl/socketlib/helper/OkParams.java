package com.dydl.socketlib.helper;

import android.content.Context;
import android.text.TextUtils;

import com.dydl.socketlib.OkSocket;

import org.apache.http.params.HttpParams;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * ━━━━ lwx ━━━━━━
 */
public  class OkParams {

    /**
     * 封装http request params
     */
    private final Map<String, Object> mRequestParams = new LinkedHashMap<>();

    /**
     * 获取request params中某个key对应的value
     *
     * @return 返回某个key对应的value
     */
    public  Object get(String key) {

        return mRequestParams.get(key);
    }


    /**
     * 设置一个key=value的http 参数
     *
     * @param key   参数的key
     * @param value 参数的value
     * @return 返回HttpParams本身，便于链式编程
     */
    public OkParams put(String key, Object value) {
        mRequestParams.put(key, value);
        return this;
    }


    /**
     * 返回一个请求格式的字符串,如:1212|122222|12121|21&21;12&121;12&312;|
     *
     * @return get请求的字符串结构
     */
    public  String toGetParams() {

        StringBuilder buffer = new StringBuilder();
        if (!mRequestParams.isEmpty()) {
            //buffer.append("|");
            for (Map.Entry<String, Object> entry : mRequestParams.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                /*if (TextUtils.isEmpty(key) || TextUtils.isEmpty(value.toString())) {
                    continue;
                }*/
                try {
                    if (value instanceof ArrayList) {
                        for (int i = 0; i < ((ArrayList) value).size(); i++) {//需要重写bean里的toString方法
                            buffer.append(((ArrayList) value).get(i).toString()
                                    /*URLEncoder.encode(((ArrayList) value).get(i).toString(), "UTF-8")*/);
                            buffer.append(";");
                        }
                        buffer.append("|");
                    } else {
                        buffer.append(URLEncoder.encode(value.toString(), "UTF-8"));
                        buffer.append("|");
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }

        }
        String str = buffer.toString();

        return str;
    }




    /**
     * 返回一个请求格式的字符串,如: 包名长度|1212|122222|12121|21&21;12&121;12&312;|
     *
     * @return get请求的字符串结构
     */
    public  String toGetParamsAndLength() {

        StringBuilder buffer = new StringBuilder();
        if (!mRequestParams.isEmpty()) {
            buffer.append("|");
            for (Map.Entry<String, Object> entry : mRequestParams.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                /*if (TextUtils.isEmpty(key) || TextUtils.isEmpty(value.toString())) {
                    continue;
                }*/
                try {
                    if (value instanceof ArrayList) {
                        for (int i = 0; i < ((ArrayList) value).size(); i++) {//需要重写bean里的toString方法
                            buffer.append(((ArrayList) value).get(i).toString()
                                    /*URLEncoder.encode(((ArrayList) value).get(i).toString(), "UTF-8")*/);
                            buffer.append(";");
                        }
                        buffer.append("|");
                    } else {
                        buffer.append(URLEncoder.encode(value.toString(), "UTF-8"));
                        buffer.append("|");
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }

        }
        String str = buffer.toString();

        int size = str.length();
        // 需要处理前面加几个0
        str =buffer.insert(0, String.format("%04d", size)).toString();

        return str;
    }


    /**
     * 返回封装了http request params的Map集合
     *
     * @return
     */
    public Map<String, Object> getParams() {
        return mRequestParams;
    }

    /**
     * 生成一个OkParams 对象，默认包含通用参数信息
     *
     * @return
     */
    public static OkParams generateHttpParams() {
        OkParams params = new OkParams();
        params
                .put("1111", "11111")
                .put("2222", 22222)
                .put("3333", "33333")
                .put("4444", "44444");


        return params;
    }

}
