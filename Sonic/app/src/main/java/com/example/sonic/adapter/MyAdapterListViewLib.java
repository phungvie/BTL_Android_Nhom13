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
import androidx.cardview.widget.CardView;

import com.example.sonic.R;
import com.example.sonic.model.ArtistAndPlaylist;
import com.example.sonic.network.remote.RetrofitClient;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MyAdapterListViewLib extends ArrayAdapter<ArtistAndPlaylist> {
    Context context;
    int layoutResource;
    List<ArtistAndPlaylist> mArtistAndPlaylists;


    public MyAdapterListViewLib(@NonNull Context context, int resource, @NonNull List<ArtistAndPlaylist> objects) {
        super(context, resource, objects);
        this.context=context;
        this.layoutResource=resource;
        this.mArtistAndPlaylists =objects;

    }



    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        convertView= inflater.inflate(layoutResource,null);

        ArtistAndPlaylist viet= mArtistAndPlaylists.get(position);

        if(viet.getPlaylistDTO()!=null){
            ImageView mImageViewLib=convertView.findViewById(R.id.imageViewLib);
            TextView mTextViewName=convertView.findViewById(R.id.textViewNameLib);
            TextView mTextViewGen=convertView.findViewById(R.id.textViewGenLib);
            CardView mCardView=convertView.findViewById(R.id.cardViewLib);
            mTextViewName.setText(viet.getPlaylistDTO().getName());
            mTextViewGen.setText("Danh sách phát");
            mCardView.setRadius(30);
            Picasso.get().load(RetrofitClient.url+viet.getPlaylistDTO().getImage()).into(mImageViewLib);


        }else{
            ImageView mImageViewLib=convertView.findViewById(R.id.imageViewLib);
            TextView mTextViewName=convertView.findViewById(R.id.textViewNameLib);
            TextView mTextViewGen=convertView.findViewById(R.id.textViewGenLib);
            mTextViewName.setText(viet.getArtistDTO().getName());
            mTextViewGen.setText("Nghệ sĩ");

            Picasso.get().load(RetrofitClient.url+viet.getArtistDTO().getImage()).into(mImageViewLib);
//            Toast.makeText(context, RetrofitClient.url+viet.getArtistDTO().getImage(), Toast.LENGTH_SHORT).show();
//            Picasso.get().load("https://hoanghamobile.com/tin-tuc/wp-content/uploads/2023/10/anh-dai-dien-zalo-1.jpg").into(mImageViewLib);
        }





        return convertView;
    }


}
