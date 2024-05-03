package com.example.sonic.model;

import com.google.gson.annotations.Expose;

public class PlaylistDTO {
    @Expose
    private Integer playlistID;
    @Expose
    private String name;
    @Expose
    private String image;
    @Expose
    private String nameUserCreate;
    @Expose
    private Integer IdUserCreate;

    public PlaylistDTO(Integer playlistID, String name, String image) {
        this.playlistID = playlistID;
        this.name = name;
        this.image = image;
    }

    public PlaylistDTO() {
    }

    public Integer getPlaylistID() {
        return playlistID;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public void setPlaylistID(Integer playlistID) {
        this.playlistID = playlistID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getNameUserCreate() {
        return nameUserCreate;
    }

    public void setNameUserCreate(String nameUserCreate) {
        this.nameUserCreate = nameUserCreate;
    }

    public Integer getIdUserCreate() {
        return IdUserCreate;
    }

    public void setIdUserCreate(Integer idUserCreate) {
        IdUserCreate = idUserCreate;
    }

    @Override
    public String toString() {
        return "PlaylistDTO{" +
                "playlistID=" + playlistID +
                ", name='" + name + '\'' +
                ", image='" + image + '\'' +
                ", nameUserCreate='" + nameUserCreate + '\'' +
                ", IdUserCreate=" + IdUserCreate +
                '}';
    }
}
