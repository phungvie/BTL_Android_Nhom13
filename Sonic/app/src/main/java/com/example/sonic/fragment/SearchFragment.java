package com.example.sonic.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.os.UserManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sonic.R;
import com.example.sonic.adapter.FindAdapter;
import com.example.sonic.model.ArtistAndPlaylist;
import com.example.sonic.model.Find;
import com.example.sonic.myInterface.IClickItemSearch;
import com.example.sonic.myInterface.IToggle;
import com.example.sonic.network.remote.APIService;
import com.example.sonic.network.remote.RetrofitClient;
import com.example.sonic.sharedPreferences.DataLocalManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchFragment extends Fragment {
    private View mView;
    private RecyclerView mRecyclerView;
    private FindAdapter mFindAdapter;
    private SearchView searchView;
    IToggle mIToggle;
    private AppCompatActivity activity;

    public void setmIToggle(IToggle toggle) {
        this.mIToggle = toggle;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_search, container, false);
        mRecyclerView = mView.findViewById(R.id.rv_search);
        searchView = mView.findViewById(R.id.sv_search);

        activity= (AppCompatActivity) getActivity();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(linearLayoutManager);

        mFindAdapter = new FindAdapter();
        mFindAdapter.setiClickItemSearch(find -> {
            activity.findViewById(R.id.view_pager_2).setVisibility(View.GONE);
            activity.findViewById(R.id.fragment_container).setVisibility(View.VISIBLE);
            activity.getSupportActionBar().setDisplayShowTitleEnabled(false);
            //
            if (find.getSongDTO() != null) {
                FragmentManager fragmentManager = activity.getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                DataLocalManager.setSongDTO(find.getSongDTO());
                SongFragment fragment = new SongFragment();
                fragmentTransaction.replace(R.id.fragment_container, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                mIToggle.TurnOnTheBackButton();
            } else {
                if (find.getPlaylistDTO() != null) {
                    FragmentManager fragmentManager = activity.getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    ArtistAndPlaylist artistAndPlaylist = new ArtistAndPlaylist(find.getPlaylistDTO());
                    PlaylistFragment fragment = new PlaylistFragment(artistAndPlaylist);
                    fragmentTransaction.replace(R.id.fragment_container, fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                    mIToggle.TurnOnTheBackButton();
                } else {
                    FragmentManager fragmentManager = activity.getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    ArtistAndPlaylist artistAndPlaylist = new ArtistAndPlaylist(find.getArtistDTO());
                    PlaylistFragment fragment = new PlaylistFragment(artistAndPlaylist);
                    fragmentTransaction.replace(R.id.fragment_container, fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                    mIToggle.TurnOnTheBackButton();
                }
            }
        });
        mRecyclerView.setAdapter(mFindAdapter);

        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(itemDecoration);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (query.isEmpty()) {
                    mFindAdapter.setData(null);
                } else {
                    APIService apiService = RetrofitClient.getClient().create(APIService.class);
                    apiService.findByKeyword(query).enqueue(new Callback<List<Find>>() {
                        @Override
                        public void onResponse(Call<List<Find>> call, Response<List<Find>> response) {
                            if (response.isSuccessful()) {
                                mFindAdapter.setData(response.body());
                            } else {
                                Log.e("lỗi seach 1:", response.code() + "");
                            }
                        }

                        @Override
                        public void onFailure(Call<List<Find>> call, Throwable t) {
                            Log.e("lỗi seach 1:", t.getMessage());
                        }
                    });
                }
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.isEmpty()) {
                    mFindAdapter.setData(null);
                } else {
                    APIService apiService = RetrofitClient.getClient().create(APIService.class);
                    apiService.findByKeyword(newText).enqueue(new Callback<List<Find>>() {
                        @Override
                        public void onResponse(Call<List<Find>> call, Response<List<Find>> response) {
                            if (response.isSuccessful()) {
                                mFindAdapter.setData(response.body());
                            } else {
                                Log.e("lỗi seach 1:", response.code() + "");
                            }
                        }

                        @Override
                        public void onFailure(Call<List<Find>> call, Throwable t) {
                            Log.e("lỗi seach 1:", t.getMessage());
                        }
                    });
                }
                return false;
            }
        });


        return mView;
    }
}
