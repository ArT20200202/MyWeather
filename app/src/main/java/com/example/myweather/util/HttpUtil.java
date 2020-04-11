package com.example.myweather.util;

import android.view.textclassifier.TextLinks;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.internal.tls.OkHostnameVerifier;

public class HttpUtil {
    /**
     * okHttp请求web服务器数据，json格式
     * @param address
     * @param callback
     */
    public static void sendOkHttpRequest(String address, okhttp3.Callback callback) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(address)
                .build();
        client.newCall(request).enqueue(callback);
    }

}
