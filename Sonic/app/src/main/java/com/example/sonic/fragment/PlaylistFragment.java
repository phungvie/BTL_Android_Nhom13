package com.example.sonic.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sonic.R;
import com.example.sonic.adapter.MyAdapterListViewSong;
import com.example.sonic.model.ArtistDTO;
import com.example.sonic.model.ArtistAndPlaylist;
import com.example.sonic.model.Find;
import com.example.sonic.model.PlaylistDTO;
import com.example.sonic.model.SongDTO;
import com.example.sonic.model.VietMessage;
import com.example.sonic.network.remote.APIService;
import com.example.sonic.network.remote.APIServiceToken;
import com.example.sonic.network.remote.RetrofitClient;
import com.example.sonic.network.remote.RetrofitClientToken;
import com.example.sonic.sharedPreferences.DataLocalManager;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class PlaylistFragment extends Fragment {
    private ArtistAndPlaylist mArtistAndPlaylist;
    private View mView;
    public static List<SongDTO> data;
    ImageButton mImageButtonAdd, mImageButtonDownload, mImageButtonMore;
    private APIService mApiService;
    private APIServiceToken mApiServiceToken;

    public PlaylistFragment(ArtistAndPlaylist viet) {
        this.mArtistAndPlaylist = viet;
    }
    private ListView mListView;
    private MyAdapterListViewSong myAdapterListViewSong;

    private Boolean check = false;
    TextView mTextViewUserCreate,textView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_playlist, container, false);
        data = new ArrayList<>();


        mImageButtonAdd = mView.findViewById(R.id.img_but_add);
        mImageButtonDownload = mView.findViewById(R.id.img_but_download);
        mImageButtonMore = mView.findViewById(R.id.img_but_more);

        ImageView mImageView = mView.findViewById(R.id.imageViewPlayList);
        mListView = mView.findViewById(R.id.ListViewPlaylist);



        myAdapterListViewSong = new MyAdapterListViewSong(getActivity(), R.layout.item_song, data);
        mListView.setAdapter(myAdapterListViewSong);

        TextView mTextViewNamePlayList = mView.findViewById(R.id.textViewNamePlaylist);


        Retrofit mRetrofit = RetrofitClient.getClient();
        mApiService = mRetrofit.create(APIService.class);

        Retrofit mRetrofit2 = RetrofitClientToken.getClientToken(null);
        mApiServiceToken = mRetrofit2.create(APIServiceToken.class);

        if (mArtistAndPlaylist.getPlaylistDTO() != null) {

            PlaylistDTO mPlaylistDTO = mArtistAndPlaylist.getPlaylistDTO();
            //
            mTextViewUserCreate=mView.findViewById(R.id.tv_user_create);
            textView=mView.findViewById(R.id.tv);

            mTextViewUserCreate.setVisibility(View.VISIBLE);
            textView.setVisibility(View.VISIBLE);

            String htmlcontent ="<b><u>"+mPlaylistDTO.getNameUserCreate()+"</u></b>";
            mTextViewUserCreate.setText(android.text.Html.fromHtml(htmlcontent));
            //
            Picasso.get().load(RetrofitClient.url + mPlaylistDTO.getImage()).into(mImageView);

            mTextViewNamePlayList.setTextSize(28);
            mTextViewNamePlayList.setText(mPlaylistDTO.getName());


            mApiService.getSongsInPlaylist(mPlaylistDTO.getPlaylistID()).enqueue(new Callback<List<SongDTO>>() {
                @Override
                public void onResponse(Call<List<SongDTO>> call, Response<List<SongDTO>> response) {
                    if (response.isSuccessful()) {
                        List<SongDTO> mSongsDTO = response.body();
                        for (SongDTO songDTO : mSongsDTO) {
                            data.add(songDTO);
                            songDTO.indexOfSong = data.indexOf(songDTO);
                        }
                        myAdapterListViewSong.notifyDataSetChanged();

                    } else {
                        // Xử lý trường hợp lỗi nếu có
                        Log.e("Lỗi playlist 1: ", response.code() + "");
                    }
                }

                @Override
                public void onFailure(Call<List<SongDTO>> call, Throwable t) {
                    Log.e("Lỗi playlist 2: ", t.getMessage());
                }
            });


            mApiServiceToken.checkPlaylistLib(mPlaylistDTO.getPlaylistID()).enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if (response.isSuccessful()) {
                        if (response.body()) {
                            mImageButtonAdd.setImageResource(R.drawable.ic_check_circle);
                            check = true;
                        }
                    } else {
                        // Xử lý trường hợp lỗi nếu có
                        Log.e("Lỗi playlist 1*: ", response.code() + "");
                    }
                }

                @Override
                public void onFailure(Call<Boolean> call, Throwable t) {
                    Log.e("Lỗi playlist 2*: ", t.getMessage());
                }
            });
            mImageButtonAdd.setOnClickListener(v -> {
                addOrDeletePlaylist(mPlaylistDTO);
            });

        } else {
            ArtistDTO mArtistDTO = mArtistAndPlaylist.getArtistDTO();

            Picasso.get().load(RetrofitClient.url + mArtistDTO.getImage()).into(mImageView);

            mTextViewNamePlayList.setText(mArtistDTO.getName());
            mTextViewNamePlayList.setTextSize(35);

            mApiService.getSongsOfArtist(mArtistDTO.getArtistID()).enqueue(new Callback<List<SongDTO>>() {
                @Override
                public void onResponse(Call<List<SongDTO>> call, Response<List<SongDTO>> response) {
                    if (response.isSuccessful()) {
                        List<SongDTO> mSongsDTO = response.body();
                        for (SongDTO songDTO : mSongsDTO) {
                            data.add(songDTO);
                            songDTO.indexOfSong = data.indexOf(songDTO);
                        }
                        myAdapterListViewSong.notifyDataSetChanged();

                    } else {
                        // Xử lý trường hợp lỗi nếu có
                        Log.e("Lỗi playlist 3: ", response.code() + "");
                    }
                }

                @Override
                public void onFailure(Call<List<SongDTO>> call, Throwable t) {
                    Log.e("Lỗi playlist 4: ", t.getMessage());
                }
            });


            mApiServiceToken.checkArtistLib(mArtistDTO.getArtistID()).enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if (response.isSuccessful()) {
                        if (response.body()) {
                            mImageButtonAdd.setImageResource(R.drawable.ic_check_circle);
                            check = true;
                        }
                    } else {
                        // Xử lý trường hợp lỗi nếu có
                        Log.e("Lỗi playlist 3*: ", response.code() + "");
                    }
                }

                @Override
                public void onFailure(Call<Boolean> call, Throwable t) {
                    Log.e("Lỗi playlist 4*: ", t.getMessage());
                }
            });
            mImageButtonAdd.setOnClickListener(v -> {
                addOrDeleteArtist(mArtistDTO);
            });

        }


        mListView.setOnItemClickListener((parent, view, position, id) -> {
            SongDTO songDTO = data.get(position);
            songDTO.setSongID(data.indexOf(songDTO));
            DataLocalManager.setSongDTO(songDTO);
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            SongFragment fragment = new SongFragment();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        });


        return mView;
    }

    private void addOrDeleteArtist(ArtistDTO mArtistDTO) {
        if (check == false) {
            mApiServiceToken.addArtistLib(mArtistDTO.getArtistID()).enqueue(new Callback<VietMessage>() {
                @Override
                public void onResponse(Call<VietMessage> call, Response<VietMessage> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        check = true;
                        mImageButtonAdd.setImageResource(R.drawable.ic_check_circle);
                    } else {
                        // Xử lý trường hợp lỗi nếu có
                        Log.e("Lỗi playlist 3**: ", response.code() + "");
                    }
                }

                @Override
                public void onFailure(Call<VietMessage> call, Throwable t) {
                    Log.e("Lỗi playlist 4**: ", t.getMessage());
                }
            });

        } else {
            mApiServiceToken.deleteArtistLib(mArtistDTO.getArtistID()).enqueue(new Callback<VietMessage>() {
                @Override
                public void onResponse(Call<VietMessage> call, Response<VietMessage> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        check = false;
                        mImageButtonAdd.setImageResource(R.drawable.ic_add_circle);
                    } else {
                        // Xử lý trường hợp lỗi nếu có
                        Log.e("Lỗi playlist 3** *: ", response.code() + "");
                    }
                }

                @Override
                public void onFailure(Call<VietMessage> call, Throwable t) {
                    Log.e("Lỗi playlist 4** *: ", t.getMessage());
                }
            });

        }
    }

    private void addOrDeletePlaylist(PlaylistDTO mPlaylistDTO) {
        if (check == false) {
            mApiServiceToken.addPlaylistLib(mPlaylistDTO.getPlaylistID()).enqueue(new Callback<VietMessage>() {
                @Override
                public void onResponse(Call<VietMessage> call, Response<VietMessage> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        check = true;
                        mImageButtonAdd.setImageResource(R.drawable.ic_check_circle);
                    } else {
                        // Xử lý trường hợp lỗi nếu có
                        Log.e("Lỗi playlist 1**: ", response.code() + "");
                    }
                }

                @Override
                public void onFailure(Call<VietMessage> call, Throwable t) {
                    Log.e("Lỗi playlist 2**: ", t.getMessage());
                }
            });

        } else {
            mApiServiceToken.deletePlaylistLib(mPlaylistDTO.getPlaylistID()).enqueue(new Callback<VietMessage>() {
                @Override
                public void onResponse(Call<VietMessage> call, Response<VietMessage> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        check = false;
                        mImageButtonAdd.setImageResource(R.drawable.ic_add_circle);
                    } else {
                        // Xử lý trường hợp lỗi nếu có
                        Log.e("Lỗi playlist 1** *: ", response.code() + "");
                    }
                }

                @Override
                public void onFailure(Call<VietMessage> call, Throwable t) {
                    Log.e("Lỗi playlist 2** *: ", t.getMessage());
                }
            });

        }
    }
}