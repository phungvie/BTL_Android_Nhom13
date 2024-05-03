package com.example.sonic;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

import com.example.sonic.network.remote.RetrofitClientToken;
import com.example.sonic.sharedPreferences.DataLocalManager;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;

public class MyApplication extends Application {
    public static final String CHANNEL_ID = "channel_service_example";

    @Override
    public void onCreate() {
        super.onCreate();
        DataLocalManager.getInstance(getApplicationContext());

        //
        String token = DataLocalManager.getToken();
        if (!token.isEmpty()) {
            Interceptor mInterceptor = chain -> {
                Request mRequest = chain.request();
                Request.Builder mBuilder = mRequest.newBuilder();
                mBuilder.addHeader("Authorization", token);
                return chain.proceed(mBuilder.build());
            };
            OkHttpClient.Builder mOkBuilder = new OkHttpClient.Builder().addInterceptor(mInterceptor);

            //
            Retrofit mRetrofit = RetrofitClientToken.getClientToken(mOkBuilder);
        }

        createChannelNotification();
    }

    private void createChannelNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    "channel service example ",
                    NotificationManager.IMPORTANCE_DEFAULT);
            channel.setSound(null,null);
            NotificationManager manager = getSystemService(NotificationManager.class);
            if (manager != null) {
                manager.createNotificationChannel(channel);
            }
        }
    }
}
