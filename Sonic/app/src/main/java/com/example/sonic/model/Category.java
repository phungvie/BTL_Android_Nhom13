package com.example.sonic.model;

import java.util.List;

public class Category {
    private String name;
    private List<ArtistAndPlaylist> artistsAndPlaylists;

    public Category(String name, List<ArtistAndPlaylist> artistsAndPlaylists)  {
        this.name = name;
        this.artistsAndPlaylists = artistsAndPlaylists;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ArtistAndPlaylist> getArtistsAndPlaylists() {
        return artistsAndPlaylists;
    }

    public void setArtistsAndPlaylists(List<ArtistAndPlaylist> artistsAndPlaylists) {
        this.artistsAndPlaylists = artistsAndPlaylists;
    }

    @Override
    public String toString() {
        return "Category{" +
                "name='" + name + '\'' +
                ", artistsAndPlaylists=" + artistsAndPlaylists +
                '}';
    }
}
