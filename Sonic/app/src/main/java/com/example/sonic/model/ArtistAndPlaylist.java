package com.example.sonic.model;

public class ArtistAndPlaylist implements Comparable<ArtistAndPlaylist> {
    private final ArtistDTO mArtistDTO;
    private final PlaylistDTO mPlaylistDTO;

    public ArtistAndPlaylist(ArtistDTO mArtistDTO) {
        this.mArtistDTO = mArtistDTO;
        this.mPlaylistDTO = null;
    }

    public ArtistAndPlaylist(PlaylistDTO mPlaylistDTO) {
        this.mArtistDTO = null;
        this.mPlaylistDTO = mPlaylistDTO;
    }

    public ArtistDTO getArtistDTO() {
        return mArtistDTO;
    }

    public PlaylistDTO getPlaylistDTO() {
        return mPlaylistDTO;
    }


    @Override
    public String toString() {
        return "lib{" +
                "mArtistDTO=" + mArtistDTO +
                ", mPlaylistDTO=" + mPlaylistDTO +
                '}';
    }

    @Override
    public int compareTo(ArtistAndPlaylist o) {
        if (
                this.mArtistDTO != null && o.mArtistDTO != null ||
                this.mPlaylistDTO != null && o.mPlaylistDTO != null) {
            return 0;
        } else {
            if (this.mArtistDTO != null) {
                return 1;
            } else {
                return -1;
            }
        }

    }
}
