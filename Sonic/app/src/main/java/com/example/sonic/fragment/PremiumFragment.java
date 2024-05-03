package com.example.sonic.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.sonic.R;
import com.example.sonic.model.VietMessage;
import com.example.sonic.network.remote.APIServiceToken;
import com.example.sonic.network.remote.RetrofitClientToken;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PremiumFragment extends Fragment {
    private View mvView;
    Button mButton;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mvView=inflater.inflate(R.layout.fragment_premium,container,false);
        mButton=mvView.findViewById(R.id.bt_premuim);

        Retrofit retrofit= RetrofitClientToken.getClientToken(null);
        APIServiceToken apiServiceToken=  retrofit.create(APIServiceToken.class);
        apiServiceToken.checkPremium().enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                TextView textView=mvView.findViewById(R.id.tv_registered_preminum);
                CardView cardView=mvView.findViewById(R.id.cv_prenium);
                if(response.body()){
                    textView.setVisibility(View.VISIBLE);
                    cardView.setVisibility(View.GONE);
                }else{
                    textView.setVisibility(View.GONE);
                    cardView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {

            }
        });
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apiServiceToken.createPayment("49000").enqueue(new Callback<VietMessage>() {
                    @Override
                    public void onResponse(Call<VietMessage> call, Response<VietMessage> response) {
                        if(response.isSuccessful()){
                            VietMessage vietMessage=response.body();
                            String url = vietMessage.getData();

                            if(url!=null){
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                                startActivity(intent);
                            }

                        }else{
                            Log.e("Lỗi preminum 1: ", response.code() + "");
                        }
                    }

                    @Override
                    public void onFailure(Call<VietMessage> call, Throwable t) {
                        Log.e("Lỗi preminum 2: ", t.getMessage() + "");

                    }
                });

            }
        });
        return mvView;
    }
}
