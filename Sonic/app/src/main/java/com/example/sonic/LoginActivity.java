package com.example.sonic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.sonic.model.LoginRequest;
import com.example.sonic.model.TokenLogin;
import com.example.sonic.model.UserDTO;
import com.example.sonic.network.remote.APIService;
import com.example.sonic.network.remote.APIServiceToken;
import com.example.sonic.network.remote.RetrofitClient;
import com.example.sonic.network.remote.RetrofitClientToken;
import com.example.sonic.sharedPreferences.DataLocalManager;
import com.squareup.picasso.Picasso;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginActivity extends AppCompatActivity {
    private Button mButtonLogin;
    private EditText mEditTextUsername, mEditTextPassword;
    ImageView mImageView;
    ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mButtonLogin = findViewById(R.id.buttonLogin);
        mEditTextUsername = findViewById(R.id.editTextUsername);
        mEditTextPassword = findViewById(R.id.editTextPassword);
        mImageView = findViewById(R.id.dataImg);
        mProgressBar = findViewById(R.id.ProgressBarLogin);

        if (!DataLocalManager.getFirstIntstalled()) {
            Toast.makeText(this, "Lần mở ứng dụng đầu tiên", Toast.LENGTH_LONG).show();
            DataLocalManager.setFirstIntstalled(true);
        }
        String token = DataLocalManager.getToken();
        if (!token.isEmpty()) {
            Intent mIntent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(mIntent);
        } else {
            mButtonLogin.setOnClickListener(v -> {
                String username = mEditTextUsername.getText().toString().trim();
                String password = mEditTextPassword.getText().toString().trim();
                hideKeyboard(this, v);
                if (!DataLocalManager.getToken().isEmpty()) {
                    Intent mIntent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(mIntent);
                } else {
                    if (!username.isEmpty() && !password.isEmpty()) {
                        loginToken(username, password);
                    } else {
                        Toast.makeText(this, "Phải nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                    }
                }

            });
        }
//        Intent mIntent = new Intent(LoginActivity.this, MainActivity.class);
        //tạo Bundle
//        Bundle mBundle = new Bundle();
        //thêm dữ liệu vào Bundle
//        mBundle.putString("name", mUserDTO.getName());
//        mBundle.putInt("followers", mUserDTO.getFollowers());
//        mBundle.putInt("following", mUserDTO.getFollowing());
        //thêm Bundle vào Intent
//        mIntent.putExtra("mBundle", mBundle);
        //kích hoạt mIntent
//        startActivity(mIntent);


        //chuyển hướng đến uerF


        Picasso.get().load(RetrofitClient.url + "/data/img/anh1.jpg").into(mImageView);
    }

    public void loginToken(String username, String passwword) {
        mProgressBar.setVisibility(View.VISIBLE);
        APIService mAPIService = RetrofitClient.getClient().create(APIService.class);

        mAPIService.loginToken(new LoginRequest(username, passwword))
                .enqueue(new Callback<TokenLogin>() {
                    @Override
                    public void onResponse(Call<TokenLogin> call, Response<TokenLogin> response) {
                        String token;
                        if (response.isSuccessful() && response.body() != null) {
                            TokenLogin tl = response.body();
                            token = "Bearer " + tl.getAccessToken();
                            DataLocalManager.setToken(token);// lưu dữ liệu vào sharedPre
                            attachTokenToHeader(token);

                        } else {
                            // Xử lý trường hợp lỗi nếu có
                            Log.e("Lỗi 1: ", response.code() + "");
                            mProgressBar.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onFailure(Call<TokenLogin> call, Throwable t) {
                        Log.e("Lỗi 2: ", t.getMessage());
                        mProgressBar.setVisibility(View.GONE);
                    }
                });
    }


    private void attachTokenToHeader(String token) {
        //gắn token vào header
        Interceptor mInterceptor = chain -> {
            Request mRequest = chain.request();
            Request.Builder mBuilder = mRequest.newBuilder();
            mBuilder.addHeader("Authorization", token);
            return chain.proceed(mBuilder.build());
        };
        OkHttpClient.Builder mOkBuilder = new OkHttpClient.Builder().addInterceptor(mInterceptor);

        //
        RetrofitClientToken.DestroyRetrofit();
        Retrofit mRetrofit = RetrofitClientToken.getClientToken(mOkBuilder);

        //lấy user
        APIServiceToken mApiServiceAfterLogin = mRetrofit.create(APIServiceToken.class);
        mApiServiceAfterLogin.getUser().enqueue(new Callback<UserDTO>() {
            @Override
            public void onResponse(Call<UserDTO> call, Response<UserDTO> response) {
                if (response.isSuccessful()) {
                    UserDTO mUserDTO = response.body();
                    DataLocalManager.setUserDTO(mUserDTO);
                    mProgressBar.setVisibility(View.GONE);
                } else {
                    // Xử lý trường hợp lỗi nếu có
                    Log.e("Lỗi 3: ", response.code() + "");
                    mProgressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<UserDTO> call, Throwable t) {
                Log.e("Lỗi 4: ", t.getMessage());
                mProgressBar.setVisibility(View.GONE);
            }
        });
    }

    public static void
    hideKeyboard(Context context, View view) {
        if (context == null || view == null) {
            return;
        }
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void exitApp() {
        // Kết thúc tất cả các Activity và thoát khỏi ứng dụng
        finishAffinity();
    }


}