package com.example.sonic.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sonic.R;
import com.example.sonic.model.ArtistDTO;
import com.example.sonic.model.ArtistAndPlaylist;
import com.example.sonic.model.PlaylistDTO;
import com.example.sonic.myInterface.IClickItemViet;
import com.example.sonic.network.remote.APIServiceToken;
import com.example.sonic.network.remote.RetrofitClient;
import com.example.sonic.network.remote.RetrofitClientToken;
import com.squareup.picasso.Picasso;

import java.util.List;

public class HomePlaylistAdapter extends RecyclerView.Adapter<HomePlaylistAdapter.HomePlaylistViewHolder> {

    private List<ArtistAndPlaylist> data;
    private IClickItemViet mIClickItemViet;
    public HomePlaylistAdapter(IClickItemViet iClickItemViet){
        mIClickItemViet=iClickItemViet;
    }

    public void setData(List<ArtistAndPlaylist> data){
        this.data=data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HomePlaylistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_playlist_home, parent, false);
        return new HomePlaylistViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomePlaylistViewHolder holder, int position) {
        ArtistAndPlaylist artistAndPlaylist =data.get(position);
        if(artistAndPlaylist ==null){
            return;
        }
       if( artistAndPlaylist.getArtistDTO()!=null){
           ArtistDTO artistDTO= artistAndPlaylist.getArtistDTO();
           holder.textViewCategory.setText("Nghệ sĩ");
           holder.textViewName.setText(artistDTO.getName());
           Picasso.get().load(RetrofitClient.url+artistDTO.getImage()).into(holder.imageView);
       }else{
           PlaylistDTO playlistDTO= artistAndPlaylist.getPlaylistDTO();
           holder.textViewCategory.setText("Danh sách phát");
           holder.textViewName.setText(playlistDTO.getName());
           Picasso.get().load(RetrofitClient.url+playlistDTO.getImage()).into(holder.imageView);
       }
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIClickItemViet.onClickIteam(artistAndPlaylist);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (data != null) {
            return data.size();
        }
        return 0;
    }

    public void setData() {
        APIServiceToken apiServiceToken = RetrofitClientToken.getClientToken(null).create(APIServiceToken.class);
    }

    public class HomePlaylistViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView textViewName;
        private TextView textViewCategory;

        private CardView cardView;


        public HomePlaylistViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.img_view_playlist_home_item);
            textViewName = itemView.findViewById(R.id.text_name_playlist_home_item);
            textViewCategory = itemView.findViewById(R.id.text_category_playlist_home_item);
            cardView=itemView.findViewById(R.id.card_view_layout_item_home);
        }
    }
}
