package com.example.sonic.network.remote;

import com.example.sonic.model.ArtistDTO;
import com.example.sonic.model.PlaylistDTO;
import com.example.sonic.model.UserDTO;
import com.example.sonic.model.VietMessage;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIServiceToken {
    @GET("/security/getUser")
//    @Headers("Authorization:Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ2aWV0QGdtYWlsLmNvbSIsImlhdCI6MTcwODA5MzM0MCwiZXhwIjoxNzA4Njk4MTQwfQ.1kEgpaIkyIwYWk_M0QEFXBP2kiGZ3BNZAx8pACmKUiS2gTJibWkN8e9Qf5zZOAbMTedGhDgKj5OUN8rtB23VqQ")
    Call<UserDTO> getUser();

    @GET("/sonic/lib/artists")
    Call<List<ArtistDTO>> getArtistDto();

    @GET("/sonic/lib/playlists")
    Call<List<PlaylistDTO>> getPlaylistDto();

    @POST("/sonic/lib/addArtist/{id}")
    Call<VietMessage> addArtistLib(@Path("id") Integer id);

    @POST("/sonic/lib/addPlaylist/{id}")
    Call<VietMessage> addPlaylistLib(@Path("id") Integer id);

    @DELETE("/sonic/lib/deletePlaylist/{id}")
    Call<VietMessage> deletePlaylistLib(@Path("id") Integer id);

    @DELETE("/sonic/lib/deleteArtist/{id}")
    Call<VietMessage> deleteArtistLib(@Path("id") Integer id);

    @GET("/sonic/lib/checkPlaylist/{id}")
    Call<Boolean> checkPlaylistLib(@Path("id") Integer id);
    @GET("/sonic/lib/checkArtist/{id}")
    Call<Boolean> checkArtistLib(@Path("id") Integer id);

    //lấy tất cả danh sách các danh sách phát
    @GET("/sonic/user/playlists")
    Call<List<PlaylistDTO>> getAllPlaylists() ;

    //lấy tất cả danh sách các nghệ sĩ
    @GET("/sonic/user/artists")
    Call<List<ArtistDTO>> getAllArtists();

    @GET("/vnpay/create_payment")
    Call<VietMessage> createPayment(@Query("amount")String amount);
    @GET("/sonic/checkPremium")
    Call<Boolean> checkPremium();


}
