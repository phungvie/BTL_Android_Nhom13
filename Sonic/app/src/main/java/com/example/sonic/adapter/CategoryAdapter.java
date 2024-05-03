package com.example.sonic.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sonic.R;
import com.example.sonic.model.ArtistAndPlaylist;
import com.example.sonic.model.Category;
import com.example.sonic.myInterface.IClickItemViet;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
    private Context mContext;
    private List<Category> data;
    private IClickItemViet mIClickItemViet;

    public CategoryAdapter(Context context ,IClickItemViet iClickItemViet) {
        this.mContext = context;
        this.mIClickItemViet=iClickItemViet;
    }

    public void setData(List<Category> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category category = data.get(position);
        if (category == null) {
            return;
        }
        holder.mTextViewNameCategory.setText(category.getName());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext, RecyclerView.HORIZONTAL, false);
        holder.mRecyclerView.setLayoutManager(linearLayoutManager);

        HomePlaylistAdapter homePlaylistAdapter = new HomePlaylistAdapter(artistAndPlaylist -> mIClickItemViet.onClickIteam(artistAndPlaylist));
        homePlaylistAdapter.setData(category.getArtistsAndPlaylists());
        holder.mRecyclerView.setAdapter(homePlaylistAdapter);
    }

    @Override
    public int getItemCount() {
        if (data != null) {
            return data.size();
        }
        return 0;
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {
        private TextView mTextViewNameCategory;
        private RecyclerView mRecyclerView;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextViewNameCategory = itemView.findViewById(R.id.tv_category_name);
            mRecyclerView = itemView.findViewById(R.id.rcw_home_playlist);


        }
    }
}
