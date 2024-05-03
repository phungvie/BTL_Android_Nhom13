package com.example.sonic.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.sonic.R;
import com.example.sonic.service.MusicService;
import com.example.sonic.sharedPreferences.DataLocalManager;


public class SongFragment extends Fragment {

    private View mView;
    private AppCompatActivity activity;
    public SongFragment(){

    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_song, container, false);
        activity = (AppCompatActivity) getActivity();


        ImageButton mImageButtonPlayOrPause=mView.findViewById(R.id.img_play_or_pause_song);
        ImageButton mImageButtonSkipNextSong=mView.findViewById(R.id.img_skip_next_song);
        ImageButton mImageButtonSkipPreviousSong=mView.findViewById(R.id.img_skip_previous_song);

        clickStopService();
        clickStartService();
        mImageButtonPlayOrPause.setOnClickListener(v -> clickStopService());


        mImageButtonSkipPreviousSong.setOnClickListener(v -> {

            int i=DataLocalManager.getSongDTO().indexOfSong;
            int j=i-1;
            Toast.makeText(activity, i+" "+j, Toast.LENGTH_SHORT).show();

            if (j >= 0 && j < PlaylistFragment.data.size()) {
                clickStopService();
                DataLocalManager.setSongDTO(PlaylistFragment.data.get(j));
                clickStartService();
            }

        });

        mImageButtonSkipNextSong.setOnClickListener(v -> {

            int i=DataLocalManager.getSongDTO().indexOfSong;
            int j=i+1;
            Toast.makeText(activity, i+" "+j, Toast.LENGTH_SHORT).show();

            if (j >= 0 && j < PlaylistFragment.data.size() ) {
                clickStopService();
                DataLocalManager.setSongDTO(PlaylistFragment.data.get(j));
                clickStartService();
            }

            });

        return mView;
    }

    private void clickStartService() {
        Intent intent = new Intent(getActivity(), MusicService.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("ob_song", DataLocalManager.getSongDTO());
        intent.putExtras(bundle);
        activity.startService(intent);
    }

    private void clickStopService() {
        Intent intent = new Intent(getActivity(), MusicService.class);
        activity.stopService(intent);
    }


}