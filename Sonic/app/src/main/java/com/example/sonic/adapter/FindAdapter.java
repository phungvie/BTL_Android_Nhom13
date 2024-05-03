package com.example.sonic.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sonic.R;
import com.example.sonic.model.ArtistDTO;
import com.example.sonic.model.Find;
import com.example.sonic.model.PlaylistDTO;
import com.example.sonic.model.SongDTO;
import com.example.sonic.myInterface.IClickItemSearch;
import com.example.sonic.network.remote.RetrofitClient;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.http.Path;

public class FindAdapter extends RecyclerView.Adapter<FindAdapter.FindViewHolder> {
    private List<Find> data;
    private IClickItemSearch mIClickItemSearch;
 ;


    public FindAdapter() {
    }
    public void setiClickItemSearch(IClickItemSearch iClickItemSearch) {
        this.mIClickItemSearch = iClickItemSearch;
    }
    public void setData(List<Find> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FindViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lib,parent,false);
        return new FindViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FindViewHolder holder, int position) {
        Find find=data.get(position);
        if(find==null){
            return;
        }
       if(find.getArtistDTO()!=null){
           ArtistDTO artistDTO=find.getArtistDTO();
           holder.textViewName.setText(artistDTO.getName());
           holder.textViewGen.setText("Nghệ sĩ");
           holder.cardView.setRadius(110);
           Picasso.get().load(RetrofitClient.url+artistDTO.getImage()).into( holder.imageView);
          holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  mIClickItemSearch.onClickIteamSearch(find);
              }
          });
       }else{
           if(find.getPlaylistDTO()!=null){
               PlaylistDTO playlistDTO=find.getPlaylistDTO();
               holder.textViewName.setText(playlistDTO.getName());
               holder.textViewGen.setText("Danh sách phát");
               Picasso.get().load(RetrofitClient.url+playlistDTO.getImage()).into( holder.imageView);
               holder.cardView.setRadius(30);
               holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       mIClickItemSearch.onClickIteamSearch(find);
                   }
               });
           }else {
               SongDTO songDTO=find.getSongDTO();
               holder.textViewName.setText(songDTO.getName());
               holder.textViewGen.setText("Bài hát");
               Picasso.get().load(RetrofitClient.url+songDTO.getImage()).into( holder.imageView);
               holder.cardView.setRadius(0);
               holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       mIClickItemSearch.onClickIteamSearch(find);
                   }
               });
           }
       }
    }

    @Override
    public int getItemCount() {
       if(data!=null){
           return data.size();
       }
       return 0;
    }

    public class FindViewHolder extends RecyclerView.ViewHolder {

        TextView textViewName,textViewGen;
        ImageView imageView;
        CardView cardView;
        RelativeLayout relativeLayout;

        public FindViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName=itemView.findViewById(R.id.textViewNameLib);
            textViewGen=itemView.findViewById(R.id.textViewGenLib);
            imageView=itemView.findViewById(R.id.imageViewLib);
            cardView=itemView.findViewById(R.id.cardViewLib);
            relativeLayout=itemView.findViewById(R.id.rl_lib);
        }
    }
}
