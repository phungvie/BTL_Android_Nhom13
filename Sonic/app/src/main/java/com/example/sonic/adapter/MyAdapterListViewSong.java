package com.example.sonic.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.sonic.R;
import com.example.sonic.model.SongDTO;
import com.example.sonic.network.remote.RetrofitClient;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MyAdapterListViewSong extends ArrayAdapter<SongDTO> {
    Context context;
    int layoutResource;
    List<SongDTO> mSongDTOs;


    public MyAdapterListViewSong(@NonNull Context context, int resource, @NonNull List<SongDTO> objects) {
        super(context, resource, objects);
        this.context=context;
        this.layoutResource=resource;
        this.mSongDTOs=objects;

    }



    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        convertView= inflater.inflate(layoutResource,null);

        SongDTO viet= mSongDTOs.get(position);

        ImageView mImageViewSong=convertView.findViewById(R.id.imageViewSong);
        TextView mTextViewName=convertView.findViewById(R.id.textViewNameSong);
        TextView mTextViewArtist=convertView.findViewById(R.id.textViewArtist);



        mTextViewName.setText(viet.getName());
        Picasso.get().load(RetrofitClient.url+viet.getImage()).into(mImageViewSong);
        mTextViewArtist.setText(viet.getArtistName());



        return convertView;
    }


}
