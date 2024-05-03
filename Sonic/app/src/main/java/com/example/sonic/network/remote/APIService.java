package com.example.sonic.network.remote;

import com.example.sonic.model.Find;
import com.example.sonic.model.LoginRequest;
import com.example.sonic.model.SongDTO;
import com.example.sonic.model.TokenLogin;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIService {
    @POST("/security/login")
    Call<TokenLogin> loginToken(@Body LoginRequest loginRequest);

    @GET("/sonic/playlist/{id}/songs")
    Call<List<SongDTO>> getSongsInPlaylist(@Path("id") Integer id);

    @GET("/sonic/artist/{id}/songs")
    Call<List<SongDTO>> getSongsOfArtist(@Path("id") Integer id);


    @GET("/sonic/findByKeyword")
    Call<List<Find>> findByKeyword(@Query("keyword")String keyword);
}
