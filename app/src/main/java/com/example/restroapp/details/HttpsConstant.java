package com.example.restroapp.details;


import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpsConstant {

    public static String sendHTTPData(String urlPath, JSONObject json) throws Exception {
        String TAG = "dinesh";
        MediaType JSON
                = MediaType.parse("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(180, TimeUnit.SECONDS)
                .writeTimeout(180, TimeUnit.SECONDS)
                .readTimeout(180, TimeUnit.SECONDS)
                .build();

        RequestBody body = RequestBody.create(JSON, json.toString());
        Request request = new Request.Builder()
                .url(urlPath)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }
    public static String sendHTTPDataWithoutReq(String urlPath) throws Exception {
        String TAG = "dinesh";
        MediaType JSON
                = MediaType.parse("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(180, TimeUnit.SECONDS)
                .writeTimeout(180, TimeUnit.SECONDS)
                .readTimeout(180, TimeUnit.SECONDS)
                .build();

        Request request = new Request.Builder()
                .url(urlPath)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }
}
