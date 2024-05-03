package com.example.sonic.network.remote;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClientToken {
    private static Retrofit retrofit = null;

    private RetrofitClientToken() {
    }
    public static Retrofit getClientToken(OkHttpClient.Builder mOkBuilder) {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
//                    .baseUrl("http://10.0.2.2:8080")
                    .baseUrl(RetrofitClient.url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(mOkBuilder.build())
                    .build();
        }
        return retrofit;
    }
    public static void DestroyRetrofit(){
        retrofit=null;
    }

}
